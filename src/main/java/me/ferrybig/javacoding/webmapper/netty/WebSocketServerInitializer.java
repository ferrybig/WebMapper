package me.ferrybig.javacoding.webmapper.netty;

import me.ferrybig.javacoding.webmapper.Listener;
import me.ferrybig.javacoding.webmapper.requests.RequestMapper;
import me.ferrybig.javacoding.webmapper.session.SessionManager;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContext;

/**
 */
public class WebSocketServerInitializer extends ChannelInitializer<SocketChannel> {

	private final Listener listener;
	private final SslContext sslCtx;
	private final SessionManager sessions;
	private final RequestMapper mapper;

	/**
	 *
	 * @param sslCtx
	 * @param sessions
	 * @param mapper
	 * @param listener
	 */
	public WebSocketServerInitializer(SslContext sslCtx, SessionManager sessions, RequestMapper mapper, Listener listener) {
		this.sslCtx = sslCtx;
		this.sessions = sessions;
		this.mapper = mapper;
		this.listener = listener;
	}

	@Override
	public void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		if (sslCtx != null) {
			// Handle ssl
			pipeline.addLast(sslCtx.newHandler(ch.alloc()));
		}
		// Decode HTTP request
		pipeline.addLast(new HttpServerCodec());
		// Support up to 8K of incoming data and handle 100-CONTINUE:
		pipeline.addLast(new HttpObjectAggregator(8 * 1024));
		// Handle our frames
		pipeline.addLast(new WebSocketServerHandler(sessions, mapper, listener));
	}
}
