package pie.ilikepiefoo.fabric.events.sleep;

import dev.latvian.mods.kubejs.entity.LivingEntityEventJS;
import dev.latvian.mods.kubejs.level.BlockContainerJS;
import dev.latvian.mods.kubejs.server.ServerScriptManager;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;
import pie.ilikepiefoo.fabric.FabricEventsJS;

/**
 * An event that sets the occupation state of a bed.
 *
 * <p>Note that this is <b>not</b> needed for blocks using {@link net.minecraft.world.level.block.BedBlock},
 * which are handled automatically.
 */
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

    /**
     * Sets the occupation state of a bed block.
     *
     * @param entity      the sleeping entity
     * @param sleepingPos the sleeping position
     * @param bedState    the block state of the bed
     * @param occupied    {@code true} if occupied, {@code false} if free
     * @return {@code true} if the occupation state was successfully modified, {@code false} to fall back to other callbacks
     */
    public static boolean handler(LivingEntity entity, BlockPos sleepingPos, BlockState bedState, boolean occupied) {
        if (ServerScriptManager.instance == null || !FabricEventsJS.SET_BED_OCCUPATION_STATE.hasListeners()) {
            return false;
        }
        return FabricEventsJS.SET_BED_OCCUPATION_STATE.post(new SetBedOccupationStateEventJS(entity, sleepingPos, bedState, occupied))
                .arch()
                .isTrue();
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

    public BlockState getBedState() {
        return bedState;
    }

    public boolean isOccupied() {
        return occupied;
    }

}

