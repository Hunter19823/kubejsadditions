package pie.ilikepiefoo.wrappergen.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class ConstructorBuilder implements JavaFileOutput {

    private final List<String> annotations;
    private final List<String> generics;
    private final List<String> parameters;
    private final List<String> body;
    private String accessModifier;
    private String name;

    public ConstructorBuilder() {
        this.accessModifier = "public";
        this.annotations = new ArrayList<>();
        this.generics = new ArrayList<>();
        this.name = "Constructor";
        this.parameters = new ArrayList<>();
        this.body = new ArrayList<>();
    }

    public ConstructorBuilder addAnnotations(String... annotations) {
        this.annotations.addAll(List.of(annotations));
        return this;
    }

    public ConstructorBuilder addGenerics(String... generics) {
        this.generics.addAll(List.of(generics));
        return this;
    }

    public ConstructorBuilder addParameters(String... parameters) {
        this.parameters.addAll(List.of(parameters));
        return this;
    }

    public ConstructorBuilder addBody(String... body) {
        this.body.addAll(List.of(body));
        return this;
    }

    public List<String> getAnnotations() {
        return annotations;
    }

    public List<String> getGenerics() {
        return generics;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public List<String> getBody() {
        return body;
    }

    public String getAccessModifier() {
        return accessModifier;
    }

    public ConstructorBuilder setAccessModifier(String accessModifier) {
        this.accessModifier = accessModifier;
        return this;
    }

    public String getName() {
        return name;
    }

    public ConstructorBuilder setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toJavaFile(int indentLevel) {
        String indent = " ".repeat(indentLevel * 4);
        StringJoiner joiner;
        StringBuilder sb = new StringBuilder();
        for (String annotation : this.annotations) {
            sb.append(indent).append(annotation).append("\n");
        }
        sb.append(indent);
        if (!this.accessModifier.isEmpty()) {
            sb.append(this.accessModifier).append(" ");
        }
        sb.append(this.name);
        joiner = new StringJoiner(", ", "(", ")");
        for (String parameter : this.parameters) {
            joiner.add(parameter);
        }
        sb.append(joiner);
        sb.append(" {\n");
        for (String line : this.body) {
            sb.append(indent).append(INDENTATION_STRING).append(line).append("\n");
        }
        sb.append(indent).append("}\n");
        return sb.toString();
    }

}
