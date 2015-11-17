package me.ferrybig.javacoding.webmapper.netty;

import me.ferrybig.javacoding.webmapper.Listener;
import me.ferrybig.javacoding.webmapper.Server;
import me.ferrybig.javacoding.webmapper.requests.RequestMapper;
import me.ferrybig.javacoding.webmapper.session.SessionManager;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 */
public class WebServerInitializer extends ChannelInitializer<SocketChannel> {

	protected final Listener listener;
	private final Server server;
	protected final SessionManager sessions;
	protected final RequestMapper mapper;

	public WebServerInitializer(Server server, SessionManager sessions, RequestMapper mapper, Listener listener) {
		this.server = server;
		this.sessions = sessions;
		this.mapper = mapper;
		this.listener = listener;
	}

	@Override
	public void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		
		// Decode HTTP request
		pipeline.addLast("http-codec", new HttpServerCodec());
		// Support up to 8K of incoming data and handle 100-CONTINUE:
		pipeline.addLast("dechunker", new HttpObjectAggregator(8 * 1024));
		// Compress data for less data usage
		pipeline.addLast("deflater", new HttpContentCompressor());
		// Handle our frames
		pipeline.addLast("main", new WebServerHandler(server, sessions, mapper, listener));
	}
}
