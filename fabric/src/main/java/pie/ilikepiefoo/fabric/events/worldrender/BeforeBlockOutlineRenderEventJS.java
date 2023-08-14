package pie.ilikepiefoo.fabric.events.worldrender;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import pie.ilikepiefoo.fabric.FabricEventsJS;

public class BeforeBlockOutlineRenderEventJS extends WorldRenderContextEventJS {
    private final @Nullable HitResult hitResult;

    public BeforeBlockOutlineRenderEventJS( WorldRenderContext context, @Nullable HitResult hitResult ) {
        super(context);
        this.hitResult = hitResult;
    }

    /**
     * Event signature for {@link WorldRenderEvents#BEFORE_BLOCK_OUTLINE}.
     *
     * @param context   Access to state and parameters available during world rendering.
     * @param hitResult The game object currently under the crosshair target.
     *                  Normally equivalent to {@link Minecraft#hitResult}. Provided for convenience.
     * @return true if vanilla block outline rendering should happen.
     * Returning false prevents {@link WorldRenderEvents#BLOCK_OUTLINE} from invoking
     * and also skips the vanilla block outline render, but has no effect on other subscribers to this event.
     */
    public static boolean handle( WorldRenderContext context, @Nullable HitResult hitResult ) {
        if (!FabricEventsJS.BEFORE_BLOCK_OUTLINE.hasListeners()) {
            return true;
        }
        return FabricEventsJS.BEFORE_BLOCK_OUTLINE.post(new BeforeBlockOutlineRenderEventJS(context, hitResult)).arch().isTrue();
    }

    @Nullable
    public HitResult getHitResult() {
        return hitResult;
    }

    public double distanceTo( Entity entity ) {
        assert hitResult != null;
        return hitResult.distanceTo(entity);
    }

    public HitResult.Type getType() {
        assert hitResult != null;
        return hitResult.getType();
    }

    /**
     * Called before default block outline rendering and before checks are
     * done to determine if it should happen. Can optionally cancel the default
     * rendering but all event handlers will always be called.
     *
     * <p>Use this to decorate or replace the default block outline rendering
     * for specific modded blocks or when the need for a block outline render
     * would not be detected.  Normally, outline rendering will not happen for
     * entities, fluids, or other game objects that do not register a block-type hit.
     *
     * <p>Returning false from any event subscriber will cancel the default block
     * outline render and suppress the {@code BLOCK_RENDER} event.  This has no
     * effect on other subscribers to this event - all subscribers will always be called.
     * Canceling here is appropriate when there is still a valid block hit (with a fluid,
     * for example) and you don't want the block outline render to appear.
     *
     * <p>This event should NOT be used for general-purpose replacement of
     * the default block outline rendering because it will interfere with mod-specific
     * renders.  Mods that replace the default block outline for specific blocks
	 * should instead subscribe to {@link WorldRenderEvents#BLOCK_OUTLINE}.
     */

    public Vec3 getLocation() {
        assert hitResult != null;
        return hitResult.getLocation();
    }

}

