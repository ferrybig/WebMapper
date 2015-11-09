/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.test.session;

import me.ferrybig.javacoding.webmapper.session.DefaultPermissionsManager;
import me.ferrybig.javacoding.webmapper.session.DefaultSession;
import me.ferrybig.javacoding.webmapper.session.PermissionLevel;
import me.ferrybig.javacoding.webmapper.session.PermissionManager;
import me.ferrybig.javacoding.webmapper.session.Session;
import me.ferrybig.javacoding.webmapper.session.SessionManager;
import java.util.Collection;
import java.util.Optional;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 *
 * @author Fernando
 */
public class DefaultSessionTest {
	
	PermissionManager man = new DefaultPermissionsManager();
	SessionManager ses = new TestingSessionManager();
	
	@Test
	public void equalsAndHashcodeTest() {
		
		
		DefaultSession session1 = new DefaultSession("key1", ses, man);
		DefaultSession session2 = new DefaultSession("key1", ses, man);
		DefaultSession session3 = new DefaultSession("key2", ses, man);
		DefaultSession session4 = new DefaultSession("key2", ses, man);
		
		assertEquals(session1,session2);
		assertEquals(session1.hashCode(),session2.hashCode());
		assertNotEquals(session1, session3);
		assertNotEquals(session1, session4);
		assertNotEquals(session2, session3);
		assertNotEquals(session2, session4);
		assertEquals(session3,session4);
		assertEquals(session3.hashCode(),session4.hashCode());
	}
	
	@Test(expected = NullPointerException.class)
	public void constructorNullKeyTest() {
		DefaultSession session1 = new DefaultSession(null, ses, man);
		session1.toString();
	}
	
	@Test(expected = NullPointerException.class)
	public void constructorNullSessionManagerTest() {
		DefaultSession session1 = new DefaultSession("key", null, man);
		session1.toString();
	}
	
	@Test(expected = NullPointerException.class)
	public void constructorNullPermissionsManagerTest() {
		DefaultSession session1 = new DefaultSession("key", ses, null);
		session1.toString();
	}
	
	@Test(expected = NullPointerException.class)
	public void methodSetExpireDateNullExpireDateTest() {
		DefaultSession session1 = new DefaultSession("key", ses, man);
		session1.setExpireDate(null);
	}
	
	@Test(expected = NullPointerException.class)
	public void methodUpgradeAuthTokenNullPermissionsLevelTest() {
		DefaultSession session1 = new DefaultSession("key", ses, man);
		session1.upgradeAuthToken(null, Optional.empty(), Optional.empty());
	}
	
	@Test(expected = NullPointerException.class)
	public void methodUpgradeAuthTokenNullEffectiveUserTest() {
		DefaultSession session1 = new DefaultSession("key", ses, man);
		session1.upgradeAuthToken(PermissionLevel.ANONYMOUS, null, Optional.empty());
	}
	
	@Test(expected = NullPointerException.class)
	public void methodUpgradeAuthTokenNullRealUserTest() {
		DefaultSession session1 = new DefaultSession("key", ses, man);
		session1.upgradeAuthToken(PermissionLevel.ANONYMOUS, Optional.empty(), null);
	}

	private static class TestingSessionManager implements SessionManager {

		@Override
		public Optional<Session> getSessionByKey(String key) {
			throw new UnsupportedOperationException("Not supported yet.");
		}

		@Override
		public Session createNewSession() {
			throw new UnsupportedOperationException("Not supported yet.");
		}
		
		@Override
		public Collection<Session> getKnownSessions() {
			throw new UnsupportedOperationException("Not supported yet.");
		}
		
		@Override
		public PermissionManager getPermissionsManager() {
			throw new UnsupportedOperationException("Not supported yet.");
		}
	}
}
