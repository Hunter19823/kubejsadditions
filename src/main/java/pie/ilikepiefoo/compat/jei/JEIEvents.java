package pie.ilikepiefoo.compat.jei;

import dev.architectury.platform.Platform;
import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;
import pie.ilikepiefoo.compat.jei.events.OnRuntimeAvailableEventJS;
import pie.ilikepiefoo.compat.jei.events.RegisterAdvancedEventJS;
import pie.ilikepiefoo.compat.jei.events.RegisterCategoriesEventJS;
import pie.ilikepiefoo.compat.jei.events.RegisterFluidSubtypeEventJS;
import pie.ilikepiefoo.compat.jei.events.RegisterGUIHandlersEventJS;
import pie.ilikepiefoo.compat.jei.events.RegisterIngredientsEventJS;
import pie.ilikepiefoo.compat.jei.events.RegisterItemSubtypeEventJS;
import pie.ilikepiefoo.compat.jei.events.RegisterRecipeCatalystsEventJS;
import pie.ilikepiefoo.compat.jei.events.RegisterRecipeTransferHandlersEventJS;
import pie.ilikepiefoo.compat.jei.events.RegisterRecipesEventJS;
import pie.ilikepiefoo.compat.jei.events.RegisterVanillaCategoryExtensionsEventJS;

public interface JEIEvents {
    EventGroup GROUP = EventGroup.of("JEIAddedEvents");

    EventHandler ON_RUNTIME_AVAILABLE = GROUP.client("onRuntimeAvailable", () -> OnRuntimeAvailableEventJS.class);
    EventHandler REGISTER_ADVANCED = GROUP.client("registerAdvanced", () -> RegisterAdvancedEventJS.class);
    EventHandler REGISTER_CATEGORIES = GROUP.client("registerCategories", () -> RegisterCategoriesEventJS.class);
    EventHandler REGISTER_FLUID_SUBTYPES = GROUP.client("registerFluidSubtypes", () -> RegisterFluidSubtypeEventJS.class);
    EventHandler REGISTER_GUI_HANDLERS = GROUP.client("registerGUIHandlers", () -> RegisterGUIHandlersEventJS.class);
    EventHandler REGISTER_INGREDIENTS = GROUP.client("registerIngredients", () -> RegisterIngredientsEventJS.class);
    EventHandler REGISTER_ITEM_SUBTYPES = GROUP.client("registerItemSubtypes", () -> RegisterItemSubtypeEventJS.class);
    EventHandler REGISTER_RECIPE_CATALYSTS = GROUP.client("registerRecipeCatalysts", () -> RegisterRecipeCatalystsEventJS.class);
    EventHandler REGISTER_RECIPES = GROUP.client("registerRecipes", () -> RegisterRecipesEventJS.class);
    EventHandler REGISTER_RECIPE_TRANSFER_HANDLERS = GROUP.client("registerRecipeTransferHandlers",
            () -> RegisterRecipeTransferHandlersEventJS.class);
    EventHandler REGISTER_VANILLA_CATEGORY_EXTENSIONS = GROUP.client("registerVanillaCategoryExtensions",
            () -> RegisterVanillaCategoryExtensionsEventJS.class);

    static void register() {
        if (Platform.isModLoaded("jei")) {
            GROUP.register();
        }
    }

}
