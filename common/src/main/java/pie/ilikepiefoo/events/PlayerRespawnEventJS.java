package pie.ilikepiefoo.events;

import dev.latvian.mods.kubejs.entity.EntityJS;
import dev.latvian.mods.kubejs.player.PlayerEventJS;
import net.minecraft.server.level.ServerPlayer;

public class PlayerRespawnEventJS extends PlayerEventJS {
    private final ServerPlayer player;
    private final boolean conqueredEnd;

    public PlayerRespawnEventJS(ServerPlayer player, boolean conqueredEnd) {
        this.player = player;
        this.conqueredEnd = conqueredEnd;
    }

    public static PlayerRespawnEventJS of(ServerPlayer player, boolean conqueredEnd) {
        return new PlayerRespawnEventJS(player, conqueredEnd);
    }

    @Override
    public EntityJS getEntity() {
        return entityOf(player);
    }

    public boolean leavingEnd() {
        return conqueredEnd;
    }

    public boolean returningFromEnd() {
        return conqueredEnd;
    }

    public boolean causedByPortal() {
        return conqueredEnd;
    }

    public boolean causedByDeath() {
        return !conqueredEnd;
    }
}
