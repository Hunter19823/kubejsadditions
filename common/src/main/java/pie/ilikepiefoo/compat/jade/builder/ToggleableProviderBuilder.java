package pie.ilikepiefoo.compat.jade.builder;

import net.minecraft.resources.ResourceLocation;

public class ToggleableProviderBuilder extends JadeProviderBuilder {
    private boolean isRequired;
    private boolean enabledByDefault;

    public ToggleableProviderBuilder(ResourceLocation uniqueIdentifier) {
        super(uniqueIdentifier);
        this.isRequired = false;
        this.enabledByDefault = true;
    }

    public ToggleableProviderBuilder required() {
        return setRequired(true);
    }

    public ToggleableProviderBuilder enabledByDefault() {
        return setEnabledByDefault(true);
    }

    public boolean isRequired() {
        return this.isRequired;
    }

    public ToggleableProviderBuilder setRequired(boolean isRequired) {
        this.isRequired = isRequired;
        return this;
    }

    public boolean isEnabledByDefault() {
        return this.enabledByDefault;
    }

    public ToggleableProviderBuilder setEnabledByDefault(boolean enabledByDefault) {
        this.enabledByDefault = enabledByDefault;
        return this;
    }
}
