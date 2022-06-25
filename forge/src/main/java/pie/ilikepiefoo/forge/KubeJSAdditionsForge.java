package pie.ilikepiefoo.forge;

import dev.architectury.platform.forge.EventBuses;
import pie.ilikepiefoo.KubeJSAdditions;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(KubeJSAdditions.MOD_ID)
public class KubeJSAdditionsForge {
    public KubeJSAdditionsForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(KubeJSAdditions.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        KubeJSAdditions.init();
    }
}
