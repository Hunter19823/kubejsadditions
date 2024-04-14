package pie.ilikepiefoo.compat.jei.impl;

import mezz.jei.api.recipe.RecipeType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CustomJSRecipe {
    @NotNull
    private final RecipeType<CustomJSRecipe> recipeType;
    private Object recipeData;

    public CustomJSRecipe(Object recipeData, @NotNull RecipeType<CustomJSRecipe> recipeType) {
        this.recipeData = recipeData;
        this.recipeType = recipeType;
    }

    public Object getRecipeData() {
        return recipeData;
    }

    public void setRecipeData(Object recipeData) {
        this.recipeData = recipeData;
    }

    public Object getData() {
        return recipeData;
    }

    public @NotNull RecipeType<CustomJSRecipe> getRecipeType() {
        return recipeType;
    }

    public static class CustomRecipeListBuilder {
        @NotNull
        private final RecipeType<CustomJSRecipe> recipeType;
        private final List<CustomJSRecipe> recipes;

        public CustomRecipeListBuilder(
                @NotNull RecipeType<CustomJSRecipe> recipeType) {
            this.recipeType = recipeType;
            this.recipes = new ArrayList<>();
        }

        public CustomJSRecipe custom(Object recipeData) {
            var recipe = new CustomJSRecipe(recipeData, recipeType);
            recipes.add(recipe);
            return recipe;
        }

        public CustomRecipeListBuilder add(Object recipeData) {
            recipes.add(new CustomJSRecipe(recipeData, recipeType));
            return this;
        }

        public CustomRecipeListBuilder add(CustomJSRecipe recipe) {
            recipes.add(recipe);
            return this;
        }

        public CustomRecipeListBuilder addAll(List<Object> recipeData) {
            this.recipes.addAll(recipeData.stream().map(data -> new CustomJSRecipe(data, recipeType)).toList());
            return this;
        }

        public List<CustomJSRecipe> getRecipes() {
            return recipes;
        }


        @NotNull
        public RecipeType<CustomJSRecipe> getRecipeType() {
            return recipeType;
        }
    }
}
