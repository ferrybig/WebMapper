/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper;

import java.nio.charset.Charset;
import java.util.Objects;
import java.util.Optional;
import org.json.JSONObject;

/**
 *
 * @author Fernando
 * @param <T>
 */
public class EndpointResult<T> {

	public static final Charset UTF8 = Charset.forName("UTF-8");
	private final Result result;
	private final T data;
	private final ContentType<T> contentType;

	public EndpointResult(Result result, T data, ContentType<T> contentType) {
		this.result = Objects.requireNonNull(result);
		this.data = Objects.requireNonNull(data);
		this.contentType = Objects.requireNonNull(contentType);
	}

	public Result getResult() {
		return result;
	}

	public T getData() {
		return data;
	}
	
	public byte[] asBytes(Optional<Charset> set) {
		return this.getContentType().toBytes(data, set);
	}

	public ContentType<T> getContentType() {
		return contentType;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 59 * hash + Objects.hashCode(this.result);
		hash = 59 * hash + Objects.hashCode(this.data);
		hash = 59 * hash + Objects.hashCode(this.contentType);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final EndpointResult<?> other = (EndpointResult<?>) obj;
		if (this.result != other.result) {
			return false;
		}
		if (!Objects.equals(this.data, other.data)) {
			return false;
		}
		if (!Objects.equals(this.contentType, other.contentType)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "EndpointResult{" + "result=" + result + ", data=" + data + ", contentType=" + contentType + '}';
	}

	public enum Result {
		OK,
		NO_PERMISSIONS,
		UNKNOWN_ENDPOINT,
		SYNTAX_ERROR,
		CONFLICT,
		AUTH_REQUIRED,
		SERVER_ERROR,
	}

	public abstract static class ContentType<T> {

		public static final ContentType<JSONObject> JSON = new ContentType<JSONObject>(true) {

			@Override
			public byte[] toBytes0(JSONObject t, Charset charset) {
				return t.toString().getBytes(charset);
			}

		};
		public static final ContentType<String> TEXT = new ContentType<String>(true) {

			@Override
			public byte[] toBytes0(String t, Charset charset) {
				return t.getBytes(charset);
			}

		};
		public static final ContentType<String> HTML = new ContentType<String>(true) {

			@Override
			public byte[] toBytes0(String t, Charset charset) {
				return t.getBytes(charset);
			}

		};
		public static final ContentType<byte[]> BYTE = new ContentType<byte[]>(false) {

			@Override
			public byte[] toBytes0(byte[] t, Charset charset) {
				return t;
			}

		};
		public static final ContentType<byte[]> PNG = new ContentType<byte[]>(false) {

			@Override
			public byte[] toBytes0(byte[] t, Charset charset) {
				return t;
			}

		};
		public static final ContentType<byte[]> JPEG = new ContentType<byte[]>(false) {

			@Override
			public byte[] toBytes0(byte[] t, Charset charset) {
				return t;
			}

		};
		public static final ContentType<String> JAVASCRIPT = new ContentType<String>(true) {

			@Override
			public byte[] toBytes0(String t, Charset charset) {
				return t.getBytes(charset);
			}

		};
		public static final ContentType<String> CSS = new ContentType<String>(true) {

			@Override
			public byte[] toBytes0(String t, Charset charset) {
				return t.getBytes(charset);
			}

		};
		private final boolean requiresCharset;

		private ContentType(boolean requiresCharset) {
			this.requiresCharset = requiresCharset;
		}

		public byte[] toBytes(T t, Optional<Charset> charset) {
			if(this.requiresCharset) {
				return this.toBytes0(t, charset.get());
			} else {
				return this.toBytes0(t, UTF8);
			}
		}
		
		public abstract byte[] toBytes0(T t, Charset charset);

		public boolean isRequiresCharset() {
			return requiresCharset;
		}

	}
}
