package pie.ilikepiefoo.fabric;

import net.fabricmc.api.ModInitializer;
import pie.ilikepiefoo.AdditionsPlugin;
import pie.ilikepiefoo.KubeJSAdditions;
import pie.ilikepiefoo.fabric.events.custom.FabricEventRegisterEventJS;

import static pie.ilikepiefoo.fabric.FabricEventsJS.FABRIC_EVENT_REGISTER;

public class KubeJSAdditionsFabric implements ModInitializer {
	static {
		AdditionsPlugin.REGISTER_EVENT_HANDLER.add(FabricEventsJS::register);
		AdditionsPlugin.STARTUP_HANDLERS.add(() -> FABRIC_EVENT_REGISTER.post(new FabricEventRegisterEventJS()));
	}

	@Override
	public void onInitialize() {
		KubeJSAdditions.init();
		FabricEventHandler.init();
	}
}
