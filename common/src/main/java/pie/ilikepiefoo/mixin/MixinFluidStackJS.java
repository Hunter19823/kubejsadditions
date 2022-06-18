package pie.ilikepiefoo.mixin;

import dev.latvian.kubejs.fluid.EmptyFluidStackJS;
import dev.latvian.kubejs.fluid.FluidStackJS;
import dev.latvian.kubejs.util.MapJS;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pie.ilikepiefoo.fluid.FluidTagJS;

@Mixin(value = FluidStackJS.class, remap = false)
public class MixinFluidStackJS {
	private static final Logger LOGGER = LogManager.getLogger();

	@Inject(at = @At("HEAD"), method = "of(Ljava/lang/Object;)Ldev/latvian/kubejs/fluid/FluidStackJS;", cancellable = true)
	private static void ofString(Object o, CallbackInfoReturnable<FluidStackJS> cir) {
		if(o instanceof CharSequence || o instanceof ResourceLocation) {
			String s = o.toString().trim();

			if(s.startsWith("#")) {
				cir.setReturnValue(new FluidTagJS(new ResourceLocation(s.substring(1))));
				cir.cancel();
			}
		}
	}


	@Inject(
			at = @At(value = "RETURN", shift = At.Shift.BEFORE, ordinal = 8),
			method = "of(Ljava/lang/Object;)Ldev/latvian/kubejs/fluid/FluidStackJS;",
			cancellable = true)
	private static void ofMap(Object o, CallbackInfoReturnable<FluidStackJS> cir) {
		MapJS map = MapJS.of(o);

		if(map != null && map.containsKey("fluidTag")){
			FluidTagJS tagJS = new FluidTagJS(new ResourceLocation(map.get("fluidTag").toString()));

			if (map.get("amount") instanceof Number) {
				tagJS.setAmount(((Number) map.get("amount")).intValue());
			}

			if (map.containsKey("nbt")) {
				tagJS.setNbt(MapJS.nbt(map.get("nbt")));
			}
			cir.setReturnValue(tagJS);
			cir.cancel();
		}
	}

}
