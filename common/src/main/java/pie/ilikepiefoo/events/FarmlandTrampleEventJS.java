package pie.ilikepiefoo.events;

import dev.latvian.kubejs.entity.EntityEventJS;
import dev.latvian.kubejs.entity.EntityJS;
import dev.latvian.kubejs.world.BlockContainerJS;
import dev.latvian.kubejs.world.WorldJS;
import me.shedaniel.architectury.event.EventResult;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class FarmlandTrampleEventJS extends EntityEventJS {
	private final Level world;
	private final BlockPos pos;
	private final BlockState state;
	private final float distance;
	private final Entity entity;

	public FarmlandTrampleEventJS(Level level, BlockPos pos, BlockState state, float distance, Entity entity) {
		this.world = level;
		this.pos = pos;
		this.state = state;
		this.distance = distance;
		this.entity = entity;
	}

	public static FarmlandTrampleEventJS of(Level world, BlockPos pos, BlockState state, float distance, Entity entity) {
		return new FarmlandTrampleEventJS(world, pos, state, distance, entity);
	}

	@Override
	public WorldJS getWorld() {
		return worldOf(world);
	}

	public BlockContainerJS getBlock() {
		return new BlockContainerJS(world, pos);
	}

	public BlockState getState() {
		return state;
	}

	public float getDistance() {
		return distance;
	}

	@Override
	public EntityJS getEntity() {
		return entityOf(entity);
	}

	@Override
	public boolean canCancel() {
		return true;
	}
}
