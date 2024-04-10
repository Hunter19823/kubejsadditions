package pie.ilikepiefoo.compat.jade;

import dev.architectury.platform.Platform;
import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;

public interface JadeEvents {
    EventGroup GROUP = EventGroup.of("JadeEvents");

    EventHandler ON_COMMON_REGISTRATION = GROUP.startup("onCommonRegistration", () -> WailaCommonRegistrationEventJS.class);

    static void register() {
        if (Platform.isModLoaded("jade")) {
            GROUP.register();
        }
    }
}
