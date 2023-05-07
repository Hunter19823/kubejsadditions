package pie.ilikepiefoo.fabric.events.sleep;

import dev.latvian.mods.kubejs.player.PlayerEventJS;
import dev.latvian.mods.kubejs.server.ServerScriptManager;
import net.minecraft.world.entity.player.Player;
import pie.ilikepiefoo.fabric.FabricEventsJS;

/**
 * An event that checks whether a sleeping player counts into skipping the current day and resetting the time to 0.
 *
 * <p>When this event is called, all vanilla time resetting checks have already succeeded, i.e. this event
 * is used in addition to vanilla checks.
 */
public class AllowResettingTimeEventJS extends PlayerEventJS {
	private final Player player;

	public AllowResettingTimeEventJS(Player player) {
		this.player = player;
	}

	@Override
	public Player getEntity() {
		return player;
	}

	/**
	 * Checks whether a sleeping player counts into skipping the current day and resetting the time to 0.
	 *
	 * @param player the sleeping player
	 * @return {@code true} if allowed, {@code false} otherwise
	 */
	public static boolean handler(Player player) {
		if (ServerScriptManager.instance == null) {
			return true;
		}
		AllowResettingTimeEventJS event = new AllowResettingTimeEventJS(player);
		FabricEventsJS.ALLOW_RESETTING_TIME.post(event);
		return !event.isCanceled();
	}
}

