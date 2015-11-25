/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper;

import me.ferrybig.javacoding.webmapper.requests.RequestMapper;
import me.ferrybig.javacoding.webmapper.exceptions.ListenerException;
import me.ferrybig.javacoding.webmapper.exceptions.ServerException;
import me.ferrybig.javacoding.webmapper.netty.WebServerInitializer;
import me.ferrybig.javacoding.webmapper.netty.WebSslServerInitializer;
import me.ferrybig.javacoding.webmapper.session.PermissionManager;
import me.ferrybig.javacoding.webmapper.session.SessionManager;
import me.ferrybig.javacoding.webmapper.util.StreamUtil;
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
import io.netty.util.concurrent.Future;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.net.ssl.SSLException;

/**
 *
 * @author Fernando
 */
public class MainServer implements Server {

	private final EventLoopGroup bossGroup = new NioEventLoopGroup(2);
	private final EventLoopGroup workerGroup = new NioEventLoopGroup();
	private final Map<Listener, Channel> listeners = new HashMap<>();
	private final ServerBootstrap b;
	private final PermissionManager permissions;
	private final RequestMapper mapper;
	private static final Logger LOGGER = Logger.getLogger(MainServer.class.getName());
	private final SessionManager sessions;

	public MainServer(SessionManager sessions, PermissionManager permissions, RequestMapper mapper) {
		this.sessions = sessions;
		this.permissions = permissions;
		this.mapper = mapper;
		this.b = new ServerBootstrap();
		
	}

	@Override
	public synchronized Listener addListener(String host, int port, SslContext sslCtx) throws ListenerException {
		try {
			Listener listener = new Listener(host, port, sslCtx != null);
			if (this.listeners.containsKey(listener)) {
				return listener;
			}
			
			WebServerInitializer init;
			if(sslCtx != null) {
				init = new WebSslServerInitializer(sslCtx, this, sessions, mapper, listener);
			} else {
				init = new WebServerInitializer(this, sessions, mapper, listener);
			}
			b.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.handler(new LoggingHandler(this.getClass(), LogLevel.INFO))
					.childHandler(init);
			ChannelFuture f;
			if(listener.getHost() == null)
				f = b.bind(port);
			else
				f = b.bind(host, port);
			Channel ch = f.sync().channel();
			LOGGER.log(Level.INFO, "Started listener on: {0}", listener.toURL());
			this.listeners.put(listener, ch);
			return listener;
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
			throw new ListenerException(ex);
		}
	}

	@Override
	public Set<Listener> getListeners() {
		return Collections.unmodifiableSet(this.listeners.keySet());
	}

	@Override
	public SessionManager getSessions() {
		return sessions;
	}

	@Override
	public PermissionManager getPermissions() {
		return permissions;
	}

	public void shutdown() throws ServerException {
		List<Throwable> exception = new LinkedList<>();
		this.listeners.values().stream().map(Channel::close).peek(ChannelFuture::awaitUninterruptibly).
				map(ChannelFuture::cause).filter(Objects::nonNull).forEach(exception::add);
		Stream.of(bossGroup.shutdownGracefully(), workerGroup.shutdownGracefully()).peek(Future::awaitUninterruptibly).
				map(Future::cause).filter(Objects::nonNull).forEach(exception::add);
		if(!exception.isEmpty()) {
			ServerException main = new ServerException("Unable to shutdown properly");
			exception.forEach(main::addSuppressed);
			throw main;
		}
	}
	
}
