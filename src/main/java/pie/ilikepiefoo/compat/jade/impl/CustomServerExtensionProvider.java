package pie.ilikepiefoo.compat.jade.impl;

import dev.latvian.mods.kubejs.script.ConsoleJS;
import org.jetbrains.annotations.Nullable;
import pie.ilikepiefoo.compat.jade.builder.ServerExtensionProviderBuilder;
import pie.ilikepiefoo.compat.jade.builder.ViewGroupBuilder;
import pie.ilikepiefoo.compat.jade.builder.callback.GetServerGroupsCallbackJS;
import snownee.jade.api.Accessor;
import snownee.jade.api.view.IServerExtensionProvider;
import snownee.jade.api.view.ViewGroup;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CustomServerExtensionProvider<IN, OUT> extends CustomJadeProvider<ServerExtensionProviderBuilder<IN, OUT>> implements IServerExtensionProvider<OUT> {
    public CustomServerExtensionProvider(ServerExtensionProviderBuilder<IN, OUT> builder) {
        super(builder);
    }

    @Override
    public @Nullable List<ViewGroup<OUT>> getGroups(Accessor<?> accessor) {
        GetServerGroupsCallbackJS<IN, OUT> callback = new GetServerGroupsCallbackJS<>(accessor);
        try {
            builder.getCallback().accept(callback);
        } catch (Throwable throwable) {
            ConsoleJS.STARTUP.error("Error while executing server extension provider callback", throwable);
            return null;
        }
        if (callback.getGroups() == null || callback.getGroups().isEmpty()) {
            return null;
        }
        return callback.getGroups()
                .stream()
                .map(ViewGroupBuilder::buildCommon)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
