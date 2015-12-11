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
import static org.junit.Assert.fail;

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

	public static void assertEqualsAndHashcodeWorks(Object o1, Object o2, boolean result) {
		if (o2.equals(o1) != result) {
			fail(o2 + ".equals(" + o1 + ") doesn't angree on excuality with " + result);
		}
		if (o1.equals(o2) != result) {
			fail(o1 + ".equals(" + o2 + ") doesn't angree on excuality with " + result);
		}
		if (!result) {
			return;
		}
		if (o1.hashCode() != o2.hashCode()) {
			fail("Hashcodes of " + o1 + " and " + o2 + " are not the same!");
		}
	}
}
