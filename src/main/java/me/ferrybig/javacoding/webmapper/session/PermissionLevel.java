/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.session;

import static me.ferrybig.javacoding.webmapper.util.StreamUtil.concat;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author Fernando
 */
public enum PermissionLevel {
	/**
	 * Users that are making their first request to the api
	 */
	EVERYONE, 
	/**
	 * Users that have their session cookie set, but aren't logged in
	 */
	ANONYMOUS(EVERYONE), 
	/**
	 * Users that are logged in, but haven't verified their email
	 */
	NEW_USER(ANONYMOUS), 
	/**
	 * Normal registered users
	 */
	REGISTERED_USER(NEW_USER), 
	/**
	 * Users with the edit role
	 */
	CONTENT_EDITOR(REGISTERED_USER), 
	/**
	 * Admin users
	 */
	ADMIN(CONTENT_EDITOR),
	;
	private final Set<PermissionLevel> parents;
	private final Set<PermissionLevel> fullParents;
	private final Set<PermissionLevel> fullParentAndMe;
	
	@SuppressWarnings("LeakingThisInConstructor")
	private PermissionLevel() {
		this.fullParents = this.parents = Collections.emptySet();
		this.fullParentAndMe = Collections.unmodifiableSet(EnumSet.of(this));
	}
	
	@SuppressWarnings("LeakingThisInConstructor")
	private PermissionLevel(PermissionLevel parent1,PermissionLevel ... parents) {
		this.parents = Collections.unmodifiableSet(EnumSet.of(parent1, parents));
		this.fullParents = Collections.unmodifiableSet(concat(this.parents.stream(),
				this.parents.stream().map(PermissionLevel::getFullParents).flatMap(Collection::stream)).
				collect(Collectors.toCollection(()->EnumSet.noneOf(PermissionLevel.class))));
		EnumSet<PermissionLevel> tmp = EnumSet.of(this);
		tmp.addAll(this.fullParents);
		this.fullParentAndMe = Collections.unmodifiableSet(tmp);
	}
	
	public Set<PermissionLevel> getParents() {
		return parents;
	}
	
	public Set<PermissionLevel> getFullParents() {
		return fullParents;
	}
	
	public Set<PermissionLevel> getFullParentsAndMe() {
		return fullParentAndMe;
	}
	
	
}
