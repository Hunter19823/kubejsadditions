package dev.kostromdan.mods.netjs.results;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class NetJSResult implements Map<String, Object> {
	public boolean success;
	protected LinkedHashMap<String, Object> result = null;

	@Override
	public int size() {
		return result.size();
	}

	@Override
	public boolean isEmpty() {
		return result.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return result.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return result.containsValue(value);
	}

	@Override
	public Object get(Object key) {
		return result.get(key);
	}

	@Override
	public Object put(String key, Object value) {
		return result.put(key, value);
	}

	@Override
	public Object remove(Object key) {
		return result.remove(key);
	}

	@Override
	public void putAll(Map<? extends String, ?> m) {
		result.putAll(m);
	}

	@Override
	public void clear() {
		result.clear();
	}

	@Override
	public Set<String> keySet() {
		return result.keySet();
	}

	@Override
	public Collection<Object> values() {
		return result.values();
	}

	@Override
	public Set<Map.Entry<String, Object>> entrySet() {
		return result.entrySet();
	}
}
