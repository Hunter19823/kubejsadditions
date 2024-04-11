package pie.ilikepiefoo.compat.jade.impl;

import org.jetbrains.annotations.Nullable;
import pie.ilikepiefoo.compat.jade.ITooltipWrapper;
import pie.ilikepiefoo.compat.jade.builder.EntityComponentProviderBuilder;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElement;

public class CustomEntityComponentProvider extends CustomToggleableProviderBuilder<EntityComponentProviderBuilder> implements IEntityComponentProvider {
    public CustomEntityComponentProvider(EntityComponentProviderBuilder builder) {
        super(builder);
    }

    @Override
    public @Nullable IElement getIcon(
            EntityAccessor accessor,
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
            EntityAccessor blockAccessor,
            IPluginConfig iPluginConfig
    ) {
        if (builder.getTooltipRetriever() == null) {
            return;
        }
        builder.getTooltipRetriever().appendTooltip(ITooltipWrapper.of(iTooltip), blockAccessor, iPluginConfig);
    }
}
