package me.ferrybig.javacoding.webmapper.netty;

import me.ferrybig.javacoding.webmapper.Listener;
import me.ferrybig.javacoding.webmapper.requests.RequestMapper;
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
	private final RequestMapper mapper;

	/**
	 *
	 * @param sslCtx
	 * @param mapper
	 * @param listener
	 */
	public WebSocketServerInitializer(SslContext sslCtx, RequestMapper mapper, Listener listener) {
		this.sslCtx = sslCtx;
		this.mapper = mapper;
		this.listener = listener;
	}

	@Override
	public void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		if (sslCtx != null) {
			pipeline.addLast(sslCtx.newHandler(ch.alloc()));
		}
		pipeline.addLast(new HttpServerCodec());
		pipeline.addLast(new HttpObjectAggregator(65536));
		pipeline.addLast(new WebSocketServerHandler(mapper, listener));
	}
}
