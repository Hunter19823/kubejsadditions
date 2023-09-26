package pie.ilikepiefoo.fabric.events.sleep;

import dev.latvian.mods.kubejs.entity.EntityJS;
import dev.latvian.mods.kubejs.level.BlockContainerJS;
import dev.latvian.mods.kubejs.player.PlayerEventJS;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.kubejs.server.ServerScriptManager;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import pie.ilikepiefoo.fabric.FabricEventsJS;

import javax.annotation.Nullable;

/**
 * An event that checks whether a player can start to sleep in a bed-like block.
 * This event only applies to sleeping using {@link Player#startSleepInBed(BlockPos)}.
 *
 * <p><b>Note:</b> Please use the more detailed events {@link #ALLOW_SLEEP_TIME} and {@link #ALLOW_NEARBY_MONSTERS}
 * if they match your use case! This helps with mod compatibility.
 *
 * <p>If this event returns a {@link net.minecraft.world.entity.player.Player.BedSleepingProblem}, it is used
 * as the return value of {@link Player#startSleepInBed(BlockPos)} and sleeping fails. A {@code null} return value
 * means that the player will start sleeping.
 *
 * <p>When this event is called, all vanilla sleeping checks have already succeeded, i.e. this event
 * is used in addition to vanilla checks. The more detailed events {@link #ALLOW_SLEEP_TIME} and {@link #ALLOW_NEARBY_MONSTERS}
 * are also checked before this event.
 */
public class AllowSleepingEventJS extends PlayerEventJS {
    private final Player player;
    private final BlockPos sleepingPos;
    @Nullable
    private Player.BedSleepingProblem sleepingProblem;

    public AllowSleepingEventJS(Player player, BlockPos sleepingPos) {
        this.player = player;
        this.sleepingPos = sleepingPos;
        this.sleepingProblem = null;
    }

    /**
     * Checks whether a player can start sleeping in a bed-like block.
     *
     * @param player      the sleeping player
     * @param sleepingPos the future {@linkplain LivingEntity#getSleepingPos() sleeping position} of the entity
     * @return {@code null} if the player can sleep, or a failure reason if they cannot
     * @see Player#startSleepInBed(BlockPos)
     */
    public static Player.BedSleepingProblem handler(Player player, BlockPos sleepingPos) {
        if (ServerScriptManager.instance == null) {
            return null;
        }
        AllowSleepingEventJS event = new AllowSleepingEventJS(player, sleepingPos);
        event.post(ScriptType.SERVER, FabricEventsJS.ALLOW_SLEEPING);
        if (event.isCancelled() && event.getSleepingProblem() == null) {
            return Player.BedSleepingProblem.OTHER_PROBLEM;
        }
        return event.sleepingProblem;
    }

    @Nullable
    public Player.BedSleepingProblem getSleepingProblem() {
        return sleepingProblem;
    }

    public void setSleepingProblem(@Nullable Player.BedSleepingProblem sleepingProblem) {
        this.sleepingProblem = sleepingProblem;
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

    @Override
    public EntityJS getEntity() {
        return entityOf(player);
    }
}

