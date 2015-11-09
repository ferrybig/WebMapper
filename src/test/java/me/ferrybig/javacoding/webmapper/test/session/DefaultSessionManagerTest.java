/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.test.session;

import me.ferrybig.javacoding.webmapper.session.DefaultPermissionsManager;
import me.ferrybig.javacoding.webmapper.session.DefaultSessionManager;
import me.ferrybig.javacoding.webmapper.session.Session;
import java.time.Instant;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 *
 * @author Fernando
 */
public class DefaultSessionManagerTest {
	@Test
	public void remembersNewSessionsCorrectlyTest() {
		DefaultSessionManager man = new DefaultSessionManager(new DefaultPermissionsManager());
		Session session = man.createNewSession();
		assertSame(session, man.getSessionByKey(session.getKey()).get());
	}
	
	@Test
	public void sessionsExpireCorrectlyTest() {
		DefaultSessionManager man = new DefaultSessionManager(new DefaultPermissionsManager());
		Session session = man.createNewSession();
		session.setExpireDate(Instant.MIN);
		assertNotEquals(session, man.findOrCreateSession(session.getKey()));
	}
	
	@Test
	public void canGenerateMultipleSessionsTest() {
		DefaultSessionManager man = new DefaultSessionManager(new DefaultPermissionsManager());
		Session session1 = man.createNewSession();
		Session session2 = man.createNewSession();
		Session session3 = man.createNewSession();
		Session session4 = man.createNewSession();
		
		assertNotEquals(session1, session2);
		assertNotEquals(session1, session3);
		assertNotEquals(session1, session4);
		assertNotEquals(session2, session3);
		assertNotEquals(session2, session4);
		assertNotEquals(session3, session4);
	}
	
	@Test(expected = NullPointerException.class)
	public void constructorNullArgumentPermissionsManagerTest() {
		DefaultSessionManager man = new DefaultSessionManager(null);
		man.toString();
	}
}
