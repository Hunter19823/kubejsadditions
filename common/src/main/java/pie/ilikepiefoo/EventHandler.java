package pie.ilikepiefoo;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent;
import dev.architectury.event.events.common.InteractionEvent;
import dev.architectury.event.events.common.PlayerEvent;
import dev.latvian.mods.kubejs.script.ScriptType;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import pie.ilikepiefoo.events.EntityEnterChunkEventJS;
import pie.ilikepiefoo.events.EntityTameEventJS;
import pie.ilikepiefoo.events.FarmlandTrampleEventJS;
import pie.ilikepiefoo.events.PlayerChangeDimensionEventJS;
import pie.ilikepiefoo.events.PlayerCloneEventJS;
import pie.ilikepiefoo.events.PlayerRespawnEventJS;

public class EventHandler {
	public static void init() {
		PlayerEvent.CHANGE_DIMENSION.register(EventHandler::onPlayerChangeDimension);
		PlayerEvent.PLAYER_CLONE.register(EventHandler::onPlayerClone);
		PlayerEvent.PLAYER_RESPAWN.register(EventHandler::onPlayerRespawn);
		InteractionEvent.FARMLAND_TRAMPLE.register(EventHandler::onFarmlandTrampled);
		EntityEvent.ENTER_SECTION.register(EventHandler::onEntityEnterChunk);
		EntityEvent.ANIMAL_TAME.register(EventHandler::onEntityTame);
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
	private static EventResult onEntityTame(Animal animal, Player player) {
		EntityTameEventJS event = EntityTameEventJS.of(animal, player);
		event.post(AdditionalEventsJS.ENTITY_TAME);
		if (event.isCanceled()) {
			return EventResult.interruptFalse();
		} else {
			return EventResult.pass();
		}
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
	private static void onEntityEnterChunk(Entity entity, int chunkX, int chunkY, int chunkZ, int prevX, int prevY, int prevZ) {
		EntityEnterChunkEventJS.of(entity, chunkX, chunkY, chunkZ, prevX, prevY, prevZ).post(AdditionalEventsJS.ENTITY_ENTER_CHUNK);
	}

	/**
	 * Invoked when an entity attempts to trample farmland.
	 * Equivalent to Forge's {@code BlockEvent.FarmlandTrampleEvent} event.
	 *
	 * @param world    The level where the block and the player are located in.
	 * @param pos      The position of the block.
	 * @param state    The state of the block.
	 * @param distance The distance of the player to the block.
	 * @param entity   The entity trampling.
	 * @return The event callback result.
	 */
	private static EventResult onFarmlandTrampled(Level world, BlockPos pos, BlockState state, float distance, Entity entity) {
		FarmlandTrampleEventJS event = FarmlandTrampleEventJS.of(world, pos, state, distance, entity);
		event.post(AdditionalEventsJS.BLOCK_TRAMPLE);
		if (event.isCanceled()) {
			return EventResult.interruptFalse();
		} else {
			return EventResult.pass();
		}
	}

	/**
	 * Invoked when a player is respawned (e.g. changing dimension).
	 * Equivalent to Forge's {@code PlayerRespawnEvent} event.
	 * To manipulate the player use {@link PlayerEvent.PlayerClone#clone(ServerPlayer, ServerPlayer, boolean)}.
	 *
	 * @param serverPlayer The respawned player.
	 * @param conqueredEnd Whether the player has conquered the end. This is true when the player joined the end and now is leaving it. {@link ServerPlayer#wonGame}
	 */
	private static void onPlayerRespawn(ServerPlayer serverPlayer, boolean conqueredEnd) {
		PlayerRespawnEventJS.of(serverPlayer, conqueredEnd).post(ScriptType.SERVER, AdditionalEventsJS.PLAYER_RESPAWN);
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
	private static void onPlayerClone(ServerPlayer oldPlayer, ServerPlayer newPlayer, boolean conqueredEnd) {
		PlayerCloneEventJS.of(oldPlayer, newPlayer, conqueredEnd).post(ScriptType.SERVER, AdditionalEventsJS.PLAYER_CLONE);
	}

	/**
	 * Invoked when a player changes their dimension.
	 * Equivalent to Forge's {@code PlayerChangedDimensionEvent} event.
	 *
	 * @param player   The teleporting player.
	 * @param oldLevel The level the player comes from.
	 * @param newLevel The level the player teleports into.
	 */
	public static void onPlayerChangeDimension(ServerPlayer player, ResourceKey<Level> oldLevel, ResourceKey<Level> newLevel) {
		PlayerChangeDimensionEventJS.of(player, oldLevel, newLevel).post(ScriptType.SERVER, AdditionalEventsJS.PLAYER_CHANGE_DIMENSION);
	}
}
