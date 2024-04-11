package pie.ilikepiefoo.compat.jade.builder;

import snownee.jade.api.view.ClientViewGroup;
import snownee.jade.api.view.ViewGroup;

import java.util.List;

public class ViewGroupBuilder<OUT> {
    private List<OUT> elements;

    public ViewGroupBuilder() {
        this.elements = null;
    }

    public List<OUT> getElements() {
        return this.elements;
    }

    public ViewGroupBuilder<OUT> setElements(List<OUT> elements) {
        this.elements = elements;
        return this;
    }

    public ViewGroupBuilder<OUT> add(OUT element) {
        return addElement(element);
    }

    public ViewGroupBuilder<OUT> addElement(OUT element) {
        this.elements.add(element);
        return this;
    }

    public ViewGroupBuilder<OUT> addAll(List<OUT> elements) {
        return addElements(elements);
    }

    public ViewGroupBuilder<OUT> addElements(List<OUT> elements) {
        this.elements.addAll(elements);
        return this;
    }

    public ViewGroupBuilder<OUT> clear() {
        this.elements.clear();
        return this;
    }

    public ViewGroup<OUT> buildCommon() {
        if (this.elements == null) {
            return null;
        }
        return new ViewGroup<>(this.elements);
    }

    public ClientViewGroup<OUT> buildClient() {
        if (this.elements == null) {
            return null;
        }
        return new ClientViewGroup<>(this.elements);
    }
}
