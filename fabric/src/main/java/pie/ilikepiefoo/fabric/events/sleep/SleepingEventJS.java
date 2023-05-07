package pie.ilikepiefoo.fabric.events.sleep;

import dev.latvian.mods.kubejs.entity.LivingEntityEventJS;
import dev.latvian.mods.kubejs.level.BlockContainerJS;
import dev.latvian.mods.kubejs.server.ServerScriptManager;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import pie.ilikepiefoo.fabric.FabricEventsJS;

/**
 * An event that is called when an entity starts to sleep.
 * or
 * An event that is called when an entity stops sleeping and wakes up.
 */
public class SleepingEventJS extends LivingEntityEventJS {
	private final LivingEntity entity;
	private final BlockPos sleepingPos;

	public SleepingEventJS(LivingEntity entity, BlockPos sleepingPos) {
		this.entity = entity;
		this.sleepingPos = sleepingPos;
	}

	@Override
	public LivingEntity getEntity() {
		return entity;
	}

	public BlockPos getSleepingPos() {
		return sleepingPos;
	}

	public BlockContainerJS getPos() {
		return getLevel().kjs$getBlock(sleepingPos);
	}

	/**
	 * Called when an entity starts to sleep.
	 *
	 * @param entity      the sleeping entity
	 * @param sleepingPos the {@linkplain LivingEntity#getSleepingPos() sleeping position} of the entity
	 */
	public static void startHandler(LivingEntity entity, BlockPos sleepingPos) {
		if (ServerScriptManager.instance == null) {
			return;
		}
		FabricEventsJS.START_SLEEPING.post(new SleepingEventJS(entity, sleepingPos));
	}

	/**
	 * Called when an entity stops sleeping and wakes up.
	 *
	 * @param entity      the sleeping entity
	 * @param sleepingPos the {@linkplain LivingEntity#getSleepingPos() sleeping position} of the entity
	 */
	public static void stopHandler(LivingEntity entity, BlockPos sleepingPos) {
		if (ServerScriptManager.instance == null) {
			return;
		}
		FabricEventsJS.STOP_SLEEPING.post(new SleepingEventJS(entity, sleepingPos));
	}
}

