package pie.ilikepiefoo.fabric;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import pie.ilikepiefoo.fabric.events.custom.FabricEventRegisterEventJS;

import static pie.ilikepiefoo.fabric.FabricEventsJS.FABRIC_EVENT_REGISTER;

public class FabricPlugin extends KubeJSPlugin {

    @Override
    public void initStartup() {
        FABRIC_EVENT_REGISTER.post(new FabricEventRegisterEventJS());
    }


    @Override
    public void registerEvents() {
        FabricEventsJS.register();
    }

}

