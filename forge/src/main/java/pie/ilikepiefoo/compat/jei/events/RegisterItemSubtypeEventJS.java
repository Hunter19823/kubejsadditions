package pie.ilikepiefoo.compat.jei.events;

import mezz.jei.api.registration.ISubtypeRegistration;

public class RegisterItemSubtypeEventJS extends JEIEventJS {
	public final ISubtypeRegistration data;

	public RegisterItemSubtypeEventJS(ISubtypeRegistration data) {
		this.data = data;
	}
}
