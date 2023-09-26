package pie.ilikepiefoo.events;


import dev.latvian.mods.kubejs.entity.EntityJS;
import dev.latvian.mods.kubejs.level.LevelJS;
import dev.latvian.mods.kubejs.player.PlayerEventJS;
import dev.latvian.mods.kubejs.util.UtilsJS;
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

    public static PlayerChangeDimensionEventJS of(ServerPlayer player, ResourceKey<Level> oldLevel, ResourceKey<Level> newLevel) {
        return new PlayerChangeDimensionEventJS(player, oldLevel, newLevel);
    }

    @Override
    public EntityJS getEntity() {
        return entityOf(player);
    }

    public ResourceKey<Level> getOldWorldKey() {
        return oldWorld;
    }

    public LevelJS getOldLevel() {
        return UtilsJS.getLevel(player.getServer().getLevel(oldWorld));
    }

    public LevelJS getNewLevel() {
        return UtilsJS.getLevel(player.getServer().getLevel(newWorld));
    }

    public ResourceKey<Level> getNewWorldKey() {
        return newWorld;
    }
}
