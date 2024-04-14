package pie.ilikepiefoo.compat.jei.builder;

import com.mojang.blaze3d.platform.InputConstants;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotTooltipCallback;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RecipeCategoryBuilder<T> {
    @NotNull
    private final RecipeType<T> recipeType;
    @NotNull
    private final IJeiHelpers jeiHelpers;
    @NotNull
    private Component title;
    @NotNull
    private IDrawable background;
    @NotNull
    private IDrawable icon;
    private int width;
    private int height;
    private SetRecipeHandler<T> setRecipeHandler;
    private DrawHandler<T> drawHandler;
    private TooltipHandler<T> tooltipHandler;
    private InputHandler<T> inputHandler;
    private IsRecipeHandledByCategory<T> isRecipeHandledByCategory;
    private GetRegisterName<T> getRegisterName;


    public RecipeCategoryBuilder(@NotNull RecipeType<T> recipeType, @NotNull IJeiHelpers jeiHelpers) {
        this.recipeType = recipeType;
        this.jeiHelpers = jeiHelpers;
        this.title = Component.literal("KubeJS Additions Custom Category");
        this.background = this.jeiHelpers.getGuiHelper().createDrawableItemStack(new ItemStack(Items.CREEPER_HEAD));
        this.icon = this.jeiHelpers.getGuiHelper().createDrawableItemStack(new ItemStack(Items.TNT));
        this.width = this.background.getWidth();
        this.height = this.background.getHeight();
    }

    @NotNull
    public IJeiHelpers getJeiHelpers() {
        return jeiHelpers;
    }

    @NotNull
    public RecipeType<T> getRecipeType() {
        return recipeType;
    }

    @NotNull
    public Component getCategoryTitle() {
        return title;
    }

    public RecipeCategoryBuilder<T> title(@NotNull Component title) {
        this.title = title;
        return this;
    }

    @NotNull
    public IDrawable getCategoryBackground() {
        return background;
    }

    public RecipeCategoryBuilder<T> background(@NotNull IDrawable background) {
        this.background = background;
        this.width = background.getWidth();
        this.height = background.getHeight();
        return this;
    }

    @NotNull
    public IDrawable getCategoryIcon() {
        return icon;
    }

    public RecipeCategoryBuilder<T> icon(@NotNull IDrawable icon) {
        this.icon = icon;
        return this;
    }

    public int getWidth() {
        return width;
    }

    public RecipeCategoryBuilder<T> setWidth(int width) {
        if (width < 0) {
            throw new IllegalArgumentException("width must be greater than or equal to zero");
        }
        if (width == 0) {
            throw new IllegalArgumentException("width must be greater than zero");
        }
        this.width = width;
        return this;
    }

    public int getHeight() {
        return height;
    }

    public RecipeCategoryBuilder<T> setHeight(int height) {
        if (height < 0) {
            throw new IllegalArgumentException("height must be greater than or equal to zero");
        }
        if (height == 0) {
            throw new IllegalArgumentException("height must be greater than zero");
        }

        this.height = height;
        return this;
    }

    public SetRecipeHandler<T> getSetRecipeHandler() {
        return setRecipeHandler;
    }

    public RecipeCategoryBuilder<T> setSetRecipeHandler(SetRecipeHandler<T> setRecipeHandler) {
        this.setRecipeHandler = setRecipeHandler;
        return this;
    }

    public DrawHandler<T> getDrawHandler() {
        return drawHandler;
    }

    public RecipeCategoryBuilder<T> setDrawHandler(DrawHandler<T> drawHandler) {
        this.drawHandler = drawHandler;
        return this;
    }

    public TooltipHandler<T> getTooltipHandler() {
        return tooltipHandler;
    }

    public RecipeCategoryBuilder<T> setTooltipHandler(TooltipHandler<T> tooltipHandler) {
        this.tooltipHandler = tooltipHandler;
        return this;
    }

    public InputHandler<T> getInputHandler() {
        return inputHandler;
    }

    public RecipeCategoryBuilder<T> setInputHandler(InputHandler<T> inputHandler) {
        this.inputHandler = inputHandler;
        return this;
    }

    public IsRecipeHandledByCategory<T> getIsRecipeHandledByCategory() {
        return isRecipeHandledByCategory;
    }

    public RecipeCategoryBuilder<T> setIsRecipeHandledByCategory(IsRecipeHandledByCategory<T> isRecipeHandledByCategory) {
        this.isRecipeHandledByCategory = isRecipeHandledByCategory;
        return this;
    }

    public GetRegisterName<T> getGetRegisterName() {
        return getRegisterName;
    }

    public RecipeCategoryBuilder<T> setGetRegisterName(GetRegisterName<T> getRegisterName) {
        this.getRegisterName = getRegisterName;
        return this;
    }

    public RecipeCategoryBuilder<T> registryName(GetRegisterName<T> getRegisterName) {
        return setGetRegisterName(getRegisterName);
    }

    public RecipeCategoryBuilder<T> isRecipeHandled(IsRecipeHandledByCategory<T> isRecipeHandledByCategory) {
        return setIsRecipeHandledByCategory(isRecipeHandledByCategory);
    }

    public RecipeCategoryBuilder<T> onInput(InputHandler<T> inputHandler) {
        return setInputHandler(inputHandler);
    }

    public RecipeCategoryBuilder<T> withTooltip(TooltipHandler<T> tooltipHandler) {
        return setTooltipHandler(tooltipHandler);
    }


    @FunctionalInterface
    public interface SetRecipeHandler<T> {
        /**
         * Sets all the recipe's ingredients by filling out an instance of {@link IRecipeLayoutBuilder}.
         * This is used by JEI for lookups, to figure out what ingredients are inputs and outputs for a recipe.
         *
         * @since 9.4.0
         */
        void setRecipe(IRecipeLayoutBuilder builder, T recipe, IFocusGroup focuses);
    }

    @FunctionalInterface
    public interface DrawHandler<T> {
        /**
         * Draw extras or additional info about the recipe.
         * Use the mouse position for things like button highlights.
         * Tooltips are handled by {@link  RecipeCategoryBuilder.TooltipHandler<T>}.
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
        void draw(T recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY);
    }

    @FunctionalInterface
    public interface TooltipHandler<T> {
        /**
         * Get the tooltip for whatever is under the mouse.
         * Ingredient tooltips are already handled by JEI, this is for anything else.
         * To add to ingredient tooltips, see {@link IRecipeSlotBuilder#addTooltipCallback(IRecipeSlotTooltipCallback)}
         *
         * @param recipe          the current recipe being drawn.
         * @param recipeSlotsView a view of the current recipe slots being drawn.
         * @param mouseX          the X position of the mouse, relative to the recipe.
         * @param mouseY          the Y position of the mouse, relative to the recipe.
         * @return tooltip strings. If there is no tooltip at this position, return an empty list.
         * @since 9.3.0
         */
        @NotNull
        List<Component> getTooltipStrings(T recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY);
    }

    @FunctionalInterface
    public interface InputHandler<T> {
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
        boolean handleInput(T recipe, double mouseX, double mouseY, InputConstants.Key input);
    }

    @FunctionalInterface
    public interface IsRecipeHandledByCategory<T> {
        /**
         * @return true if the given recipe can be handled by this category.
         * @since 7.2.0
         */
        boolean isHandled(T recipe);
    }

    @FunctionalInterface
    public interface GetRegisterName<T> {
        /**
         * Return the registry name of the recipe here.
         * With advanced tooltips on, this will show on the output item's tooltip.
         * <p>
         * This will also show the modId when the recipe modId and output item modId do not match.
         * This lets the player know where the recipe came from.
         *
         * @return the registry name of the recipe, or null if there is none
         * @since 9.3.0
         */
        @Nullable
        ResourceLocation getRegistryName(T recipe);
    }

}
