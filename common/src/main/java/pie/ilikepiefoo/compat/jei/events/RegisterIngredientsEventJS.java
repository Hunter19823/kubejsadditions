package pie.ilikepiefoo.compat.jei.events;

import mezz.jei.api.registration.IModIngredientRegistration;

public class RegisterIngredientsEventJS extends JEIEventJS {
    public final IModIngredientRegistration data;

    public RegisterIngredientsEventJS( IModIngredientRegistration data ) {
        this.data = data;
    }

}
