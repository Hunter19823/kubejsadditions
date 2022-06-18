package pie.ilikepiefoo.events;

import dev.latvian.kubejs.entity.EntityEventJS;
import dev.latvian.kubejs.entity.EntityJS;
import net.minecraft.world.entity.Entity;

public class EntityEnterChunkEventJS extends EntityEventJS {
	private final Entity entity;
	private final int chunkX;
	private final int chunkZ;
	private final int prevX;
	private final int prevZ;

	public EntityEnterChunkEventJS(Entity entity, int chunkX, int chunkZ, int prevX, int prevZ) {
		this.entity = entity;
		this.chunkX = chunkX;
		this.chunkZ = chunkZ;
		this.prevX = prevX;
		this.prevZ = prevZ;
	}

	@Override
	public EntityJS getEntity() {
		return entityOf(entity);
	}

	public int getChunkX() {
		return chunkX;
	}

	public int getChunkZ() {
		return chunkZ;
	}

	public int getPrevX() {
		return prevX;
	}

	public int getPrevZ() {
		return prevZ;
	}

	public static EntityEnterChunkEventJS of(Entity entity, int chunkX, int chunkZ, int prevX, int prevZ) {
		return new EntityEnterChunkEventJS(entity, chunkX, chunkZ, prevX, prevZ);
	}
}
