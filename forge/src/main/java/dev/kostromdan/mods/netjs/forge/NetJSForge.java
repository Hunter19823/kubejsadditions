package dev.kostromdan.mods.netjs.forge;

import dev.architectury.platform.forge.EventBuses;
import dev.kostromdan.mods.netjs.NetJS;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(NetJS.MOD_ID)
public class NetJSForge {
	public NetJSForge() {
		// Submit our event bus to let architectury register our content on the right time
		EventBuses.registerModEventBus(NetJS.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
	}
}
