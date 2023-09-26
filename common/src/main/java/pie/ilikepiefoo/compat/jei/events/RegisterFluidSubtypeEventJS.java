package pie.ilikepiefoo.compat.jei.events;

import mezz.jei.api.registration.ISubtypeRegistration;

public class RegisterFluidSubtypeEventJS extends JEIEventJS {
    public final ISubtypeRegistration data;

    public RegisterFluidSubtypeEventJS(ISubtypeRegistration data) {
        this.data = data;
    }
}
