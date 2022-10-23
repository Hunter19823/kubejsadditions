package pie.ilikepiefoo.fabric.events.sleep;

import dev.latvian.mods.kubejs.entity.EntityJS;
import dev.latvian.mods.kubejs.player.PlayerEventJS;
import dev.latvian.mods.kubejs.script.ScriptType;
import net.minecraft.world.entity.player.Player;
import pie.ilikepiefoo.fabric.FabricEventsJS;

public class AllowResettingTimeEventJS extends PlayerEventJS {
	private final Player player;

	public AllowResettingTimeEventJS(Player player) {
		this.player = player;
	}

	@Override
	public boolean canCancel() {
		return true;
	}

	@Override
	public EntityJS getEntity() {
		return entityOf(player);
	}

	public static boolean handler(Player player) {
		AllowResettingTimeEventJS event = new AllowResettingTimeEventJS(player);
		event.post(ScriptType.SERVER, FabricEventsJS.ALLOW_RESETTING_TIME);
		return !event.isCancelled();
	}
}

