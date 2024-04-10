package pie.ilikepiefoo.compat.jade.builder;

import net.minecraft.resources.ResourceLocation;

public class JadeProviderBuilder {
    private final ResourceLocation uniqueIdentifier;
    private int priority;

    public JadeProviderBuilder(ResourceLocation uniqueIdentifier) {
        this.uniqueIdentifier = uniqueIdentifier;
        this.priority = 0;
    }

    public ResourceLocation getUniqueIdentifier() {
        return this.uniqueIdentifier;
    }

    public int getDefaultPriority() {
        return this.priority;
    }

    public JadeProviderBuilder setDefaultPriority(int priority) {
        this.priority = priority;
        return this;
    }


}
