/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.requests.requests;

import me.ferrybig.javacoding.webmapper.EndpointResult;
import me.ferrybig.javacoding.webmapper.Listener;
import me.ferrybig.javacoding.webmapper.Server;
import me.ferrybig.javacoding.webmapper.session.DataStorage;
import me.ferrybig.javacoding.webmapper.session.PermissionManager;
import me.ferrybig.javacoding.webmapper.session.Session;
import me.ferrybig.javacoding.webmapper.session.SessionManager;
import io.netty.channel.ChannelHandlerContext;

/**
 *
 * @author Fernando
 */
public interface WebServerRequest extends DataStorage {
	public String getEndpoint();
	
	public default String endpoint() {
		return getEndpoint();
	}
	
	public default WebServerRequest endpoint(String endpoint) {
		setEndpoint(endpoint);
		return this;
	}
	
	public void setEndpoint(String endpoint);
	
	public Listener getListener();
	
	public ChannelHandlerContext getHandlerContext();
	
	
	public Server getServer();
	
	public default SessionManager getSessionManager() {
		return getServer().getSessions();
	}
	
	public default PermissionManager getPermissionsManager() {
		return getServer().getPermissions();
	}
	
	public SessionSupplier getSessionSupplier();
	
	public void setSessionSupplier(SessionSupplier ses);
	
	public default Session session() {
		return getSession();
	}
	
	public default Session getSession() {
		return getSessionSupplier().getSession();
	}
	
	public default boolean hasTouchedSession() {
		return getSessionSupplier().hasTouchedSession();
	}
	
	
	
	public boolean canRegisterAsCallback();
	
	public FutureRequest registerFutureCallbackListener(FutureRequestListener list);
	
	public interface FutureRequest extends AutoCloseable {
		public void writeMessage(EndpointResult<?> req);
		
		@Override
		public void close();
		
		public boolean isClosed();
		
		public CallId getId();
	}
	
	public interface FutureRequestListener {
		public void onClose(CallId req);
		
		public void onOpen(CallId req);
		
		public void onMessage(WebServerRequest req);
		
	}
	
	public interface CallId extends SessionSupplier {
		public long getNumber();
		
		public ChannelHandlerContext getContext();
		
	}
}
