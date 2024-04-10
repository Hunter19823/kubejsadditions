package pie.ilikepiefoo.compat.jade.builder.callback;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import pie.ilikepiefoo.compat.jade.builder.ViewGroupBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class GetGroupsCallbackJS<IN, OUT> {
    public final ServerPlayer player;
    public final ServerLevel world;
    public final IN target;
    public final boolean showDetails;
    private final List<ViewGroupBuilder<OUT>> groups;

    public GetGroupsCallbackJS(ServerPlayer player, ServerLevel world, IN target, boolean showDetails) {
        this.player = player;
        this.world = world;
        this.target = target;
        this.showDetails = showDetails;
        this.groups = new ArrayList<>();
    }

    public ServerPlayer getPlayer() {
        return this.player;
    }

    public ServerLevel getWorld() {
        return this.world;
    }

    public ServerLevel getLevel() {
        return this.world;
    }

    public IN getTarget() {
        return this.target;
    }

    public boolean showDetails() {
        return this.showDetails;
    }

    public List<ViewGroupBuilder<OUT>> getGroups() {
        return this.groups;
    }

    public GetGroupsCallbackJS<IN, OUT> addGroup(ViewGroupBuilder<OUT> group) {
        this.groups.add(group);
        return this;
    }

    public GetGroupsCallbackJS<IN, OUT> addGroup(List<OUT> group) {
        this.groups.add(new ViewGroupBuilder<OUT>().addAll(group));
        return this;
    }

    public GetGroupsCallbackJS<IN, OUT> addGroup(Consumer<ViewGroupBuilder<OUT>> groupBuilderConsumer) {
        ViewGroupBuilder<OUT> group = new ViewGroupBuilder<>();
        groupBuilderConsumer.accept(group);
        this.groups.add(group);
        return this;
    }

    public GetGroupsCallbackJS<IN, OUT> addGroups(List<ViewGroupBuilder<OUT>> groups) {
        this.groups.addAll(groups);
        return this;
    }

    public GetGroupsCallbackJS<IN, OUT> clearGroups() {
        this.groups.clear();
        return this;
    }
}
