package pie.ilikepiefoo.fabric;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.latvian.mods.kubejs.script.ScriptType;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import pie.ilikepiefoo.KubeJSAdditions;
import net.fabricmc.api.ModInitializer;
import pie.ilikepiefoo.fabric.events.HudRenderEventJS;
import pie.ilikepiefoo.fabric.events.WorldRenderContextEventJS;

public class KubeJSAdditionsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        KubeJSAdditions.init();
		FabricEventHandler.init();
	}
}
