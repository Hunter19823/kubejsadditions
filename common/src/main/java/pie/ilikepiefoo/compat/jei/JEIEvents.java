package pie.ilikepiefoo.compat.jei;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;
import pie.ilikepiefoo.compat.jei.events.OnRuntimeAvailableEventJS;
import pie.ilikepiefoo.compat.jei.events.RegisterAdvancedEventJS;
import pie.ilikepiefoo.compat.jei.events.RegisterCategoriesEventJS;

public interface JEIEvents {
	EventGroup GROUP = EventGroup.of("JEIAddedEvents");

	EventHandler ON_RUNTIME_AVAILABLE = GROUP.client("onRuntimeAvailable", () -> OnRuntimeAvailableEventJS.class);
	EventHandler REGISTER_ADVANCED = GROUP.client("registerAdvanced", () -> RegisterAdvancedEventJS.class);
	EventHandler REGISTER_CATEGORIES = GROUP.client("registerCategories", () -> RegisterCategoriesEventJS.class);
	EventHandler REGISTER_FLUID_SUBTYPES = GROUP.client("registerFluidSubtypes", () -> RegisterAdvancedEventJS.class);
	EventHandler REGISTER_GUI_HANDLERS = GROUP.client("registerGUIHandlers", () -> RegisterAdvancedEventJS.class);
	EventHandler REGISTER_INGREDIENTS = GROUP.client("registerIngredients", () -> RegisterAdvancedEventJS.class);
	EventHandler REGISTER_ITEM_SUBTYPES = GROUP.client("registerItemSubtypes", () -> RegisterAdvancedEventJS.class);
	EventHandler REGISTER_RECIPE_CATALYSTS = GROUP.client("registerRecipeCatalysts", () -> RegisterAdvancedEventJS.class);
	EventHandler REGISTER_RECIPES = GROUP.client("registerRecipes", () -> RegisterAdvancedEventJS.class);
	EventHandler REGISTER_RECIPE_TRANSFER_HANDLERS = GROUP.client("registerRecipeTransferHandlers", () -> RegisterAdvancedEventJS.class);
	EventHandler REGISTER_VANILLA_CATEGORY_EXTENSIONS = GROUP.client("registerVanillaCategoryExtensions", () -> RegisterAdvancedEventJS.class);

	static void register() {
		GROUP.register();
	}
}
