package me.ferrybig.javacoding.webmapper.test;

import java.nio.charset.Charset;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Fernando
 */
public class DefaultTest {
	@Test
	public void defaultCharsetTests() {
		assertTrue(Charset.isSupported("UTF-8"));
	}
}
