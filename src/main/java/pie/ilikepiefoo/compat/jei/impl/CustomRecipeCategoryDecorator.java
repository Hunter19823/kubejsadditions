package pie.ilikepiefoo.compat.jei.impl;

import dev.latvian.mods.kubejs.script.ConsoleJS;
import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.category.extensions.IRecipeCategoryDecorator;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("removal")
public record CustomRecipeCategoryDecorator<T>(DrawDecorator<T> draw, TooltipDecorator<T> tooltip) implements IRecipeCategoryDecorator<T> {

    @Override
    public void draw(T recipe, IRecipeCategory<T> recipeCategory, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        try {
            draw.decorate(recipe, recipeCategory, recipeSlotsView, guiGraphics, mouseX, mouseY);
        } catch (Throwable e) {
            ConsoleJS.CLIENT.error("Error decorating existing draw handler for recipe category: " + recipeCategory.getRecipeType().getUid(), e);
        }
    }

    @Override
    public void decorateTooltips(ITooltipBuilder tooltipBuilder, T recipe, IRecipeCategory<T> recipeCategory, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        try {
            List<Component> existing = new ArrayList<>(tooltipBuilder.toLegacyToComponents());
            List<Component> result = tooltip.decorate(existing, recipe, recipeCategory, recipeSlotsView, mouseX, mouseY);
            if (result != existing) {
                tooltipBuilder.removeAll(existing);
                tooltipBuilder.addAll(result);
            }
        } catch (Throwable e) {
            ConsoleJS.CLIENT.error("Error decorating existing tooltips for recipe category: " + recipeCategory.getRecipeType().getUid(), e);
            IRecipeCategoryDecorator.super.decorateTooltips(tooltipBuilder, recipe, recipeCategory, recipeSlotsView, mouseX, mouseY);
        }
    }

    @FunctionalInterface
    public interface DrawDecorator<R> {

        void decorate(R recipe, IRecipeCategory<R> category, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY);
    }

    @FunctionalInterface
    public interface TooltipDecorator<R> {

        List<Component> decorate(List<Component> tooltips, R recipe, IRecipeCategory<R> category, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY);
    }
}
