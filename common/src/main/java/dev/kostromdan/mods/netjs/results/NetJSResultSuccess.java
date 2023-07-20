package dev.kostromdan.mods.netjs.results;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.latvian.mods.kubejs.util.JsonIO;
import org.jsoup.nodes.Element;

public class NetJSResultSuccess extends NetJSResult {

	public NetJSResultSuccess(String id, JsonObject result) {
		this.success = true;
		this.id = id;
		this.result = result;
	}
}
