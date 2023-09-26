package pie.ilikepiefoo.fabric.events.sleep;

import dev.latvian.mods.kubejs.entity.EntityJS;
import dev.latvian.mods.kubejs.level.BlockContainerJS;
import dev.latvian.mods.kubejs.player.PlayerEventJS;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.kubejs.server.ServerScriptManager;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import pie.ilikepiefoo.fabric.FabricEventsJS;

import javax.annotation.Nonnull;

/**
 * An event that checks whether players can sleep when monsters are nearby.
 *
 * <p>This event can also be used to force a failing result, meaning it can do custom monster checks.
 */
public class AllowNearbyMonstersEventJS extends PlayerEventJS {

    private final Player player;
    private final BlockPos sleepingPos;
    private final boolean vanillaResult;
    @Nonnull
    private InteractionResult result;

    public AllowNearbyMonstersEventJS(Player player, BlockPos sleepingPos, boolean vanillaResult) {
        this.player = player;
        this.sleepingPos = sleepingPos;
        this.vanillaResult = vanillaResult;
        this.result = InteractionResult.PASS;
    }

    /**
     * Checks whether a player can sleep when monsters are nearby.
     *
     * <p>Non-{@linkplain InteractionResult#PASS passing} return values cancel further callbacks.
     *
     * @param player        the sleeping player
     * @param sleepingPos   the (possibly still unset) {@linkplain LivingEntity#getSleepingPos() sleeping position} of the player
     * @param vanillaResult {@code true} if vanilla's monster check succeeded (there were no monsters), {@code false} otherwise
     * @return {@link InteractionResult#SUCCESS} to allow sleeping, {@link InteractionResult#FAIL} to prevent sleeping,
     * {@link InteractionResult#PASS} to fall back to other callbacks
     */
    public static InteractionResult handler(Player player, BlockPos sleepingPos, boolean vanillaResult) {
        if (ServerScriptManager.instance == null) {
            return InteractionResult.PASS;
        }
        AllowNearbyMonstersEventJS event = new AllowNearbyMonstersEventJS(player, sleepingPos, vanillaResult);
        event.post(ScriptType.SERVER, FabricEventsJS.ALLOW_NEARBY_MONSTERS);
        if (event.isCancelled() && event.getResult() == InteractionResult.PASS) {
            return InteractionResult.FAIL;
        }
        return event.getResult();
    }

    @Nonnull
    public InteractionResult getResult() {
        return result;
    }

    public void setResult(@Nonnull InteractionResult result) {
        this.result = result;
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

    public boolean getVanillaResult() {
        return vanillaResult;
    }
}

