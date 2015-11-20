/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.util;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.json.JSONObject;

/**
 *
 * @author Fernando
 */
public class JsonObjectMap extends AbstractMap<String, Object> {

	private final Set<Entry<String, Object>> set;

	public JsonObjectMap(JSONObject map) {
		set = new JsonObjectMapEntrySet(map);
	}

	@Override
	public Set<Entry<String, Object>> entrySet() {
		return set;
	}

	private class JsonObjectMapEntrySet extends AbstractSet<Entry<String, Object>> {

		private final JSONObject map;

		private JsonObjectMapEntrySet(JSONObject map) {
			this.map = Objects.requireNonNull(map, "map == null");
		}

		@Override
		public Iterator<Entry<String, Object>> iterator() {
			@SuppressWarnings("unchecked")
			final Iterator<String> i = map.keys();
			return new Iterator<Entry<String, Object>>() {

				@Override
				public boolean hasNext() {
					return i.hasNext();
				}

				@Override
				public Entry<String, Object> next() {
					String k;
					return new SimpleEntry<>(k = i.next(), map.get(k));
				}

			};
		}

		@Override
		public int size() {
			return map.length();
		}

	}
}
