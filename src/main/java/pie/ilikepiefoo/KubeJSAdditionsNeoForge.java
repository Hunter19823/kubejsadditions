package pie.ilikepiefoo;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(KubeJSAdditions.MOD_ID)
public class KubeJSAdditionsNeoForge {
    public KubeJSAdditionsNeoForge(IEventBus modEventBus) {
        KubeJSAdditions.init();
    }
}
