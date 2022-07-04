package pie.ilikepiefoo.mixin;

import dev.latvian.kubejs.block.MaterialJS;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.material.PushReaction;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(MaterialJS.class)
public class MixinMaterialJS {
	public boolean isLiquid() {
		return minecraftMaterial.isLiquid();
	}

	public boolean isSolid() {
		return minecraftMaterial.isSolid();
	}

	public boolean blocksMotion() {
		return minecraftMaterial.blocksMotion();
	}

	public boolean isFlammable() {
		return minecraftMaterial.isFlammable();
	}

	public boolean isReplaceable() {
		return minecraftMaterial.isReplaceable();
	}

	public boolean isSolidBlocking() {
		return minecraftMaterial.isSolidBlocking();
	}

	public PushReaction getPushReaction() {
		return minecraftMaterial.getPushReaction();
	}

	public MaterialColor getColor() {
		return minecraftMaterial.getColor();
	}

	@Shadow
	@Final
	private Material minecraftMaterial;
	@Shadow
	@Final
	private SoundType sound;

	public float getVolume() {
		return sound.getVolume();
	}

	public float getPitch() {
		return sound.getPitch();
	}

	@Environment(EnvType.CLIENT)
	public SoundEvent getBreakSound() {
		return sound.getBreakSound();
	}

	public SoundEvent getStepSound() {
		return sound.getStepSound();
	}

	public SoundEvent getPlaceSound() {
		return sound.getPlaceSound();
	}

	@Environment(EnvType.CLIENT)
	public SoundEvent getHitSound() {
		return sound.getHitSound();
	}

	public SoundEvent getFallSound() {
		return sound.getFallSound();
	}
}
