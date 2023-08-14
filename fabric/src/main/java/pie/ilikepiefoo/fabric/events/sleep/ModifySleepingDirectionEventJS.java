package pie.ilikepiefoo.fabric.events.sleep;

import dev.latvian.mods.kubejs.entity.LivingEntityEventJS;
import dev.latvian.mods.kubejs.level.BlockContainerJS;
import dev.latvian.mods.kubejs.server.ServerScriptManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import pie.ilikepiefoo.fabric.FabricEventsJS;

/**
 * An event that can be used to provide the entity's sleep direction if missing.
 *
 * <p>This is useful for custom bed blocks that need to determine the sleeping direction themselves.
 * If the block is not a {@link net.minecraft.world.level.block.BedBlock}, you need to provide the sleeping direction manually
 * with this event.
 */
public class ModifySleepingDirectionEventJS extends LivingEntityEventJS {
    private final LivingEntity entity;
    private final BlockPos sleepingPos;
    @Nullable
    private Direction sleepingDirection;

    public ModifySleepingDirectionEventJS( LivingEntity entity, BlockPos sleepingPos, @Nullable Direction sleepingDirection ) {
        this.entity = entity;
        this.sleepingPos = sleepingPos;
        this.sleepingDirection = sleepingDirection;
    }

    /**
     * Modifies or provides a sleeping direction for a block.
     * The sleeping direction is where a player's head is pointing when they're sleeping.
     *
     * @param entity            the sleeping entity
     * @param sleepingPos       the position of the block slept on
     * @param sleepingDirection the old sleeping direction, or {@code null} if not determined by vanilla or previous callbacks
     * @return the new sleeping direction
     */
    @Nullable
    public static Direction handler( LivingEntity entity, BlockPos sleepingPos, @Nullable Direction sleepingDirection ) {
        if (ServerScriptManager.instance == null || !FabricEventsJS.MODIFY_SLEEPING_DIRECTION.hasListeners()) {
            return sleepingDirection;
        }
        ModifySleepingDirectionEventJS event = new ModifySleepingDirectionEventJS(entity, sleepingPos, sleepingDirection);
        FabricEventsJS.MODIFY_SLEEPING_DIRECTION.post(event);
        return event.getSleepingDirection();
    }

    @Nullable
    public Direction getSleepingDirection() {
        return sleepingDirection;
    }

    public void setSleepingDirection( @Nullable Direction sleepingDirection ) {
        this.sleepingDirection = sleepingDirection;
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

    @Nullable
    public Direction getDirection() {
        return sleepingDirection;
    }

    public void setDirection( @Nullable Direction sleepingDirection ) {
        this.sleepingDirection = sleepingDirection;
    }

}

