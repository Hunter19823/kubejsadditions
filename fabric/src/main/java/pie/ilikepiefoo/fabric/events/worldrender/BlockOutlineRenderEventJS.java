package pie.ilikepiefoo.fabric.events.worldrender;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import pie.ilikepiefoo.fabric.FabricEventsJS;

public class BlockOutlineRenderEventJS extends WorldRenderContextEventJS {
	private final WorldRenderContext.BlockOutlineContext blockOutlineContext;

	public BlockOutlineRenderEventJS(WorldRenderContext context, WorldRenderContext.BlockOutlineContext blockOutlineContext) {
		super(context);
		this.blockOutlineContext = blockOutlineContext;
	}

	public WorldRenderContext.BlockOutlineContext getBlockOutlineContext() {
		return blockOutlineContext;
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

	/**
	 * Called after block outline render checks are made and before the
	 * default block outline render runs.  Will NOT be called if the default outline
	 * render was cancelled in {@link #BEFORE_BLOCK_OUTLINE}.
	 *
	 * <p>Use this to replace the default block outline rendering for specific blocks that
	 * need special outline rendering or to add information that doesn't replace the block outline.
	 * Subscribers cannot affect each other or detect if another subscriber is also
	 * handling a specific block.  If two subscribers render for the same block, both
	 * renders will appear.
	 *
	 * <p>Returning false from any event subscriber will cancel the default block
	 * outline render.  This has no effect on other subscribers to this event -
	 * all subscribers will always be called.  Canceling is appropriate when the
	 * subscriber replacing the default block outline render for a specific block.
	 *
	 * <p>This event is not appropriate for mods that replace the default block
	 * outline render for <em>all</em> blocks because all event subscribers will
	 * always render - only the default outline render can be cancelled.  That should
	 * be accomplished by mixin to the block outline render routine itself, typically
	 * by targeting {@link LevelRenderer#renderShape}.
	 */
	public static boolean handle(WorldRenderContext context, WorldRenderContext.BlockOutlineContext blockOutlineContext) {
		BlockOutlineRenderEventJS event = new BlockOutlineRenderEventJS(context, blockOutlineContext);
		FabricEventsJS.BLOCK_OUTLINE.post(event);
		return !event.isCanceled();
	}

}

