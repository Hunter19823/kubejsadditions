package dev.kostromdan.mods.netjs.results;

import dev.latvian.mods.kubejs.util.JsonIO;
import dev.latvian.mods.kubejs.util.MapJS;

import java.util.Map;

public class NetJSResultSuccess extends NetJSResult {
	public NetJSResultSuccess(Map<String, Object> result) {
		this.success = true;
		this.result = result;
	}

	public String getRaw() {
		if (this instanceof NetJSPasteBinResultSuccess) {
			return result.get("raw_text").toString();
		}
		if (this instanceof NetJSGistsResultSuccess) {
			var files = (Map) result.get("files");
			var first = (Map) files.get(files.keySet().iterator().next());
			return first.get("content").toString();
		}
		return null;
	}

	public Map<?, ?> parseRawToJson() {
		return MapJS.of(JsonIO.parse(getRaw()));
	}
}
