/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.session;

import java.util.Set;

/**
 *
 * @author Fernando
 */
public interface PermissionManager {
	public Set<Permission> getPermissionsForLevel(PermissionLevel level);
	
	public void registerPermission(Permission permission);
	
	public void unregisterPermission(Permission permission);
}
