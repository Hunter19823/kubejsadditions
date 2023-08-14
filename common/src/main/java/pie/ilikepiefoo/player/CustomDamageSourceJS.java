package pie.ilikepiefoo.player;

import net.minecraft.world.damagesource.DamageSource;

public class CustomDamageSourceJS extends DamageSource {
    public CustomDamageSourceJS( String id ) {
        super(id);
    }

    public static CustomDamageSourceJS custom( String id ) {
        return new CustomDamageSourceJS(id);
    }

}
