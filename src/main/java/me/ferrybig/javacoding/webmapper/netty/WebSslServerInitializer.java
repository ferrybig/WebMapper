/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.netty;

import me.ferrybig.javacoding.webmapper.Listener;
import me.ferrybig.javacoding.webmapper.Server;
import me.ferrybig.javacoding.webmapper.requests.RequestMapper;
import me.ferrybig.javacoding.webmapper.session.SessionManager;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslContext;
import java.util.Objects;

public class WebSslServerInitializer extends WebServerInitializer {

	private final SslContext sslCtx;
	
	public WebSslServerInitializer(SslContext sslCtx, Server server, SessionManager sessions, RequestMapper mapper, Listener listener) {
		super(server, sessions, mapper, listener);
		this.sslCtx = Objects.requireNonNull(sslCtx, "sslCtx == null");
	}
	
	@Override
	public void initChannel(SocketChannel ch) throws Exception {
		super.initChannel(ch);
		ChannelPipeline pipeline = ch.pipeline();
		
		// handle ssl
		pipeline.addFirst("ssl-translator", sslCtx.newHandler(ch.alloc()));
		
	}
}
