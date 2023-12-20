package pie.ilikepiefoo;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent;
import dev.architectury.event.events.common.PlayerEvent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import pie.ilikepiefoo.events.AdditionalEvents;
import pie.ilikepiefoo.events.EntityEnterChunkEventJS;
import pie.ilikepiefoo.events.EntityTameEventJS;
import pie.ilikepiefoo.events.PlayerChangeDimensionEventJS;
import pie.ilikepiefoo.events.PlayerCloneEventJS;
import pie.ilikepiefoo.events.PlayerRespawnEventJS;

public class EventHandler {
    public static void init() {
        PlayerEvent.CHANGE_DIMENSION.register(EventHandler::onPlayerChangeDimension);
        PlayerEvent.PLAYER_CLONE.register(EventHandler::onPlayerClone);
        PlayerEvent.PLAYER_RESPAWN.register(EventHandler::onPlayerRespawn);
        EntityEvent.ENTER_SECTION.register(EventHandler::onEntityEnterChunk);
        EntityEvent.ANIMAL_TAME.register(EventHandler::onEntityTame);
    }

    /**
     * Invoked when a player changes their dimension.
     * Equivalent to Forge's {@code PlayerChangedDimensionEvent} event.
     *
     * @param player   The teleporting player.
     * @param oldLevel The level the player comes from.
     * @param newLevel The level the player teleports into.
     */
    public static void onPlayerChangeDimension( ServerPlayer player, ResourceKey<Level> oldLevel, ResourceKey<Level> newLevel ) {
        AdditionalEvents.PLAYER_CHANGE_DIMENSION.post(new PlayerChangeDimensionEventJS(player, oldLevel, newLevel));
    }

    /**
     * Invoked when a player respawns.
     * This can be used to manipulate the new player.
     * Equivalent to Forge's {@code PlayerEvent.Clone} event.
     *
     * @param oldPlayer    The old player.
     * @param newPlayer    The new player.
     * @param conqueredEnd This is true when the player joined the end and now is leaving it. {@link ServerPlayer#wonGame}
     */
    private static void onPlayerClone( ServerPlayer oldPlayer, ServerPlayer newPlayer, boolean conqueredEnd ) {
        AdditionalEvents.PLAYER_CLONE.post(new PlayerCloneEventJS(oldPlayer, newPlayer, conqueredEnd));
    }

    /**
     * Invoked when a player is respawned (e.g. changing dimension).
     * Equivalent to Forge's {@code PlayerRespawnEvent} event.
     * To manipulate the player use {@link PlayerEvent.PlayerClone#clone(ServerPlayer, ServerPlayer, boolean)}.
     *
     * @param serverPlayer The respawned player.
     * @param conqueredEnd Whether the player has conquered the end. This is true when the player joined the end and now is leaving it.
     *                     {@link ServerPlayer#wonGame}
     */
    private static void onPlayerRespawn( ServerPlayer serverPlayer, boolean conqueredEnd ) {
        AdditionalEvents.PLAYER_RESPAWN.post(new PlayerRespawnEventJS(serverPlayer, conqueredEnd));
    }

    /**
     * Invoked whenever an entity enters a chunk.
     * Equivalent to Forge's {@code EnteringChunk} event.
     *
     * @param entity The entity moving to a different chunk.
     * @param chunkX The chunk x-coordinate.
     * @param chunkZ The chunk z-coordinate.
     * @param prevX  The previous chunk x-coordinate.
     * @param prevZ  The previous chunk z-coordinate.
     */
    private static void onEntityEnterChunk( Entity entity, int chunkX, int chunkY, int chunkZ, int prevX, int prevY, int prevZ ) {
        AdditionalEvents.ENTITY_ENTER_CHUNK.post(new EntityEnterChunkEventJS(entity, chunkX, chunkY, chunkZ, prevX, prevY, prevZ));
    }

    /**
     * Invoked before a tamable animal is tamed.
     * This event only works on vanilla mobs. Mods implementing their own entities may want to make their own events or invoke this.
     * Equivalent to Forge's {@code AnimalTameEvent} event.
     *
     * @param animal The animal being tamed.
     * @param player The tamer.
     * @return A {@link EventResult} determining the outcome of the event,
     * the action may be cancelled by the result.
     */
    private static EventResult onEntityTame( Animal animal, Player player ) {
        if (player instanceof ServerPlayer serverPlayer) {
            return AdditionalEvents.ENTITY_TAME.post(new EntityTameEventJS(animal, player)).arch();
        }
        return EventResult.pass();
    }

}
