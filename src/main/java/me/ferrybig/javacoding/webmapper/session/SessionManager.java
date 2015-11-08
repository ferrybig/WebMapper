/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.session;

import java.util.Collection;
import java.util.Optional;

/**
 *
 * @author Fernando
 */
public interface SessionManager {
	public Optional<Session> getSessionByKey(String key);
	
	public Session createNewSession();
	
	public Collection<Session> getKnownSessions();
	
	public default Session findOrCreateSession(Optional<String> key) {
		return key.flatMap(this::getSessionByKey).orElseGet(this::createNewSession);
	}
	
	public default Session findOrCreateSession(String key) {
		return this.getSessionByKey(key).orElseGet(this::createNewSession);
	}

	PermissionManager getPermissionsManager();
}
