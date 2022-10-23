package pie.ilikepiefoo.fabric.events.worldrender;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import dev.latvian.mods.kubejs.event.EventJS;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.Nullable;

public class WorldRenderContextEventJS extends EventJS {
	private final WorldRenderContext context;

	public WorldRenderContextEventJS(WorldRenderContext context) {
		this.context = context;
	}

	public WorldRenderContext getContext() {
		return context;
	}

	/**
	 * The world renderer instance doing the rendering and invoking the event.
	 *
	 * @return WorldRenderer instance invoking the event
	 */
	public LevelRenderer worldRenderer() {
		return context.worldRenderer();
	}

	public PoseStack matrixStack() {
		return context.matrixStack();
	}

	public float tickDelta() {
		return context.tickDelta();
	}

	public long limitTime() {
		return context.limitTime();
	}

	public boolean blockOutlines() {
		return context.blockOutlines();
	}

	public Camera camera() {
		return context.camera();
	}

	public GameRenderer gameRenderer() {
		return context.gameRenderer();
	}

	public LightTexture lightmapTextureManager() {
		return context.lightmapTextureManager();
	}

	public Matrix4f projectionMatrix() {
		return context.projectionMatrix();
	}

	/**
	 * Convenient access to {WorldRenderer.world}.
	 *
	 * @return world renderer's client world instance
	 */
	public ClientLevel world() {
		return context.world();
	}

	/**
	 * Convenient access to game performance profiler.
	 *
	 * @return the active profiler
	 */
	public ProfilerFiller profiler() {
		return context.profiler();
	}

	/**
	 * Test to know if "fabulous" graphics mode is enabled.
	 *
	 * <p>Use this for renders that need to render on top of all translucency to activate or deactivate different
	 * event handlers to get optimal depth testing results. When fabulous is off, it may be better to render
	 * during {@code WorldRenderLastCallback} after clouds and weather are drawn. Conversely, when fabulous mode is on,
	 * it may be better to draw during {@code WorldRenderPostTranslucentCallback}, before the fabulous mode composite
	 * shader runs, depending on which translucent buffer is being targeted.
	 *
	 * @return {@code true} when "fabulous" graphics mode is enabled.
	 */
	public boolean advancedTranslucency() {
		return context.advancedTranslucency();
	}

	/**
	 * The {@code VertexConsumerProvider} instance being used by the world renderer for most non-terrain renders.
	 * Generally this will be better for most use cases because quads for the same layer can be buffered
	 * incrementally and then drawn all at once by the world renderer.
	 *
	 * <p>IMPORTANT - all vertex coordinates sent to consumers should be relative to the camera to
	 * be consistent with other quads emitted by the world renderer and other mods.  If this isn't
	 * possible, caller should use a separate "immediate" instance.
	 *
	 * <p>This property is {@code null} before {@link WorldRenderEvents#BEFORE_ENTITIES} and after
	 * {@link WorldRenderEvents#BEFORE_DEBUG_RENDER} because the consumer buffers are not available before or
	 * drawn after that in vanilla world rendering.  Renders that cannot draw in one of the supported events
	 * must be drawn directly to the frame buffer, preferably in {@link WorldRenderEvents#LAST} to avoid being
	 * overdrawn or cleared.
	 */
	public @Nullable MultiBufferSource consumers() {
		return context.consumers();
	}

	/**
	 * View frustum, after it is initialized. Will be {@code null} during
	 * {@link WorldRenderEvents#START}.
	 */
	public @Nullable Frustum frustum() {
		return context.frustum();
	}
}

