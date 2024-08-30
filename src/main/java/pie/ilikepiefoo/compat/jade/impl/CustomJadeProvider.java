package pie.ilikepiefoo.compat.jade.impl;

import net.minecraft.resources.ResourceLocation;
import pie.ilikepiefoo.compat.jade.builder.JadeProviderBuilder;
import snownee.jade.api.IJadeProvider;

public class CustomJadeProvider<T extends JadeProviderBuilder> implements IJadeProvider {
    protected final T builder;

    public CustomJadeProvider(T builder) {
        this.builder = builder;
    }

    @Override
    public ResourceLocation getUid() {
        return builder.getUniqueIdentifier();
    }

    @Override
    public int getDefaultPriority() {
        return builder.getDefaultPriority();
    }
}
