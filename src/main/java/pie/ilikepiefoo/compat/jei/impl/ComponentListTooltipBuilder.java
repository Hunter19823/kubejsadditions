package pie.ilikepiefoo.compat.jei.impl;

import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.ingredients.ITypedIngredient;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SuppressWarnings("removal")
public final class ComponentListTooltipBuilder implements ITooltipBuilder {
    private final List<Component> components = new ArrayList<>();

    public List<Component> getComponents() {
        return components;
    }

    @Override
    public void add(@NotNull FormattedText component) {
        if (component instanceof Component c) {
            components.add(c);
        } else {
            components.add(Component.literal(component.getString()));
        }
    }

    @Override
    public void addAll(Collection<? extends FormattedText> lines) {
        for (FormattedText line : lines) {
            add(line);
        }
    }

    @Override
    public void add(@NotNull TooltipComponent component) {

    }

    @Override
    public void setIngredient(@NotNull ITypedIngredient<?> typedIngredient) {
    }

    @Override
    @Deprecated
    public @NotNull List<Component> toLegacyToComponents() {
        return components;
    }

    @Override
    @Deprecated
    public void removeAll(@NotNull List<Component> lines) {
        components.removeAll(lines);
    }
}
