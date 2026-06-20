package pie.ilikepiefoo.compat.jei.events;


import dev.latvian.mods.kubejs.event.KubeEvent;
import mezz.jei.api.registration.IRecipeTransferRegistration;

public record RegisterRecipeTransferHandlersEventJS(IRecipeTransferRegistration data) implements KubeEvent {

}
