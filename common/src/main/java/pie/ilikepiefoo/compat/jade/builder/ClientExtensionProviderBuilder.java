package pie.ilikepiefoo.compat.jade.builder;

import net.minecraft.resources.ResourceLocation;
import pie.ilikepiefoo.compat.jade.builder.callback.GetClientGroupsCallbackJS;

import java.util.function.Consumer;

public class ClientExtensionProviderBuilder<IN, OUT> extends JadeProviderBuilder {

    private Consumer<GetClientGroupsCallbackJS<IN, OUT>> callback;

    public ClientExtensionProviderBuilder(ResourceLocation uniqueIdentifier) {
        super(uniqueIdentifier);
    }

    public static <IN, OUT> void doNothing(GetClientGroupsCallbackJS<IN, OUT> callback) {
        // Do nothing
    }

    public Consumer<GetClientGroupsCallbackJS<IN, OUT>> getCallback() {
        return this.callback;
    }

    public ClientExtensionProviderBuilder<IN, OUT> setCallback(Consumer<GetClientGroupsCallbackJS<IN, OUT>> callback) {
        this.callback = callback;
        return this;
    }

    public ClientExtensionProviderBuilder<IN, OUT> callback(Consumer<GetClientGroupsCallbackJS<IN, OUT>> callback) {
        return setCallback(callback);
    }

    public ClientExtensionProviderBuilder<IN, OUT> groupCallback(Consumer<GetClientGroupsCallbackJS<IN, OUT>> callback) {
        return setCallback(callback);
    }

    public ClientExtensionProviderBuilder<IN, OUT> onGroups(Consumer<GetClientGroupsCallbackJS<IN, OUT>> callback) {
        return setCallback(callback);
    }
}
