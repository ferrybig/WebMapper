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
	private final Set<PermissionsLevel> defaultPermissions;
	private final String description;

	public DefaultPermission(String name, String description, PermissionsLevel ... defaultPermissions) {
		this(name, description, new HashSet<>(Arrays.asList(defaultPermissions)));
	}
	public DefaultPermission(String name, String description, Set<PermissionsLevel> defaultPermissions) {
		this.name = Objects.requireNonNull(name);
		if(Objects.requireNonNull(defaultPermissions).stream().filter((l) -> l == null).count() != 0) {
			throw new NullPointerException("Passed set contain a null element");
		}
		this.defaultPermissions = Collections.unmodifiableSet(defaultPermissions);
		this.description = Objects.requireNonNull(description);
	}

	@Override
	public String toString() {
		return "DefaultPermission{" + "name=" + name + ", defaultPermissions=" + defaultPermissions + ", description=" + description + '}';
	}
	
	@Override
	public int hashCode() {
		int hash = 7;
		hash = 71 * hash + Objects.hashCode(this.name);
		hash = 71 * hash + Objects.hashCode(this.defaultPermissions);
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
		if (!Objects.equals(this.defaultPermissions, other.defaultPermissions)) {
			return false;
		}
		if (!Objects.equals(this.description, other.description)) {
			return false;
		}
		return true;
	}
	
	
	
	@Override
	public String getName() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public Set<PermissionsLevel> getDefaultPermissions() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public String getDescription() {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
}
