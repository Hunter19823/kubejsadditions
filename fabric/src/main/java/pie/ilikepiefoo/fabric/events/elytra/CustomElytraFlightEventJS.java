package pie.ilikepiefoo.fabric.events.elytra;

import dev.architectury.platform.Platform;
import dev.latvian.mods.kubejs.entity.EntityJS;
import dev.latvian.mods.kubejs.entity.LivingEntityEventJS;
import dev.latvian.mods.kubejs.script.ScriptType;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.entity.event.v1.FabricElytraItem;
import net.minecraft.world.entity.LivingEntity;
import org.apache.logging.log4j.LogManager;
import pie.ilikepiefoo.fabric.FabricEventsJS;

/**
 * An event to grant elytra flight to living entities when some condition is met.
 * Will be called when players try to start elytra flight by pressing space in mid-air, and every tick for all flying living entities to check if elytra flight is still allowed.
 *
 * <p>Items that wish to enable custom elytra flight when worn in the chest equipment slot can simply implement {@link FabricElytraItem} instead of registering a listener.
 */
public class CustomElytraFlightEventJS extends LivingEntityEventJS {

	private final LivingEntity entity;
	private final boolean tickElytra;

	public CustomElytraFlightEventJS(LivingEntity entity, boolean tickElytra) {
		this.entity = entity;
		this.tickElytra = tickElytra;
	}

	@Override
	public boolean canCancel() {
		return true;
	}

	public boolean getTickElytra() {
		return tickElytra;
	}

	/**
	 * Try to use a custom elytra for an entity.
	 * A custom elytra is anything that allows an entity to enter and continue elytra flight when some condition is met.
	 * Listeners should follow the following pattern:
	 * <pre>{@code
	 * EntityElytraEvents.CUSTOM.register((entity, tickElytra) -> {
	 *     if (check if condition for custom elytra is met) {
	 *         if (tickElytra) {
	 *             // Optionally consume some resources that are being used up in order to fly, for example damaging an item.
	 *             // Optionally perform other side effects of elytra flight, for example playing a sound.
	 *         }
	 *         // Allow entering/continuing elytra flight with this custom elytra
	 *         return true;
	 *     }
	 *     // Condition for the custom elytra is not met: don't let players enter or continue elytra flight (unless another elytra is available).
	 *     return false;
	 * });
	 * }</pre>
	 *
	 * @param entity     the entity
	 * @param tickElytra false if this is just to check if the custom elytra can be used, true if the custom elytra should also be ticked, i.e. perform side-effects of flying such as using resources.
	 * @return true to use a custom elytra, enabling elytra flight for the entity and cancelling subsequent handlers
	 */
	public static boolean handler(LivingEntity entity, boolean tickElytra) {
		if(ScriptType.SERVER.manager == null)
			return false;
		CustomElytraFlightEventJS event = new CustomElytraFlightEventJS(entity, tickElytra);
		event.post(ScriptType.SERVER, FabricEventsJS.CUSTOM_ELYTRA_FLIGHT);
		return event.isCancelled();
	}

	@Override
	public EntityJS getEntity() {
		return entityOf(entity);
	}
}

