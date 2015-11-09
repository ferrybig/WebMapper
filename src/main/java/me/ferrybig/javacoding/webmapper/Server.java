/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper;

import me.ferrybig.javacoding.webmapper.requests.RequestMapper;
import me.ferrybig.javacoding.webmapper.exceptions.ListenerException;
import me.ferrybig.javacoding.webmapper.netty.WebSocketServerInitializer;
import me.ferrybig.javacoding.webmapper.session.SessionManager;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslProvider;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLException;

/**
 *
 * @author Fernando
 */
public class Server {

	private final EventLoopGroup bossGroup = new NioEventLoopGroup(2);
	private final EventLoopGroup workerGroup = new NioEventLoopGroup();
	private final Map<Listener, Channel> listeners = new HashMap<>();
	private final RequestMapper mapper;
	private static final Logger LOGGER = Logger.getLogger(Server.class.getName());
	private final SessionManager sessions;

	public Server(SessionManager sessions, RequestMapper mapper) {
		this.sessions = sessions;
		this.mapper = mapper;
		
	}

	public Listener addListener(String host, int port, boolean ssl) throws ListenerException {
		try {
			Listener listener = new Listener(host, port, ssl);
			if (this.listeners.containsKey(listener)) {
				return listener;
			}
			
			final SslContext sslCtx;
			if (ssl) {
				SelfSignedCertificate ssc = new SelfSignedCertificate();
				sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey())
						.sslProvider(SslProvider.JDK).build();
			} else {
				sslCtx = null;
			}
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.handler(new LoggingHandler(this.getClass(), LogLevel.INFO))
					.childHandler(new WebSocketServerInitializer(sslCtx, sessions, mapper, listener));
			ChannelFuture f;
			if(listener.getHost() == null)
				f = b.bind(port);
			else
				f = b.bind(host, port);
			Channel ch = f.sync().channel();
			LOGGER.info("Started listener on: "+listener.toURL());
			this.listeners.put(listener, ch);
			return listener;
		} catch (CertificateException | InterruptedException | SSLException ex) {
			throw new ListenerException(ex);
		}
	}

	public Set<Listener> getListeners() {
		return Collections.unmodifiableSet(this.listeners.keySet());
	}
}
