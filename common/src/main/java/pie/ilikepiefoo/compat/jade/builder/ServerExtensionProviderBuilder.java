package pie.ilikepiefoo.compat.jade.builder;

import net.minecraft.resources.ResourceLocation;
import pie.ilikepiefoo.compat.jade.builder.callback.GetServerGroupsCallbackJS;

import java.util.function.Consumer;

public class ServerExtensionProviderBuilder<IN, OUT> extends JadeProviderBuilder {

    public Consumer<GetServerGroupsCallbackJS<IN, OUT>> callback;

    public ServerExtensionProviderBuilder(ResourceLocation uniqueIdentifier) {
        super(uniqueIdentifier);
    }

    public static <IN, OUT> void doNothing(GetServerGroupsCallbackJS<IN, OUT> callback) {
        // Do nothing
    }

    public Consumer<GetServerGroupsCallbackJS<IN, OUT>> getCallback() {
        return this.callback;
    }

    public ServerExtensionProviderBuilder<IN, OUT> setCallback(Consumer<GetServerGroupsCallbackJS<IN, OUT>> callback) {
        this.callback = callback;
        return this;
    }

    public ServerExtensionProviderBuilder<IN, OUT> callback(Consumer<GetServerGroupsCallbackJS<IN, OUT>> callback) {
        return setCallback(callback);
    }

    public ServerExtensionProviderBuilder<IN, OUT> groupCallback(Consumer<GetServerGroupsCallbackJS<IN, OUT>> callback) {
        return setCallback(callback);
    }

    public ServerExtensionProviderBuilder<IN, OUT> onGroups(Consumer<GetServerGroupsCallbackJS<IN, OUT>> callback) {
        return setCallback(callback);
    }
}
