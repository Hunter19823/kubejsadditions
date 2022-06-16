package pie.ilikepiefoo.fabric;

import pie.ilikepiefoo.KubeJSAdditions;
import net.fabricmc.api.ModInitializer;

public class KubeJSAdditionsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        KubeJSAdditions.init();
    }
}
