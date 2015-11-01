/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper;

import java.nio.charset.Charset;
import org.json.JSONObject;

/**
 *
 * @author Fernando
 * @param <T>
 */
public class EndpointResult<T> {

	private final Result result;
	private final T data;
	private final Charset charset;
	private final ContentType<T> contentType;

	public EndpointResult(Result result, T data, Charset charset, ContentType<T> contentType) {
		this.result = result;
		this.data = data;
		this.charset = charset;
		this.contentType = contentType;
	}

	public Result getResult() {
		return result;
	}

	public T getData() {
		return data;
	}

	public Charset getCharset() {
		return charset;
	}

	public ContentType<T> getContentType() {
		return contentType;
	}

	public enum Result {
		OK,
		NO_PERMISSIONS,
		UNKNOWN_ENDPOINT,
		SYNTAX_ERROR,
		CONFLICT,
		AUTH_REQUIRED
	}

	public abstract static class ContentType<T> {

		public static final ContentType<JSONObject> JSON = new ContentType<JSONObject>() {

			@Override
			public byte[] toBytes(JSONObject t, Charset charset) {
				return t.toString().getBytes(charset);
			}

		};
		public static final ContentType<String> TEXT = new ContentType<String>() {

			@Override
			public byte[] toBytes(String t, Charset charset) {
				return t.getBytes(charset);
			}

		};
		public static final ContentType<byte[]> BYTE = new ContentType<byte[]>() {

			@Override
			public byte[] toBytes(byte[] t, Charset charset) {
				return t;
			}

		};

		private ContentType() {
		}

		public abstract byte[] toBytes(T t, Charset charset);

	}
}
