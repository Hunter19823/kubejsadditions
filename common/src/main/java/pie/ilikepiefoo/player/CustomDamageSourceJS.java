package pie.ilikepiefoo.player;

import net.minecraft.core.Holder;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DeathMessageType;

public class CustomDamageSourceJS extends DamageSource {
	public CustomDamageSourceJS(DamageType damageType) {
		super(Holder.direct(damageType));
	}

	public static CustomDamageSourceJS custom(String id) {
		return new CustomDamageSourceJS(new DamageType(id, 0));
	}

	public static CustomDamageSourceJS custom(String id, float exhaustion) {
		return new CustomDamageSourceJS(new DamageType(id, exhaustion));
	}

	public static CustomDamageSourceJS custom(String id, float exhaustion, DamageEffects effects) {
		return new CustomDamageSourceJS(new DamageType(id, exhaustion, effects));
	}

	public static CustomDamageSourceJS custom(String id, DamageScaling scaling, float exhaustion, DamageEffects effects) {
		return new CustomDamageSourceJS(new DamageType(id, scaling, exhaustion, effects));
	}

	public static CustomDamageSourceJS custom(String id, DamageScaling scaling, float exhaustion, DamageEffects effects, DeathMessageType deathMessageType) {
		return new CustomDamageSourceJS(new DamageType(id, scaling, exhaustion, effects, deathMessageType));
	}
}
