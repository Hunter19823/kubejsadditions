package pie.ilikepiefoo;

import dev.latvian.mods.kubejs.script.ScriptType;
import pie.ilikepiefoo.events.custom.ArchEventRegisterEventJS;

public class KubeJSAdditions {
	public static final String MOD_ID = "kubejsadditions";

	static {
		AdditionsPlugin.STARTUP_HANDLERS.add(() -> {
			ArchEventRegisterEventJS event = new ArchEventRegisterEventJS();
			event.post(ScriptType.STARTUP, AdditionalEventsJS.ARCH_REGISTER);
		});
	}

	public static void init() {
		EventHandler.init();
	}
}