/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.test.session;

import me.ferrybig.javacoding.webmapper.session.DefaultPermission;
import me.ferrybig.javacoding.webmapper.session.Permission;
import me.ferrybig.javacoding.webmapper.session.PermissionLevel;
import java.util.Collections;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 *
 * @author Fernando
 */
public class DefaultPermissionsTest {
	
	@Test
	public void equalsAndHashcodeTest() {
		Permission p1 = new DefaultPermission("server.test", "test permission", PermissionLevel.ADMIN);
		Permission p2 = new DefaultPermission("server.test", "test permission", PermissionLevel.ADMIN);
		Permission p3 = new DefaultPermission("server.test123", "test permission", PermissionLevel.ADMIN);
		Permission p4 = new DefaultPermission("server.test", "test permission", PermissionLevel.ANONYMOUS);
		
		assertEquals(p1, p2);
		assertEquals(p1.hashCode(), p2.hashCode());
		assertNotEquals(p1, p3);
		assertNotEquals(p1, p4);
		assertNotEquals(p2, p3);
		assertNotEquals(p2, p4);
		assertNotEquals(p3, p4);
	}
	
	@Test(expected = NullPointerException.class)
	public void nullFirstArgumentTest() {
		new DefaultPermission(null, "test permission", PermissionLevel.ADMIN).toString();
	}
	
	@Test(expected = NullPointerException.class)
	public void nullSecondArgumentTest() {
		new DefaultPermission("server.test", null, PermissionLevel.ADMIN).toString();
	}
	
	@Test(expected = NullPointerException.class)
	public void nullThirdArgumentSetTest() {
		new DefaultPermission("server.test", "test permission", (PermissionLevel[])null).toString();
	}
	
	@Test(expected = NullPointerException.class)
	public void nullThirdArgumentArrayTest() {
		new DefaultPermission("server.test", "test permission", (Set<PermissionLevel>)null).toString();
	}
	
	@Test(expected = NullPointerException.class)
	public void nullThirdArgumentNullElementTest() {
		new DefaultPermission("server.test", "test permission", Collections.singleton(null)).toString();
	}
}
