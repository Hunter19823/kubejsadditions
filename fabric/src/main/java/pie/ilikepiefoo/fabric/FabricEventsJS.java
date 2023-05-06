package pie.ilikepiefoo.fabric;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;
import pie.ilikepiefoo.fabric.events.elytra.AllowElytraFlightEventJS;
import pie.ilikepiefoo.fabric.events.elytra.CustomElytraFlightEventJS;
import pie.ilikepiefoo.fabric.events.hud.HudRenderEventJS;
import pie.ilikepiefoo.fabric.events.sleep.AllowBedEventJS;
import pie.ilikepiefoo.fabric.events.sleep.AllowNearbyMonstersEventJS;
import pie.ilikepiefoo.fabric.events.sleep.AllowResettingTimeEventJS;
import pie.ilikepiefoo.fabric.events.sleep.AllowSettingSpawnEventJS;
import pie.ilikepiefoo.fabric.events.sleep.AllowSleepTimeEventJS;
import pie.ilikepiefoo.fabric.events.sleep.AllowSleepingEventJS;
import pie.ilikepiefoo.fabric.events.sleep.ModifySleepingDirectionEventJS;
import pie.ilikepiefoo.fabric.events.sleep.ModifyWakeUpPositionEventJS;
import pie.ilikepiefoo.fabric.events.sleep.SetBedOccupationStateEventJS;
import pie.ilikepiefoo.fabric.events.sleep.SleepingEventJS;
import pie.ilikepiefoo.fabric.events.worldrender.BeforeBlockOutlineRenderEventJS;
import pie.ilikepiefoo.fabric.events.worldrender.BlockOutlineRenderEventJS;
import pie.ilikepiefoo.fabric.events.worldrender.WorldRenderContextEventJS;

public interface FabricEventsJS {
	EventGroup GROUP = EventGroup.of("FabricEvents");

	// Elytra Events
	EventHandler ALLOW_ELYTRA_FLIGHT = GROUP.server("allowElytraFlight", () -> AllowElytraFlightEventJS.class).cancelable();
	EventHandler CUSTOM_ELYTRA_FLIGHT = GROUP.server("customElytraFlight", () -> CustomElytraFlightEventJS.class).cancelable();

	// Hud Events
	EventHandler RENDER_HUD = GROUP.client("renderHUD", () -> HudRenderEventJS.class);

	// Sleep Events
	EventHandler ALLOW_SLEEPING = GROUP.server("allowSleeping", () -> AllowSleepingEventJS.class).cancelable();
	EventHandler START_SLEEPING = GROUP.server("startSleeping", () -> SleepingEventJS.class);
	EventHandler STOP_SLEEPING = GROUP.server("stopSleeping", () -> SleepingEventJS.class);
	EventHandler ALLOW_BED = GROUP.server("allowBed", () -> AllowBedEventJS.class).cancelable();
	EventHandler ALLOW_SLEEP_TIME = GROUP.server("allowSleepTime", () -> AllowSleepTimeEventJS.class).cancelable();
	EventHandler ALLOW_NEARBY_MONSTERS = GROUP.server("allowNearbyMonsters", () -> AllowNearbyMonstersEventJS.class).cancelable();
	EventHandler ALLOW_RESETTING_TIME = GROUP.server("allowResettingTime", () -> AllowResettingTimeEventJS.class).cancelable();
	EventHandler MODIFY_SLEEPING_DIRECTION = GROUP.server("ModifySleepingDirection", () -> ModifySleepingDirectionEventJS.class);
	EventHandler ALLOW_SETTING_SPAWN = GROUP.server("allowSettingSpawn", () -> AllowSettingSpawnEventJS.class).cancelable();
	EventHandler SET_BED_OCCUPATION_STATE = GROUP.server("setBedOccupationState", () -> SetBedOccupationStateEventJS.class).cancelable();
	EventHandler MODIFY_WAKE_UP_POSITION = GROUP.server("modifyWakeUpPosition", () -> ModifyWakeUpPositionEventJS.class);

	// World Render Events
	EventHandler BEFORE_ENTITIES = GROUP.client("beforeEntities", () -> WorldRenderContextEventJS.class);
	EventHandler AFTER_ENTITIES = GROUP.client("afterEntities", () -> WorldRenderContextEventJS.class);
	EventHandler AFTER_TRANSLUCENT = GROUP.client("afterTranslucent", () -> WorldRenderContextEventJS.class);
	EventHandler AFTER_SETUP = GROUP.client("afterSetup", () -> WorldRenderContextEventJS.class);
	EventHandler START_RENDER = GROUP.client("startRender", () -> WorldRenderContextEventJS.class);
	EventHandler LAST_RENDER = GROUP.client("lastRender", () -> WorldRenderContextEventJS.class);
	EventHandler END_RENDER = GROUP.client("endRender", () -> WorldRenderContextEventJS.class);
	EventHandler BEFORE_BLOCK_OUTLINE = GROUP.client("beforeBlockOutline", () -> BeforeBlockOutlineRenderEventJS.class).cancelable();
	EventHandler BLOCK_OUTLINE = GROUP.client("blockOutline", () -> BlockOutlineRenderEventJS.class);

	static void register() {
		GROUP.register();
	}

}

