/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.util;

import me.ferrybig.javacoding.webmapper.exceptions.StreamException;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 *
 * @author Fernando
 */
public class StreamExceptionWrapper {

	public static <T, U> Function<T, U>
			wrapEx(ExceptionFunction<T, U, ?> f) {
		return (T i) -> {
			try {
				return f.apply(i);
			} catch (Exception ex) {
				if (ex instanceof RuntimeException) {
					throw (RuntimeException) ex;
				}
				throw new StreamException(ex);
			}
		};
	}

	public static <R, T extends Throwable> R unwrap(Supplier<R> r, Class<? extends T> ex) throws T {
		try {
			return r.get();
		} catch (StreamException ex1) {
			Throwable upstream = ex1.getCause();
			if (ex.isAssignableFrom(upstream.getClass())) {
				throw ex.cast(upstream);
			}
			throw ex1;
		}
	}

	public interface ExceptionFunction<T, R, E extends Exception> {

		/**
		 * Applies this function to the given argument.
		 *
		 * @param t the function argument
		 * @return the function result
		 * @throws E the checked exception
		 */
		R apply(T t) throws E;
	}
}
