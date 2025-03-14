package pie.ilikepiefoo.compat.jei.impl;

import com.mojang.blaze3d.platform.InputConstants;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotRichTooltipCallback;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.inputs.IJeiInputHandler;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.gui.widgets.IRecipeWidget;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pie.ilikepiefoo.compat.jei.builder.RecipeCategoryBuilder;

import java.util.List;
import java.util.Optional;

public class CustomRecipeCategory<T> implements IRecipeCategory<T> {

    private final RecipeCategoryBuilder<T> builder;

    public CustomRecipeCategory(RecipeCategoryBuilder<T> builder) {
        this.builder = builder;
    }

    /**
     * @return the type of recipe that this category handles.
     * @since 9.5.0
     */
    @Override
    public @NotNull RecipeType<T> getRecipeType() {
        return this.builder.getRecipeType();
    }

    /**
     * Returns a text component representing the name of this recipe type.
     * Drawn at the top of the recipe GUI pages for this category.
     *
     * @since 7.6.4
     */
    @Override
    public @NotNull Component getTitle() {
        return this.builder.getCategoryTitle();
    }

    /**
     * Returns the drawable background for a single recipe in this category.
     *
     * @apiNote this became nullable in 15.20.0.
     * If the background is null, getWidth() and getHeight() must be overridden
     *
     * @deprecated you can optionally draw a background image in {@link #draw}, and specify the width and height with {@link #getWidth()} and {@link #getHeight()}
     */
    @SuppressWarnings({"DeprecatedIsStillUsed", "removal"})
    @Deprecated(since = "15.20.0", forRemoval = true)
    @Nullable
    @Override
    public IDrawable getBackground() {
        try {
            return Optional.ofNullable(this.builder.getBackgroundSupplier())
                    .or(() -> Optional.of(IRecipeCategory.super::getBackground))
                    .get().get();
        } catch (Throwable e) {
            ConsoleJS.CLIENT.error("Error drawing recipe category: " + this.builder.getRecipeType().getUid(), e);
        }
        return IRecipeCategory.super.getBackground();
    }

    /**
     * Returns the width of recipe layouts that are drawn for this recipe category.
     *
     * @since 11.5.0
     */
    @Override
    public int getWidth() {
        try {
            return Optional.ofNullable(this.builder.getWidthSupplier())
                    .or(() -> Optional.of(IRecipeCategory.super::getWidth))
                    .get().get();
        } catch (Throwable e) {
            ConsoleJS.CLIENT.error("Error drawing recipe category: " + this.builder.getRecipeType().getUid(), e);
        }
        return IRecipeCategory.super.getWidth();
    }

    /**
     * Returns the height of recipe layouts that are drawn for this recipe category.
     *
     * @since 11.5.0
     */
    @Override
    public int getHeight() {
        try {
            return Optional.ofNullable(this.builder.getHeightSupplier())
                    .or(() -> Optional.of(IRecipeCategory.super::getHeight))
                    .get().get();
        } catch (Throwable e) {
            ConsoleJS.CLIENT.error("Error drawing recipe category: " + this.builder.getRecipeType().getUid(), e);
        }
        return IRecipeCategory.super.getHeight();
    }

    /**
     * Icon for the category tab.
     * You can use {@link IGuiHelper#createDrawableIngredient(IIngredientType, Object)}
     * to create a drawable from an ingredient.
     *
     * @return icon to draw on the category tab, max size is 16x16 pixels.
     */
    @Override
    public @NotNull IDrawable getIcon() {
        return this.builder.getCategoryIcon();
    }

    /**
     * Sets all the recipe's ingredients by filling out an instance of {@link IRecipeLayoutBuilder}.
     * This is used by JEI for lookups, to figure out what ingredients are inputs and outputs for a recipe.
     *
     * @param builder the layout builder to use to set the ingredients.
     * @param recipe  the recipe to set the ingredients for.
     * @param focuses the focus areas of the recipe.
     * @since 9.4.0
     */
    @Override
    public void setRecipe(
            IRecipeLayoutBuilder builder,
            T recipe,
            IFocusGroup focuses
    ) {
        if (this.builder.getSetRecipeHandler() == null) {
            return;
        }
        try {
            this.builder.getSetRecipeHandler().setRecipe(builder, recipe, focuses);
        } catch (Throwable e) {
            ConsoleJS.CLIENT.error("Error setting recipe for recipe category: " + this.builder.getRecipeType().getUid(), e);
        }
    }

    /**
     * Create per-recipe extras like {@link IRecipeWidget} and {@link IJeiInputHandler}.
     *
     * These have access to a specific recipe, and will persist as long as a recipe layout is on screen,
     * so they can be used for caching and displaying recipe-specific
     * information more easily than from the recipe category directly.
     *
     * @since 15.9.0
     */
    @Override
    public void createRecipeExtras(IRecipeExtrasBuilder builder, T recipe, IFocusGroup focuses) {
        try {
            Optional.ofNullable(this.builder.getCreateRecipeExtrasHandler())
                    .or(() -> Optional.of(IRecipeCategory.super::createRecipeExtras))
                    .get().createRecipeExtras(builder, recipe, focuses);
        } catch (Throwable e) {
            ConsoleJS.CLIENT
                    .error("Error creating recipe extras for category: " + this.builder.getRecipeType().getUid(), e);
        }
    }

    /**
     * Draw extras or additional info about the recipe.
     * Use the mouse position for things like button highlights.
     * Tooltips are handled by {@link #getTooltipStrings(Object, IRecipeSlotsView, double, double)}
     *
     * @param recipe          the current recipe being drawn.
     * @param recipeSlotsView a view of the current recipe slots being drawn.
     * @param guiGraphics     the current {@link GuiGraphics} for rendering.
     * @param mouseX          the X position of the mouse, relative to the recipe.
     * @param mouseY          the Y position of the mouse, relative to the recipe.
     * @see IDrawable for a simple class for drawing things.
     * @see IGuiHelper for useful functions.
     * @see IRecipeSlotsView for information about the ingredients that are currently being drawn.
     * @since 9.3.0
     */
    @Override
    public void draw(
            T recipe,
            IRecipeSlotsView recipeSlotsView,
            GuiGraphics guiGraphics,
            double mouseX,
            double mouseY
    ) {
        try {
            Optional.ofNullable(this.builder.getDrawHandler())
                    .or(() -> Optional.of(IRecipeCategory.super::draw))
                    .ifPresent(handler -> handler.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY));
        } catch (Throwable e) {
            ConsoleJS.CLIENT.error("Error drawing recipe category: " + this.builder.getRecipeType().getUid(), e);
        }
        IRecipeCategory.super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
    }

    /**
     * Called every time JEI updates the cycling displayed ingredients on a recipe.
     * <p>
     * Use this (for example) to compute recipe outputs that result from complex relationships between ingredients.
     * <p>
     * Use {@link IRecipeSlotDrawable#getDisplayedIngredient()} from your regular slots to see what is
     * currently being drawn, and calculate what you need from there.
     * You can override any slot's displayed ingredient with {@link IRecipeSlotDrawable#createDisplayOverrides()}.
     * <p>
     * Note that overrides set this way are not searchable via recipe lookups in JEI,
     * it is only for displaying things too complex for normal lookups to handle.
     *
     * @param recipe      the current recipe being drawn.
     * @param recipeSlots the current recipe slots being drawn.
     * @param focuses     the current focuses
     * @since 15.12.1
     */
    @Override
    public void onDisplayedIngredientsUpdate(
            T recipe,
            List<IRecipeSlotDrawable> recipeSlots,
            IFocusGroup focuses
    ) {
        try {
            Optional.ofNullable(this.builder.getDisplayedIngredientsUpdateHandler())
                    .or(() -> Optional.of(IRecipeCategory.super::onDisplayedIngredientsUpdate))
                    .get().onDisplayedIngredientsUpdate(recipe, recipeSlots, focuses);
        } catch (Throwable e) {
            ConsoleJS.CLIENT.error("Error updating displayed ingredients for recipe category: " + this.builder.getRecipeType().getUid(), e);
        }
    }

    /**
     * Get the tooltip for whatever is under the mouse.
     * Ingredient tooltips from recipe slots are already handled by JEI, this is for anything else.
     *
     * To add to ingredient tooltips, see {@link IRecipeSlotBuilder#addRichTooltipCallback(IRecipeSlotRichTooltipCallback)}
     *
     * @param recipe          the current recipe being drawn.
     * @param recipeSlotsView a view of the current recipe slots being drawn.
     * @param mouseX          the X position of the mouse, relative to the recipe.
     * @param mouseY          the Y position of the mouse, relative to the recipe.
     * @return tooltip strings. If there is no tooltip at this position, return an empty list.
     *
     * @since 9.3.0
     * @deprecated use {@link #getTooltip(ITooltipBuilder, Object, IRecipeSlotsView, double, double)}
     */
    @SuppressWarnings({"DeprecatedIsStillUsed", "removal"})
    @Deprecated(since = "15.8.4", forRemoval = true)
    @Override
    public @NotNull List<Component> getTooltipStrings(
            T recipe,
            IRecipeSlotsView recipeSlotsView,
            double mouseX,
            double mouseY
    ) {
        try {
            return Optional.ofNullable(this.builder.getTooltipStringsHandler())
                    .or(() -> Optional.of(IRecipeCategory.super::getTooltipStrings))
                    .get().getTooltipStrings(recipe, recipeSlotsView, mouseX, mouseY);
        } catch (Throwable e) {
            ConsoleJS.CLIENT.error("Error getting tooltip strings for recipe category: " + this.builder.getRecipeType().getUid(), e);
        }
        return IRecipeCategory.super.getTooltipStrings(recipe, recipeSlotsView, mouseX, mouseY);
    }

    /**
     * Get the tooltip for whatever is under the mouse.
     * Ingredient tooltips from recipe slots are already handled by JEI, this is for anything else.
     * <p>
     * To add to ingredient tooltips, see {@link IRecipeSlotBuilder#addRichTooltipCallback(IRecipeSlotRichTooltipCallback)}
     *
     * @param tooltip         a tooltip builder to add tooltip lines to
     * @param recipe          the current recipe being drawn.
     * @param recipeSlotsView a view of the current recipe slots being drawn.
     * @param mouseX          the X position of the mouse, relative to the recipe.
     * @param mouseY          the Y position of the mouse, relative to the recipe.
     * @since 15.8.4
     */
    @Override
    public void getTooltip(
            ITooltipBuilder tooltip,
            T recipe,
            IRecipeSlotsView recipeSlotsView,
            double mouseX,
            double mouseY
    ) {
        try {
            Optional.ofNullable(this.builder.getTooltipHandlerOverride())
                    .or(() -> Optional.of(IRecipeCategory.super::getTooltip))
                    .get().getTooltip(tooltip, recipe, recipeSlotsView, mouseX, mouseY);
        } catch (Throwable e) {
            ConsoleJS.CLIENT.error("Error getting tooltip for recipe category: " + this.builder.getRecipeType().getUid(), e);
        }
    }

    /**
     * Called when a player clicks the recipe.
     * Useful for implementing buttons, hyperlinks, and other interactions to your recipe.
     *
     * @param recipe the currently hovered recipe
     * @param mouseX the X position of the mouse, relative to the recipe.
     * @param mouseY the Y position of the mouse, relative to the recipe.
     * @param input  the current input
     * @return true if the input was handled, false otherwise
     * @since 8.3.0
     *
     * @deprecated create an {@link IJeiInputHandler} or {@link GuiEventListener} and add it with
     * {@link IRecipeExtrasBuilder#addInputHandler} or {@link IRecipeExtrasBuilder#addGuiEventListener}
     */
    @SuppressWarnings({"DeprecatedIsStillUsed", "removal"})
    @Deprecated(since = "15.9.0", forRemoval = true)
    @Override
    public boolean handleInput(T recipe, double mouseX, double mouseY, InputConstants.Key input) {
        try {
            return Optional.ofNullable(this.builder.getInputHandler())
                    .or(() -> Optional.of(IRecipeCategory.super::handleInput))
                    .get().handleInput(recipe, mouseX, mouseY, input);
        } catch (Throwable e) {
            ConsoleJS.CLIENT.error("Error handling input for recipe category: " + this.builder.getRecipeType().getUid(), e);
        }
        return IRecipeCategory.super.handleInput(recipe, mouseX, mouseY, input);
    }

    /**
     * @param recipe the recipe to check
     * @return true if the given recipe can be handled by this category.
     * @since 7.2.0
     */
    @Override
    public boolean isHandled(T recipe) {
        try {
            return Optional.ofNullable(this.builder.getIsRecipeHandledByCategory())
                    .or(() -> Optional.of(IRecipeCategory.super::isHandled))
                    .get()
                    .isHandled(recipe);
        } catch (Throwable e) {
            ConsoleJS.CLIENT.error("Error checking if recipe is handled by category: " + this.builder.getRecipeType().getUid(), e);
        }
        return IRecipeCategory.super.isHandled(recipe);
    }

    /**
     * Return the registry name of the recipe here.
     * With advanced tooltips on, this will show on the output item's tooltip.
     * <p>
     * This will also show the modId when the recipe modId and output item modId do not match.
     * This lets the player know where the recipe came from.
     *
     * @param recipe
     * @return the registry name of the recipe, or null if there is none
     * @since 9.3.0
     */
    @Override
    public @Nullable ResourceLocation getRegistryName(T recipe) {
        try {
            return Optional.ofNullable(this.builder.getGetRegisterName())
                    .or(() -> Optional.of(IRecipeCategory.super::getRegistryName))
                    .get()
                    .getRegistryName(recipe);
        } catch (Throwable e) {
            ConsoleJS.CLIENT.error("Error getting registry name for recipe category: " + this.builder.getRecipeType().getUid(), e);
        }
        return IRecipeCategory.super.getRegistryName(recipe);
    }
}
