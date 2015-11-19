/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.test.requests.requests;

import me.ferrybig.javacoding.webmapper.Listener;
import me.ferrybig.javacoding.webmapper.requests.requests.SimpleWebServerRequest;
import me.ferrybig.javacoding.webmapper.test.empty.EmptyChannelHandlerContext;
import me.ferrybig.javacoding.webmapper.test.empty.EmptyServer;
import me.ferrybig.javacoding.webmapper.test.empty.EmptySessionSupplier;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Ignore;

/**
 *
 * @author Fernando
 */
public class SimpleWebServerRequestTest {

	SimpleWebServerRequest req;

	@Before
	public void init() {
		req = new SimpleWebServerRequest("",
				new EmptyChannelHandlerContext(),
				new EmptySessionSupplier(),
				new EmptyServer(),
				new Listener("127.0.0.1", 80, false));
	}

	@Test
	public void endpointGetAndSetTest() {
		req.endpoint("test");
		assertEquals("test", req.endpoint());
		req.endpoint("test1234");
		assertEquals("test1234", req.endpoint());
		req.endpoint("test4321");
		assertEquals("test4321", req.endpoint());
	}

	@Test
	public void endpointSetReturnsSameInstanceTest() {
		assertSame(req, req.endpoint(""));
	}

}
