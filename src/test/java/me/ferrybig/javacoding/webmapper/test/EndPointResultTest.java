/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.test;

import me.ferrybig.javacoding.webmapper.EndpointResult;
import me.ferrybig.javacoding.webmapper.EndpointResult.ContentType;
import me.ferrybig.javacoding.webmapper.EndpointResult.Result;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Test;
/**
 *
 * @author Fernando
 */
public class EndPointResultTest {
	
	@Test
	public void equalsTest() {
		EndpointResult result1 = new EndpointResult<>(Result.OK, "Hello", EndpointResult.ContentType.TEXT);
		EndpointResult result2 = new EndpointResult<>(Result.OK, "Hello", EndpointResult.ContentType.TEXT);
		EndpointResult result3 = new EndpointResult<>(Result.OK, "Hello World!", EndpointResult.ContentType.TEXT);
		EndpointResult result4 = new EndpointResult<>(Result.OK, "Hello World!", EndpointResult.ContentType.HTML);
		
		assertEquals(result1, result2);
		assertEquals(result1.hashCode(), result2.hashCode());
		assertNotEquals(result1, result3);
		assertNotEquals(result1, result4);
		assertNotEquals(result2, result3);
		assertNotEquals(result2, result4);
		assertNotEquals(result3, result4);
	}
	
	@Test
	public void noEqualsContentTypes() throws IllegalAccessException {
		Field[] fields = EndpointResult.ContentType.class.getFields();
		List<ContentType> seen = new ArrayList<>();
		for(Field f : fields) {
			if(java.lang.reflect.Modifier.isStatic(f.getModifiers())) {
				ContentType get = (ContentType)f.get(null);
				assertTrue(!seen.contains(get));
				seen.add(get);
			}
		}
	}
	
	@Test(expected = NullPointerException.class)
	public void nullResultTest() {
		new EndpointResult<>(null, "Hello", EndpointResult.ContentType.TEXT).toString();
		Assert.fail();
	}
	
	@Test(expected = NullPointerException.class)
	public void nullDataTest() {
		new EndpointResult<>(Result.OK, null, EndpointResult.ContentType.TEXT).toString();
		Assert.fail();
	}
	
	@Test(expected = NullPointerException.class)
	public void nullContentTypeTest() {
		new EndpointResult<>(Result.OK, "Hello", null).toString();
		Assert.fail();
	}
	
	@Test(expected = NullPointerException.class)
	public void requiredCharsetAsNullContentTypeTest() {
		ContentType.HTML.toBytes("", null);
	}
	
	@Test(expected = NoSuchElementException.class)
	public void requiredCharsetAsEmptyOptionalContentTypeTest() {
		ContentType.HTML.toBytes("", Optional.empty());
	}
	
	@Test
	public void requiredCharsetAsCorrectCharsetContentTypeTest() {
		ContentType.HTML.toBytes("", Optional.of(Charset.defaultCharset()));
	}
}
