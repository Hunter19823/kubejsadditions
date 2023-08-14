package pie.ilikepiefoo.compat.jei.events;

import mezz.jei.api.registration.IRecipeRegistration;

public class RegisterRecipesEventJS extends JEIEventJS {
    public final IRecipeRegistration data;

    public RegisterRecipesEventJS( IRecipeRegistration data ) {
        this.data = data;
    }

}
