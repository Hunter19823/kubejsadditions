package pie.ilikepiefoo.compat.jei.events;

import mezz.jei.api.registration.IRecipeCategoryRegistration;

public class RegisterCategoriesEventJS extends JEIEventJS {
	public final IRecipeCategoryRegistration data;

	public RegisterCategoriesEventJS(IRecipeCategoryRegistration data) {
		this.data = data;
	}
}
