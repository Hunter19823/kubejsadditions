package pie.ilikepiefoo.fabric;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.latvian.mods.kubejs.script.ScriptType;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.entity.event.v1.EntityElytraEvents;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;
import pie.ilikepiefoo.fabric.events.sleep.AllowBedEventJS;
import pie.ilikepiefoo.fabric.events.elytra.AllowElytraFlightEventJS;
import pie.ilikepiefoo.fabric.events.sleep.AllowNearbyMonstersEventJS;
import pie.ilikepiefoo.fabric.events.sleep.AllowResettingTimeEventJS;
import pie.ilikepiefoo.fabric.events.sleep.AllowSettingSpawnEventJS;
import pie.ilikepiefoo.fabric.events.sleep.AllowSleepTimeEventJS;
import pie.ilikepiefoo.fabric.events.sleep.AllowSleepingEventJS;
import pie.ilikepiefoo.fabric.events.worldrender.BeforeBlockOutlineRenderEventJS;
import pie.ilikepiefoo.fabric.events.worldrender.BlockOutlineRenderEventJS;
import pie.ilikepiefoo.fabric.events.elytra.CustomElytraFlightEventJS;
import pie.ilikepiefoo.fabric.events.hud.HudRenderEventJS;
import pie.ilikepiefoo.fabric.events.sleep.ModifyWakeUpPositionEventJS;
import pie.ilikepiefoo.fabric.events.sleep.ModifySleepingDirectionEventJS;
import pie.ilikepiefoo.fabric.events.sleep.SetBedOccupationStateEventJS;
import pie.ilikepiefoo.fabric.events.sleep.SleepingEventJS;
import pie.ilikepiefoo.fabric.events.worldrender.WorldRenderContextEventJS;

public class FabricEventHandler {

	public static void init() {
		registerWorldRenderEvents();
		registerHudEvents();
		registerElytraEvents();
		registerSleepEvents();
	}

	private static void registerWorldRenderEvents() {
		WorldRenderEvents.BEFORE_ENTITIES.register((context) -> postRenderContextEvent(context, FabricEventsJS.CLIENT_BEFORE_ENTITIES));
		WorldRenderEvents.AFTER_TRANSLUCENT.register((context) -> postRenderContextEvent(context, FabricEventsJS.CLIENT_AFTER_TRANSLUCENT));
		WorldRenderEvents.AFTER_ENTITIES.register((context) -> postRenderContextEvent(context, FabricEventsJS.CLIENT_AFTER_ENTITIES));
		WorldRenderEvents.START.register((context) -> postRenderContextEvent(context, FabricEventsJS.CLIENT_START_RENDER));
		WorldRenderEvents.END.register((context) -> postRenderContextEvent(context, FabricEventsJS.CLIENT_END_RENDER));
		WorldRenderEvents.AFTER_SETUP.register((context) -> postRenderContextEvent(context, FabricEventsJS.CLIENT_AFTER_SETUP));
		WorldRenderEvents.BEFORE_BLOCK_OUTLINE.register(FabricEventHandler::postBeforeBlockOutlineEvent);
		WorldRenderEvents.BLOCK_OUTLINE.register(FabricEventHandler::postBlockOutlineEvent);
	}

	private static void registerHudEvents() {
		HudRenderCallback.EVENT.register(FabricEventHandler::postRenderContextEvent);
	}

	private static void registerElytraEvents() {
		EntityElytraEvents.ALLOW.register(AllowElytraFlightEventJS::handler);
		EntityElytraEvents.CUSTOM.register(CustomElytraFlightEventJS::handler);
	}

	private static void registerSleepEvents() {
		EntitySleepEvents.ALLOW_SLEEPING.register(AllowSleepingEventJS::handler);
		EntitySleepEvents.START_SLEEPING.register(SleepingEventJS::startHandler);
		EntitySleepEvents.STOP_SLEEPING.register(SleepingEventJS::stopHandler);
		EntitySleepEvents.ALLOW_BED.register(AllowBedEventJS::handler);
		EntitySleepEvents.ALLOW_SLEEP_TIME.register(AllowSleepTimeEventJS::handler);
		EntitySleepEvents.ALLOW_NEARBY_MONSTERS.register(AllowNearbyMonstersEventJS::handler);
		EntitySleepEvents.ALLOW_RESETTING_TIME.register(AllowResettingTimeEventJS::handler);
		EntitySleepEvents.MODIFY_SLEEPING_DIRECTION.register(ModifySleepingDirectionEventJS::handler);
		EntitySleepEvents.ALLOW_SETTING_SPAWN.register(AllowSettingSpawnEventJS::handler);
		EntitySleepEvents.SET_BED_OCCUPATION_STATE.register(SetBedOccupationStateEventJS::handler);
		EntitySleepEvents.MODIFY_WAKE_UP_POSITION.register(ModifyWakeUpPositionEventJS::handler);
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

