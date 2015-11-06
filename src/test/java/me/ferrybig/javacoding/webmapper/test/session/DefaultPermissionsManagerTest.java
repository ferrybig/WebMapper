/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.test.session;

import me.ferrybig.javacoding.webmapper.session.DefaultPermission;
import me.ferrybig.javacoding.webmapper.session.DefaultPermissionsManager;
import me.ferrybig.javacoding.webmapper.session.Permission;
import me.ferrybig.javacoding.webmapper.session.PermissionsLevel;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 *
 * @author Fernando
 */
public class DefaultPermissionsManagerTest {
	
	@Test
	public void registerTest() {
		DefaultPermissionsManager man = new DefaultPermissionsManager();
		int before = man.getPermissionsForLevel(PermissionsLevel.ADMIN).size();
		man.registerPermission(new DefaultPermission("server.test", "test", PermissionsLevel.ADMIN));
		int after = man.getPermissionsForLevel(PermissionsLevel.ADMIN).size();
		assertNotEquals(before, after);
	}
	
	@Test
	public void unregisterTest() {
		DefaultPermissionsManager man = new DefaultPermissionsManager();
		Permission perm = new DefaultPermission("server.test", "test", PermissionsLevel.ADMIN);
		man.registerPermission(perm);
		int before = man.getPermissionsForLevel(PermissionsLevel.ADMIN).size();
		man.unregisterPermission(perm);
		int after = man.getPermissionsForLevel(PermissionsLevel.ADMIN).size();
		assertNotEquals(before, after);
	}
	
	@Test
	public void noNullReturnTest() {
		DefaultPermissionsManager man = new DefaultPermissionsManager();
		for(PermissionsLevel level : PermissionsLevel.values()) {
			Set<Permission> permissionsForLevel = man.getPermissionsForLevel(level);
			assertNotNull(permissionsForLevel);
			assertEquals(permissionsForLevel.size(), 0);
		}
	}
}
