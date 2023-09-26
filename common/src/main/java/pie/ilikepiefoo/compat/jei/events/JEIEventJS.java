package pie.ilikepiefoo.compat.jei.events;

import dev.latvian.mods.kubejs.event.EventJS;

public class JEIEventJS extends EventJS {

    @Override
    public boolean canCancel() {
        return false;
    }
}
