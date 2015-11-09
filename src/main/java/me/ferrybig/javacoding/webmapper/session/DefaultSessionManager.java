/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.session;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 *
 * @author Fernando
 */
public class DefaultSessionManager implements SessionManager {

	private final PermissionManager manager;
	
	// Should be a multiple of 5 so it can be encoded efficent into base 32(2^5)
	private static final int HASH_SIZE_BITS = 255; 
	
	private final SecureRandom random = new SecureRandom();
	
	private final Map<String, Session> sessions = new HashMap<>();
	
	private int cleanupcheck = 0;
	
	private final SessionFactory sessionFactory;

	public DefaultSessionManager(PermissionManager manager) {
		this(manager, (String hash, SessionManager manager1) 
				-> new DefaultSession(hash, manager1, manager1.getPermissionsManager()));
	}

	public DefaultSessionManager(PermissionManager manager, SessionFactory sessionFactory) {
		this.manager = Objects.requireNonNull(manager, "manager == null");
		this.sessionFactory = Objects.requireNonNull(sessionFactory, "sessionFactory == null");
	}
	
	@Override
	public Optional<Session> getSessionByKey(String key) {
		Session s = this.sessions.get(key);
		if(s == null) {
			return Optional.empty();
		}
		if(s.getExpireDate().isBefore(Instant.now())) {
			this.sessions.remove(key);
			return Optional.empty();
		}
		return Optional.of(s);
	}

	@Override
	public Session createNewSession() {
		String hash = new BigInteger(HASH_SIZE_BITS, random).toString(32);
		Session s = sessionFactory.createSession(hash, this);
		if(cleanupcheck++ > 100 && this.sessions.size()> 200) {
			this.cleanup();
		}
		this.sessions.put(hash, s);
		return s;
	}

	@Override
	public PermissionManager getPermissionsManager() {
		return manager;
	}

	@Override
	public Collection<Session> getKnownSessions() {
		return Collections.unmodifiableCollection(sessions.values());
	}

	private void cleanup() {
		this.cleanupcheck = 0;
		Instant now = Instant.now();
		for (Iterator<Map.Entry<String, Session>> it = this.sessions.entrySet().iterator(); it.hasNext();) {
			Map.Entry<String, Session> next = it.next();
			if(next.getValue().getExpireDate().isBefore(now)) {
				it.remove();
			}
		}
	}
	public interface SessionFactory {
		public Session createSession(String hash, SessionManager manager);
	}
}
