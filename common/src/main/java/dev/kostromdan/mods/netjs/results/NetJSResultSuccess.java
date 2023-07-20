package dev.kostromdan.mods.netjs.results;

import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.util.JsonIO;
import dev.latvian.mods.kubejs.util.MapJS;

import java.util.Map;

public abstract class NetJSResultSuccess extends NetJSResult {

	public NetJSResultSuccess(String id, JsonObject result) {
		this.success = true;
		this.id = id;
		this.result = result;
	}

	public String getRaw() {
		if (this instanceof NetJSPasteBinResultSuccess) {
			return result.get("raw_text").toString();
		}
		if (this instanceof NetJSGistsResultSuccess) {
			JsonObject gists_result = (JsonObject) result.get("gists_answer");
			JsonObject files = (JsonObject) gists_result.get("files");
			JsonObject first = (JsonObject) files.get(files.keySet().iterator().next());
			return first.get("content").toString();
		}
		return null;
	}

	public Map<?, ?> parseRawToJson() {
		return MapJS.of(JsonIO.parse(getRaw()));
	}
}
