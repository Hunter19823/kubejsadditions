package pie.ilikepiefoo.compat.jei.builder;

import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import org.jetbrains.annotations.NotNull;

public class RecipeCategoryWrapperBuilder<T> extends RecipeCategoryBuilder<T> {
    private final IRecipeCategory<T> sourceCategory;

    @SuppressWarnings("removal")
    public RecipeCategoryWrapperBuilder(
            @NotNull RecipeType<T> recipeType,
            @NotNull IJeiHelpers jeiHelpers,
            @NotNull IRecipeCategory<T> recipeCategory
    ) {
        super(recipeType, jeiHelpers);
        this.sourceCategory = recipeCategory;
        this.setHeightSupplier(recipeCategory::getHeight);
        this.setWidthSupplier(recipeCategory::getWidth);
        this.setDrawHandler(recipeCategory::draw);
        this.setIsRecipeHandledByCategory(recipeCategory::isHandled);
        this.setSetRecipeHandler(recipeCategory::setRecipe);
        this.setCreateRecipeExtrasHandler(recipeCategory::createRecipeExtras);
        this.setInputHandler(recipeCategory::handleInput);
        this.setTooltipHandler(recipeCategory::getTooltipStrings);
        this.titleSupplier(recipeCategory::getTitle);
        this.backgroundSupplier(recipeCategory::getBackground);
        this.iconSupplier(recipeCategory::getIcon);
        this.handleLookup(recipeCategory::setRecipe);
        this.registryName(recipeCategory::getRegistryName);
        this.setTooltipHandlerOverride(recipeCategory::getTooltip);
        this.setDisplayedIngredientsUpdateHandler(recipeCategory::onDisplayedIngredientsUpdate);
    }

    public IRecipeCategory<T> getSourceCategory() {
        return sourceCategory;
    }
}
