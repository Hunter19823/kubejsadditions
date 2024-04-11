package pie.ilikepiefoo.compat.jade.builder;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import pie.ilikepiefoo.compat.jade.ITooltipWrapper;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElement;

public class BlockComponentProviderBuilder extends ToggleableProviderBuilder {
    private IconRetriever iconRetriever;
    private TooltipRetriever tooltipRetriever;

    public BlockComponentProviderBuilder(ResourceLocation uniqueIdentifier) {
        super(uniqueIdentifier);
    }

    public BlockComponentProviderBuilder iconRetriever(IconRetriever iconRetriever) {
        return setIconRetriever(iconRetriever);
    }

    public BlockComponentProviderBuilder icon(IconRetriever iconRetriever) {
        return setIconRetriever(iconRetriever);
    }

    public BlockComponentProviderBuilder tooltipRetriever(TooltipRetriever tooltipRetriever) {
        return setTooltipRetriever(tooltipRetriever);
    }

    public BlockComponentProviderBuilder tooltip(TooltipRetriever tooltipRetriever) {
        return setTooltipRetriever(tooltipRetriever);
    }

    public IconRetriever getIconRetriever() {
        return this.iconRetriever;
    }

    public BlockComponentProviderBuilder setIconRetriever(IconRetriever iconRetriever) {
        this.iconRetriever = iconRetriever;
        return this;
    }

    public TooltipRetriever getTooltipRetriever() {
        return this.tooltipRetriever;
    }

    public BlockComponentProviderBuilder setTooltipRetriever(TooltipRetriever tooltipRetriever) {
        this.tooltipRetriever = tooltipRetriever;
        return this;
    }

    public interface IconRetriever {
        @Nullable IElement getIcon(BlockAccessor accessor, IPluginConfig config, IElement currentIcon);
    }

    public interface TooltipRetriever {
        void appendTooltip(ITooltipWrapper currentToolTip, BlockAccessor blockAccessor, IPluginConfig config);
    }

}
