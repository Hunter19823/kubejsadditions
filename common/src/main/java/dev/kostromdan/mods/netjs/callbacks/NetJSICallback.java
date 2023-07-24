package dev.kostromdan.mods.netjs.callbacks;

import dev.kostromdan.mods.netjs.tasks.AbstractNetJSTask;

@FunctionalInterface
public interface NetJSICallback {
	void onCallback(AbstractNetJSTask callback);
}