package pie.ilikepiefoo.mixin;

import dev.latvian.mods.kubejs.fluid.FluidStackJS;
import dev.latvian.mods.kubejs.util.MapJS;
import dev.latvian.mods.rhino.mod.util.NBTUtils;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pie.ilikepiefoo.fluid.FluidTagJS;

import java.util.Map;

@Mixin(value = FluidStackJS.class, remap = false)
public class MixinFluidStackJS {
	@Inject(at = @At("HEAD"), method = "of(Ljava/lang/Object;)Ldev/latvian/mods/kubejs/fluid/FluidStackJS;", cancellable = true)
	private static void ofString(Object o, CallbackInfoReturnable<FluidStackJS> cir) {
		if (o instanceof CharSequence || o instanceof ResourceLocation) {
			String s = o.toString().trim();

			if (s.startsWith("#")) {
				cir.setReturnValue(new FluidTagJS(new ResourceLocation(s.substring(1))));
				cir.cancel();
			}
		}
	}

	@Inject(
			at = @At(value = "RETURN", shift = At.Shift.BEFORE, ordinal = 8),
			method = "of(Ljava/lang/Object;)Ldev/latvian/mods/kubejs/fluid/FluidStackJS;",
			cancellable = true)
	private static void ofMap(Object o, CallbackInfoReturnable<FluidStackJS> cir) {
		Map<?, ?> map = MapJS.of(o);

		if (map != null && map.containsKey("fluidTag")) {
			FluidTagJS tagJS = new FluidTagJS(new ResourceLocation(map.get("fluidTag").toString()));

			if (map.get("amount") instanceof Number number) {
				tagJS.setAmount(number.longValue());
			}

			if (map.containsKey("nbt")) {
				tagJS.setNbt(NBTUtils.toTagCompound(map.get("nbt")));
			}
			cir.setReturnValue(tagJS);
			cir.cancel();
		}
	}
}
