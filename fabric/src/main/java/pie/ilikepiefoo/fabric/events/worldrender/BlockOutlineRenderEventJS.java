package pie.ilikepiefoo.fabric.events.worldrender;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;

public class BlockOutlineRenderEventJS extends WorldRenderContextEventJS {
	private final WorldRenderContext.BlockOutlineContext blockOutlineContext;

	public BlockOutlineRenderEventJS(WorldRenderContext context, WorldRenderContext.BlockOutlineContext blockOutlineContext) {
		super(context);
		this.blockOutlineContext = blockOutlineContext;
	}

	public WorldRenderContext.BlockOutlineContext getBlockOutlineContext() {
		return blockOutlineContext;
	}

	/**
	 * @deprecated Use {@link #consumers()} directly.
	 */
	@Deprecated
	public VertexConsumer vertexConsumer() {
		return blockOutlineContext.vertexConsumer();
	}

	public Entity entity() {
		return blockOutlineContext.entity();
	}

	public double cameraX() {
		return blockOutlineContext.cameraX();
	}

	public double cameraY() {
		return blockOutlineContext.cameraY();
	}

	public double cameraZ() {
		return blockOutlineContext.cameraZ();
	}

	public BlockPos blockPos() {
		return blockOutlineContext.blockPos();
	}

	public BlockState blockState() {
		return blockOutlineContext.blockState();
	}
}

