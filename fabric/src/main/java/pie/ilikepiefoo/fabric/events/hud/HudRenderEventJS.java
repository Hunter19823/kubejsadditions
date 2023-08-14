package pie.ilikepiefoo.fabric.events.hud;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import dev.latvian.mods.kubejs.event.EventJS;
import pie.ilikepiefoo.fabric.FabricEventsJS;

public class HudRenderEventJS extends EventJS {
    private final float tickDelta;
    private final PoseStack matrixStack;

    public HudRenderEventJS( float tickDelta, PoseStack matrixStack ) {
        this.tickDelta = tickDelta;
        this.matrixStack = matrixStack;
    }

    /**
     * Called after rendering the whole hud, which is displayed in game, in a world.
     *
     * @param matrixStack the matrixStack
     * @param tickDelta   Progress for linearly interpolating between the previous and current game state
     */
    public static void handle( PoseStack matrixStack, float tickDelta ) {
        FabricEventsJS.RENDER_HUD.post(new HudRenderEventJS(tickDelta, matrixStack));
    }

    public float getTickDelta() {
        return tickDelta;
    }

    public PoseStack getMatrixStack() {
        return matrixStack;
    }

    public void translate( double d, double e, double f ) {
        matrixStack.translate(d, e, f);
    }

    public void scale( float f, float g, float h ) {
        matrixStack.scale(f, g, h);
    }

    public void mulPose( Quaternion quaternion ) {
        matrixStack.mulPose(quaternion);
    }

    public void pushPose() {
        matrixStack.pushPose();
    }

    public void popPose() {
        matrixStack.popPose();
    }

    public PoseStack.Pose last() {
        return matrixStack.last();
    }

    public boolean clear() {
        return matrixStack.clear();
    }

    public void setIdentity() {
        matrixStack.setIdentity();
    }

    public void mulPoseMatrix( Matrix4f matrix4f ) {
        matrixStack.mulPoseMatrix(matrix4f);
    }

}

