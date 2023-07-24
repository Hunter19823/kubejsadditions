package dev.kostromdan.mods.netjs.tasks;

import dev.kostromdan.mods.netjs.callbacks.NetJSICallback;
import dev.kostromdan.mods.netjs.utils.NetJSUtils;
import dev.latvian.mods.kubejs.util.ConsoleJS;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public abstract class AbstractNetJSTask implements Runnable, Map<String, Object> {


	public String id;
	public LinkedHashMap<String, Object> result = null;
	public boolean success;
	public Exception exception;

	public NetJSICallback callback;

	public AbstractNetJSTask(String id) {
		result = new LinkedHashMap<String, Object>();
		this.id = id;
	}

	public String getRaw() {
		if (this instanceof PasteBinTask) {
			return result.get("raw_text").toString();
		}
		if (this instanceof GistsTask) {
			var files = (Map) result.get("files");
			var first = (Map) files.get(files.keySet().iterator().next());
			return first.get("content").toString();
		}
		return null;
	}

	public void callback() {
		try {
			callback.onCallback(this);
		} catch (Throwable ex) {
			ConsoleJS.SERVER.error("Error occurred while handling async NetJS callback: " + ex.getMessage());
			ex.printStackTrace();
		}
	}


	public Map<?, ?> parseRawToJson() {
		return NetJSUtils.parseRawJsonToMap(getRaw());
	}

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
