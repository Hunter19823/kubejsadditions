package pie.ilikepiefoo.compat.jade;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.ITooltip;
import snownee.jade.api.ui.IElement;
import snownee.jade.api.ui.IElementHelper;

import java.util.List;

/**
 * This class exists to resolve ambiguous method calls on the JS side,
 * when methods are overloaded with similar parameters.
 */
public class ITooltipWrapper {
    public final ITooltip tooltip;

    public ITooltipWrapper(ITooltip tooltip) {
        this.tooltip = tooltip;
    }

    public static ITooltipWrapper of(ITooltip tooltip) {
        return new ITooltipWrapper(tooltip);
    }

    public ITooltip getTooltip() {
        return this.tooltip;
    }

    public void clear() {
        tooltip.clear();
    }

    public int size() {
        return tooltip.size();
    }

    public boolean isEmpty() {
        return tooltip.isEmpty();
    }

    public void add(Component component) {
        tooltip.add(component);
    }

    public void add(IElement element) {
        tooltip.add(element);
    }

    public void add(Component component, ResourceLocation tag) {
        tooltip.add(component, tag);
    }

    public void add(int index, Component component) {
        tooltip.add(index, component);
    }

    public void add(int index, Component component, ResourceLocation tag) {
        tooltip.add(index, component, tag);
    }

    public void addAll(List<Component> components) {
        tooltip.addAll(components);
    }

    public void addElements(List<IElement> elements) {
        tooltip.add(elements);
    }

    public void add(int index, List<IElement> elements) {
        tooltip.add(index, elements);
    }

    public void add(int i, IElement iElement) {
        tooltip.add(i, iElement);
    }

    public void append(Component component) {
        tooltip.append(component);
    }

    public void append(Component component, ResourceLocation tag) {
        tooltip.append(component, tag);
    }

    public void append(IElement element) {
        tooltip.append(element);
    }

    public void append(int index, List<IElement> elements) {
        tooltip.append(index, elements);
    }

    public void append(int i, IElement iElement) {
        tooltip.append(i, iElement);
    }

    public void remove(ResourceLocation resourceLocation) {
        tooltip.remove(resourceLocation);
    }

    public IElementHelper getElementHelper() {
        return tooltip.getElementHelper();
    }

    public List<IElement> get(ResourceLocation resourceLocation) {
        return tooltip.get(resourceLocation);
    }

    public List<IElement> get(int i, IElement.Align align) {
        return tooltip.get(i, align);
    }

    public String getMessage() {
        return tooltip.getMessage();
    }
}
