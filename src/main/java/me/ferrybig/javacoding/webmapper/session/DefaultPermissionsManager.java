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

	private final Map<PermissionsLevel,Set<Permission>> permissionsByLevel = new HashMap<>();
	
	{
		Arrays.stream(PermissionsLevel.values()).forEach(l->permissionsByLevel.put(l,new HashSet<>()));
	}
	
	@Override
	public Set<Permission> getPermissionsForLevel(PermissionsLevel level) {
		return Collections.unmodifiableSet(permissionsByLevel.get(level));
	}

	@Override
	public void registerPermission(Permission permission) {
		permission.getDefaultPermissions().stream().forEach(l->permissionsByLevel.get(l).add(permission));
	}

	@Override
	public void unregisterPermission(Permission permission) {
		permission.getDefaultPermissions().stream().forEach(l->permissionsByLevel.get(l).remove(permission));
	}
	
}
