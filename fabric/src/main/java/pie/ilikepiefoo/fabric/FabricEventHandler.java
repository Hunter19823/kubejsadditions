package pie.ilikepiefoo.fabric;

import dev.architectury.platform.Platform;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.entity.event.v1.EntityElytraEvents;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.world.entity.LivingEntity;
import pie.ilikepiefoo.config.FeatureFlags;
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

import static pie.ilikepiefoo.config.FeatureFlags.feature;

public class FabricEventHandler {

    public static void init() {
        if (Platform.getEnv() == EnvType.CLIENT) {
			feature("Clientside Event Handlers", FabricEventHandler::registerClient);
        }
		feature("Serverside Event Handlers", FabricEventHandler::registerServer);
		FeatureFlags.INSTANCE.save();
    }

    private static void registerClient() {
		feature("World Rendering Event Handlers", FabricEventHandler::registerWorldRenderEvents);
		feature("HUD Rendering Event Handlers", FabricEventHandler::registerHudEvents);
    }

    private static void registerServer() {
		feature("Elytra Event Handlers", FabricEventHandler::registerElytraEvents);
		feature("Sleep Event Handlers", FabricEventHandler::registerSleepEvents);
    }

    /**
     * Mods should use these events to introduce custom rendering during
     * {@link LevelRenderer#renderLevel(com.mojang.blaze3d.vertex.PoseStack, float, long, boolean, net.minecraft.client.Camera, net.minecraft.client.renderer.GameRenderer, net.minecraft.client.renderer.LightTexture, com.mojang.math.Matrix4f)}
     * without adding complicated and conflict-prone injections there.  Using these events also enables 3rd-party renderers
     * that make large-scale changes to rendering maintain compatibility by calling any broken event invokers directly.
     *
     * <p>The order of events each frame is as follows:
     * <ul><li>START
     * <li>AFTER_SETUP
     * <li>BEFORE_ENTITIES
     * <li>AFTER_ENTITIES
     * <li>BEFORE_BLOCK_OUTLINE
     * <li>BLOCK_OUTLINE  (If not cancelled in BEFORE_BLOCK_OUTLINE)
     * <li>BEFORE_DEBUG_RENDER
     * <li>AFTER_TRANSLUCENT
     * <li>LAST
     * <li>END</ul>
     *
     * <p>These events are not dependent on the Fabric rendering API or Indigo but work when those are present.
     */
    private static void registerWorldRenderEvents() {
		feature("Before Entities World Render Event Listener", () -> WorldRenderEvents.BEFORE_ENTITIES.register(WorldRenderContextEventJS::beforeEntitiesHandle));
		feature("After Translucent World Render Event Listener", () -> WorldRenderEvents.AFTER_TRANSLUCENT.register(WorldRenderContextEventJS::afterTranslucentHandle));
		feature("After Entities World Render Event Listener", () -> WorldRenderEvents.AFTER_ENTITIES.register(WorldRenderContextEventJS::afterEntitiesHandle));
		feature("Start World Render Event Listener", () -> WorldRenderEvents.START.register(WorldRenderContextEventJS::startHandle));
		feature("Last World Render Event Listener", () -> WorldRenderEvents.LAST.register(WorldRenderContextEventJS::lastHandle));
		feature("End World Render Event Listener", () -> WorldRenderEvents.END.register(WorldRenderContextEventJS::endHandle));
		feature("After Setup World Render Event Listener", () -> WorldRenderEvents.AFTER_SETUP.register(WorldRenderContextEventJS::afterSetupHandle));
		feature("Before Block Outline World Render Event Listener", () -> WorldRenderEvents.BEFORE_BLOCK_OUTLINE.register(BeforeBlockOutlineRenderEventJS::handle));
		feature("Block Outline World Render Event Listener", () -> WorldRenderEvents.BLOCK_OUTLINE.register(BlockOutlineRenderEventJS::handle));
    }

    private static void registerHudEvents() {
		feature("HUD Render Event Listener", () -> HudRenderCallback.EVENT.register(HudRenderEventJS::handle));
    }

    /**
     * Events related to elytra flight for living entities. Elytra flight is also known as "fall flying".
     */
    private static void registerElytraEvents() {
		feature("Allow Elytra Event Listener Listener", () -> EntityElytraEvents.ALLOW.register(AllowElytraFlightEventJS::handler));
		feature("Custom Elytra Flight Event Listener", () -> EntityElytraEvents.CUSTOM.register(CustomElytraFlightEventJS::handler));
    }

    /**
     * Events about the sleep of {@linkplain LivingEntity living entities}.
     *
     * <p>These events can be categorized into three groups:
     * <ol>
     * <li>Simple listeners: {@link EntitySleepEvents#START_SLEEPING} and {@link EntitySleepEvents#STOP_SLEEPING}</li>
     * <li>Predicates: {@link EntitySleepEvents#ALLOW_BED}, {@link EntitySleepEvents#ALLOW_SLEEP_TIME},
     * {@link EntitySleepEvents#ALLOW_RESETTING_TIME},
     * {@link EntitySleepEvents#ALLOW_NEARBY_MONSTERS}, {@link EntitySleepEvents#ALLOW_SETTING_SPAWN} and {@link EntitySleepEvents#ALLOW_SLEEPING}
     *
     * <p><b>Note:</b> Only the {@link EntitySleepEvents#ALLOW_BED} event applies to non-player entities.</li>
     * <li>Modifiers: {@link EntitySleepEvents#MODIFY_SLEEPING_DIRECTION}, {@link EntitySleepEvents#SET_BED_OCCUPATION_STATE}
     * and {@link EntitySleepEvents#MODIFY_WAKE_UP_POSITION}</li>
     * </ol>
     *
     * <p>Sleep events are useful for making custom bed blocks that do not extend {@link net.minecraft.world.level.block.BedBlock}.
     * Custom beds generally only need a custom {@link EntitySleepEvents#ALLOW_BED} checker and a
     * {@link EntitySleepEvents#MODIFY_SLEEPING_DIRECTION} callback,
     * but the other events might be useful as well.
     */
    private static void registerSleepEvents() {
		feature("Allow Sleeping Event Listener", () -> EntitySleepEvents.ALLOW_SLEEPING.register(AllowSleepingEventJS::handler));
		feature("Start Sleeping Event Listener", () -> EntitySleepEvents.START_SLEEPING.register(SleepingEventJS::startHandler));
		feature("Stop Sleeping Event Listener", () -> EntitySleepEvents.STOP_SLEEPING.register(SleepingEventJS::stopHandler));
		feature("Allow Bed Event Listener", () -> EntitySleepEvents.ALLOW_BED.register(AllowBedEventJS::handler));
		feature("Allow Sleep Time Event Listener", () -> EntitySleepEvents.ALLOW_SLEEP_TIME.register(AllowSleepTimeEventJS::handler));
		feature("Allow Nearby Monsters Event Listener", () -> EntitySleepEvents.ALLOW_NEARBY_MONSTERS.register(AllowNearbyMonstersEventJS::handler));
		feature("Allow Resetting Time Event Listener", () -> EntitySleepEvents.ALLOW_RESETTING_TIME.register(AllowResettingTimeEventJS::handler));
		feature("Modify Sleeping Direction Event Listener", () -> EntitySleepEvents.MODIFY_SLEEPING_DIRECTION.register(ModifySleepingDirectionEventJS::handler));
		feature("Allow Setting Spawn Event Listener", () -> EntitySleepEvents.ALLOW_SETTING_SPAWN.register(AllowSettingSpawnEventJS::handler));
		feature("Set Bed Occupation State Event Listener", () -> EntitySleepEvents.SET_BED_OCCUPATION_STATE.register(SetBedOccupationStateEventJS::handler));
		feature("Modify Wake Up Position Event Listener", () -> EntitySleepEvents.MODIFY_WAKE_UP_POSITION.register(ModifyWakeUpPositionEventJS::handler));
    }

}

