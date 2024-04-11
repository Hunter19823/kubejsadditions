package pie.ilikepiefoo.compat.jade.impl;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.Nullable;
import pie.ilikepiefoo.compat.jade.builder.ServerExtensionProviderBuilder;
import pie.ilikepiefoo.compat.jade.builder.ViewGroupBuilder;
import pie.ilikepiefoo.compat.jade.builder.callback.GetServerGroupsCallbackJS;
import snownee.jade.api.view.IServerExtensionProvider;
import snownee.jade.api.view.ViewGroup;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CustomServerExtensionProvider<IN, OUT> extends CustomJadeProvider<ServerExtensionProviderBuilder<IN, OUT>> implements IServerExtensionProvider<IN, OUT> {
    public CustomServerExtensionProvider(ServerExtensionProviderBuilder<IN, OUT> builder) {
        super(builder);
    }

    @Override
    public @Nullable List<ViewGroup<OUT>> getGroups(
            ServerPlayer serverPlayer,
            ServerLevel serverLevel,
            IN in,
            boolean b
    ) {
        GetServerGroupsCallbackJS<IN, OUT> callback = new GetServerGroupsCallbackJS<>(
                serverPlayer,
                serverLevel,
                in,
                b
        );
        builder.getCallback().accept(callback);
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
