package pie.ilikepiefoo.events;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;

public interface AdditionalEvents {
	EventGroup GROUP = EventGroup.of("CommonAddedEvents");

	EventHandler ENTITY_ENTER_CHUNK = GROUP.server("entityEnterChunk", () -> EntityEnterChunkEventJS.class);
	EventHandler ENTITY_TAME = GROUP.server("entityTame", () -> EntityTameEventJS.class).cancelable();
	EventHandler PLAYER_CHANGE_DIMENSION = GROUP.server("playerChangeDimension", () -> PlayerChangeDimensionEventJS.class);
	EventHandler PLAYER_CLONE = GROUP.server("playerClone", () -> PlayerCloneEventJS.class);
	EventHandler PLAYER_RESPAWN = GROUP.server("playerRespawn", () -> PlayerRespawnEventJS.class);

	static void register() {
		GROUP.register();
	}
}
