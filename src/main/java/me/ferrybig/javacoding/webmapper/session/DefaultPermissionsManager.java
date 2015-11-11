/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.session;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Fernando
 */
public class DefaultPermissionsManager implements PermissionManager {

	private final Map<PermissionLevel,Set<Permission>> permissionsByLevel = new HashMap<>();
	
	{
		Arrays.stream(PermissionLevel.values()).forEach(l->permissionsByLevel.put(l,new HashSet<>()));
	}
	
	@Override
	public Set<Permission> getPermissionsForLevel(PermissionLevel level) {
		return Collections.unmodifiableSet(permissionsByLevel.get(level));
	}

	@Override
	public Permission registerPermission(Permission permission) {
		permission.getPermissionLevel().stream().forEach(l->permissionsByLevel.get(l).add(permission));
		return permission;
	}

	@Override
	public Permission unregisterPermission(Permission permission) {
		permission.getPermissionLevel().stream().forEach(l->permissionsByLevel.get(l).remove(permission));
		return permission;
	}
	
}
