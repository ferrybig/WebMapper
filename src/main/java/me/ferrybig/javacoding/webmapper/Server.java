/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper;

import me.ferrybig.javacoding.webmapper.exceptions.ListenerException;
import me.ferrybig.javacoding.webmapper.session.PermissionManager;
import me.ferrybig.javacoding.webmapper.session.SessionManager;
import java.util.Set;

/**
 *
 * @author Fernando
 */
public interface Server {

	Listener addListener(String host, int port, boolean ssl) throws ListenerException;

	Set<Listener> getListeners();

	PermissionManager getPermissions();

	SessionManager getSessions();
	
}
