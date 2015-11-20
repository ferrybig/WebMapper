/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.util;

import java.util.AbstractList;
import java.util.Objects;
import org.json.JSONArray;

/**
 *
 * @author Fernando
 */
public class JsonArrayCollection extends AbstractList<Object> {

	private final JSONArray arr;

	public JsonArrayCollection(JSONArray arr) {
		this.arr = Objects.requireNonNull(arr, "arr == null");
	}
	
	@Override
	public Object get(int index) {
		return this.arr.get(index);
	}

	@Override
	public int size() {
		return this.arr.length();
	}
	
}
