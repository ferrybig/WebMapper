/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.netty;

import me.ferrybig.javacoding.webmapper.EndpointResult;
import me.ferrybig.javacoding.webmapper.EndpointResult.ContentType;
import me.ferrybig.javacoding.webmapper.EndpointResult.Result;
import me.ferrybig.javacoding.webmapper.Listener;
import me.ferrybig.javacoding.webmapper.requests.RequestMapper;
import me.ferrybig.javacoding.webmapper.session.Session;
import me.ferrybig.javacoding.webmapper.session.SessionManager;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;

import static io.netty.handler.codec.http.HttpHeaderNames.*;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpMessage;
import static io.netty.handler.codec.http.HttpMethod.*;
import io.netty.handler.codec.http.HttpResponseStatus;
import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.*;
import static java.net.URLEncoder.encode;
import io.netty.handler.codec.http.QueryStringDecoder;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Handles handshakes and messages
 */
public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {

	private static final String WEBSOCKET_PATH = "/service";
	private static final Optional<Charset> DEFAULT_CHARSET = Optional.of(Charset.forName("UTF-8"));
	private static final Logger LOGGER = Logger.getLogger(WebSocketServerHandler.class.getName());
	
	private final Listener listener;
	private final SessionManager sessions;
	private final RequestMapper mapper;
	
	private Session mySession;
	private WebSocketServerHandshaker handshaker;

	public WebSocketServerHandler(SessionManager sessions, RequestMapper mapper, Listener listener) {
		this.sessions = sessions;
		this.mapper = mapper;
		this.listener = listener;
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}

	private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
		// Handle a bad request.
		if (!req.decoderResult().isSuccess()) {
			sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, BAD_REQUEST));
			return;
		}

		if (WEBSOCKET_PATH.equals(req.uri())) {
			// Handshake
			WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
					null, null, true);
			handshaker = wsFactory.newHandshaker(req);
			if (handshaker == null) {
				WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
			} else {
				handshaker.handshake(ctx.channel(), req);
				mySession = sessions.createNewSession();
			}
		} else {
			if (req.method() == POST || req.method() == GET) {
				String url = req.uri();
				if (url.startsWith("/")) {
					url = url.substring(1);
				}
				String host = req.headers().get(HOST);
				if(host.indexOf(':')> -1) {
					host = host.substring(0, host.indexOf(':'));
				}
				String cookie = req.headers().get(HttpHeaderNames.COOKIE, "");
				Optional<String> sessionId = Optional.empty();
				for (String cookiePart : cookie.split(";")) {
					cookiePart = cookiePart.trim();
					String[] split = cookiePart.split("=");
					if (split.length != 2) {
						continue;
					}
					if (split[0].trim().equals("SESSION")) {
						sessionId = Optional.of(split[1].trim());
						break;
					}
				}
				Session ses = sessions.findOrCreateSession(sessionId);
				EndpointResult<?> res = this.mapper.handleHttpRequest(ctx, url, ses, 
						decodeRequest(Optional.ofNullable(req.headers().get(CONTENT_TYPE)), req.content()));

				ByteBuf content = Unpooled.wrappedBuffer(res.asBytes(DEFAULT_CHARSET));
				HttpResponseStatus status;
				switch (res.getResult()) {
					case AUTH_REQUIRED:
						status = FORBIDDEN;
						break;
					case CONFLICT:
						status = CONFLICT;
						break;
					case NO_PERMISSIONS:
						status = FORBIDDEN;
						break;
					case OK:
						status = OK;
						break;
					case SYNTAX_ERROR:
						status = BAD_REQUEST;
						break;
					case UNKNOWN_ENDPOINT:
						status = NOT_FOUND;
						break;
					case SERVER_ERROR:
					default:
						status = INTERNAL_SERVER_ERROR;
						break;
				}

				FullHttpResponse res1 = new DefaultFullHttpResponse(HTTP_1_1, status, content);
				try {
					res1.headers().add(SET_COOKIE,
							"SESSION=" + encode(ses.getKey(), "UTF-8") + "; "
							+ "domain=" + encode(host, "UTF-8") + "; "
							+ "HttpOnly");
				} catch (UnsupportedEncodingException ex) {
					throw new AssertionError("Charset UTF-8 tested in DefaultTest");
				}
				if (res.getContentType() == EndpointResult.ContentType.HTML) {
					res1.headers().set(CONTENT_TYPE, "text/html; charset=UTF-8");
				} else if (res.getContentType() == EndpointResult.ContentType.TEXT) {
					res1.headers().set(CONTENT_TYPE, "text/plain; charset=UTF-8");
				} else if (res.getContentType() == EndpointResult.ContentType.JSON) {
					res1.headers().set(CONTENT_TYPE, "application/json; charset=UTF-8");
				} else if (res.getContentType() == EndpointResult.ContentType.CSS) {
					res1.headers().set(CONTENT_TYPE, "text/css; charset=UTF-8");
				} else if (res.getContentType() == EndpointResult.ContentType.JAVASCRIPT) {
					res1.headers().set(CONTENT_TYPE, "text/javascript; charset=UTF-8");
				} else if (res.getContentType() == EndpointResult.ContentType.PNG) {
					res1.headers().set(CONTENT_TYPE, "image/png");
				} else if (res.getContentType() == EndpointResult.ContentType.JPEG) {
					res1.headers().set(CONTENT_TYPE, "image/jpeg");
				} else {

				}

				res1.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

				sendHttpResponse(ctx, req, res1);
			} else {
				sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, NOT_ACCEPTABLE));
			}

		}

	}

	private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {

		// Check for closing frame
		if (frame instanceof CloseWebSocketFrame) {
			handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
			return;
		}
		if (frame instanceof PingWebSocketFrame) {
			ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
			return;
		}
		if (!(frame instanceof TextWebSocketFrame)) {
			throw new UnsupportedOperationException(String.format("%s frame types not supported", frame.getClass()
					.getName()));
		}

		String request = ((TextWebSocketFrame) frame).text();
		JSONObject json;
		try {
			json = new JSONObject(request);
		} catch (JSONException ex) {
			ctx.channel().writeAndFlush(new TextWebSocketFrame("{\"error\":\"BAD_JSON\"}"))
					.addListener(ChannelFutureListener.CLOSE);
			return;
		}
		String endpoint = json.optString("endpoint", null);
		if(endpoint == null) {
			ctx.channel().writeAndFlush(new TextWebSocketFrame("{\"error\":\"MISSING_ENDPOINT\"}"));
			return;
		}
		String reqid = json.optString("reqid", json.optInt("reqid", 0) + "");
		EndpointResult<?> res = 
				mapper.handleHttpRequest(ctx, endpoint, mySession, Optional.ofNullable(json.optJSONObject("data")));
		JSONObject jsonRes;
		if(res.getContentType() != ContentType.JSON) {
			jsonRes = new JSONObject();
			if(res.getResult() != Result.OK) {
				jsonRes.put("error", res.getResult().name());
			} else {
				jsonRes.put("error", "BAD_RETURN_DATA");
			}
			jsonRes.put("status", "BAD_RETURN_DATA");
			jsonRes.put("reqid", reqid);
			jsonRes.put("reqid-connection", "close");
			jsonRes.put("data", new JSONObject());
		} else {
			EndpointResult<JSONObject> obj = (EndpointResult<JSONObject>)res;
			jsonRes = new JSONObject();
			if(res.getResult() != Result.OK) {
				jsonRes.put("error", res.getResult().name());
			}
			jsonRes.put("status", res.getResult().name());
			jsonRes.put("reqid", reqid);
			jsonRes.put("reqid-connection", "close");
			jsonRes.put("data", obj.getData());
		}
		ctx.channel().writeAndFlush(new TextWebSocketFrame(jsonRes.toString()));
	}

	private static void sendHttpResponse(
			ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse res) {
		ChannelFuture f = ctx.channel().writeAndFlush(res);
		if (!isKeepAlive(req) || res.status().code() != 200) {
			f.addListener(ChannelFutureListener.CLOSE);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		LOGGER.log(Level.SEVERE, "Exception caught: ", cause);
		ctx.close();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof FullHttpRequest) {
			handleHttpRequest(ctx, (FullHttpRequest) msg);
		} else if (msg instanceof WebSocketFrame) {
			handleWebSocketFrame(ctx, (WebSocketFrame) msg);
		}
	}

	public static boolean isKeepAlive(HttpMessage message) {
		CharSequence connection = message.headers().get(HttpHeaderNames.CONNECTION);
		if (connection != null && HttpHeaderValues.CLOSE.equalsIgnoreCase(connection)) {
			return false;
		}

		if (message.protocolVersion().isKeepAliveDefault()) {
			return !HttpHeaderValues.CLOSE.equalsIgnoreCase(connection);
		} else {
			return HttpHeaderValues.KEEP_ALIVE.equalsIgnoreCase(connection);
		}
	}

	public Optional<?> decodeRequest(Optional<String> contentTypeHeader, ByteBuf post) {
		try {
			if (!contentTypeHeader.isPresent()) {

				return Optional.empty();
			}
			int responseSize = post.readableBytes();
			if (responseSize > 1 * 1024 * 1024) {
				return Optional.empty();
			}
			String[] header = contentTypeHeader.get().split(";");
			String type = header[0].trim();
			String charset = "UFT-8";
			for (int i = 1; i < header.length; i++) {
				String[] split = header[i].split("=");
				if (split.length != 2) {
					continue;
				}
				split[0] = split[0].trim();
				split[1] = split[1].trim();
				if (split[0].equals("charset")) {
					charset = split[1];
				}
			}
			charset = charset.toUpperCase();
			Charset set = null;
			for (Map.Entry<String, Charset> key : Charset.availableCharsets().entrySet()) {
				if (charset.equals(key.getKey().toUpperCase())) {
					set = key.getValue();
					break;
				}
			}
			if (set == null) {
				return Optional.empty();
			}
			switch (type.toLowerCase()) {
				case "application/json": {
					String input = post.toString(set);
					try {
						return Optional.of(new JSONObject(input));
					} catch (JSONException ex) {
						return Optional.empty();
					}
				}
				case "application/x-www-form-urlencoded": {
					String input = post.toString(set);
					return Optional.of(new QueryStringDecoder(input));
				}
				default:
					return Optional.empty();
			}
		} finally {
			// Consume bytes remaining...
			post.skipBytes(post.readableBytes());
		}
	}
}
