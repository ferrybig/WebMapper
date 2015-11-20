/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.session;

import java.net.InetAddress;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 *
 * @author Fernando
 */
public class DefaultSession implements Session {

	private Instant expireDate;
	
	private AuthToken authToken;
	
	private final String key;
	
	private final Set<InetAddress> addresses = new HashSet<>();
	
	private final SessionManager creator;
	
	private final PermissionManager permissionManager;
	
	private final Map<String, DataStorage> data = new HashMap<>(); 
	
	public DefaultSession(String key, SessionManager creator, PermissionManager permissionManager) {
		this.key = Objects.requireNonNull(key, "key == null");
		this.permissionManager = Objects.requireNonNull(permissionManager, "permissionsmanager == null");
		this.creator = Objects.requireNonNull(creator, "creator == null");
		this.authToken = new DefaultAuthToken(
				permissionManager.getPermissionsForLevel(PermissionLevel.ANONYMOUS),PermissionLevel.ANONYMOUS);
		this.expireDate = Instant.now().plus(10, ChronoUnit.SECONDS);
	}

	@Override
	public AuthToken getAuthToken() {
		return authToken;
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public void upgradeAuthToken(PermissionLevel level, Optional<User> realUser, Optional<User> effectiveUser) {
		this.authToken = new DefaultAuthToken(
				permissionManager.getPermissionsForLevel(level), level, realUser, effectiveUser);
		this.expireDate = Instant.now().plus(10, ChronoUnit.MINUTES);
	}

	@Override
	public Set<InetAddress> getSeenIpAddresses() {
		return this.addresses;
	}

	@Override
	public boolean isAnonymous() {
		return this.authToken.getPermissionsLevel() == PermissionLevel.ANONYMOUS;
	}

	@Override
	public SessionManager getCreator() {
		return creator;
	}

	@Override
	public void setExpireDate(Instant date) {
		this.expireDate = Objects.requireNonNull(date,"date == null");
	}

	@Override
	public Instant getExpireDate() {
		return expireDate;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 29 * hash + Objects.hashCode(this.key);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final DefaultSession other = (DefaultSession) obj;
		if (!Objects.equals(this.key, other.key)) {
			return false;
		}
		return true;
	}

	@Override
	public Map<String, ? extends DataStorage> getKnownData() {
		return this.data;
	}

	@Override
	public void setMultipleData(String key, Collection<?> data) {
		this.data.put(key, new DefaultDataStorage(data));
	}

	@Override
	public boolean deleteData(String key) {
		return this.data.remove(key) != null;
	}
	
}
