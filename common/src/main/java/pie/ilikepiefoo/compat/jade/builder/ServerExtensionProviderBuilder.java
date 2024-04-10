package pie.ilikepiefoo.compat.jade.builder;

import net.minecraft.resources.ResourceLocation;
import pie.ilikepiefoo.compat.jade.builder.callback.GetGroupsCallbackJS;

import java.util.function.Consumer;

public class ServerExtensionProviderBuilder<IN, OUT> extends JadeProviderBuilder {

    public Consumer<GetGroupsCallbackJS<IN, OUT>> callback;

    public ServerExtensionProviderBuilder(ResourceLocation uniqueIdentifier) {
        super(uniqueIdentifier);
    }

    public static <IN, OUT> void doNothing(GetGroupsCallbackJS<IN, OUT> callback) {
        // Do nothing
    }

    public Consumer<GetGroupsCallbackJS<IN, OUT>> getCallback() {
        return this.callback;
    }

    public ServerExtensionProviderBuilder<IN, OUT> setCallback(Consumer<GetGroupsCallbackJS<IN, OUT>> callback) {
        this.callback = callback;
        return this;
    }
}
