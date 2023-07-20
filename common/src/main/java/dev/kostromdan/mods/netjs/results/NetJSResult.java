package dev.kostromdan.mods.netjs.results;

import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.util.MapJS;

import java.util.Map;

public class NetJSResult {
	public String id = null;
	public boolean success;
	protected JsonObject result = null;

	public Map<?, ?> getResult() {
		return MapJS.of(result);
	}


}
