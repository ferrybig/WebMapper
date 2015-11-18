/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.test.empty;

import me.ferrybig.javacoding.webmapper.Listener;
import me.ferrybig.javacoding.webmapper.Server;
import me.ferrybig.javacoding.webmapper.exceptions.ListenerException;
import me.ferrybig.javacoding.webmapper.session.PermissionManager;
import me.ferrybig.javacoding.webmapper.session.SessionManager;
import io.netty.handler.ssl.SslContext;
import java.util.Set;

/**
 *
 * @author Fernando
 */
public class EmptyServer implements Server {

	public EmptyServer() {
	}

	@Override
	public Listener addListener(String host, int port, SslContext ssl) throws ListenerException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public Set<Listener> getListeners() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public PermissionManager getPermissions() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public SessionManager getSessions() {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
}
