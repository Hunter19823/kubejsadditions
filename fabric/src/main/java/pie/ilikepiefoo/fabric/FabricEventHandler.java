package pie.ilikepiefoo.fabric;

import dev.architectury.platform.Platform;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.entity.event.v1.EntityElytraEvents;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.world.entity.LivingEntity;
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

public class FabricEventHandler {

    public static void init() {
        if (Platform.getEnv() == EnvType.CLIENT) {
            registerClient();
        }
        registerServer();
    }

    private static void registerClient() {
        registerWorldRenderEvents();
        registerHudEvents();
    }

    private static void registerServer() {
        registerElytraEvents();
        registerSleepEvents();
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
        WorldRenderEvents.BEFORE_ENTITIES.register(WorldRenderContextEventJS::beforeEntitiesHandle);
        WorldRenderEvents.AFTER_TRANSLUCENT.register(WorldRenderContextEventJS::afterTranslucentHandle);
        WorldRenderEvents.AFTER_ENTITIES.register(WorldRenderContextEventJS::afterEntitiesHandle);
        WorldRenderEvents.START.register(WorldRenderContextEventJS::startHandle);
        WorldRenderEvents.LAST.register(WorldRenderContextEventJS::lastHandle);
        WorldRenderEvents.END.register(WorldRenderContextEventJS::endHandle);
        WorldRenderEvents.AFTER_SETUP.register(WorldRenderContextEventJS::afterSetupHandle);
        WorldRenderEvents.BEFORE_BLOCK_OUTLINE.register(BeforeBlockOutlineRenderEventJS::handle);
        WorldRenderEvents.BLOCK_OUTLINE.register(BlockOutlineRenderEventJS::handle);
    }

    private static void registerHudEvents() {
        HudRenderCallback.EVENT.register(HudRenderEventJS::handle);
    }

    /**
     * Events related to elytra flight for living entities. Elytra flight is also known as "fall flying".
     */
    private static void registerElytraEvents() {
        EntityElytraEvents.ALLOW.register(AllowElytraFlightEventJS::handler);
        EntityElytraEvents.CUSTOM.register(CustomElytraFlightEventJS::handler);
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

}

