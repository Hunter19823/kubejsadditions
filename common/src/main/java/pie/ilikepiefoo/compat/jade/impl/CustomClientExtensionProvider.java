package pie.ilikepiefoo.compat.jade.impl;

import dev.latvian.mods.kubejs.util.ConsoleJS;
import pie.ilikepiefoo.compat.jade.builder.ClientExtensionProviderBuilder;
import pie.ilikepiefoo.compat.jade.builder.ViewGroupBuilder;
import pie.ilikepiefoo.compat.jade.builder.callback.GetClientGroupsCallbackJS;
import snownee.jade.api.Accessor;
import snownee.jade.api.view.ClientViewGroup;
import snownee.jade.api.view.IClientExtensionProvider;
import snownee.jade.api.view.ViewGroup;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CustomClientExtensionProvider<IN, OUT> extends CustomJadeProvider<ClientExtensionProviderBuilder<IN, OUT>> implements IClientExtensionProvider<IN, OUT> {
    public CustomClientExtensionProvider(ClientExtensionProviderBuilder<IN, OUT> builder) {
        super(builder);
    }

    @Override
    public List<ClientViewGroup<OUT>> getClientGroups(Accessor<?> accessor, List<ViewGroup<IN>> groups) {
        GetClientGroupsCallbackJS<IN, OUT> callback = new GetClientGroupsCallbackJS<>(
                accessor,
                groups
        );
        try {
            builder.getCallback().accept(callback);
        } catch (Throwable throwable) {
            ConsoleJS.CLIENT.error("Error while executing client extension provider callback", throwable);
            return null;
        }
        if (callback.getResultingGroups() == null || callback.getResultingGroups().isEmpty()) {
            return null;
        }
        return callback.getResultingGroups()
                .stream()
                .map(ViewGroupBuilder::buildClient)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
