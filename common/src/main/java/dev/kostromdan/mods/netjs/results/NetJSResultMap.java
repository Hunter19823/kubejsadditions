package dev.kostromdan.mods.netjs.results;

import dev.kostromdan.mods.netjs.tasks.GistsTask;
import dev.kostromdan.mods.netjs.tasks.PasteBinTask;
import dev.kostromdan.mods.netjs.utils.NetJSUtils;

import java.util.LinkedHashMap;
import java.util.Map;

public class NetJSResultMap<K, V> extends LinkedHashMap<K, V> {
	private final Class<?> task_class;

	public NetJSResultMap(Class<?> task_class) {
		this.task_class = task_class;
	}

	public boolean isSuccess() {
		return (boolean) this.get("success");
	}

	public Exception getException() {
		return (Exception) this.get("exception");
	}

	public String getRaw() {
		if (PasteBinTask.class == task_class) {
			return this.get("raw_text").toString();
		}
		if (GistsTask.class == task_class) {
			var files = (Map) this.get("files");
			var first = (Map) files.get(files.keySet().iterator().next());
			return first.get("content").toString();
		}
		return null;
	}

	public Map<?, ?> parseRawToJson() {
		return NetJSUtils.parseRawJsonToMap(getRaw());
	}

}
