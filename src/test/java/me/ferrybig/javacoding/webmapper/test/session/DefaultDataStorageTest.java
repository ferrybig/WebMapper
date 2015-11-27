/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.test.session;

import me.ferrybig.javacoding.webmapper.session.DefaultDataStorage;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.json.JSONObject;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author Fernando
 */
public class DefaultDataStorageTest {
	
	private DefaultDataStorage data;

	@Before
	public void init() {
		data = new DefaultDataStorage();
	}
	
	@Test
	public void setAndGetDataTest() {
		data.setData(Collections.emptyList());
		assertEquals(0, data.getData().size());
		data.setData(Collections.singleton(new Object()));
		assertEquals(1, data.getData().size());
	}

	@Test
	public void getDataAsSupportsOneBackendTypeTest() {
		Object o;
		data.setData(Collections.singleton(o = new Object()));
		assertEquals(o, extr(data.getDataAs(Object.class)));
	}

	@Test
	public void getDataAsSupportsOneBackendSuperCastTypeTest() {
		Object o;
		data.setData(Collections.singleton(o = BigInteger.ZERO));
		assertEquals(o, extr(data.getDataAs(Object.class)));
	}

	@Test
	public void getDataAsSupportsOneBackendDownCastTypeTest() {
		data.setData(Collections.singleton(new Object()));
		assertEquals(null, extr(data.getDataAs(BigInteger.class)));
	}

	@Test
	public void getDataAsChoosesCorrectBackendTypeTest() {
		List<Object> list = new ArrayList<>();
		list.add(new Object());
		list.add(BigInteger.ZERO);
		list.add("string");
		list.add(Boolean.FALSE);
		list.add(new JSONObject());
		data.setData(list);
		assertEquals("string", extr(data.getDataAs(String.class)));
	}

	@Test
	public void getDataReturnsBestType() {
		Object r = new Object();
		List<Object> list = new ArrayList<>();
		list.add(new JSONObject());
		list.add(r);
		data.setData(list);
		assertEquals(r, extr(data.getDataAs(Object.class)));
	}

	private static <T> T extr(Optional<T> in) {
		if (in.isPresent()) {
			return in.get();
		}
		return null;
	}
}
