package pie.ilikepiefoo.events;

import dev.latvian.mods.kubejs.player.PlayerEventJS;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
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

    public static PlayerChangeDimensionEventJS of(ServerPlayer player, ResourceKey<Level> oldLevel, ResourceKey<Level> newLevel) {
        return new PlayerChangeDimensionEventJS(player, oldLevel, newLevel);
    }

    @Override
    public Player getEntity() {
        return player;
    }

    public ResourceKey<Level> getOldWorldKey() {
        return oldWorld;
    }

    public Level getOldLevel() {
        return player.getServer().getLevel(oldWorld);
    }

    public Level getNewLevel() {
        return player.getServer().getLevel(newWorld);
    }

    public ResourceKey<Level> getNewWorldKey() {
        return newWorld;
    }

}
