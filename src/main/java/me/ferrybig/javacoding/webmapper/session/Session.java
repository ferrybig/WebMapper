/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.session;

import java.net.InetAddress;
import java.util.Date;
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
	
	public void destroySession();
	
	public boolean isAnonymous();
	
	public SessionManager getCreator();
	
	public Date getExpireDate();
	
	public void setExpireDate(Date date);

}
