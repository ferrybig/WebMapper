/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.util;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.function.Supplier;

/**
 *
 * @author Fernando
 * @param <T>
 */
public class LazyObjectCreator<T> implements Supplier<T> {
	private final boolean weakReference;
	private Reference<T> data = null;
	private T rawData = null;
	private final Supplier<? extends T> factory;

	public LazyObjectCreator(Supplier<? extends T> factory) {
		this(false, factory);
	}
	
	public LazyObjectCreator(boolean weakReference, Supplier<? extends T> factory) {
		this.weakReference = weakReference;
		this.factory = factory;
	}
	
	public boolean has() {
		T data;
		if(this.data != null)
			data = this.data.get();
		else
			data = rawData;
		return data != null;
	}
	
	@Override
	public T get() {
		T data;
		if(this.data != null)
			data = this.data.get();
		else
			data = rawData;
		if(data == null) {
			data = factory.get();
			if(weakReference)
				this.data = new SoftReference<>(data);
			else
				this.rawData = data;
		}
		return data;
	}
	
}
