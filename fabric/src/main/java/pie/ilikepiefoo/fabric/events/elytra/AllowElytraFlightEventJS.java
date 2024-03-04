package pie.ilikepiefoo.fabric.events.elytra;

import dev.latvian.mods.kubejs.entity.LivingEntityEventJS;
import dev.latvian.mods.kubejs.event.EventJS;
import dev.latvian.mods.kubejs.server.ServerScriptManager;
import net.minecraft.world.entity.LivingEntity;
import pie.ilikepiefoo.fabric.FabricEventsJS;

/**
 * An event to check if elytra flight (both through normal and custom elytras) is allowed.
 * All listeners need to return true to allow the entity to fly, otherwise elytra flight will be blocked/stopped.
 */
public class AllowElytraFlightEventJS extends LivingEntityEventJS {
    private final LivingEntity entity;

    public AllowElytraFlightEventJS(LivingEntity entity) {
        this.entity = entity;
    }

    /**
     * If you want to allow elytra flight, you can call {@link EventJS#success()}.
     *
     * @return false to block elytra flight, true to allow it (unless another listener returns false)
     */
    public static boolean handler(LivingEntity entity) {
        if (ServerScriptManager.instance == null) {
            return true;
        }
        if (!FabricEventsJS.ALLOW_ELYTRA_FLIGHT.hasListeners()) {
            return true;
        }
        return FabricEventsJS.ALLOW_ELYTRA_FLIGHT.post(new AllowElytraFlightEventJS(entity)).arch().isTrue();
    }


    @Override
    public LivingEntity getEntity() {
        return entity;
    }

}

