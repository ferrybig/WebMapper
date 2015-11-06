/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.test.requests;

import me.ferrybig.javacoding.webmapper.EndpointResult;
import me.ferrybig.javacoding.webmapper.session.Session;
import me.ferrybig.javacoding.webmapper.requests.RequestMapper;
import me.ferrybig.javacoding.webmapper.requests.SimpleRequestRouter;
import io.netty.channel.ChannelHandlerContext;
import java.nio.charset.Charset;
import java.util.Optional;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Fernando
 */
public class SimpleRequestRouterTest {

	private static final EndpointResult RESULT
			= new EndpointResult(EndpointResult.Result.OK, "", EndpointResult.ContentType.TEXT);
	private static final RequestMapper SUCCESS = (ChannelHandlerContext ctx, String endpoint, Session session, Optional<?> userData) -> {
		return RESULT;
	};
	private static final RequestMapper FAILING = (ChannelHandlerContext ctx, String endpoint, Session session, Optional<?> userData) -> {
		fail();
		throw new AssertionError();
	};

	@Test
	public void exactMatchTest() {
		SimpleRequestRouter mapper = new SimpleRequestRouter(FAILING);
		mapper.addRoute("/test", SUCCESS);
		assertEquals(mapper.handleHttpRequest(null, "/test", null, Optional.empty()), RESULT);
	}

	@Test
	public void weakMatchTest() {
		SimpleRequestRouter mapper = new SimpleRequestRouter(FAILING);
		mapper.addRoute("/test", SUCCESS);
		assertEquals(mapper.handleHttpRequest(null, "/test/1", null, Optional.empty()), RESULT);
	}

	@Test
	public void longestRouteTest() {
		SimpleRequestRouter mapper = new SimpleRequestRouter(FAILING);
		mapper.addRoute("/test", FAILING);
		mapper.addRoute("/test/1", SUCCESS);
		assertEquals(mapper.handleHttpRequest(null, "/test/1/2", null, Optional.empty()), RESULT);

		mapper = new SimpleRequestRouter(FAILING);
		mapper.addRoute("/test/1", SUCCESS);
		mapper.addRoute("/test", FAILING);
		assertEquals(mapper.handleHttpRequest(null, "/test/1/2", null, Optional.empty()), RESULT);
	}

	@Test
	public void takesDefaultRouteTest() {
		SimpleRequestRouter mapper = new SimpleRequestRouter(SUCCESS);
		mapper.addRoute("/test", FAILING);
		mapper.addRoute("/test/1", FAILING);
		mapper.addRoute("/1/2", FAILING);
		assertEquals(mapper.handleHttpRequest(null, "/blah/1/2", null, Optional.empty()), RESULT);
	}

	@Test
	public void substringWorksTest() {
		SimpleRequestRouter mapper = new SimpleRequestRouter(FAILING);
		mapper.addRoute("/test", (ChannelHandlerContext ctx, String endpoint, Session session, Optional<?> userData) -> {
			assertEquals(endpoint.length(), "".length());
			return RESULT;
		});
		assertEquals(mapper.handleHttpRequest(null, "/test", null, Optional.empty()), RESULT);
	}

	@Test
	public void substringWorksNonExactMatchTest() {
		SimpleRequestRouter mapper = new SimpleRequestRouter(FAILING);
		mapper.addRoute("/test", (ChannelHandlerContext ctx, String endpoint, Session session, Optional<?> userData) -> {
			assertEquals(endpoint.length(), "/ttt".length());
			return RESULT;
		});
		assertEquals(mapper.handleHttpRequest(null, "/test/ttt", null, Optional.empty()), RESULT);
	}

	@Test
	public void substringCanBeDisabledTest() {
		SimpleRequestRouter mapper = new SimpleRequestRouter(FAILING);
		mapper.addRoute("/test", (ChannelHandlerContext ctx, String endpoint, Session session, Optional<?> userData) -> {
			assertEquals(endpoint.length(), "/test".length());
			return RESULT;
		}, false);
		assertEquals(mapper.handleHttpRequest(null, "/test", null, Optional.empty()), RESULT);
	}

	@Test
	public void substringCanBeDisabledNonExactMatchTest() {
		SimpleRequestRouter mapper = new SimpleRequestRouter(FAILING);
		mapper.addRoute("/test", (ChannelHandlerContext ctx, String endpoint, Session session, Optional<?> userData) -> {
			assertEquals(endpoint.length(), "/test/ttt".length());
			return RESULT;
		}, false);
		assertEquals(mapper.handleHttpRequest(null, "/test/ttt", null, Optional.empty()), RESULT);
	}
	
	@Test
	public void routeCanBeRemovedTest() {
		SimpleRequestRouter mapper = new SimpleRequestRouter(SUCCESS);
		mapper.addRoute("/test", FAILING);
		mapper.removeRoute("/test");
		assertEquals(mapper.handleHttpRequest(null, "/test", null, Optional.empty()), RESULT);
	}
	
	@Test(expected = NullPointerException.class)
	public void addRouteArgument1nullTest() {
		SimpleRequestRouter mapper = new SimpleRequestRouter(FAILING);
		mapper.addRoute(null, FAILING);
	}
	
	@Test(expected = NullPointerException.class)
	public void addRouteArgument2nullTest() {
		SimpleRequestRouter mapper = new SimpleRequestRouter(FAILING);
		mapper.addRoute("", null);
	}
	
	@Test(expected = NullPointerException.class)
	public void removeRouteArgument1nullTest() {
		SimpleRequestRouter mapper = new SimpleRequestRouter(FAILING);
		mapper.removeRoute(null);
	}
	
	@Test(expected = NullPointerException.class)
	public void getRouteArgument1nullTest() {
		SimpleRequestRouter mapper = new SimpleRequestRouter(FAILING);
		mapper.getRoute(null);
	}
	
	@Test(expected = NullPointerException.class)
	public void getRouteExactArgument1nullTest() {
		SimpleRequestRouter mapper = new SimpleRequestRouter(FAILING);
		mapper.getRouteExact(null);
	}
	
	@Test(expected = NullPointerException.class)
	public void __INITArgument1nullTest() {
		SimpleRequestRouter mapper = new SimpleRequestRouter(null);
		mapper.toString();
	}
}
