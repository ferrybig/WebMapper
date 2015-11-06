/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.session;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 *
 * @author Fernando
 */
public class DefaultAuthToken implements AuthToken {

	private final Set<Permission> permissions;
	private final PermissionLevel level;
	private final Optional<User> effectiveUser;
	private final Optional<User> realUser;

	public DefaultAuthToken(Set<Permission> permissions, PermissionLevel level) {
		this(permissions, level, Optional.empty());
	}
	
	public DefaultAuthToken(Set<Permission> permissions, PermissionLevel level, Optional<User> effectiveUser) {
		this(permissions, level, effectiveUser, effectiveUser);
	}
	
	public DefaultAuthToken(Set<Permission> permissions, PermissionLevel level, Optional<User> effectiveUser, Optional<User> realUser) {
		this.permissions = Objects.requireNonNull(permissions, "permissions == null");
		this.level = Objects.requireNonNull(level, "level == null");
		this.effectiveUser = Objects.requireNonNull(effectiveUser, "effectiveUser == null");
		this.realUser = Objects.requireNonNull(realUser, "realUser == null");
	}
	
	@Override
	public Set<Permission> getPermissions() {
		return Collections.unmodifiableSet(permissions);
	}

	@Override
	public PermissionLevel getPermissionsLevel() {
		return level;
	}

	@Override
	public Optional<User> getEffectiveUser() {
		return effectiveUser;
	}

	@Override
	public Optional<User> getRealUser() {
		return realUser;
	}

	@Override
	public String toString() {
		return "DefaultAuthToken{" + "permissions=" + permissions + ", level=" + level + ", effectiveUser=" + effectiveUser + ", realUser=" + realUser + '}';
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 89 * hash + Objects.hashCode(this.permissions);
		hash = 89 * hash + Objects.hashCode(this.level);
		hash = 89 * hash + Objects.hashCode(this.effectiveUser);
		hash = 89 * hash + Objects.hashCode(this.realUser);
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
		final DefaultAuthToken other = (DefaultAuthToken) obj;
		if (!Objects.equals(this.permissions, other.permissions)) {
			return false;
		}
		if (this.level != other.level) {
			return false;
		}
		if (!Objects.equals(this.effectiveUser, other.effectiveUser)) {
			return false;
		}
		if (!Objects.equals(this.realUser, other.realUser)) {
			return false;
		}
		return true;
	}
	
	
	
}
