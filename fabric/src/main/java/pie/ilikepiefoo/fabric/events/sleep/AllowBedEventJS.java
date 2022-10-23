package pie.ilikepiefoo.fabric.events.sleep;

import dev.latvian.mods.kubejs.entity.LivingEntityEventJS;
import dev.latvian.mods.kubejs.entity.LivingEntityJS;
import dev.latvian.mods.kubejs.level.BlockContainerJS;
import dev.latvian.mods.kubejs.script.ScriptType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import pie.ilikepiefoo.fabric.FabricEventsJS;

import javax.annotation.Nonnull;

public class AllowBedEventJS extends LivingEntityEventJS {

	private final LivingEntity entity;
	private final BlockPos sleepingPos;
	private final BlockState state;
	private final boolean vanillaResult;
	@Nonnull
	private InteractionResult result;

	public AllowBedEventJS(LivingEntity entity, BlockPos sleepingPos, BlockState state, boolean vanillaResult) {
		this.entity = entity;
		this.sleepingPos = sleepingPos;
		this.state = state;
		this.vanillaResult = vanillaResult;
		this.result = InteractionResult.PASS;
	}

	@Override
	public boolean canCancel() {
		return true;
	}

	public LivingEntityJS getEntity() {
		return (LivingEntityJS) entityOf(entity);
	}

	public BlockContainerJS getPos() {
		return getLevel().getBlock(sleepingPos);
	}

	public BlockPos getSleepingPos() {
		return sleepingPos;
	}

	public BlockState getState() {
		return state;
	}

	public boolean isVanillaResult() {
		return vanillaResult;
	}

	public @NotNull InteractionResult getResult() {
		return result;
	}

	public void setResult(@Nonnull InteractionResult result) {
		this.result = result;
	}

	public static InteractionResult handler(LivingEntity entity, BlockPos sleepingPos, BlockState state, boolean vanillaResult) {
		AllowBedEventJS event = new AllowBedEventJS(entity, sleepingPos, state, vanillaResult);
		event.post(ScriptType.SERVER, FabricEventsJS.ALLOW_BED);
		if(event.isCancelled() && event.getResult() == InteractionResult.PASS) {
			return InteractionResult.FAIL;
		}
		return event.result;
	}
}

