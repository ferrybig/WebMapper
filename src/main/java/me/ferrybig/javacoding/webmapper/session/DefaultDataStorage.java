/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.session;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Fernando
 */
public class DefaultDataStorage implements DataStorage {
	
	private final List<Object> data;
	
	public DefaultDataStorage() {
		data = new LinkedList<>();
	}
	
	public DefaultDataStorage(Collection<?> data) {
		this();
		this.data.addAll(data);
	}
	
	@Override
	public void setData(Collection<?> data) {
		this.data.clear();
		this.data.addAll(data);
	}

	@Override
	public Collection<?> getData() {
		return this.data;
	}
}
