package pie.ilikepiefoo.events;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventGroupRegistry;
import dev.latvian.mods.kubejs.event.EventHandler;
import dev.latvian.mods.kubejs.event.EventTargetType;
import dev.latvian.mods.kubejs.event.TargetedEventHandler;
import pie.ilikepiefoo.events.custom.ArchEventRegisterEventJS;

public interface AdditionalEvents {
    EventGroup GROUP = EventGroup.of("CommonAddedEvents");

    EventHandler ENTITY_ENTER_CHUNK = GROUP.server("entityEnterChunk", () -> EntityEnterChunkEventJS.class);
    EventHandler ENTITY_TAME = GROUP.server("entityTame", () -> EntityTameEventJS.class).hasResult();
    EventHandler PLAYER_CHANGE_DIMENSION = GROUP.server("playerChangeDimension", () -> PlayerChangeDimensionEventJS.class);
    EventHandler PLAYER_CLONE = GROUP.server("playerClone", () -> PlayerCloneEventJS.class);
    EventHandler PLAYER_RESPAWN = GROUP.server("playerRespawn", () -> PlayerRespawnEventJS.class);

    EventGroup ARCH_EVENTS = EventGroup.of("ArchEvents");
    TargetedEventHandler<String> ARCH_STARTUP_EVENT_HANDLER = ARCH_EVENTS.startup("handleStartup", () -> ProxyEventJS.class)
            .requiredTarget(EventTargetType.STRING);
    TargetedEventHandler<String> ARCH_CLIENT_EVENT_HANDLER = ARCH_EVENTS.client("handleClient", () -> ProxyEventJS.class)
            .requiredTarget(EventTargetType.STRING);
    TargetedEventHandler<String> ARCH_SERVER_EVENT_HANDLER = ARCH_EVENTS.server("handleServer", () -> ProxyEventJS.class)
            .requiredTarget(EventTargetType.STRING);
    EventHandler ARCH_EVENT_REGISTER = ARCH_EVENTS.startup("registry", () -> ArchEventRegisterEventJS.class);

    static void register(EventGroupRegistry registry) {
        registry.register(GROUP);
        registry.register(ARCH_EVENTS);
    }

}
