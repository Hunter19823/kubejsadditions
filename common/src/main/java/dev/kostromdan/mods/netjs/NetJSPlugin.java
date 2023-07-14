package dev.kostromdan.mods.netjs;

import dev.kostromdan.mods.netjs.bindings.NetJSWrapper;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingsEvent;

public class NetJSPlugin extends KubeJSPlugin {
	@Override
	public void registerBindings(BindingsEvent event) {
		event.add("NetJS", NetJSWrapper.class);
	}
}
