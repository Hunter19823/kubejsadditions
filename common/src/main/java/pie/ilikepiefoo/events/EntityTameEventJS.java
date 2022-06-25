package pie.ilikepiefoo.events;

import dev.latvian.mods.kubejs.entity.EntityJS;
import dev.latvian.mods.kubejs.player.PlayerEventJS;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;

public class EntityTameEventJS extends PlayerEventJS {
	private final Animal animal;
	private final Player player;

	public EntityTameEventJS(Animal animal, Player player) {
		this.animal = animal;
		this.player = player;
	}

	@Override
	public EntityJS getEntity() {
		return entityOf(player);
	}

	public EntityJS getAnimal() {
		return entityOf(animal);
	}

	public static EntityTameEventJS of(Animal animal, Player player) {
		return new EntityTameEventJS(animal, player);
	}

	@Override
	public boolean canCancel() {
		return true;
	}
}
