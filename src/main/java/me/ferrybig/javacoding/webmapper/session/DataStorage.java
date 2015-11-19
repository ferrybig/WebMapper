/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.session;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

/**
 *
 * @author Fernando
 */
public interface DataStorage {
	public void setData(Collection<?> data);
	
	public default void setData(Object data) {
		setData(Collections.singletonList(data));
	}
	
	public default void clear() {
		getData().clear();
	}
	
	public default DataStorage setDataOrClear(Object data) {
		if(data == null)
			setData(Collections.emptyList());
		else
			setData(data);
		return this;
	}
	
	public default boolean hasData() {
		return !getData().isEmpty();
	}
	
	public Collection<?> getData();
	
	public default <T> Optional<T> getDataAs(Class<T> type) {
		return getData().stream().filter((v) -> (type.isAssignableFrom(v.getClass()))).map(type::cast).findAny();
	}
	
	public default boolean hasData(Class<?> type) {
		return getData().stream().anyMatch((v) -> (type.isAssignableFrom(v.getClass())));
	}
}
