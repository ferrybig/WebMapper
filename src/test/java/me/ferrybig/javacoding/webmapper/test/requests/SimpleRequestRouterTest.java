/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.test.requests;

import me.ferrybig.javacoding.webmapper.test.empty.EmptySessionSupplier;
import me.ferrybig.javacoding.webmapper.test.empty.EmptyServer;
import me.ferrybig.javacoding.webmapper.EndpointResult;
import me.ferrybig.javacoding.webmapper.Listener;
import me.ferrybig.javacoding.webmapper.Server;
import me.ferrybig.javacoding.webmapper.exceptions.RouteException;
import me.ferrybig.javacoding.webmapper.requests.RequestMapper;
import me.ferrybig.javacoding.webmapper.requests.SimpleRequestRouter;
import me.ferrybig.javacoding.webmapper.requests.requests.SessionSupplier;
import me.ferrybig.javacoding.webmapper.requests.requests.SimpleWebServerRequest;
import me.ferrybig.javacoding.webmapper.requests.requests.WebServerRequest;
import me.ferrybig.javacoding.webmapper.test.empty.EmptyChannel;
import io.netty.channel.Channel;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Fernando
 */
public class SimpleRequestRouterTest {

	private static final EndpointResult<?> RESULT
			= new EndpointResult<>(EndpointResult.Result.OK, "", EndpointResult.ContentType.TEXT);
	private static final RequestMapper SUCCESS = (req) -> {
		return RESULT;
	};
	private static final RequestMapper FAILING = (req) -> {
		fail();
		throw new AssertionError();
	};
	private static final Server emptyServer = EmptyServer.INSTANCE;
	private static final Listener listener = new Listener("127.0.0.1", 80, false);
	private static final Channel emptycontext = EmptyChannel.INSTANCE;
	private static final SessionSupplier emptysessionSupplier = new EmptySessionSupplier();

	@Test
	public void exactMatchTest() throws RouteException {
		SimpleRequestRouter mapper = new SimpleRequestRouter(FAILING);
		mapper.addRoute("/test", SUCCESS);
		WebServerRequest req = new SimpleWebServerRequest("/test", emptycontext, emptysessionSupplier, emptyServer, listener);
		assertEquals(mapper.handleHttpRequest(req), RESULT);
	}

	@Test
	public void weakMatchTest() throws RouteException {
		SimpleRequestRouter mapper = new SimpleRequestRouter(FAILING);
		mapper.addRoute("/test", SUCCESS);
		WebServerRequest req = new SimpleWebServerRequest("/test/1", emptycontext, emptysessionSupplier, emptyServer, listener);
		assertEquals(mapper.handleHttpRequest(req), RESULT);
	}

	@Test
	public void longestRouteTest() throws RouteException {
		SimpleRequestRouter mapper = new SimpleRequestRouter(FAILING);
		mapper.addRoute("/test", FAILING);
		mapper.addRoute("/test/1", SUCCESS);
		WebServerRequest req = new SimpleWebServerRequest("/test/1/2", emptycontext, emptysessionSupplier, emptyServer, listener);
		assertEquals(mapper.handleHttpRequest(req), RESULT);

		mapper = new SimpleRequestRouter(FAILING);
		mapper.addRoute("/test/1", SUCCESS);
		mapper.addRoute("/test", FAILING);
		req = new SimpleWebServerRequest("/test/1/2", emptycontext, emptysessionSupplier, emptyServer, listener);
		assertEquals(mapper.handleHttpRequest(req), RESULT);
	}

	@Test
	public void takesDefaultRouteTest() throws RouteException {
		SimpleRequestRouter mapper = new SimpleRequestRouter(SUCCESS);
		mapper.addRoute("/test", FAILING);
		mapper.addRoute("/test/1", FAILING);
		mapper.addRoute("/1/2", FAILING);
		WebServerRequest req = new SimpleWebServerRequest("/blah/1/2", emptycontext, emptysessionSupplier, emptyServer, listener);
		assertEquals(mapper.handleHttpRequest(req), RESULT);
	}

	@Test
	public void substringWorksTest() throws RouteException {
		SimpleRequestRouter mapper = new SimpleRequestRouter(FAILING);
		mapper.addRoute("/test", (req) -> {
			assertEquals(req.endpoint().length(), "".length());
			return RESULT;
		});
		WebServerRequest req = new SimpleWebServerRequest("/test", emptycontext, emptysessionSupplier, emptyServer, listener);
		assertEquals(mapper.handleHttpRequest(req), RESULT);
	}

	@Test
	public void substringWorksNonExactMatchTest() throws RouteException {
		SimpleRequestRouter mapper = new SimpleRequestRouter(FAILING);
		mapper.addRoute("/test", (req) -> {
			assertEquals(req.endpoint().length(), "/ttt".length());
			return RESULT;
		});
		WebServerRequest req = new SimpleWebServerRequest("/test/ttt", emptycontext, emptysessionSupplier, emptyServer, listener);
		assertEquals(mapper.handleHttpRequest(req), RESULT);
	}

	@Test
	public void substringCanBeDisabledTest() throws RouteException {
		SimpleRequestRouter mapper = new SimpleRequestRouter(FAILING);
		mapper.addRoute("/test", (req) -> {
			assertEquals(req.endpoint().length(), "/test".length());
			return RESULT;
		}, false);
		WebServerRequest req = new SimpleWebServerRequest("/test", emptycontext, emptysessionSupplier, emptyServer, listener);
		assertEquals(mapper.handleHttpRequest(req), RESULT);
	}

	@Test
	public void substringCanBeDisabledNonExactMatchTest() throws RouteException {
		SimpleRequestRouter mapper = new SimpleRequestRouter(FAILING);
		mapper.addRoute("/test", (req) -> {
			assertEquals(req.endpoint().length(), "/test/ttt".length());
			return RESULT;
		}, false);
		WebServerRequest req = new SimpleWebServerRequest("/test/ttt", emptycontext, emptysessionSupplier, emptyServer, listener);
		assertEquals(mapper.handleHttpRequest(req), RESULT);
	}
	
	@Test
	public void substringCanBeDisabledExactRouteMatchTest() throws RouteException {
		SimpleRequestRouter mapper = new SimpleRequestRouter(FAILING);
		mapper.addRoute("/test", (req) -> {
			assertEquals(req.endpoint().length(), "/test".length());
			return RESULT;
		}, false, true);
		WebServerRequest req = new SimpleWebServerRequest("/test", emptycontext, emptysessionSupplier, emptyServer, listener);
		assertEquals(mapper.handleHttpRequest(req), RESULT);
	}
	
	@Test
	public void substringCanBeEnabledExactRouteMatchTest() throws RouteException {
		SimpleRequestRouter mapper = new SimpleRequestRouter(FAILING);
		mapper.addRoute("/test", (req) -> {
			assertEquals(req.endpoint().length(), "".length());
			return RESULT;
		}, true, true);
		WebServerRequest req = new SimpleWebServerRequest("/test", emptycontext, emptysessionSupplier, emptyServer, listener);
		assertEquals(mapper.handleHttpRequest(req), RESULT);
	}
	
	@Test
	public void routeCanBeRemovedTest() throws RouteException {
		SimpleRequestRouter mapper = new SimpleRequestRouter(SUCCESS);
		mapper.addRoute("/test", FAILING);
		mapper.removeRoute("/test");
		WebServerRequest req = new SimpleWebServerRequest("/test", emptycontext, emptysessionSupplier, emptyServer, listener);
		assertEquals(mapper.handleHttpRequest(req), RESULT);
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
