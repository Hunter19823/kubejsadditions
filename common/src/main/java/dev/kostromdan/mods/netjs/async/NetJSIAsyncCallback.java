package dev.kostromdan.mods.netjs.async;

import dev.kostromdan.mods.netjs.results.NetJSResult;

@FunctionalInterface
public interface NetJSIAsyncCallback {
	void onCallback(NetJSResult callback);
}