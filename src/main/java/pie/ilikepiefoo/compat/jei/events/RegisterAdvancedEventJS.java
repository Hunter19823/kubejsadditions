package pie.ilikepiefoo.compat.jei.events;

import mezz.jei.api.registration.IAdvancedRegistration;
import pie.ilikepiefoo.compat.jei.impl.CustomRecipeCategoryDecorator;

public class RegisterAdvancedEventJS extends JEIEventJS {
    public final IAdvancedRegistration data;

    public RegisterAdvancedEventJS(IAdvancedRegistration data) {
        this.data = data;
    }


    public <T> CustomRecipeCategoryDecorator<T> categoryDecorator(CustomRecipeCategoryDecorator.DrawDecorator<T> drawDecorator, CustomRecipeCategoryDecorator.TooltipDecorator<T> tooltipDecorator) {
        return new CustomRecipeCategoryDecorator<>(drawDecorator, tooltipDecorator);
    }

    public <T> CustomRecipeCategoryDecorator<T> categoryDecorator(CustomRecipeCategoryDecorator.DrawDecorator<T> drawDecorator) {
        return new CustomRecipeCategoryDecorator<>(drawDecorator, (t, r, c, s, x, y) -> t);
    }
}
