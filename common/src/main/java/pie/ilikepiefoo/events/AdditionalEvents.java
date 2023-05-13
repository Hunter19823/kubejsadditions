package pie.ilikepiefoo.events;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;
import dev.latvian.mods.kubejs.event.Extra;
import pie.ilikepiefoo.events.custom.ArchEventRegisterEventJS;

public interface AdditionalEvents {
	EventGroup GROUP = EventGroup.of("CommonAddedEvents");

	EventHandler ENTITY_ENTER_CHUNK = GROUP.server("entityEnterChunk", () -> EntityEnterChunkEventJS.class);
	EventHandler ENTITY_TAME = GROUP.server("entityTame", () -> EntityTameEventJS.class).cancelable();
	EventHandler PLAYER_CHANGE_DIMENSION = GROUP.server("playerChangeDimension", () -> PlayerChangeDimensionEventJS.class);
	EventHandler PLAYER_CLONE = GROUP.server("playerClone", () -> PlayerCloneEventJS.class);
	EventHandler PLAYER_RESPAWN = GROUP.server("playerRespawn", () -> PlayerRespawnEventJS.class);

	EventGroup ARCH_EVENTS = EventGroup.of("ArchEvents");
	EventHandler ARCH_STARTUP_EVENT_HANDLER = ARCH_EVENTS.startup("handle", () -> ProxyEventJS.class).extra(Extra.REQUIRES_STRING);
	EventHandler ARCH_CLIENT_EVENT_HANDLER = ARCH_EVENTS.client("handle", () -> ProxyEventJS.class).extra(Extra.REQUIRES_STRING);
	EventHandler ARCH_SERVER_EVENT_HANDLER = ARCH_EVENTS.server("handle", () -> ProxyEventJS.class).extra(Extra.REQUIRES_STRING);
	EventHandler ARCH_EVENT_REGISTER = ARCH_EVENTS.startup("registry", () -> ArchEventRegisterEventJS.class);

	static void register() {
		GROUP.register();
		ARCH_EVENTS.register();
	}
}
