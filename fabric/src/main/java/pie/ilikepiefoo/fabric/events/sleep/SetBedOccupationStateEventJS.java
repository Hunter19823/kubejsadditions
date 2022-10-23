package pie.ilikepiefoo.fabric.events.sleep;

import dev.latvian.mods.kubejs.entity.EntityJS;
import dev.latvian.mods.kubejs.entity.LivingEntityEventJS;
import dev.latvian.mods.kubejs.level.BlockContainerJS;
import dev.latvian.mods.kubejs.script.ScriptType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;
import pie.ilikepiefoo.fabric.FabricEventsJS;

public class SetBedOccupationStateEventJS extends LivingEntityEventJS {

	private final LivingEntity entity;
	private final BlockPos sleepingPos;
	private final BlockState bedState;
	private final boolean occupied;

	public SetBedOccupationStateEventJS(LivingEntity entity, BlockPos sleepingPos, BlockState bedState, boolean occupied) {
		this.entity = entity;
		this.sleepingPos = sleepingPos;
		this.bedState = bedState;
		this.occupied = occupied;
	}

	@Override
	public boolean canCancel() {
		return true;
	}

	public BlockPos getSleepingPos() {
		return sleepingPos;
	}

	public BlockContainerJS getPos() {
		return getLevel().getBlock(sleepingPos);
	}

	public BlockState getBedState() {
		return bedState;
	}

	public boolean isOccupied() {
		return occupied;
	}

	public static boolean handler(LivingEntity entity, BlockPos sleepingPos, BlockState bedState, boolean occupied) {
		SetBedOccupationStateEventJS event = new SetBedOccupationStateEventJS(entity, sleepingPos, bedState, occupied);
		event.post(ScriptType.SERVER, FabricEventsJS.SET_BED_OCCUPATION_STATE);
		return !event.isCancelled();
	}

	@Override
	public EntityJS getEntity() {
		return entityOf(entity);
	}
}

