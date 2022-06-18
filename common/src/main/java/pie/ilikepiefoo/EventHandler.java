package pie.ilikepiefoo;

import dev.latvian.kubejs.bindings.UtilsWrapper;
import dev.latvian.kubejs.script.ScriptType;
import me.shedaniel.architectury.event.events.PlayerEvent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import pie.ilikepiefoo.events.PlayerChangeDimensionEventJS;
import pie.ilikepiefoo.events.PlayerCloneEventJS;
import pie.ilikepiefoo.events.PlayerRespawnEventJS;

public class EventHandler {

	public static void init() {
		PlayerEvent.CHANGE_DIMENSION.register(EventHandler::onPlayerChangeDimension);
		PlayerEvent.PLAYER_CLONE.register(EventHandler::onPlayerClone);
		PlayerEvent.PLAYER_RESPAWN.register(EventHandler::onPlayerRespawn);
	}

	private static void onPlayerRespawn(ServerPlayer serverPlayer, boolean conqueredEnd) {
		PlayerRespawnEventJS.of(serverPlayer, conqueredEnd).post(ScriptType.SERVER, AdditionalEventsJS.PLAYER_RESPAWN);
	}

	private static void onPlayerClone(ServerPlayer oldPlayer, ServerPlayer newPlayer, boolean conqueredEnd) {
		PlayerCloneEventJS.of(oldPlayer, newPlayer, conqueredEnd).post(ScriptType.SERVER, AdditionalEventsJS.PLAYER_CLONE);
	}

	public static void onPlayerChangeDimension(ServerPlayer player, ResourceKey<Level> oldLevel, ResourceKey<Level> newLevel){
		PlayerChangeDimensionEventJS.of(player, oldLevel, newLevel).post(ScriptType.SERVER, AdditionalEventsJS.PLAYER_CHANGE_DIMENSION);
	}
}
