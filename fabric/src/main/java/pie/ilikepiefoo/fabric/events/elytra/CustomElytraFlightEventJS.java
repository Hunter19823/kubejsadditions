package pie.ilikepiefoo.fabric.events.elytra;

import dev.latvian.mods.kubejs.entity.EntityJS;
import dev.latvian.mods.kubejs.entity.LivingEntityEventJS;
import dev.latvian.mods.kubejs.script.ScriptType;
import net.minecraft.world.entity.LivingEntity;
import pie.ilikepiefoo.fabric.FabricEventsJS;

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

	public static boolean handler(LivingEntity entity, boolean tickElytra) {
		CustomElytraFlightEventJS event = new CustomElytraFlightEventJS(entity, tickElytra);
		event.post(ScriptType.SERVER, FabricEventsJS.CUSTOM_ELYTRA_FLIGHT);
		return event.isCancelled();
	}

	@Override
	public EntityJS getEntity() {
		return entityOf(entity);
	}
}

