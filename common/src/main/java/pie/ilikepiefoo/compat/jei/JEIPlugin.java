package pie.ilikepiefoo.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IPlatformFluidHelper;
import mezz.jei.api.registration.IAdvancedRegistration;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IModIngredientRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import mezz.jei.api.registration.IVanillaCategoryExtensionRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.resources.ResourceLocation;
import pie.ilikepiefoo.KubeJSAdditions;
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

@JeiPlugin
public class JEIPlugin implements IModPlugin {
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
    public void registerItemSubtypes( ISubtypeRegistration registration ) {
        JEIEvents.REGISTER_ITEM_SUBTYPES.post(new RegisterItemSubtypeEventJS(registration));
    }

    /**
     * If your fluid has subtypes that depend on NBT or capabilities, use this to help JEI identify those subtypes correctly.
     *
     * @param registration
     */
    @Override
    public <T> void registerFluidSubtypes( ISubtypeRegistration registration, IPlatformFluidHelper<T> platformFluidHelper ) {
        JEIEvents.REGISTER_FLUID_SUBTYPES.post(new RegisterFluidSubtypeEventJS(registration));
    }

    /**
     * Register special ingredients, beyond the basic ItemStack and FluidStack.
     *
     * @param registration
     */
    @Override
    public void registerIngredients( IModIngredientRegistration registration ) {
        JEIEvents.REGISTER_INGREDIENTS.post(new RegisterIngredientsEventJS(registration));
    }

    /**
     * Register the categories handled by this plugin.
     * These are registered before recipes so they can be checked for validity.
     *
     * @param registration
     */
    @Override
    public void registerCategories( IRecipeCategoryRegistration registration ) {
        JEIEvents.REGISTER_CATEGORIES.post(new RegisterCategoriesEventJS(registration));
    }

    /**
     * Register modded extensions to the vanilla crafting recipe category.
     * Custom crafting recipes for your mod should use this to tell JEI how they work.
     *
     * @param registration
     */
    @Override
    public void registerVanillaCategoryExtensions( IVanillaCategoryExtensionRegistration registration ) {
        JEIEvents.REGISTER_VANILLA_CATEGORY_EXTENSIONS.post(new RegisterVanillaCategoryExtensionsEventJS(registration));
    }

    /**
     * Register modded recipes.
     *
     * @param registration
     */
    @Override
    public void registerRecipes( IRecipeRegistration registration ) {
        JEIEvents.REGISTER_RECIPES.post(new RegisterRecipesEventJS(registration));
    }

    /**
     * Register recipe transfer handlers (move ingredients from the inventory into crafting GUIs).
     *
     * @param registration
     */
    @Override
    public void registerRecipeTransferHandlers( IRecipeTransferRegistration registration ) {
        JEIEvents.REGISTER_RECIPE_TRANSFER_HANDLERS.post(new RegisterRecipeTransferHandlersEventJS(registration));
    }

    /**
     * Register recipe catalysts.
     * Recipe Catalysts are ingredients that are needed in order to craft other things.
     * Vanilla examples of Recipe Catalysts are the Crafting Table and Furnace.
     *
     * @param registration
     */
    @Override
    public void registerRecipeCatalysts( IRecipeCatalystRegistration registration ) {
        JEIEvents.REGISTER_RECIPE_CATALYSTS.post(new RegisterRecipeCatalystsEventJS(registration));
    }

    /**
     * Register various GUI-related things for your mod.
     * This includes adding clickable areas in your guis to open JEI,
     * and adding areas on the screen that JEI should avoid drawing.
     *
     * @param registration
     */
    @Override
    public void registerGuiHandlers( IGuiHandlerRegistration registration ) {
        JEIEvents.REGISTER_GUI_HANDLERS.post(new RegisterGUIHandlersEventJS(registration));
    }

    /**
     * Register advanced features for your mod plugin.
     *
     * @param registration
     */
    @Override
    public void registerAdvanced( IAdvancedRegistration registration ) {
        JEIEvents.REGISTER_ADVANCED.post(new RegisterAdvancedEventJS(registration));
    }

    /**
     * Called when jei's runtime features are available, after all mods have registered.
     *
     * @param jeiRuntime
     */
    @Override
    public void onRuntimeAvailable( IJeiRuntime jeiRuntime ) {
        JEIEvents.ON_RUNTIME_AVAILABLE.post(new OnRuntimeAvailableEventJS(jeiRuntime));
    }

}
