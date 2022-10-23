package pie.ilikepiefoo.fabric.events.sleep;

import dev.latvian.mods.kubejs.entity.EntityJS;
import dev.latvian.mods.kubejs.entity.LivingEntityEventJS;
import dev.latvian.mods.kubejs.level.BlockContainerJS;
import dev.latvian.mods.kubejs.script.ScriptType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import pie.ilikepiefoo.fabric.FabricEventsJS;

public class SleepingEventJS extends LivingEntityEventJS {

	private final LivingEntity entity;
	private final BlockPos sleepingPos;

	public SleepingEventJS(LivingEntity entity, BlockPos sleepingPos) {
		this.entity = entity;
		this.sleepingPos = sleepingPos;
	}

	public BlockPos getSleepingPos() {
		return sleepingPos;
	}

	public BlockContainerJS getPos() {
		return getLevel().getBlock(sleepingPos);
	}

	public static void startHandler(LivingEntity entity, BlockPos sleepingPos) {
		SleepingEventJS event = new SleepingEventJS(entity, sleepingPos);
		event.post(ScriptType.SERVER, FabricEventsJS.START_SLEEPING);
	}

	public static void stopHandler(LivingEntity entity, BlockPos sleepingPos) {
		SleepingEventJS event = new SleepingEventJS(entity, sleepingPos);
		event.post(ScriptType.SERVER, FabricEventsJS.STOP_SLEEPING);
	}

	@Override
	public EntityJS getEntity() {
		return entityOf(entity);
	}
}

