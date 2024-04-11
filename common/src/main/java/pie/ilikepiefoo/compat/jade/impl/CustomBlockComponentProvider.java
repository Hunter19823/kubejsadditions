package pie.ilikepiefoo.compat.jade.impl;

import org.jetbrains.annotations.Nullable;
import pie.ilikepiefoo.compat.jade.ITooltipWrapper;
import pie.ilikepiefoo.compat.jade.builder.BlockComponentProviderBuilder;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElement;

public class CustomBlockComponentProvider extends CustomToggleableProviderBuilder<BlockComponentProviderBuilder> implements IBlockComponentProvider {
    public CustomBlockComponentProvider(BlockComponentProviderBuilder builder) {
        super(builder);
    }

    @Override
    public @Nullable IElement getIcon(
            BlockAccessor accessor,
            IPluginConfig config,
            IElement currentIcon
    ) {
        if (builder.getIconRetriever() == null) {
            return null;
        }
        return builder.getIconRetriever().getIcon(accessor, config, currentIcon);
    }

    @Override
    public void appendTooltip(
            ITooltip iTooltip,
            BlockAccessor blockAccessor,
            IPluginConfig iPluginConfig
    ) {
        if (builder.getTooltipRetriever() == null) {
            return;
        }
        builder.getTooltipRetriever().appendTooltip(ITooltipWrapper.of(iTooltip), blockAccessor, iPluginConfig);
    }
}
