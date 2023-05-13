package pie.ilikepiefoo;

import pie.ilikepiefoo.compat.jei.JEIEvents;
import pie.ilikepiefoo.events.AdditionalEvents;
import pie.ilikepiefoo.events.custom.ArchEventRegisterEventJS;

public class KubeJSAdditions {
	public static final String MOD_ID = "kubejsadditions";

	static {
		AdditionsPlugin.REGISTER_EVENT_HANDLER.add(() -> {
			AdditionalEvents.register();
			JEIEvents.register();
		});
		AdditionsPlugin.STARTUP_HANDLERS.add(() -> AdditionalEvents.ARCH_EVENT_REGISTER.post(new ArchEventRegisterEventJS()));
	}

	public static void init() {
		EventHandler.init();
	}
}