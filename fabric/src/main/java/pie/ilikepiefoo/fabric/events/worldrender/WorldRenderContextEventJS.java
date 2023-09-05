package pie.ilikepiefoo.fabric.events.worldrender;

import com.mojang.blaze3d.vertex.PoseStack;
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
import org.joml.Matrix4f;
import pie.ilikepiefoo.fabric.FabricEventsJS;


public class WorldRenderContextEventJS extends EventJS {
	private final WorldRenderContext context;

	public WorldRenderContextEventJS(WorldRenderContext context) {
		this.context = context;
	}

	/**
	 * Called after the Solid, Cutout and Cutout Mipped terrain layers have been output to the framebuffer.
	 *
	 * <p>Use to render non-translucent terrain to the framebuffer.
	 *
	 * <p>Note that 3rd-party renderers may combine these passes or otherwise alter the
	 * rendering pipeline for sake of performance or features. This can break direct writes to the
	 * framebuffer.  Use this event for cases that cannot be satisfied by FabricBakedModel,
	 * BlockEntityRenderer or other existing abstraction. If at all possible, use an existing terrain
	 * RenderLayer instead of outputting to the framebuffer directly with GL calls.
	 *
	 * <p>The consumer is responsible for setup and tear down of GL state appropriate for the intended output.
	 *
	 * <p>Because solid and cutout quads are depth-tested, order of output does not matter except to improve
	 * culling performance, which should not be significant after primary terrain rendering. This means
	 * mods that currently hook calls to individual render layers can simply execute them all at once when
	 * the event is called.
	 *
	 * <p>This event fires before entities and block entities are rendered and may be useful to prepare them.
	 */
	public static void beforeEntitiesHandle(WorldRenderContext context) {
		FabricEventsJS.BEFORE_ENTITIES.post(new WorldRenderContextEventJS(context));
	}

	/**
	 * Called after entity, terrain, and particle translucent layers have been
	 * drawn to the framebuffer but before translucency combine has happened
	 * in fabulous mode.
	 *
	 * <p>Use for drawing overlays or other effects on top of those targets
	 * (or the main target when fabulous isn't active) before clouds and weather
	 * are drawn.  However, note that {@code WorldRenderPostEntityCallback} will
	 * offer better results in most use cases.
	 *
	 * <p>Vertex consumers are not available in this event because all buffered quads
	 * are drawn before this event is called.  Any rendering here must be drawn
	 * directly to the frame buffer.  The render state matrix will not include
	 * camera transformation, so {@link WorldRenderEvents#LAST} may be preferable if that is wanted.
	 */
	public static void afterTranslucentHandle(WorldRenderContext context) {
		FabricEventsJS.AFTER_TRANSLUCENT.post(new WorldRenderContextEventJS(context));
	}

	/**
	 * Called after entities are rendered and solid entity layers
	 * have been drawn to the main frame buffer target, before
	 * block entity rendering begins.
	 *
	 * <p>Use for global block entity render setup, or
	 * to append block-related quads to the entity consumers using the
	 * VertexConsumerProvider from the provided context. This
	 * will generally give better (if not perfect) results
	 * for non-terrain translucency vs. drawing directly later on.
	 */
	public static void afterEntitiesHandle(WorldRenderContext context) {
		FabricEventsJS.AFTER_ENTITIES.post(new WorldRenderContextEventJS(context));
	}

	/**
	 * Called before world rendering executes. Input parameters are available but frustum is not.
	 * Use this event instead of injecting to the HEAD of {@link LevelRenderer#renderLevel} to avoid
	 * compatibility problems with 3rd-party renderer implementations.
	 *
	 * <p>Use for setup of state that is needed during the world render call that
	 * does not depend on the view frustum.
	 */
	public static void startHandle(WorldRenderContext context) {
		FabricEventsJS.START_RENDER.post(new WorldRenderContextEventJS(context));
	}

	/**
	 * Called after all framebuffer writes are complete but before all world
	 * rendering is torn down.
	 *
	 * <p>Unlike most other events, renders in this event are expected to be drawn
	 * directly and immediately to the framebuffer. The OpenGL render state view
	 * matrix will be transformed to match the camera view before the event is called.
	 *
	 * <p>Use to draw content that should appear on top of the world before hand and GUI rendering occur.
	 */
	public static void lastHandle(WorldRenderContext context) {
		FabricEventsJS.LAST_RENDER.post(new WorldRenderContextEventJS(context));
	}

	/**
	 * Called after all world rendering is complete and changes to GL state are unwound.
	 *
	 * <p>Use to draw overlays that handle GL state management independently or to tear
	 * down transient state in event handlers or as a hook that precedes hand/held item
	 * and GUI rendering.
	 */
	public static void endHandle(WorldRenderContext context) {
		FabricEventsJS.END_RENDER.post(new WorldRenderContextEventJS(context));
	}

	/**
	 * Called after view Frustum is computed and all render chunks to be rendered are
	 * identified and rebuilt but before chunks are uploaded to GPU.
	 *
	 * <p>Use for setup of state that depends on view frustum.
	 */
	public static void afterSetupHandle(WorldRenderContext context) {
		FabricEventsJS.AFTER_SETUP.post(new WorldRenderContextEventJS(context));
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

