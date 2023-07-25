package dev.kostromdan.mods.netjs.callbacks;

import dev.kostromdan.mods.netjs.results.NetJSResultMap;

@FunctionalInterface
public interface NetJSICallback {
	void onCallback(NetJSResultMap<String, Object> callback);
}