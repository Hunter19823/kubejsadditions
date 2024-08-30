package pie.ilikepiefoo.compat.jei.impl;

import dev.latvian.mods.kubejs.util.ConsoleJS;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.category.extensions.IRecipeCategoryDecorator;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

import java.util.List;

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
    public List<Component> decorateExistingTooltips(List<Component> tooltips, T recipe, IRecipeCategory<T> recipeCategory, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        try {
            return tooltip.decorate(tooltips, recipe, recipeCategory, recipeSlotsView, mouseX, mouseY);
        } catch (Throwable e) {
            ConsoleJS.CLIENT.error("Error decorating existing tooltips for recipe category: " + recipeCategory.getRecipeType().getUid(), e);
            return IRecipeCategoryDecorator.super.decorateExistingTooltips(tooltips, recipe, recipeCategory, recipeSlotsView, mouseX, mouseY);
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
