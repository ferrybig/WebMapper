/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper;

import me.ferrybig.javacoding.webmapper.exceptions.ListenerException;
import me.ferrybig.javacoding.webmapper.session.PermissionManager;
import me.ferrybig.javacoding.webmapper.session.SessionManager;
import io.netty.handler.ssl.SslContext;
import java.io.File;
import java.util.Set;

/**
 *
 * @author Fernando
 */
public interface Server {
	
	Listener addListener(String host, int port, File privateKey, File publicKey, String pass) throws ListenerException;
	
	Listener addListener(String host, int port, File privateKey, File publicKey) throws ListenerException;
	
	Listener addListener(String host, int port) throws ListenerException;

	@Deprecated
	Listener addListener(String host, int port, SslContext sslCtx) throws ListenerException;

	Set<Listener> getListeners();

	PermissionManager getPermissions();

	SessionManager getSessions();
	
}
