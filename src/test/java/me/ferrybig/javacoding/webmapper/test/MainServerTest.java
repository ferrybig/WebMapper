/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.test;

import me.ferrybig.javacoding.webmapper.MainServer;
import me.ferrybig.javacoding.webmapper.exceptions.ListenerException;
import me.ferrybig.javacoding.webmapper.exceptions.ServerException;
import me.ferrybig.javacoding.webmapper.session.DefaultPermissionsManager;
import me.ferrybig.javacoding.webmapper.session.DefaultSessionManager;
import me.ferrybig.javacoding.webmapper.session.PermissionManager;
import me.ferrybig.javacoding.webmapper.session.SessionManager;
import java.io.File;
import static org.junit.Assert.*;
import org.junit.Test;
/**
 *
 * @author Fernando
 */
public class MainServerTest {
	@Test
	public void canReadSSLCertTest() throws ListenerException, ServerException {
		PermissionManager perm = new DefaultPermissionsManager();
		SessionManager ses = new DefaultSessionManager(perm);
		MainServer s = new MainServer(ses, perm, r->null);
		try {
			s.addListener("127.24.54.230", 59684, 
					new File(getClass().getResource("unsafe01.key").getFile()), 
					new File(getClass().getResource("unsafe01.cert").getFile()));
		} finally {
			s.shutdown();
		}
	}
}
