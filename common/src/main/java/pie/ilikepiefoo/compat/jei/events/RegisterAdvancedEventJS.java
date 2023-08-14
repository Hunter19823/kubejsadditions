package pie.ilikepiefoo.compat.jei.events;

import mezz.jei.api.registration.IAdvancedRegistration;

public class RegisterAdvancedEventJS extends JEIEventJS {
    public final IAdvancedRegistration data;

    public RegisterAdvancedEventJS( IAdvancedRegistration data ) {
        this.data = data;
    }

}
