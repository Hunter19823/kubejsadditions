package pie.ilikepiefoo.compat.jei.builder;

import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import org.jetbrains.annotations.NotNull;

public class RecipeCategoryWrapperBuilder<T> extends RecipeCategoryBuilder<T> {
    private final IRecipeCategory<T> sourceCategory;

    public RecipeCategoryWrapperBuilder(
            @NotNull RecipeType<T> recipeType,
            @NotNull IJeiHelpers jeiHelpers,
            @NotNull IRecipeCategory<T> recipeCategory
    ) {
        super(recipeType, jeiHelpers);
        this.sourceCategory = recipeCategory;
        this.setHeight(recipeCategory.getHeight());
        this.setWidth(recipeCategory.getWidth());
        this.setDrawHandler(recipeCategory::draw);
        this.setIsRecipeHandledByCategory(recipeCategory::isHandled);
        this.setSetRecipeHandler(recipeCategory::setRecipe);
        this.setTooltipHandler(recipeCategory::getTooltipStrings);
        this.setInputHandler(recipeCategory::handleInput);
        this.setTooltipHandler(recipeCategory::getTooltipStrings);
        this.title(recipeCategory.getTitle());
        this.background(recipeCategory.getBackground());
        this.icon(recipeCategory.getIcon());
        this.handleLookup(recipeCategory::setRecipe);
        this.registryName(recipeCategory::getRegistryName);
    }

    public IRecipeCategory<T> getSourceCategory() {
        return sourceCategory;
    }
}

