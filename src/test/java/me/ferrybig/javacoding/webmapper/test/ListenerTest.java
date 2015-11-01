/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.test;

import me.ferrybig.javacoding.webmapper.Listener;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Fernando
 */
public class ListenerTest {
	@Test
	public void equalsAndHashCodeTest() {
		final Listener l1 = new Listener("localhost",80,false);
		final Listener l2 = new Listener("localhost",80,false);
	    final Listener l3 = new Listener("127.0.0.1",80,false);
		assertEquals(l1, l2);
		assertEquals(l1.hashCode(), l2.hashCode());
		assertNotEquals(12, l3);
		assertNotEquals(11, l3);
	}
}
