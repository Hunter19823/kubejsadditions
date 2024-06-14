package pie.ilikepiefoo.compat.jei.events;

import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import net.minecraft.resources.ResourceLocation;
import pie.ilikepiefoo.compat.jei.builder.RecipeCategoryBuilder;
import pie.ilikepiefoo.compat.jei.builder.RecipeCategoryWrapperBuilder;
import pie.ilikepiefoo.compat.jei.impl.CustomJSRecipe;
import pie.ilikepiefoo.compat.jei.impl.CustomRecipeCategory;

import java.util.function.Consumer;

public class RegisterCategoriesEventJS extends JEIEventJS {
    public final IRecipeCategoryRegistration data;

    public RegisterCategoriesEventJS(IRecipeCategoryRegistration data) {
        this.data = data;
    }

    public CustomRecipeCategory<?> custom(ResourceLocation recipeType, Consumer<RecipeCategoryBuilder<CustomJSRecipe>> categoryConsumer) {
        return register(getOrCreateCustomRecipeType(recipeType), categoryConsumer);
    }

    public <T> CustomRecipeCategory<T> wrap(ResourceLocation recipeType, IRecipeCategory<T> existingCategory, Consumer<RecipeCategoryWrapperBuilder<T>> categoryConsumer) {
        RecipeCategoryWrapperBuilder<T> builder = new RecipeCategoryWrapperBuilder<>(
                getOrCreateCustomOverriddenRecipeType(recipeType, existingCategory.getRecipeType()),
                data.getJeiHelpers(),
                existingCategory
        );
        categoryConsumer.accept(builder);
        var customRecipeCategory = new CustomRecipeCategory<>(builder);
        data.addRecipeCategories(customRecipeCategory);
        return customRecipeCategory;
    }

    public <T> CustomRecipeCategory<T> register(RecipeType<T> recipeType, Consumer<RecipeCategoryBuilder<T>> categoryConsumer) {
        RecipeCategoryBuilder<T> category = new RecipeCategoryBuilder<>(recipeType, data.getJeiHelpers());
        categoryConsumer.accept(category);
        var customRecipeCategory = new CustomRecipeCategory<>(category);
        data.addRecipeCategories(customRecipeCategory);
        return customRecipeCategory;
    }
}
