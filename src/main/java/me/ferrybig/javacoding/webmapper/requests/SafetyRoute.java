/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.requests;

import me.ferrybig.javacoding.webmapper.EndpointResult;
import me.ferrybig.javacoding.webmapper.requests.requests.WebServerRequest;
import me.ferrybig.javacoding.webmapper.requests.routes.NoPermissionRoute;
import java.util.Arrays;

/**
 * Blocks a large number of characters that might be even later
 *
 * @author Fernando
 */
public class SafetyRoute implements RequestMapper {

	private final RequestMapper upstream;
	private final SafetyLevel level;
	private final long maxUriLength;

	public SafetyRoute(RequestMapper upstream, SafetyLevel level, long maxUriLength) {
		this.upstream = upstream;
		this.level = level;
		this.maxUriLength = maxUriLength;
	}

	@Override
	public EndpointResult<?> handleHttpRequest(WebServerRequest req) {
		if(req.endpoint().length() < maxUriLength && level.isSafe(req.endpoint())) {
			return upstream.handleHttpRequest(req);
		}
		return NoPermissionRoute.TEXT.handleHttpRequest(req);
	}

	public static enum SafetyLevel {

		SAFE("abcdefghijklmnopqrstuvwxyz"
				+ "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
				+ "0123456789"
				+ ".,/?%&"),
		MODERATE(SAFE, "'!@#$%^*(){}[]~"),
		RISKY(MODERATE, "\\\"`"),
		UNFILTERED(new char[0]) {
					@Override
					public boolean isAllowed(char a) {
						return true;
					}

					@Override
					public boolean isSafe(String str) {
						return true;
					}
				};

		private final char[] allowed;

		private SafetyLevel(String allowed) {
			this(allowed.toCharArray());
		}

		private SafetyLevel(SafetyLevel parent, String allowed) {
			this(concat(parent.allowed, allowed.toCharArray()));
		}

		private SafetyLevel(char[] allowed) {
			Arrays.sort(allowed);
			this.allowed = allowed;
		}

		private static char[] concat(char[] a, char[] b) {
			int aLen = a.length;
			int bLen = b.length;
			char[] c = new char[aLen + bLen];
			System.arraycopy(a, 0, c, 0, aLen);
			System.arraycopy(b, 0, c, aLen, bLen);
			return c;
		}

		public boolean isAllowed(char a) {
			return Arrays.binarySearch(allowed, a) > -1;
		}

		@SuppressWarnings("empty-statement")
		public boolean isSafe(String str) {
			char[] arr = str.toCharArray();
			boolean safe = true;
			for (int i = 0; i < arr.length && (safe = safe && isAllowed(arr[i])); i++);
			return safe;
		}
	}

}
