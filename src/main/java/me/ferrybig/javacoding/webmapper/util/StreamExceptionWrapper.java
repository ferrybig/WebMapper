/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.util;

import me.ferrybig.javacoding.webmapper.exceptions.StreamException;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 *
 * @author Fernando
 */
public class StreamExceptionWrapper {

	@Deprecated
	public static <T, U> Function<T, U> wrapEx(ExceptionFunction<T, U, ?> f) {
		return function(f);
	}
	
	public static <T, U> Function<T, U> function(ExceptionFunction<T, U, ?> f) {
		return new Function<T, U>() {

			@Override
			public U apply(T i) {
				try {
					return f.apply(i);
				} catch (Exception ex) {
					if (ex instanceof RuntimeException) {
						throw (RuntimeException) ex;
					}
					throw new StreamException(ex);
				}
			}
		};
	}

	public static <T, S, U> BiFunction<T, S, U> biFunction(ExceptionBiFunction<T, S, U, ?> f) {
		return new BiFunction<T, S, U>() {

			@Override
			public U apply(T i, S s) {
				try {
					return f.apply(i, s);
				} catch (Exception ex) {
					if (ex instanceof RuntimeException) {
						throw (RuntimeException) ex;
					}
					throw new StreamException(ex);
				}
			}
		};
	}

	public static <U> Supplier<U> supplier(ExceptionSupplier<U, ?> f) {
		return new Supplier<U>() {

			@Override
			public U get() {
				try {
					return f.get();
				} catch (Exception ex) {
					if (ex instanceof RuntimeException) {
						throw (RuntimeException) ex;
					}
					throw new StreamException(ex);
				}
			}
		};
	}

	public static <U> Consumer<U> consumer(ExceptionConsumer<U, ?> f) {
		return new Consumer<U>() {

			@Override
			public void accept(U u) {
				try {
					f.accept(u);
				} catch (Exception ex) {
					if (ex instanceof RuntimeException) {
						throw (RuntimeException) ex;
					}
					throw new StreamException(ex);
				}
			}
		};
	}

	@FunctionalInterface
	public interface ExceptionFunction<T, R, E extends Exception> {

		R apply(T t) throws E;
	}

	@FunctionalInterface
	public interface ExceptionConsumer<T, E extends Exception> {

		void accept(T t) throws E;
	}

	@FunctionalInterface
	public interface ExceptionBiFunction<T, S, R, E extends Exception> {

		R apply(T t, S s) throws E;
	}

	@FunctionalInterface
	public interface ExceptionSupplier<R, E extends Exception> {

		R get() throws E;
	}
}
