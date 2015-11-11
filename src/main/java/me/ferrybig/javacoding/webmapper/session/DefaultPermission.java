/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.session;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author Fernando
 */
public class DefaultPermission implements Permission {

	private final String name;
	private final Set<PermissionLevel> permissionLevel;
	private final String description;

	public DefaultPermission(String name, PermissionLevel ... permissionsLevel) {
		this(name, "", new HashSet<>(Arrays.asList(permissionsLevel)));
	}
	
	public DefaultPermission(String name, String description, PermissionLevel ... permissionsLevel) {
		this(name, description, new HashSet<>(Arrays.asList(permissionsLevel)));
	}
	
	public DefaultPermission(String name, Set<PermissionLevel> permissionLevel) {
		this(name, "", permissionLevel);
	}
	
	public DefaultPermission(String name, String description, Set<PermissionLevel> permissionLevel) {
		this.name = Objects.requireNonNull(name);
		if(Objects.requireNonNull(permissionLevel).stream().filter((l) -> l == null).count() != 0) {
			throw new NullPointerException("Passed set contain a null element");
		}
		this.permissionLevel = Collections.unmodifiableSet(permissionLevel);
		this.description = Objects.requireNonNull(description);
	}

	@Override
	public String toString() {
		return "DefaultPermission{" + "name=" + name + ", permissionLevel=" + permissionLevel + ", description=" + description + '}';
	}
	
	@Override
	public int hashCode() {
		int hash = 7;
		hash = 71 * hash + Objects.hashCode(this.name);
		hash = 71 * hash + Objects.hashCode(this.permissionLevel);
		hash = 71 * hash + Objects.hashCode(this.description);
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
		final DefaultPermission other = (DefaultPermission) obj;
		if (!Objects.equals(this.name, other.name)) {
			return false;
		}
		if (!Objects.equals(this.permissionLevel, other.permissionLevel)) {
			return false;
		}
		if (!Objects.equals(this.description, other.description)) {
			return false;
		}
		return true;
	}
	
	
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public Set<PermissionLevel> getPermissionLevel() {
		return permissionLevel;
	}

	@Override
	public String getDescription() {
		return description;
	}
	
}
