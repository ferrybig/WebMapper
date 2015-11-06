/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.session;

import java.net.InetAddress;
import java.util.Optional;
import java.util.Set;

/**
 *
 * @author Fernando
 */
public interface AuthToken {
	public Set<Permission> getPermissions();
	
	public PermissionsLevel getPermissionsLevel();
	
	public default boolean hasPermission(Permission permission) {
		return this.getPermissions().contains(permission);
	}
	
	public Optional<User> getEffectiveUser();
	
	public Optional<User> getRealUser();
}
