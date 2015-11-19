/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.test.requests.requests;

import me.ferrybig.javacoding.webmapper.requests.requests.LazySessionSupplier;
import me.ferrybig.javacoding.webmapper.session.DefaultPermissionsManager;
import me.ferrybig.javacoding.webmapper.session.DefaultSession;
import me.ferrybig.javacoding.webmapper.session.DefaultSessionManager;
import me.ferrybig.javacoding.webmapper.session.Session;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author Fernando
 */
public class LazySessionSupplierTest {
	
	static DefaultPermissionsManager man;
	static DefaultSessionManager ses;
	LazySessionSupplier instance;
	
	@BeforeClass
	public static void setupClass() {
		man = new DefaultPermissionsManager();
		ses = new DefaultSessionManager(man);
	}
	
	@Before
	public void setUp() {
		instance = new LazySessionSupplier(() -> new DefaultSession("", ses, man));
	}
	
	@Test
	public void sessionIsLazyLoadedTest() {
		assertFalse(instance.hasTouchedSession());
		instance.getSession();
		assertTrue(instance.hasTouchedSession());
	}
	
	@Test
	public void returnsSameObjectAlwaysTest() {
		assertSame(instance.getSession(), instance.getSession());
	}
	
	@Test(expected = NullPointerException.class)
	public void constructorArgumentNullTest() {
		instance = new LazySessionSupplier(null);
		instance.getClass();
	}
	
}
