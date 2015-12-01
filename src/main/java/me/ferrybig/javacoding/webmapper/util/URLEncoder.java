/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 *
 * @author Fernando
 */
public class URLEncoder {

	private URLEncoder() {
	}

	public static String encode(String input) {
		try {
			return java.net.URLEncoder.encode(input, "UTF-8");
		} catch (UnsupportedEncodingException ex) {
			throw new AssertionError("Cannot find UTF-8 charset, java broken", ex);
		}
	}

	public static String encode(String input, Charset set) {
		try {
			return java.net.URLEncoder.encode(input, set.name());
		} catch (UnsupportedEncodingException ex) {
			throw new AssertionError("Cannot find charset " + set.displayName(), ex);
		}
	}
}
