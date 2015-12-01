/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper;

import me.ferrybig.javacoding.webmapper.exceptions.ListenerException;
import me.ferrybig.javacoding.webmapper.exceptions.ServerException;
import me.ferrybig.javacoding.webmapper.session.PermissionManager;
import me.ferrybig.javacoding.webmapper.session.SessionManager;
import io.netty.handler.ssl.SslContext;
import java.io.File;
import java.util.Set;

/**
 *
 * @author Fernando
 */
public interface Server extends AutoCloseable {
	
	public Listener addListener(String host, int port, File privateKey, File publicKey, String pass) throws ListenerException;
	
	public Listener addListener(String host, int port, File privateKey, File publicKey) throws ListenerException;
	
	public Listener addListener(String host, int port) throws ListenerException;

	@Deprecated
	public Listener addListener(String host, int port, SslContext sslCtx) throws ListenerException;

	public Set<Listener> getListeners();

	public PermissionManager getPermissions();

	public SessionManager getSessions();
	
	@Override
	public void close() throws ServerException;
	
	public boolean isClosed();
	
}
