package pie.ilikepiefoo.compat.jade;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;
import net.neoforged.fml.ModList;

public interface JadeEvents {
    EventGroup GROUP = EventGroup.of("JadeEvents");

    EventHandler ON_COMMON_REGISTRATION = GROUP.startup("onCommonRegistration", () -> WailaCommonRegistrationEventJS.class);
    EventHandler ON_CLIENT_REGISTRATION = GROUP.client("onClientRegistration", () -> WailaClientRegistrationEventJS.class);

    static void register() {
        if (ModList.get().isLoaded("jade")) {
            GROUP.register();
        }
    }
}
