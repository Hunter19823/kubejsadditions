package pie.ilikepiefoo;

import dev.latvian.mods.kubejs.script.ScriptType;
import pie.ilikepiefoo.events.custom.ArchEventRegisterEventJS;
import pie.ilikepiefoo.events.custom.RegisterProxyEventJS;

public class KubeJSAdditions {
	public static final String MOD_ID = "kubejsadditions";

	static {
		AdditionsPlugin.STARTUP_HANDLERS.add(() -> {
			ArchEventRegisterEventJS event = new ArchEventRegisterEventJS();
			event.post(ScriptType.STARTUP, AdditionalEventsJS.ARCH_REGISTER);
		});
        AdditionsPlugin.STARTUP_HANDLERS.add(() -> {
            RegisterProxyEventJS event = new RegisterProxyEventJS();
            event.post(ScriptType.STARTUP, AdditionalEventsJS.PROXY_EVENT_REGISTER);
        });
	}

	public static void init() {
		EventHandler.init();
	}
}