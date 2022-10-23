package pie.ilikepiefoo.fabric.events.sleep;

import dev.latvian.mods.kubejs.entity.EntityJS;
import dev.latvian.mods.kubejs.level.BlockContainerJS;
import dev.latvian.mods.kubejs.player.PlayerEventJS;
import dev.latvian.mods.kubejs.script.ScriptType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import pie.ilikepiefoo.fabric.FabricEventsJS;

import javax.annotation.Nullable;

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

	@Nullable
	public Player.BedSleepingProblem getSleepingProblem() {
		return sleepingProblem;
	}

	public void setSleepingProblem(@Nullable Player.BedSleepingProblem sleepingProblem) {
		this.sleepingProblem = sleepingProblem;
	}

	public static Player.BedSleepingProblem handler(Player player, BlockPos sleepingPos) {
		AllowSleepingEventJS event = new AllowSleepingEventJS(player, sleepingPos);
		event.post(ScriptType.SERVER, FabricEventsJS.ALLOW_SLEEPING);
		if(event.isCancelled() && event.getSleepingProblem() == null) {
			return Player.BedSleepingProblem.OTHER_PROBLEM;
		}
		return event.sleepingProblem;
	}

	@Override
	public EntityJS getEntity() {
		return entityOf(player);
	}
}

