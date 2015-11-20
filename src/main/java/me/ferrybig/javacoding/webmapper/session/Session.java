/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.session;

import java.net.InetAddress;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 *
 * @author Fernando
 */
public interface Session {
	public AuthToken getAuthToken();
	
	public String getKey();
	
	public void upgradeAuthToken(PermissionLevel level, Optional<User> realUser, Optional<User> effectiveUser);
	
	public Set<InetAddress> getSeenIpAddresses();
	
	public default void destroySession() {
		this.setExpireDate(Instant.MIN);
	}
	
	public boolean isAnonymous();
	
	public SessionManager getCreator();
	
	public Instant getExpireDate();
	
	public void setExpireDate(Instant date);

	public Map<String, ? extends DataStorage> getKnownData();

	public default <T> Optional<T> getData(String key, Class<T> type) {
		return getData(key).flatMap(d->d.getDataAs(type));
	}
	
	public default Optional<DataStorage> getData(String key) {
		return Optional.ofNullable(getKnownData().get(key));
	}
	
	public default void setData(String key, Object data) {
		setMultipleData(key, Collections.singleton(data));
	}
	
	public void setMultipleData(String key, Collection<?> data);
	
	public boolean deleteData(String key);

}
