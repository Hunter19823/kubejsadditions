package pie.ilikepiefoo.compat.jei.events;

import mezz.jei.api.runtime.IJeiRuntime;

public class OnRuntimeAvailableEventJS extends JEIEventJS {
    public final IJeiRuntime data;

    public OnRuntimeAvailableEventJS(IJeiRuntime data) {
        this.data = data;
    }

}
