package pie.ilikepiefoo.fabric;

import net.fabricmc.api.ModInitializer;
import pie.ilikepiefoo.KubeJSAdditions;

public class KubeJSAdditionsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        KubeJSAdditions.init();
		FabricEventHandler.init();
		FabricEventsJS.register();
	}
}
