package pie.ilikepiefoo.fabric.events.elytra;

import dev.latvian.mods.kubejs.entity.EntityJS;
import dev.latvian.mods.kubejs.entity.LivingEntityEventJS;
import dev.latvian.mods.kubejs.script.ScriptType;
import net.minecraft.world.entity.LivingEntity;
import pie.ilikepiefoo.fabric.FabricEventsJS;

public class AllowElytraFlightEventJS extends LivingEntityEventJS {
	private final LivingEntity entity;

	public AllowElytraFlightEventJS(LivingEntity entity) {
		this.entity = entity;
	}

	@Override
	public boolean canCancel() {
		return true;
	}

	public static boolean handler(LivingEntity entity) {
		AllowElytraFlightEventJS event = new AllowElytraFlightEventJS(entity);
		event.post(ScriptType.SERVER, FabricEventsJS.ALLOW_ELYTRA_FLIGHT);
		return !event.isCancelled();
	}


	@Override
	public EntityJS getEntity() {
		return entityOf(entity);
	}
}

