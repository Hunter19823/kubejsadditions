package pie.ilikepiefoo.fabric.events.elytra;

import dev.latvian.mods.kubejs.entity.LivingEntityEventJS;
import dev.latvian.mods.kubejs.server.ServerScriptManager;
import net.fabricmc.fabric.api.entity.event.v1.FabricElytraItem;
import net.minecraft.world.entity.LivingEntity;
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

	public boolean getTickElytra() {
		return tickElytra;
	}

	/**
	 * An event to grant elytra flight to living entities when some condition is met.
	 * Will be called when players try to start elytra flight by pressing space in mid-air, and every tick for all flying living entities to check if elytra flight is still allowed.
	 *
	 * <p>Items that wish to enable custom elytra flight when worn in the chest equipment slot can simply implement {@link FabricElytraItem} instead of registering a listener.
	 *
	 * @param entity     the entity
	 * @param tickElytra false if this is just to check if the custom elytra can be used, true if the custom elytra should also be ticked, i.e. perform side-effects of flying such as using resources.
	 * @return true to use a custom elytra, enabling elytra flight for the entity and cancelling subsequent handlers
	 */
	public static boolean handler(LivingEntity entity, boolean tickElytra) {
		if (ServerScriptManager.instance == null) {
			return false;
		}
		// Canceling this event will result in Elytra flight being allowed and canceling subsequent handlers.
		// (Returning true to handler).
		// Not canceling this event will result in Custom Elytra flight event being passed to subsequent listeners.
		// (Returning false to handler).
		return FabricEventsJS.CUSTOM_ELYTRA_FLIGHT.post(new CustomElytraFlightEventJS(entity, tickElytra));
	}

	@Override
	public LivingEntity getEntity() {
		return entity;
	}
}

