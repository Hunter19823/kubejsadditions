package pie.ilikepiefoo.compat.jade.builder.callback;

import pie.ilikepiefoo.compat.jade.builder.ViewGroupBuilder;
import snownee.jade.api.Accessor;
import snownee.jade.api.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class GetClientGroupsCallbackJS<IN, OUT> {
    public final Accessor<?> accessor;
    public final List<ViewGroup<IN>> groups;
    private final List<ViewGroupBuilder<OUT>> resultingGroups;

    public GetClientGroupsCallbackJS(Accessor<?> accessor, List<ViewGroup<IN>> groups) {
        this.accessor = accessor;
        this.groups = groups;
        this.resultingGroups = new ArrayList<>();
    }

    public Accessor<?> getAccessor() {
        return this.accessor;
    }

    public List<ViewGroup<IN>> getGroups() {
        return this.groups;
    }

    public GetClientGroupsCallbackJS<IN, OUT> addGroup(ViewGroupBuilder<OUT> group) {
        this.resultingGroups.add(group);
        return this;
    }

    public GetClientGroupsCallbackJS<IN, OUT> addGroup(ViewGroup<OUT> group) {
        this.resultingGroups.add(new ViewGroupBuilder<OUT>().addAll(group.views));
        return this;
    }

    public GetClientGroupsCallbackJS<IN, OUT> addGroup(List<OUT> group) {
        this.resultingGroups.add(new ViewGroupBuilder<OUT>().addAll(group));
        return this;
    }

    public GetClientGroupsCallbackJS<IN, OUT> addGroup(Consumer<ViewGroupBuilder<OUT>> groupBuilderConsumer) {
        ViewGroupBuilder<OUT> group = new ViewGroupBuilder<>();
        groupBuilderConsumer.accept(group);
        this.resultingGroups.add(group);
        return this;
    }

    public GetClientGroupsCallbackJS<IN, OUT> addGroups(List<ViewGroup<OUT>> groups) {
        if (groups == null || groups.isEmpty()) {
            return this;
        }
        for (ViewGroup<OUT> group : groups) {
            this.resultingGroups.add(new ViewGroupBuilder<OUT>().addAll(group.views));
        }
        return this;
    }

    public GetClientGroupsCallbackJS<IN, OUT> clearGroups() {
        this.resultingGroups.clear();
        return this;
    }

    public List<ViewGroupBuilder<OUT>> getResultingGroups() {
        return this.resultingGroups;
    }
}
