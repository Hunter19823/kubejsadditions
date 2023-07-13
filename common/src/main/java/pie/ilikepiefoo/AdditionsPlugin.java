package pie.ilikepiefoo;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingsEvent;
import pie.ilikepiefoo.bindings.NetJSWrapper;

public class AdditionsPlugin extends KubeJSPlugin {
	@Override
	public void registerBindings(BindingsEvent event) {
		event.add("NetJS", NetJSWrapper.class);
	}
}
