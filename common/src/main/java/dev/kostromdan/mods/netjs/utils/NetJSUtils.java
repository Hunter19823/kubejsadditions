package dev.kostromdan.mods.netjs.utils;

import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.util.JsonIO;
import dev.latvian.mods.kubejs.util.MapJS;

import java.util.LinkedHashMap;

public interface NetJSUtils {
	static boolean validIdChar(char c) {
		return c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c >= '0' && c <= '9';
	}

	static boolean isValidId(String s, int max_len) {
		if (s.length() > max_len) {
			return false;
		}
		for (int i = 0; i < s.length(); ++i) {
			if (!validIdChar(s.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	static LinkedHashMap<String, Object> parseRawJsonToMap(String raw_text) {
		JsonObject json_response_result = (JsonObject) JsonIO.parseRaw(raw_text);// Parsing raw json string response to JsonObject
		return (LinkedHashMap<String, Object>) MapJS.of(json_response_result); // Converting Json classes to Java classes
	}
}
