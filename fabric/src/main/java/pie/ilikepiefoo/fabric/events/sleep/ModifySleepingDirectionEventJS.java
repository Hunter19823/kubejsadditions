package pie.ilikepiefoo.fabric.events.sleep;

import dev.latvian.mods.kubejs.entity.EntityJS;
import dev.latvian.mods.kubejs.entity.LivingEntityEventJS;
import dev.latvian.mods.kubejs.level.BlockContainerJS;
import dev.latvian.mods.kubejs.script.ScriptType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import pie.ilikepiefoo.fabric.FabricEventsJS;

public class ModifySleepingDirectionEventJS extends LivingEntityEventJS {

	private final LivingEntity entity;
	private final BlockPos sleepingPos;
	@Nullable
	private Direction sleepingDirection;

	public ModifySleepingDirectionEventJS(LivingEntity entity, BlockPos sleepingPos, @Nullable Direction sleepingDirection) {
		this.entity = entity;
		this.sleepingPos = sleepingPos;
		this.sleepingDirection = sleepingDirection;
	}

	public BlockPos getSleepingPos() {
		return sleepingPos;
	}

	public BlockContainerJS getPos() {
		return getLevel().getBlock(sleepingPos);
	}

	@Nullable
	public Direction getSleepingDirection() {
		return sleepingDirection;
	}

	public void setSleepingDirection(@Nullable Direction sleepingDirection) {
		this.sleepingDirection = sleepingDirection;
	}

	@Nullable
	public Direction getDirection() {
		return sleepingDirection;
	}

	public void setDirection(@Nullable Direction sleepingDirection) {
		this.sleepingDirection = sleepingDirection;
	}

	@Nullable
	public static Direction handler(LivingEntity entity, BlockPos sleepingPos, @Nullable Direction sleepingDirection) {
		ModifySleepingDirectionEventJS event = new ModifySleepingDirectionEventJS(entity, sleepingPos, sleepingDirection);
		event.post(ScriptType.SERVER, FabricEventsJS.MODIFY_SLEEPING_DIRECTION);
		return event.getSleepingDirection();
	}

	@Override
	public EntityJS getEntity() {
		return entityOf(entity);
	}
}

