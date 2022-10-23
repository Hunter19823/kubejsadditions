package pie.ilikepiefoo.fabric.events.sleep;

import dev.latvian.mods.kubejs.entity.EntityJS;
import dev.latvian.mods.kubejs.entity.LivingEntityEventJS;
import dev.latvian.mods.kubejs.level.BlockContainerJS;
import dev.latvian.mods.kubejs.script.ScriptType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import pie.ilikepiefoo.fabric.FabricEventsJS;

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

	public BlockPos getSleepingPos() {
		return sleepingPos;
	}

	public BlockContainerJS getSleepPos() {
		return getLevel().getBlock(sleepingPos);
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
		this.wakeUpPos = new Vec3(x,y,z);
	}

	public void setWakeUpPos(@Nullable BlockContainerJS block) {
		if(block != null)
			this.wakeUpPos = new Vec3(block.getX(),block.getY(),block.getZ());
		else
			this.wakeUpPos = null;
	}

	@Nullable
	public static Vec3 handler(LivingEntity entity, BlockPos sleepingPos, BlockState bedState, @Nullable Vec3 wakeUpPos) {
		ModifyWakeUpPositionEventJS event = new ModifyWakeUpPositionEventJS(entity, sleepingPos, bedState, wakeUpPos);
		event.post(ScriptType.SERVER, FabricEventsJS.MODIFY_WAKE_UP_POSITION);
		return event.getWakeUpPos();
	}

	@Override
	public EntityJS getEntity() {
		return entityOf(entity);
	}
}

