package pie.ilikepiefoo.fabric.events.sleep;

import dev.latvian.mods.kubejs.level.BlockContainerJS;
import dev.latvian.mods.kubejs.player.PlayerEventJS;
import dev.latvian.mods.kubejs.server.ServerScriptManager;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import pie.ilikepiefoo.fabric.FabricEventsJS;


/**
 * An event that checks whether the current time of day is valid for sleeping.
 *
 * <p>Note that if sleeping during day time is allowed, the game will still reset the time to 0 if the usual
 * conditions are met, unless forbidden with {@link EntitySleepEvents#ALLOW_RESETTING_TIME}.
 */
public class AllowSleepTimeEventJS extends PlayerEventJS {
    private final Player player;
    private final BlockPos sleepingPos;
    private final boolean vanillaResult;

    public AllowSleepTimeEventJS(Player player, BlockPos sleepingPos, boolean vanillaResult) {
        this.player = player;
        this.sleepingPos = sleepingPos;
        this.vanillaResult = vanillaResult;
    }

    /**
     * Checks whether the current time of day is valid for sleeping.
     *
     * <p>Non-{@linkplain InteractionResult#PASS passing} return values cancel further callbacks.
     *
     * @param player        the sleeping player
     * @param sleepingPos   the (possibly still unset) {@linkplain LivingEntity#getSleepingPos() sleeping position} of the player
     * @param vanillaResult {@code true} if vanilla allows the time, {@code false} otherwise
     * @return {@link InteractionResult#SUCCESS} if the time is valid, {@link InteractionResult#FAIL} if it's not,
     * {@link InteractionResult#PASS} to fall back to other callbacks
     */
    public static InteractionResult handler(Player player, BlockPos sleepingPos, boolean vanillaResult) {
        if (ServerScriptManager.instance == null || !FabricEventsJS.ALLOW_SLEEP_TIME.hasListeners()) {
            return InteractionResult.PASS;
        }
        return FabricEventsJS.ALLOW_SLEEP_TIME.post(new AllowSleepTimeEventJS(player, sleepingPos, vanillaResult)).arch().asMinecraft();
    }

    @Override
    public Player getEntity() {
        return player;
    }

    public BlockPos getSleepingPos() {
        return sleepingPos;
    }

    public BlockContainerJS getPos() {
        return getLevel().kjs$getBlock(sleepingPos);
    }

    public boolean getVanillaResult() {
        return vanillaResult;
    }

}

