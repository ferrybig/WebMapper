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
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import java.io.File;
import java.util.function.Function;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Fernando
 */
public class MainServerTest {

	private static EventLoopGroup eventGroup;
	
	@BeforeClass
	public static void init() {
		eventGroup = new NioEventLoopGroup(1);
	}
	
	@AfterClass
	public static void shutdown() {
		eventGroup.shutdownGracefully();
	}
	
	@Test
	public void canReadSSLCertTest() throws ListenerException, ServerException {
		PermissionManager perm = new DefaultPermissionsManager();
		SessionManager ses = new DefaultSessionManager(perm);
		try (MainServer s = new MainServer(eventGroup, eventGroup, ses, perm, r -> null)) {
			s.addListener("127.24.54.230", 59684,
					new File(getClass().getResource("unsafe01.key").getFile()),
					new File(getClass().getResource("unsafe01.cert").getFile()));
		}
	}

	@Test
	public void serverCanBeClosedWithoutListenersActiveTest() throws ListenerException, ServerException {
		PermissionManager perm = new DefaultPermissionsManager();
		SessionManager ses = new DefaultSessionManager(perm);
		try (MainServer s = new MainServer(eventGroup, eventGroup, ses, perm, r -> null)) {
			// Void call to prevent compiler optimalization
			Function.identity().apply(s);
		}
	}

	@Test
	public void getListenersReturnsListenersTest() throws ListenerException, ServerException {
		PermissionManager perm = new DefaultPermissionsManager();
		SessionManager ses = new DefaultSessionManager(perm);
		try (MainServer s = new MainServer(eventGroup, eventGroup, ses, perm, r -> null)) {
			s.addListener("127.24.45.230", 59684);
			s.addListener("127.25.45.230", 59684);
			s.addListener("127.26.45.230", 59684);
			s.addListener("127.27.45.230", 59684);
			assertEquals(4, s.getListeners().size());
		}
	}

}
