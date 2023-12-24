package pie.ilikepiefoo.compat.jei.events;

import mezz.jei.api.registration.IRecipeCatalystRegistration;

public class RegisterRecipeCatalystsEventJS extends JEIEventJS {
	public final IRecipeCatalystRegistration data;

	public RegisterRecipeCatalystsEventJS(IRecipeCatalystRegistration data) {
		this.data = data;
	}
}
