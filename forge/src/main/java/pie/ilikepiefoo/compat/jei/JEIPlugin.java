package pie.ilikepiefoo.compat.jei;

import dev.latvian.kubejs.script.ScriptType;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.*;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.resources.ResourceLocation;
import pie.ilikepiefoo.AdditionalEventsJS;
import pie.ilikepiefoo.KubeJSAdditions;
import pie.ilikepiefoo.compat.jei.events.*;

@JeiPlugin
public class JEIPlugin implements IModPlugin {
	private static final ScriptType SCRIPT_TYPE = ScriptType.CLIENT;

	/**
	 * The unique ID for this mod plugin.
	 * The namespace should be your mod's modId.
	 */
	@Override
	public ResourceLocation getPluginUid() {
		return new ResourceLocation(KubeJSAdditions.MOD_ID, "jei_plugin");
	}

	/**
	 * If your item has subtypes that depend on NBT or capabilities, use this to help JEI identify those subtypes correctly.
	 *
	 * @param registration
	 */
	@Override
	public void registerItemSubtypes(ISubtypeRegistration registration) {
		// Create a new event and post it.
		new RegisterItemSubtypeEventJS(registration)
				.post(SCRIPT_TYPE, AdditionalEventsJS.JEI_REGISTER_ITEM_SUBTYPES);
	}

	/**
	 * If your fluid has subtypes that depend on NBT or capabilities, use this to help JEI identify those subtypes correctly.
	 *
	 * @param registration
	 */
	@Override
	public void registerFluidSubtypes(ISubtypeRegistration registration) {
		// Create a new event and post it.
		new RegisterFluidSubtypeEventJS(registration)
				.post(SCRIPT_TYPE, AdditionalEventsJS.JEI_REGISTER_FLUID_SUBTYPES);
	}

	/**
	 * Register special ingredients, beyond the basic ItemStack and FluidStack.
	 *
	 * @param registration
	 */
	@Override
	public void registerIngredients(IModIngredientRegistration registration) {
		// Create a new event and post it.
		new RegisterIngredientsEventJS(registration)
				.post(SCRIPT_TYPE, AdditionalEventsJS.JEI_REGISTER_INGREDIENTS);
	}

	/**
	 * Register the categories handled by this plugin.
	 * These are registered before recipes so they can be checked for validity.
	 *
	 * @param registration
	 */
	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		// Create a new event and post it.
		new RegisterCategoriesEventJS(registration)
				.post(SCRIPT_TYPE, AdditionalEventsJS.JEI_REGISTER_CATEGORIES);
	}

	/**
	 * Register modded extensions to the vanilla crafting recipe category.
	 * Custom crafting recipes for your mod should use this to tell JEI how they work.
	 *
	 * @param registration
	 */
	@Override
	public void registerVanillaCategoryExtensions(IVanillaCategoryExtensionRegistration registration) {
		// Create a new event and post it.
		new RegisterVanillaCategoryExtensionsEventJS(registration)
				.post(SCRIPT_TYPE, AdditionalEventsJS.JEI_REGISTER_VANILLA_CATEGORY_EXTENSIONS);
	}

	/**
	 * Register modded recipes.
	 *
	 * @param registration
	 */
	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		// Create a new event and post it.
		new RegisterRecipesEventJS(registration)
				.post(SCRIPT_TYPE, AdditionalEventsJS.JEI_REGISTER_RECIPES);
	}

	/**
	 * Register recipe transfer handlers (move ingredients from the inventory into crafting GUIs).
	 *
	 * @param registration
	 */
	@Override
	public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
		// Create a new event and post it.
		new RegisterRecipeTransferHandlersEventJS(registration)
				.post(SCRIPT_TYPE, AdditionalEventsJS.JEI_REGISTER_RECIPE_TRANSFER_HANDLERS);
	}

	/**
	 * Register recipe catalysts.
	 * Recipe Catalysts are ingredients that are needed in order to craft other things.
	 * Vanilla examples of Recipe Catalysts are the Crafting Table and Furnace.
	 *
	 * @param registration
	 */
	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		// Create a new event and post it.
		new RegisterRecipeCatalystsEventJS(registration)
				.post(SCRIPT_TYPE, AdditionalEventsJS.JEI_REGISTER_RECIPE_CATALYSTS);
	}

	/**
	 * Register various GUI-related things for your mod.
	 * This includes adding clickable areas in your guis to open JEI,
	 * and adding areas on the screen that JEI should avoid drawing.
	 *
	 * @param registration
	 */
	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registration) {
		// Create a new event and post it.
		new RegisterGUIHandlersEventJS(registration)
				.post(SCRIPT_TYPE, AdditionalEventsJS.JEI_REGISTER_GUI_HANDLERS);
	}

	/**
	 * Register advanced features for your mod plugin.
	 *
	 * @param registration
	 */
	@Override
	public void registerAdvanced(IAdvancedRegistration registration) {
		// Create a new event and post it.
		new RegisterAdvancedEventJS(registration)
				.post(SCRIPT_TYPE, AdditionalEventsJS.JEI_REGISTER_ADVANCED);
	}

	/**
	 * Called when jei's runtime features are available, after all mods have registered.
	 *
	 * @param jeiRuntime
	 */
	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
		// Create a new event and post it.
		new OnRuntimeAvailableEventJSJS(jeiRuntime)
				.post(SCRIPT_TYPE, AdditionalEventsJS.JEI_ON_RUNTIME_AVAILABLE);
	}
}
