/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.util;

import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * http://stackoverflow.com/a/22741679/1542723
 * @author nosid
 */
public class StreamUtil {

	public static <T> Stream<T> concat(Stream<? extends T> lhs, Stream<? extends T> rhs) {
		return Stream.concat(lhs, rhs);
	}

	public static <T> Stream<T> concat(Stream<? extends T> lhs, T rhs) {
		return Stream.concat(lhs, Stream.of(rhs));
	}

	private static <T, A, R, S> Collector<T, ?, S> combine(Collector<T, A, R> collector, Function<? super R, ? extends S> function) {
		return Collector.of(
				collector.supplier(),
				collector.accumulator(),
				collector.combiner(),
				collector.finisher().andThen(function));
	}

	public static <T> Collector<T, ?, Stream<T>> concat(Stream<? extends T> other) {
		return combine(Collectors.toList(),
				list -> Stream.concat(list.stream(), other));
	}

	public static <T> Collector<T, ?, Stream<T>> concat(T element) {
		return concat(Stream.of(element));
	}
}
