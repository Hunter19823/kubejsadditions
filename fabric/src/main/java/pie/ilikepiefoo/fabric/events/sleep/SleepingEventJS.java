package pie.ilikepiefoo.fabric.events.sleep;

import dev.architectury.platform.Platform;
import dev.latvian.mods.kubejs.entity.EntityJS;
import dev.latvian.mods.kubejs.entity.LivingEntityEventJS;
import dev.latvian.mods.kubejs.level.BlockContainerJS;
import dev.latvian.mods.kubejs.script.ScriptType;
import net.fabricmc.api.EnvType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.server.IntegratedServer;
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

	public BlockPos getSleepingPos() {
		return sleepingPos;
	}

	public BlockContainerJS getPos() {
		return getLevel().getBlock(sleepingPos);
	}

	/**
	 * Called when an entity starts to sleep.
	 *
	 * @param entity      the sleeping entity
	 * @param sleepingPos the {@linkplain LivingEntity#getSleepingPos() sleeping position} of the entity
	 */
	public static void startHandler(LivingEntity entity, BlockPos sleepingPos) {
		if(ScriptType.SERVER.manager == null)
			return;
		SleepingEventJS event = new SleepingEventJS(entity, sleepingPos);
		event.post(ScriptType.SERVER, FabricEventsJS.START_SLEEPING);
	}

	/**
	 * Called when an entity stops sleeping and wakes up.
	 *
	 * @param entity      the sleeping entity
	 * @param sleepingPos the {@linkplain LivingEntity#getSleepingPos() sleeping position} of the entity
	 */
	public static void stopHandler(LivingEntity entity, BlockPos sleepingPos) {
		if(ScriptType.SERVER.manager == null)
			return;
		SleepingEventJS event = new SleepingEventJS(entity, sleepingPos);
		event.post(ScriptType.SERVER, FabricEventsJS.STOP_SLEEPING);
	}

	@Override
	public EntityJS getEntity() {
		return entityOf(entity);
	}
}

