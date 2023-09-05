package pie.ilikepiefoo.fabric.events.hud;

import dev.latvian.mods.kubejs.event.EventJS;
import net.minecraft.client.gui.GuiGraphics;
import pie.ilikepiefoo.fabric.FabricEventsJS;

public class HudRenderEventJS extends EventJS {
	private final float tickDelta;
	private final GuiGraphics drawContext;

	public HudRenderEventJS(float tickDelta, GuiGraphics drawContext) {
		this.tickDelta = tickDelta;
		this.drawContext = drawContext;
	}

	/**
	 * Called after rendering the whole hud, which is displayed in game, in a world.
	 *
	 * @param drawContext the {@link GuiGraphics} instance
	 * @param tickDelta   Progress for linearly interpolating between the previous and current game state
	 */
	public static void handle(GuiGraphics drawContext, float tickDelta) {
		FabricEventsJS.RENDER_HUD.post(new HudRenderEventJS(tickDelta, drawContext));
	}

	public float getTickDelta() {
		return tickDelta;
	}

	public GuiGraphics getDrawContext() {
		return drawContext;
	}
}

