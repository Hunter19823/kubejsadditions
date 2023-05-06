package pie.ilikepiefoo.events;

import dev.latvian.mods.kubejs.player.PlayerEventJS;
import net.minecraft.world.entity.Entity;
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
	public Player getEntity() {
		return player;
	}

	public Entity getAnimal() {
		return animal;
	}

	public static EntityTameEventJS of(Animal animal, Player player) {
		return new EntityTameEventJS(animal, player);
	}

	@Override
	public boolean canCancel() {
		return true;
	}
}
