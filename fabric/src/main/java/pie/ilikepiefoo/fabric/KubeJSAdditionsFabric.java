package pie.ilikepiefoo.fabric;

import dev.latvian.mods.kubejs.script.ScriptType;
import net.fabricmc.api.ModInitializer;
import pie.ilikepiefoo.AdditionsPlugin;
import pie.ilikepiefoo.KubeJSAdditions;
import pie.ilikepiefoo.fabric.events.custom.FabricEventRegisterEventJS;

public class KubeJSAdditionsFabric implements ModInitializer {
	static {
		AdditionsPlugin.STARTUP_HANDLERS.add(() -> {
			FabricEventRegisterEventJS event = new FabricEventRegisterEventJS();
			event.post(ScriptType.STARTUP, FabricEventsJS.FABRIC_EVENT_REGISTER);
		});
	}

	@Override
	public void onInitialize() {
		KubeJSAdditions.init();
		FabricEventHandler.init();
	}
}
