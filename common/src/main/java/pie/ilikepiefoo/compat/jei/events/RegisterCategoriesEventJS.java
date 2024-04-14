package pie.ilikepiefoo.compat.jei.events;

import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import net.minecraft.resources.ResourceLocation;
import pie.ilikepiefoo.compat.jei.builder.RecipeCategoryBuilder;
import pie.ilikepiefoo.compat.jei.impl.CustomJSRecipe;
import pie.ilikepiefoo.compat.jei.impl.CustomRecipeCategory;

import java.util.function.Consumer;

public class RegisterCategoriesEventJS extends JEIEventJS {
    public final IRecipeCategoryRegistration data;

    public RegisterCategoriesEventJS(IRecipeCategoryRegistration data) {
        this.data = data;
    }

    public void custom(ResourceLocation recipeType, Consumer<RecipeCategoryBuilder<CustomJSRecipe>> categoryConsumer) {
        register(getOrCreateCustomRecipeType(recipeType), categoryConsumer);
    }

    public <T> void register(RecipeType<T> recipeType, Consumer<RecipeCategoryBuilder<T>> categoryConsumer) {
        RecipeCategoryBuilder<T> category = new RecipeCategoryBuilder<>(recipeType, data.getJeiHelpers());
        categoryConsumer.accept(category);
        data.addRecipeCategories(new CustomRecipeCategory<>(category));
    }
}
