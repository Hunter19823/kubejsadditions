package pie.ilikepiefoo.compat.jei.events;

import dev.latvian.mods.kubejs.event.EventJS;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.resources.ResourceLocation;
import pie.ilikepiefoo.compat.jei.impl.CustomJSRecipe;

import java.util.Map;
import java.util.TreeMap;

public class JEIEventJS extends EventJS {
    public static final Map<ResourceLocation, RecipeType<CustomJSRecipe>> customRecipeTypes = new TreeMap<>();
    public static IJeiHelpers JEI_HELPERS;

    public static void clearCustomRecipeTypes() {
        customRecipeTypes.clear();
    }

    public static void removeCustomRecipeType(ResourceLocation recipeType) {
        customRecipeTypes.remove(recipeType);
    }

    public static RecipeType<CustomJSRecipe> getCustomRecipeType(ResourceLocation recipeType) {
        return customRecipeTypes.get(recipeType);
    }

    public static RecipeType<CustomJSRecipe> getOrCreateCustomRecipeType(ResourceLocation recipeType) {
        customRecipeTypes.computeIfAbsent(
                recipeType,
                (key) -> RecipeType.create(key.getNamespace(), key.getPath(), CustomJSRecipe.class)
        );
        return customRecipeTypes.get(recipeType);
    }
}
