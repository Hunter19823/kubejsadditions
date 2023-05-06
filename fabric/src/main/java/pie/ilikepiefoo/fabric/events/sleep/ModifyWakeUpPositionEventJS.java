package pie.ilikepiefoo.fabric.events.sleep;

import dev.latvian.mods.kubejs.entity.LivingEntityEventJS;
import dev.latvian.mods.kubejs.level.BlockContainerJS;
import dev.latvian.mods.kubejs.server.ServerScriptManager;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import pie.ilikepiefoo.fabric.FabricEventsJS;

/**
 * An event that can be used to provide the entity's wake-up position if missing.
 *
 * <p>This is useful for custom bed blocks that need to determine the wake-up position themselves.
 * If the block is not a {@link net.minecraft.world.level.block.BedBlock}, you need to provide the wake-up position manually
 * with this event.
 */
public class ModifyWakeUpPositionEventJS extends LivingEntityEventJS {
	private final LivingEntity entity;
	private final BlockPos sleepingPos;
	private final BlockState bedState;
	@Nullable
	private Vec3 wakeUpPos;

	public ModifyWakeUpPositionEventJS(LivingEntity entity, BlockPos sleepingPos, BlockState bedState, @Nullable Vec3 wakeUpPos) {
		this.entity = entity;
		this.sleepingPos = sleepingPos;
		this.bedState = bedState;
		this.wakeUpPos = wakeUpPos;
	}

	@Override
	public LivingEntity getEntity() {
		return entity;
	}

	public BlockPos getSleepingPos() {
		return sleepingPos;
	}

	public BlockContainerJS getSleepPos() {
		return getLevel().kjs$getBlock(sleepingPos);
	}

	public BlockState getBedState() {
		return bedState;
	}

	@Nullable
	public Vec3 getWakeUpPos() {
		return wakeUpPos;
	}

	public void setWakeUpPos(@Nullable Vec3 wakeUpPos) {
		this.wakeUpPos = wakeUpPos;
	}

	public void setWakeUpPos(double x, double y, double z) {
		this.wakeUpPos = new Vec3(x, y, z);
	}

	public void setWakeUpPos(@Nullable BlockContainerJS block) {
		if (block != null) {
			this.wakeUpPos = new Vec3(block.getX(), block.getY(), block.getZ());
		} else {
			this.wakeUpPos = null;
		}
	}

	/**
	 * Modifies or provides a wake-up position for an entity waking up.
	 *
	 * @param entity      the sleeping entity
	 * @param sleepingPos the position of the block slept on
	 * @param bedState    the block slept on
	 * @param wakeUpPos   the old wake-up position, or {@code null} if not determined by vanilla or previous callbacks
	 * @return the new wake-up position
	 */
	@Nullable
	public static Vec3 handler(LivingEntity entity, BlockPos sleepingPos, BlockState bedState, @Nullable Vec3 wakeUpPos) {
		if (ServerScriptManager.instance == null) {
			return wakeUpPos;
		}
		ModifyWakeUpPositionEventJS event = new ModifyWakeUpPositionEventJS(entity, sleepingPos, bedState, wakeUpPos);
		FabricEventsJS.MODIFY_WAKE_UP_POSITION.post(event);
		return event.getWakeUpPos();
	}
}

