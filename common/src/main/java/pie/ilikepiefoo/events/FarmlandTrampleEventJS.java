package pie.ilikepiefoo.events;


import dev.latvian.mods.kubejs.entity.EntityEventJS;
import dev.latvian.mods.kubejs.entity.EntityJS;
import dev.latvian.mods.kubejs.level.BlockContainerJS;
import dev.latvian.mods.kubejs.level.LevelJS;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class FarmlandTrampleEventJS extends EntityEventJS {
    private final Level level;
    private final BlockPos pos;
    private final BlockState state;
    private final float distance;
    private final Entity entity;

    public FarmlandTrampleEventJS(Level level, BlockPos pos, BlockState state, float distance, Entity entity) {
        this.level = level;
        this.pos = pos;
        this.state = state;
        this.distance = distance;
        this.entity = entity;
    }

    public static FarmlandTrampleEventJS of(Level world, BlockPos pos, BlockState state, float distance, Entity entity) {
        return new FarmlandTrampleEventJS(world, pos, state, distance, entity);
    }

    public BlockContainerJS getBlock() {
        return new BlockContainerJS(level, pos);
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
    public LevelJS getLevel() {
        return levelOf(level);
    }

    @Override
    public boolean canCancel() {
        return true;
    }
}
