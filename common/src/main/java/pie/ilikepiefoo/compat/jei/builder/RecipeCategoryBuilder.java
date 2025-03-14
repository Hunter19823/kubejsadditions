package pie.ilikepiefoo.compat.jei.builder;

import com.mojang.blaze3d.platform.InputConstants;
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
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class RecipeCategoryBuilder<T> {
    @NotNull
    private final RecipeType<T> recipeType;
    @NotNull
    private final IJeiHelpers jeiHelpers;
    @NotNull
    private Supplier<Component> title;
    @NotNull
    private Supplier<IDrawable> background;
    @NotNull
    private Supplier<IDrawable> icon;
    private Supplier<Integer> width;
    private Supplier<Integer> height;
    private SetRecipeHandler<T> setRecipeHandler;
    private CreateRecipeExtrasHandler<T> createRecipeExtrasHandler;
    private DrawHandler<T> drawHandler;
    private TooltipStringsHandler<T> tooltipStringsHandler;
    private GetTooltipHandler<T> getTooltipHandler;
    private InputHandler<T> inputHandler;
    private IsRecipeHandledByCategory<T> isRecipeHandledByCategory;
    private GetRegisterName<T> getRegisterName;
    private DisplayedIngredientsUpdateHandler<T> displayedIngredientsUpdateHandler;

    public RecipeCategoryBuilder(
        @NotNull RecipeType<T> recipeType, @NotNull IJeiHelpers jeiHelpers
    ) {
        this.recipeType = recipeType;
        this.jeiHelpers = jeiHelpers;
        var title = Component.literal("KubeJS Additions Custom Category");
        this.title = () -> title;
        final IDrawable background = this.jeiHelpers.getGuiHelper()
            .createDrawableItemStack(new ItemStack(Items.CREEPER_HEAD));
        this.background = () -> background;
        var icon = this.jeiHelpers.getGuiHelper().createDrawableItemStack(new ItemStack(Items.TNT));
        this.icon = () -> icon;
        this.width = background::getWidth;
        this.height = background::getHeight;
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
        return title.get();
    }

    public RecipeCategoryBuilder<T> title( @NotNull Component title ) {
        this.title = () -> title;
        return this;
    }

    public RecipeCategoryBuilder<T> titleSupplier( @NotNull Supplier<Component> title ) {
        this.title = title;
        return this;
    }

    @Nullable
    public IDrawable getCategoryBackground() {
        return background.get();
    }

    public Supplier<IDrawable> getBackgroundSupplier() {
        return background;
    }

    public RecipeCategoryBuilder<T> background( @Nullable IDrawable background ) {
        this.background = () -> background;
        return this;
    }

    public RecipeCategoryBuilder<T> backgroundSupplier( @NotNull Supplier<IDrawable> background ) {
        this.background = background;
        return this;
    }

    @NotNull
    public IDrawable getCategoryIcon() {
        return icon.get();
    }

    public RecipeCategoryBuilder<T> icon( @NotNull IDrawable icon ) {
        this.icon = () -> icon;
        return this;
    }

    public RecipeCategoryBuilder<T> iconSupplier( @NotNull Supplier<IDrawable> icon ) {
        this.icon = icon;
        return this;
    }

    public int getWidth() {
        return width.get();
    }

    public RecipeCategoryBuilder<T> setWidth( int width ) {
        if (width < 0) {
            throw new IllegalArgumentException("width must be greater than or equal to zero");
        }
        if (width == 0) {
            throw new IllegalArgumentException("width must be greater than zero");
        }
        this.width = () -> width;
        return this;
    }

    public Supplier<Integer> getWidthSupplier() {
        return width;
    }

    public RecipeCategoryBuilder<T> setWidthSupplier( Supplier<Integer> widthSupplier ) {
        this.width = widthSupplier;
        return this;
    }

    public int getHeight() {
        return height.get();
    }

    public RecipeCategoryBuilder<T> setHeight( int height ) {
        if (height < 0) {
            throw new IllegalArgumentException("height must be greater than or equal to zero");
        }
        if (height == 0) {
            throw new IllegalArgumentException("height must be greater than zero");
        }

        this.height = () -> height;
        return this;
    }

    public Supplier<Integer> getHeightSupplier() {
        return height;
    }

    public RecipeCategoryBuilder<T> setHeightSupplier( Supplier<Integer> heightSupplier ) {
        this.height = heightSupplier;
        return this;
    }

    public SetRecipeHandler<T> getSetRecipeHandler() {
        return setRecipeHandler;
    }

    public RecipeCategoryBuilder<T> setDrawHandler( DrawHandler<T> drawHandler ) {
        this.drawHandler = drawHandler;
        return this;
    }

    public CreateRecipeExtrasHandler<T> getCreateRecipeExtrasHandler() {
        return createRecipeExtrasHandler;
    }

    public TooltipStringsHandler<T> getTooltipStringsHandler() {
        return tooltipStringsHandler;
    }

    public DrawHandler<T> getDrawHandler() {
        return drawHandler;
    }

    public GetTooltipHandler<T> getTooltipHandlerOverride() {
        return this.getTooltipHandler;
    }

    public RecipeCategoryBuilder<T> setTooltipHandlerOverride( GetTooltipHandler<T> getTooltipHandler ) {
        this.getTooltipHandler = getTooltipHandler;
        return this;
    }

    public DisplayedIngredientsUpdateHandler<T> getDisplayedIngredientsUpdateHandler() {
        return displayedIngredientsUpdateHandler;
    }

    public RecipeCategoryBuilder<T> setDisplayedIngredientsUpdateHandler(
        DisplayedIngredientsUpdateHandler<T> displayedIngredientsUpdateHandler
    ) {
        this.displayedIngredientsUpdateHandler = displayedIngredientsUpdateHandler;
        return this;
    }

    public RecipeCategoryBuilder<T> registryName( GetRegisterName<T> getRegisterName ) {
        return setGetRegisterName(getRegisterName);
    }


    public InputHandler<T> getInputHandler() {
        return inputHandler;
    }

    public RecipeCategoryBuilder<T> setGetRegisterName( GetRegisterName<T> getRegisterName ) {
        this.getRegisterName = getRegisterName;
        return this;
    }

    public IsRecipeHandledByCategory<T> getIsRecipeHandledByCategory() {
        return isRecipeHandledByCategory;
    }

    public RecipeCategoryBuilder<T> isRecipeHandled( IsRecipeHandledByCategory<T> isRecipeHandledByCategory ) {
        return setIsRecipeHandledByCategory(isRecipeHandledByCategory);
    }

    public GetRegisterName<T> getGetRegisterName() {
        return getRegisterName;
    }

    public RecipeCategoryBuilder<T> setIsRecipeHandledByCategory( IsRecipeHandledByCategory<T> isRecipeHandledByCategory ) {
        this.isRecipeHandledByCategory = isRecipeHandledByCategory;
        return this;
    }

    public RecipeCategoryBuilder<T> onInput( InputHandler<T> inputHandler ) {
        return setInputHandler(inputHandler);
    }

    public RecipeCategoryBuilder<T> setInputHandler( InputHandler<T> inputHandler ) {
        this.inputHandler = inputHandler;
        return this;
    }

    public RecipeCategoryBuilder<T> withTooltip( TooltipStringsHandler<T> tooltipStringsHandler ) {
        return setTooltipHandler(tooltipStringsHandler);
    }

    public RecipeCategoryBuilder<T> setTooltipHandler( TooltipStringsHandler<T> tooltipStringsHandler ) {
        this.tooltipStringsHandler = tooltipStringsHandler;
        return this;
    }

    public RecipeCategoryBuilder<T> handleLookup( SetRecipeHandler<T> recipeHandler ) {
        return this.setSetRecipeHandler(recipeHandler);
    }

    public RecipeCategoryBuilder<T> setSetRecipeHandler( SetRecipeHandler<T> setRecipeHandler ) {
        this.setRecipeHandler = setRecipeHandler;
        return this;
    }

    public RecipeCategoryBuilder<T> createRecipeExtras( CreateRecipeExtrasHandler<T> createRecipeExtrasHandler ) {
        return setCreateRecipeExtrasHandler(createRecipeExtrasHandler);
    }

    public RecipeCategoryBuilder<T> setCreateRecipeExtrasHandler( CreateRecipeExtrasHandler<T> setRecipeExtrasHandler ) {
        this.createRecipeExtrasHandler = setRecipeExtrasHandler;
        return this;
    }

    public RecipeCategoryBuilder<T> onDisplayedIngredientsUpdate( DisplayedIngredientsUpdateHandler<T> displayedIngredientsUpdateHandler ) {
        this.displayedIngredientsUpdateHandler = displayedIngredientsUpdateHandler;
        return this;
    }

    @FunctionalInterface
    public interface SetRecipeHandler<T> {
        /**
         * Get the recipe slots that were created in {@link IRecipeCategory#setRecipe}.
         *
         * @since 15.20.0
         */
        void setRecipe( IRecipeLayoutBuilder builder, T recipe, IFocusGroup focuses );

    }

    @FunctionalInterface
    public interface CreateRecipeExtrasHandler<T> {
        /**
         * Create per-recipe extras like {@link IRecipeWidget} and {@link IJeiInputHandler}.
         * <p>
         * These have access to a specific recipe, and will persist as long as a recipe layout is
         * on screen,
         * so they can be used for caching and displaying recipe-specific
         * information more easily than from the recipe category directly.
         *
         * @since 15.9.0
         */
        void createRecipeExtras(
            IRecipeExtrasBuilder builder, T recipe, IFocusGroup focuses
        );

    }

    @FunctionalInterface
    public interface DrawHandler<T> {
        /**
         * Draw extras or additional info about the recipe.
         * Use the mouse position for things like button highlights.
         * Tooltips are handled by {@link  TooltipStringsHandler <T>}.
         *
         * @param recipe          the current recipe being drawn.
         * @param recipeSlotsView a view of the current recipe slots being drawn.
         * @param guiGraphics     the current {@link GuiGraphics} for rendering.
         * @param mouseX          the X position of the mouse, relative to the recipe.
         * @param mouseY          the Y position of the mouse, relative to the recipe.
         * @see IDrawable for a simple class for drawing things.
         * @see IGuiHelper for useful functions.
         * @see IRecipeSlotsView for information about the ingredients that are currently being
         * drawn.
         * @since 9.3.0
         */
        void draw(
            T recipe,
            IRecipeSlotsView recipeSlotsView,
            GuiGraphics guiGraphics,
            double mouseX,
            double mouseY
        );

    }

    @FunctionalInterface
    public interface TooltipStringsHandler<T> {
        /**
         * Get the tooltip for whatever is under the mouse.
         * Ingredient tooltips from recipe slots are already handled by JEI, this is for anything
         * else.
         *
         * To add to ingredient tooltips, see
         * {@link IRecipeSlotBuilder#addRichTooltipCallback(IRecipeSlotRichTooltipCallback)}
         *
         * @param recipe          the current recipe being drawn.
         * @param recipeSlotsView a view of the current recipe slots being drawn.
         * @param mouseX          the X position of the mouse, relative to the recipe.
         * @param mouseY          the Y position of the mouse, relative to the recipe.
         * @return tooltip strings. If there is no tooltip at this position, return an empty list.
         *
         * @since 9.3.0
         * @deprecated use {@link GetTooltipHandler#getTooltip(ITooltipBuilder, Object, IRecipeSlotsView, double, double)}
         */
        @SuppressWarnings( "DeprecatedIsStillUsed" )
        @Deprecated( since = "15.8.4", forRemoval = true )
        @NotNull List<Component> getTooltipStrings(
            T recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY
        );

    }

    @FunctionalInterface
    public interface GetTooltipHandler<T> {
        /**
         * Get the tooltip for whatever is under the mouse.
         * Ingredient tooltips from recipe slots are already handled by JEI, this is for anything
         * else.
         * <p>
         * To add to ingredient tooltips, see
         * {@link IRecipeSlotBuilder#addRichTooltipCallback(IRecipeSlotRichTooltipCallback)}
         *
         * @param tooltip         a tooltip builder to add tooltip lines to
         * @param recipe          the current recipe being drawn.
         * @param recipeSlotsView a view of the current recipe slots being drawn.
         * @param mouseX          the X position of the mouse, relative to the recipe.
         * @param mouseY          the Y position of the mouse, relative to the recipe.
         * @since 15.8.4
         */
        void getTooltip(
            ITooltipBuilder tooltip,
            T recipe,
            IRecipeSlotsView recipeSlotsView,
            double mouseX,
            double mouseY
        );

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
        boolean handleInput( T recipe, double mouseX, double mouseY, InputConstants.Key input );

    }

    @FunctionalInterface
    public interface IsRecipeHandledByCategory<T> {
        /**
         * @return true if the given recipe can be handled by this category.
         * @since 7.2.0
         */
        boolean isHandled( T recipe );

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
        @Nullable ResourceLocation getRegistryName( T recipe );

    }

    @FunctionalInterface
    public interface DisplayedIngredientsUpdateHandler<T> {
        /**
         * Called every time JEI updates the cycling displayed ingredients on a recipe.
         * <p>
         * Use this (for example) to compute recipe outputs that result from complex
         * relationships between ingredients.
         * <p>
         * Use {@link IRecipeSlotDrawable#getDisplayedIngredient()} from your regular slots to
         * see what is
         * currently being drawn, and calculate what you need from there.
         * You can override any slot's displayed ingredient with
         * {@link IRecipeSlotDrawable#createDisplayOverrides()}.
         * <p>
         * Note that overrides set this way are not searchable via recipe lookups in JEI,
         * it is only for displaying things too complex for normal lookups to handle.
         *
         * @param recipe      the current recipe being drawn.
         * @param recipeSlots the current recipe slots being drawn.
         * @param focuses     the current focuses
         * @since 15.12.1
         */
        void onDisplayedIngredientsUpdate(
            T recipe, List<IRecipeSlotDrawable> recipeSlots, IFocusGroup focuses
        );

    }
}
