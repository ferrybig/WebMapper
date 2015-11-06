/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.test.requests;

import me.ferrybig.javacoding.webmapper.EndpointResult;
import me.ferrybig.javacoding.webmapper.EndpointResult.Result;
import me.ferrybig.javacoding.webmapper.session.Session;
import me.ferrybig.javacoding.webmapper.requests.DefaultRequestWrapper;
import me.ferrybig.javacoding.webmapper.requests.RequestMapper;
import io.netty.channel.ChannelHandlerContext;
import java.nio.charset.Charset;
import java.util.Optional;
import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Fernando
 */
public class DefaultRequestMapperTest {

	private static final EndpointResult RESULT
			= new EndpointResult(EndpointResult.Result.OK, "", EndpointResult.ContentType.TEXT);

	@Test
	public void requiredUserDataMissingTest() {
		DefaultRequestWrapper wrap = new DefaultRequestWrapper(Object.class) {

			@Override
			public EndpointResult handle(ChannelHandlerContext ctx, String endpoint, Session session, Optional userData) {
				fail();
				return null;
			}

			{
				this.setAllowUserData(DefaultRequestWrapper.DataType.REQUIRED);
			}
		};
		
		assertEquals(wrap.handleHttpRequest(null, null, null, Optional.empty()).getResult(), Result.SYNTAX_ERROR);
	}

	@Test
	public void requiredUserDataWorkingTest() {
		DefaultRequestWrapper wrap = new DefaultRequestWrapper(Object.class) {

			@Override
			public EndpointResult handle(ChannelHandlerContext ctx, String endpoint, Session session, Optional userData) {
				return RESULT;
			}

			{
				this.setAllowUserData(DefaultRequestWrapper.DataType.REQUIRED);
			}
		};
		
		assertEquals(wrap.handleHttpRequest(null, null, null, Optional.of(new Object())).getResult(), Result.OK);
	}

	@Test
	public void automaticCastingWorkingTest() {
		DefaultRequestWrapper wrap = new DefaultRequestWrapper(JSONObject.class) {

			@Override
			public EndpointResult handle(ChannelHandlerContext ctx, String endpoint, Session session, Optional userData) {
				return RESULT;
			}

			{
				this.setAllowUserData(DefaultRequestWrapper.DataType.REQUIRED);
			}
		};
		
		assertEquals(wrap.handleHttpRequest(null, null, null, Optional.of(new JSONObject())).getResult(), Result.OK);
	}

	@Test
	public void automaticCastingFailedTest() {
		DefaultRequestWrapper wrap = new DefaultRequestWrapper(JSONObject.class) {

			@Override
			public EndpointResult handle(ChannelHandlerContext ctx, String endpoint, Session session, Optional userData) {
				return RESULT;
			}

			{
				this.setAllowUserData(DefaultRequestWrapper.DataType.REQUIRED);
			}
		};
		
		assertEquals(wrap.handleHttpRequest(null, null, null, Optional.of(new Object())).getResult(), Result.SYNTAX_ERROR);
	}
}
