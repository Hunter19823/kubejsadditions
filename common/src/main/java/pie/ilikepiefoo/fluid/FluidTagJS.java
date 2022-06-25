package pie.ilikepiefoo.fluid;

import com.google.gson.JsonObject;
import dev.architectury.fluid.FluidStack;
import dev.latvian.mods.kubejs.fluid.FluidStackJS;
import dev.latvian.mods.kubejs.util.MapJS;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public class FluidTagJS extends FluidStackJS {
	private final ResourceLocation tag;
	private final String fluid;
	private long amount;
	private CompoundTag nbt;
	private FluidStack cached;

	public FluidTagJS(ResourceLocation location) {
		this.tag = location;
		this.fluid = tag.toString();
		this.amount = FluidStack.bucketAmount();
		this.nbt = null;
		this.cached = null;
	}

	@Override
	public String getId() {
		return fluid;
	}

	@Override
	public FluidStack getFluidStack() {
		if(cached == null) {
			cached = FluidStack.create(this::getFluid, amount, nbt);
		}
		return cached;
	}

	@Override
	public Fluid getFluid() {
		return .getTagOrEmpty(tag).getValues().stream().findFirst().orElse(Fluids.EMPTY);
	}

	@Override
	public long getAmount() {
		return amount;
	}

	@Override
	public void setAmount(long amount) {
		this.amount = amount;
	}

	@Override
	public void setAmount(int amount) {
		this.amount = amount;
		cached = null;
	}

	@Override
	public @Nullable CompoundTag getNbt() {
		return nbt;
	}

	@Override
	public void setNbt(@Nullable CompoundTag nbt) {
		this.nbt = nbt;
		cached = null;
	}

	@Override
	public FluidStackJS copy() {
		FluidTagJS fluidTagJS = new FluidTagJS(tag);
		fluidTagJS.amount = amount;
		fluidTagJS.nbt = nbt == null ? null : nbt.copy();
		return fluidTagJS;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof CharSequence){
			return getId().equals(o.toString());
		}

		FluidStackJS stack = FluidStackJS.of(o);

		if (stack.isEmpty()) {
			return false;
		}

		return stack.hasTag(tag) && getAmount() == stack.getAmount() && Objects.equals(getNbt(),stack.getNbt());
	}

	@Override
	public Collection<ResourceLocation> getTags() {
		return Collections.singleton(tag);
	}

	@Override
	public boolean hasTag(ResourceLocation tag) {
		return this.tag.equals(tag);
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getNbt());
	}

	@Override
	public JsonObject toJson() {
		JsonObject o = new JsonObject();
		o.addProperty("fluidTag", getId());

		if (getAmount() != FluidStack.bucketAmount().intValue()) {
			o.addProperty("amount", getAmount());
		}

		if (getNbt() != null) {
			o.add("nbt", MapJS.json(getNbt()));
		}

		if (hasChance()) {
			o.addProperty("chance", getChance());
		}

		return o;
	}
}
