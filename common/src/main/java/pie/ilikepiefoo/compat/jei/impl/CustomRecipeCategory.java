package pie.ilikepiefoo.compat.jei.impl;

import com.mojang.blaze3d.platform.InputConstants;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotTooltipCallback;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pie.ilikepiefoo.compat.jei.builder.RecipeCategoryBuilder;

import java.util.List;

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
     */
    @Override
    public @NotNull IDrawable getBackground() {
        return this.builder.getCategoryBackground();
    }

    /**
     * Returns the width of recipe layouts that are drawn for this recipe category.
     *
     * @since 11.5.0
     */
    @Override
    public int getWidth() {
        return this.builder.getWidth();
    }

    /**
     * Returns the height of recipe layouts that are drawn for this recipe category.
     *
     * @since 11.5.0
     */
    @Override
    public int getHeight() {
        return this.builder.getHeight();
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
        this.builder.getSetRecipeHandler().setRecipe(builder, recipe, focuses);
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
        if (this.builder.getDrawHandler() == null) {
            IRecipeCategory.super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
            return;
        }
        this.builder.getDrawHandler().draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
    }

    /**
     * Get the tooltip for whatever is under the mouse.
     * Ingredient tooltips are already handled by JEI, this is for anything else.
     * <p>
     * To add to ingredient tooltips, see {@link IRecipeSlotBuilder#addTooltipCallback(IRecipeSlotTooltipCallback)}
     *
     * @param recipe          the current recipe being drawn.
     * @param recipeSlotsView a view of the current recipe slots being drawn.
     * @param mouseX          the X position of the mouse, relative to the recipe.
     * @param mouseY          the Y position of the mouse, relative to the recipe.
     * @return tooltip strings. If there is no tooltip at this position, return an empty list.
     * @since 9.3.0
     */
    @Override
    public @NotNull List<Component> getTooltipStrings(
            T recipe,
            IRecipeSlotsView recipeSlotsView,
            double mouseX,
            double mouseY
    ) {
        if (this.builder.getTooltipHandler() != null) {
            return this.builder.getTooltipHandler().getTooltipStrings(recipe, recipeSlotsView, mouseX, mouseY);
        }
        return IRecipeCategory.super.getTooltipStrings(recipe, recipeSlotsView, mouseX, mouseY);
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
     */
    @Override
    public boolean handleInput(T recipe, double mouseX, double mouseY, InputConstants.Key input) {
        if (this.builder.getInputHandler() != null) {
            return this.builder.getInputHandler().handleInput(recipe, mouseX, mouseY, input);
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
        if (this.builder.getIsRecipeHandledByCategory() != null) {
            return this.builder.getIsRecipeHandledByCategory().isHandled(recipe);
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
        if (this.builder.getGetRegisterName() != null) {
            return this.builder.getGetRegisterName().getRegistryName(recipe);
        }
        return IRecipeCategory.super.getRegistryName(recipe);
    }
}
