package pie.ilikepiefoo.events;

import dev.latvian.kubejs.entity.EntityJS;
import dev.latvian.kubejs.player.PlayerEventJS;
import dev.latvian.kubejs.util.UtilsJS;
import dev.latvian.kubejs.world.WorldJS;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

public class PlayerChangeDimensionEventJS extends PlayerEventJS {
	private final ServerPlayer player;
	private final ResourceKey<Level> oldWorld;
	private final ResourceKey<Level> newWorld;

	public PlayerChangeDimensionEventJS(ServerPlayer player, ResourceKey<Level> oldWorld, ResourceKey<Level> newWorld) {
		this.player = player;
		this.oldWorld = oldWorld;
		this.newWorld = newWorld;
	}

	@Override
	public EntityJS getEntity() {
		return entityOf(player);
	}

	public ResourceKey<Level> getOldWorldKey() {
		return oldWorld;
	}

	public WorldJS getOldWorld() {
		return UtilsJS.getWorld(player.getServer().getLevel(oldWorld));
	}

	public WorldJS getNewWorld() {
		return UtilsJS.getWorld(player.getServer().getLevel(newWorld));
	}

	public ResourceKey<Level> getNewWorldKey() {
		return newWorld;
	}

	public static PlayerChangeDimensionEventJS of(ServerPlayer player, ResourceKey<Level> oldLevel, ResourceKey<Level> newLevel) {
		return new PlayerChangeDimensionEventJS(player, oldLevel, newLevel);
	}
}
