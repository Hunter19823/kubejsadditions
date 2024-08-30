package pie.ilikepiefoo.wrappergen.builder;

import java.util.ArrayList;
import java.util.List;

public class FieldBuilder implements JavaFileOutput {
    private final List<String> modifiers;
    private final List<String> annotations;
    private final List<String> generics;
    private String name;
    private String type;
    private String accessModifier;
    private String value;

    public FieldBuilder() {
        this.name = "field";
        this.type = "Object";
        this.accessModifier = "public";
        this.modifiers = new ArrayList<>();
        this.annotations = new ArrayList<>();
        this.generics = new ArrayList<>();
        this.value = "";
    }

    public FieldBuilder addModifiers(String... modifiers) {
        this.modifiers.addAll(List.of(modifiers));
        return this;
    }

    public FieldBuilder addAnnotations(String... annotations) {
        this.annotations.addAll(List.of(annotations));
        return this;
    }

    public FieldBuilder addModifier(String modifier) {
        this.modifiers.add(modifier);
        return this;
    }

    public FieldBuilder addAnnotation(String annotation) {
        this.annotations.add(annotation);
        return this;
    }

    public FieldBuilder addGenerics(String... generics) {
        this.generics.addAll(List.of(generics));
        return this;
    }

    public String getName() {
        return name;
    }

    public FieldBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public String getType() {
        return type;
    }

    public FieldBuilder setType(String type) {
        this.type = type;
        return this;
    }

    public String getAccessModifier() {
        return accessModifier;
    }

    public FieldBuilder setAccessModifier(String accessModifier) {
        this.accessModifier = accessModifier;
        return this;
    }

    public List<String> getModifiers() {
        return modifiers;
    }

    public List<String> getAnnotations() {
        return annotations;
    }

    public List<String> getGenerics() {
        return generics;
    }

    public String getValue() {
        return value;
    }

    public FieldBuilder setValue(String value) {
        this.value = value;
        return this;
    }

    public String toJavaFile(int indent) {
        StringBuilder sb = new StringBuilder();
        String indentStr = INDENTATION_STRING.repeat(indent);
        for (String annotation : annotations) {
            sb.append(indentStr).append(annotation).append("\n");
        }
        sb.append(indentStr);
        if (!accessModifier.isBlank()) {
            sb.append(accessModifier).append(" ");
        }
        for (String modifier : modifiers) {
            sb.append(modifier).append(" ");
        }
        sb.append(type).append(" ").append(name);
        for (String generic : generics) {
            sb.append("<").append(generic).append(">");
        }
        if (!value.isEmpty()) {
            sb.append(" = ").append(value);
        }
        sb.append(";");
        return sb.toString();
    }
}
