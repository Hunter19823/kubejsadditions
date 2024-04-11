package pie.ilikepiefoo.compat.jade.builder;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import pie.ilikepiefoo.compat.jade.ITooltipWrapper;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElement;

public class EntityComponentProviderBuilder extends ToggleableProviderBuilder {
    private IconRetriever iconRetriever;
    private TooltipRetriever tooltipRetriever;

    public EntityComponentProviderBuilder(ResourceLocation uniqueIdentifier) {
        super(uniqueIdentifier);
    }

    public EntityComponentProviderBuilder iconRetriever(IconRetriever iconRetriever) {
        return setIconRetriever(iconRetriever);
    }

    public EntityComponentProviderBuilder icon(IconRetriever iconRetriever) {
        return setIconRetriever(iconRetriever);
    }

    public EntityComponentProviderBuilder tooltipRetriever(TooltipRetriever tooltipRetriever) {
        return setTooltipRetriever(tooltipRetriever);
    }

    public EntityComponentProviderBuilder tooltip(TooltipRetriever tooltipRetriever) {
        return setTooltipRetriever(tooltipRetriever);
    }

    public IconRetriever getIconRetriever() {
        return this.iconRetriever;
    }

    public EntityComponentProviderBuilder setIconRetriever(IconRetriever iconRetriever) {
        this.iconRetriever = iconRetriever;
        return this;
    }

    public TooltipRetriever getTooltipRetriever() {
        return this.tooltipRetriever;
    }

    public EntityComponentProviderBuilder setTooltipRetriever(TooltipRetriever tooltipRetriever) {
        this.tooltipRetriever = tooltipRetriever;
        return this;
    }

    public interface IconRetriever {
        @Nullable IElement getIcon(EntityAccessor accessor, IPluginConfig config, IElement currentIcon);
    }

    public interface TooltipRetriever {
        void appendTooltip(ITooltipWrapper currentToolTip, EntityAccessor entityAccessor, IPluginConfig config);
    }

}
