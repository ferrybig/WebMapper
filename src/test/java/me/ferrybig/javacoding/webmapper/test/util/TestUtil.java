/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.test.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author Fernando
 */
public class TestUtil {

	public static <T> void assertCollectionOnlyContains(Collection<T> haystack, Collection<? extends T> search) {
		String message = "The contents of " + haystack + " aren't matching " + search;
		List<T> tmp = new ArrayList<>(haystack);
		assertTrue(message, tmp.containsAll(search));
		tmp.removeAll(search);
		assertTrue(message, tmp.isEmpty());
	}
}
