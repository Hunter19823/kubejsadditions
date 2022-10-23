package pie.ilikepiefoo.fabric.events.sleep;

import dev.latvian.mods.kubejs.entity.EntityJS;
import dev.latvian.mods.kubejs.level.BlockContainerJS;
import dev.latvian.mods.kubejs.player.PlayerEventJS;
import dev.latvian.mods.kubejs.script.ScriptType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import pie.ilikepiefoo.fabric.FabricEventsJS;

import javax.annotation.Nonnull;

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

	public static InteractionResult handler(Player player, BlockPos sleepingPos, boolean vanillaResult) {
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

