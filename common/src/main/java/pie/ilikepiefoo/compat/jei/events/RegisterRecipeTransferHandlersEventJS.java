package pie.ilikepiefoo.compat.jei.events;

import dev.latvian.mods.kubejs.event.EventJS;
import mezz.jei.api.registration.IRecipeTransferRegistration;

public class RegisterRecipeTransferHandlersEventJS extends EventJS {
    public final IRecipeTransferRegistration data;

    public RegisterRecipeTransferHandlersEventJS( IRecipeTransferRegistration data ) {
        this.data = data;
    }

}
