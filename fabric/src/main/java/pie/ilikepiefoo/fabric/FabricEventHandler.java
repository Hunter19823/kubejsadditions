package pie.ilikepiefoo.fabric;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.latvian.mods.kubejs.script.ScriptType;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;
import pie.ilikepiefoo.fabric.events.BeforeBlockOutlineRenderEventJS;
import pie.ilikepiefoo.fabric.events.BlockOutlineRenderEventJS;
import pie.ilikepiefoo.fabric.events.HudRenderEventJS;
import pie.ilikepiefoo.fabric.events.WorldRenderContextEventJS;

public class FabricEventHandler {

	public static void init() {
		WorldRenderEvents.BEFORE_ENTITIES.register((context) -> postRenderContextEvent(context, FabricEventsJS.CLIENT_BEFORE_ENTITIES));
		WorldRenderEvents.AFTER_TRANSLUCENT.register((context) -> postRenderContextEvent(context, FabricEventsJS.CLIENT_AFTER_TRANSLUCENT));
		WorldRenderEvents.AFTER_ENTITIES.register((context) -> postRenderContextEvent(context, FabricEventsJS.CLIENT_AFTER_ENTITIES));
		WorldRenderEvents.START.register((context) -> postRenderContextEvent(context, FabricEventsJS.CLIENT_START_RENDER));
		WorldRenderEvents.END.register((context) -> postRenderContextEvent(context, FabricEventsJS.CLIENT_END_RENDER));
		WorldRenderEvents.AFTER_SETUP.register((context) -> postRenderContextEvent(context, FabricEventsJS.CLIENT_AFTER_SETUP));
		WorldRenderEvents.BEFORE_BLOCK_OUTLINE.register(FabricEventHandler::postBeforeBlockOutlineEvent);
		WorldRenderEvents.BLOCK_OUTLINE.register(FabricEventHandler::postBlockOutlineEvent);
		HudRenderCallback.EVENT.register(FabricEventHandler::postRenderContextEvent);
	}

	public static void postRenderContextEvent(WorldRenderContext context, String eventID) {
		WorldRenderContextEventJS eventJS = new WorldRenderContextEventJS(context);
		eventJS.post(ScriptType.CLIENT, eventID);
	}

	private static void postRenderContextEvent(PoseStack matrix, float tickDelta) {
		HudRenderEventJS eventjs = new HudRenderEventJS(tickDelta, matrix);
		eventjs.post(ScriptType.CLIENT, FabricEventsJS.CLIENT_RENDER_HUD);
	}

	private static boolean postBeforeBlockOutlineEvent(WorldRenderContext context, @Nullable HitResult hitResult) {
		BeforeBlockOutlineRenderEventJS event = new BeforeBlockOutlineRenderEventJS(context, hitResult);
		event.post(ScriptType.CLIENT, FabricEventsJS.CLIENT_BEFORE_BLOCK_OUTLINE);
		return event.isCancelled();
	}

	private static boolean postBlockOutlineEvent(WorldRenderContext context, WorldRenderContext.BlockOutlineContext blockOutlineContext) {
		BlockOutlineRenderEventJS event = new BlockOutlineRenderEventJS(context, blockOutlineContext);
		event.post(ScriptType.CLIENT, FabricEventsJS.CLIENT_BLOCK_OUTLINE);
		return event.isCancelled();
	}

}

