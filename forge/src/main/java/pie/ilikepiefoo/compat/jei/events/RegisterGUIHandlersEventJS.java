package pie.ilikepiefoo.compat.jei.events;

import mezz.jei.api.registration.IGuiHandlerRegistration;

public class RegisterGUIHandlersEventJS extends JEIEventJS {
	public final IGuiHandlerRegistration data;

	public RegisterGUIHandlersEventJS(IGuiHandlerRegistration data) {
		this.data = data;
	}
}
