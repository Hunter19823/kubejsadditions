package dev.kostromdan.mods.netjs.results;

import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.util.JsonIO;
import dev.latvian.mods.kubejs.util.MapJS;

import java.util.Map;

public class NetJSResult {
	public String id = null;
	public boolean success;
	protected JsonObject result = null;

	public String getRaw() {
		return result.get("raw_text").toString();
	}

	public Map<?, ?> getResult() {
		return MapJS.of(result);
	}

	public Map<?, ?> parseRawToJson() {
		return MapJS.of(JsonIO.parse(getRaw()));
	}
}
