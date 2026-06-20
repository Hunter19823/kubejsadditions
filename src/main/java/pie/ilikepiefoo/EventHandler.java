package pie.ilikepiefoo;

import dev.latvian.mods.kubejs.event.EventResult;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityEvent;
import net.neoforged.neoforge.event.entity.living.AnimalTameEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import pie.ilikepiefoo.events.AdditionalEvents;
import pie.ilikepiefoo.events.EntityEnterChunkEventJS;
import pie.ilikepiefoo.events.EntityTameEventJS;
import pie.ilikepiefoo.events.PlayerChangeDimensionEventJS;
import pie.ilikepiefoo.events.PlayerCloneEventJS;
import pie.ilikepiefoo.events.PlayerRespawnEventJS;

@EventBusSubscriber(modid = KubeJSAdditions.MOD_ID)
public class EventHandler {
    public static void init() {
        // Listeners are registered via @SubscribeEvent on static methods.
    }

    @SubscribeEvent
    public static void onPlayerChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            ResourceKey<Level> from = event.getFrom();
            ResourceKey<Level> to = event.getTo();
            AdditionalEvents.PLAYER_CHANGE_DIMENSION.post(new PlayerChangeDimensionEventJS(player, from, to));
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (event.getOriginal() instanceof ServerPlayer oldPlayer && event.getEntity() instanceof ServerPlayer newPlayer) {
            AdditionalEvents.PLAYER_CLONE.post(new PlayerCloneEventJS(oldPlayer, newPlayer, event.isWasDeath()));
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            AdditionalEvents.PLAYER_RESPAWN.post(new PlayerRespawnEventJS(serverPlayer, event.isEndConquered()));
        }
    }

    @SubscribeEvent
    public static void onEntityEnterChunk(EntityEvent.EnteringSection event) {
        if (event.didChunkChange()) {
            Entity entity = event.getEntity();
            int chunkX = event.getNewPos().getX();
            int chunkY = event.getNewPos().getY();
            int chunkZ = event.getNewPos().getZ();
            int prevX = event.getOldPos().getX();
            int prevY = event.getOldPos().getY();
            int prevZ = event.getOldPos().getZ();
            AdditionalEvents.ENTITY_ENTER_CHUNK.post(new EntityEnterChunkEventJS(entity, chunkX, chunkY, chunkZ, prevX, prevY, prevZ));
        }
    }

    @SubscribeEvent
    public static void onEntityTame(AnimalTameEvent event) {
        Animal animal = event.getAnimal();
        Player player = event.getTamer();
        if (player instanceof ServerPlayer serverPlayer) {
            EventResult result = AdditionalEvents.ENTITY_TAME.post(new EntityTameEventJS(animal, player));
            result.applyCancel(event);
        }
    }
}
