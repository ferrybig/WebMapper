/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.test.session;

import me.ferrybig.javacoding.webmapper.session.DefaultAuthToken;
import me.ferrybig.javacoding.webmapper.session.DefaultPermission;
import me.ferrybig.javacoding.webmapper.session.Permission;
import me.ferrybig.javacoding.webmapper.session.PermissionLevel;
import java.security.Permissions;
import java.util.Collections;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 *
 * @author Fernando
 */
public class DefaultAuthTokenTest {
	@Test
	public void equalsAndHashcodeTest() {
		DefaultAuthToken token1 = new DefaultAuthToken(Collections.emptySet(), PermissionLevel.ADMIN);
		DefaultAuthToken token2 = new DefaultAuthToken(Collections.emptySet(), PermissionLevel.ADMIN);
		DefaultAuthToken token3 = new DefaultAuthToken(Collections.emptySet(), PermissionLevel.ANONYMOUS);
		DefaultAuthToken token4 = new DefaultAuthToken(Collections.emptySet(), PermissionLevel.ANONYMOUS);
		
		assertEquals(token1,token2);
		assertEquals(token1.hashCode(),token2.hashCode());
		assertNotEquals(token1, token3);
		assertNotEquals(token1, token4);
		assertNotEquals(token2, token3);
		assertNotEquals(token2, token4);
		assertEquals(token3,token4);
		assertEquals(token3.hashCode(),token4.hashCode());
	}
	
	@Test
	public void hasPermissionsTest() {
		Permission p = new DefaultPermission("server.test", "", PermissionLevel.ANONYMOUS);
		DefaultAuthToken token1 = new DefaultAuthToken(Collections.singleton(p), PermissionLevel.ADMIN);
		DefaultAuthToken token2 = new DefaultAuthToken(Collections.emptySet(), PermissionLevel.ADMIN);
		assertTrue(token1.hasPermission(p));
		assertFalse(token2.hasPermission(p));
	}
}
