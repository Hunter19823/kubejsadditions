package pie.ilikepiefoo.compat.jei.events;

import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import pie.ilikepiefoo.compat.jei.impl.CustomJSRecipe;

import java.util.ArrayList;
import java.util.List;

public class RegisterRecipesEventJS extends JEIEventJS {
    public final IRecipeRegistration data;
    public final List<CustomJSRecipe.CustomRecipeListBuilder> customRecipeListBuilders = new ArrayList<>();

    public RegisterRecipesEventJS(IRecipeRegistration data) {
        this.data = data;
    }

    public <T> void register(RecipeType<T> recipeType, List<T> recipes) {
        data.addRecipes(recipeType, recipes);
    }

    public CustomJSRecipe.CustomRecipeListBuilder custom(ResourceLocation recipeType) {
        var recipeListBuilder = new CustomJSRecipe.CustomRecipeListBuilder(getOrCreateCustomRecipeType(recipeType));
        customRecipeListBuilders.add(recipeListBuilder);
        return recipeListBuilder;
    }


}
