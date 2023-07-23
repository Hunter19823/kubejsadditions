package dev.kostromdan.mods.netjs.callbacks;

import dev.kostromdan.mods.netjs.results.NetJSResult;

@FunctionalInterface
public interface NetJSICallback {
	void onCallback(NetJSResult callback);
}