package pie.ilikepiefoo.compat.jei.events;

import mezz.jei.api.registration.IVanillaCategoryExtensionRegistration;

public class RegisterVanillaCategoryExtensionsEventJS extends JEIEventJS {
	public final IVanillaCategoryExtensionRegistration data;

	public RegisterVanillaCategoryExtensionsEventJS(IVanillaCategoryExtensionRegistration data) {
		this.data = data;
	}
}
