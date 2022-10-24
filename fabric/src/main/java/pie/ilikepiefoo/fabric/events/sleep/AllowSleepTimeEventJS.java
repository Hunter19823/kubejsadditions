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
 * An event that checks whether the current time of day is valid for sleeping.
 *
 * <p>Note that if sleeping during day time is allowed, the game will still reset the time to 0 if the usual
 * conditions are met, unless forbidden with {@link #ALLOW_RESETTING_TIME}.
 */
public class AllowSleepTimeEventJS extends PlayerEventJS {

	private final Player player;
	private final BlockPos sleepingPos;
	private final boolean vanillaResult;
	@Nonnull
	private InteractionResult result;

	public AllowSleepTimeEventJS(Player player, BlockPos sleepingPos, boolean vanillaResult) {
		this.player = player;
		this.sleepingPos = sleepingPos;
		this.vanillaResult = vanillaResult;
		this.result = InteractionResult.PASS;
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

	public boolean getVanillaResult() {
		return vanillaResult;
	}

	@Nonnull
	public InteractionResult getResult() {
		return result;
	}

	public void setResult(@Nonnull InteractionResult result) {
		this.result = result;
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
	 *         {@link InteractionResult#PASS} to fall back to other callbacks
	 */
	public static InteractionResult handler(Player player, BlockPos sleepingPos, boolean vanillaResult) {
		if(ServerScriptManager.instance == null)
			return InteractionResult.PASS;
		AllowSleepTimeEventJS event = new AllowSleepTimeEventJS(player, sleepingPos, vanillaResult);
		event.post(ScriptType.SERVER, FabricEventsJS.ALLOW_SLEEP_TIME);
		if(event.isCancelled() && event.getResult() == InteractionResult.PASS) {
			return InteractionResult.FAIL;
		}
		return event.getResult();
	}

	@Override
	public EntityJS getEntity() {
		return entityOf(player);
	}
}

