/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.requests.requests;

import me.ferrybig.javacoding.webmapper.Listener;
import me.ferrybig.javacoding.webmapper.Server;
import me.ferrybig.javacoding.webmapper.session.DefaultDataStorage;
import me.ferrybig.javacoding.webmapper.session.Session;
import io.netty.channel.Channel;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Fernando
 */
public class SimpleWebServerRequest extends DefaultDataStorage implements WebServerRequest {
	private String endpoint;
	
	private final Channel context;
	
	private final Server server;
	
	private final Listener port;
	
	private SessionSupplier sessionsuplier;
	
	public SimpleWebServerRequest(String endpoint, Channel context,
			SessionSupplier sessionsuplier, Server server, Listener port) {
		this(endpoint, context, sessionsuplier, server, port, Collections.emptyList());
	}

	public SimpleWebServerRequest(String endpoint, Channel context,
			SessionSupplier sessionsuplier, Server server, Listener port, List<Object> data) {
		super(data);
		this.endpoint = Objects.requireNonNull(endpoint, "endpoint == null");
		this.sessionsuplier = Objects.requireNonNull(sessionsuplier, "sessionsuplier == null");
		this.context = Objects.requireNonNull(context, "context == null");
		this.server = Objects.requireNonNull(server, "server == null");
		this.port = Objects.requireNonNull(port, "port == null");
	}

	@Override
	public String getEndpoint() {
		return endpoint;
	}

	@Override
	public void setEndpoint(String endpoint) {
		this.endpoint = Objects.requireNonNull(endpoint, "endpoint == null");
	}

	@Override
	public Listener getListener() {
		return port;
	}

	@Override
	public Channel getChannel() {
		return context;
	}

	@Override
	public Server getServer() {
		return server;
	}

	@Override
	public boolean canRegisterAsCallback() {
		return false;
	}

	@Override
	public FutureRequest registerFutureCallbackListener(FutureRequestListener list) {
		throw new UnsupportedOperationException("Not supported!");
	}

	@Override
	public Session getSession() {
		return sessionsuplier.getSession();
	}
	
	@Override
	public SessionSupplier getSessionSupplier() {
		return this.sessionsuplier;
	}

	@Override
	public void setSessionSupplier(SessionSupplier sessionsuplier) {
		this.sessionsuplier = Objects.requireNonNull(sessionsuplier, "sessionsuplier == null");
	}

}
