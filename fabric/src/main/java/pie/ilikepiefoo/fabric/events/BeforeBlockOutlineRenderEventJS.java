package pie.ilikepiefoo.fabric.events;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class BeforeBlockOutlineRenderEventJS extends WorldRenderContextEventJS {
	private final @Nullable HitResult hitResult;

	public BeforeBlockOutlineRenderEventJS(WorldRenderContext context, @Nullable HitResult hitResult) {
		super(context);
		this.hitResult = hitResult;
	}

	@Override
	public boolean canCancel() {
		return true;
	}

	@Nullable
	public HitResult getHitResult() {
		return hitResult;
	}

	public double distanceTo(Entity entity) {
		assert hitResult != null;
		return hitResult.distanceTo(entity);
	}

	public HitResult.Type getType() {
		assert hitResult != null;
		return hitResult.getType();
	}

	public Vec3 getLocation() {
		assert hitResult != null;
		return hitResult.getLocation();
	}
}

