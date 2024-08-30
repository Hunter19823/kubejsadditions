package pie.ilikepiefoo.wrappergen.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class ClassBuilder implements JavaFileOutput {
    protected final List<String> annotations;
    protected final List<String> modifiers;
    protected final List<String> generics;
    protected final List<String> interfaces;
    protected final List<String> body;
    protected String imports;
    protected String accessModifier;
    protected String structureType;
    protected String name;
    protected String superClass;

    public ClassBuilder() {
        this.imports = new ImportBuilder().toJavaFile(0);
        this.structureType = "class";
        this.name = "Example";
        this.accessModifier = "public";
        this.modifiers = new ArrayList<>();
        this.annotations = new ArrayList<>();
        this.generics = new ArrayList<>();
        this.superClass = "";
        this.interfaces = new ArrayList<>();
        this.body = new ArrayList<>();
    }

    public String getImports() {
        return imports;
    }

    public ClassBuilder setImports(String imports) {
        this.imports = imports;
        return this;
    }

    public List<String> getAnnotations() {
        return annotations;
    }

    public String getAccessModifier() {
        return accessModifier;
    }

    public ClassBuilder setAccessModifier(String accessModifier) {
        this.accessModifier = accessModifier;
        return this;
    }

    public List<String> getModifiers() {
        return modifiers;
    }

    public String getStructureType() {
        return structureType;
    }

    public ClassBuilder setStructureType(String structureType) {
        this.structureType = structureType;
        return this;
    }

    public String getName() {
        return name;
    }

    public ClassBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public List<String> getGenerics() {
        return generics;
    }

    public String getSuperClass() {
        return superClass;
    }

    public ClassBuilder setSuperClass(String superClass) {
        this.superClass = superClass;
        return this;
    }

    public List<String> getInterfaces() {
        return interfaces;
    }

    public List<String> getBody() {
        return body;
    }

    public ClassBuilder setBody(String... body) {
        this.body.clear();
        this.body.addAll(List.of(body));
        return this;
    }

    public ClassBuilder setBody(List<String> body) {
        this.body.clear();
        this.body.addAll(body);
        return this;
    }

    public ClassBuilder addModifiers(String... modifiers) {
        this.modifiers.addAll(List.of(modifiers));
        return this;
    }

    public ClassBuilder addAnnotations(String... annotations) {
        this.annotations.addAll(List.of(annotations));
        return this;
    }

    public ClassBuilder addGenerics(String... generics) {
        for (String generic : generics) {
            if (!this.generics.contains(generic)) {
                this.generics.add(generic);
            }
        }
        return this;
    }

    public ClassBuilder addInterfaces(String... interfaces) {
        this.interfaces.addAll(List.of(interfaces));
        return this;
    }

    public ClassBuilder addBody(String... body) {
        this.body.addAll(List.of(body));
        return this;
    }

    @Override
    public String toJavaFile(int indentLevel) {
        String indent = INDENTATION_STRING.repeat(indentLevel);
        StringBuilder sb = new StringBuilder();
        StringJoiner joiner;
        sb.append(this.imports);
        for (String annotation : this.annotations) {
            sb.append(indent).append(annotation).append('\n');
        }
        if (!this.accessModifier.isBlank()) {
            sb.append(indent).append(this.accessModifier).append(" ");
        }
        for (String modifier : this.modifiers) {
            sb.append(modifier).append(" ");
        }
        sb.append(this.structureType).append(" ").append(this.getFormattedName());
        sb.append(" ");
        if (!this.superClass.isEmpty()) {
            sb.append("extends ").append(this.superClass).append(" ");
        }
        if (!this.interfaces.isEmpty()) {
            joiner = new StringJoiner(", ", "implements ", " ");
            for (String interfaceName : this.interfaces) {
                joiner.add(interfaceName);
            }
            sb.append(joiner);
        }
        sb.append("{\n");
        for (String line : this.body) {
            sb.append(line).append('\n');
        }
        sb.append(indent).append("}\n");
        return sb.toString();
    }

    public String getFormattedName() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.name);
        if (!this.generics.isEmpty()) {
            StringJoiner joiner = new StringJoiner(", ", "<", ">");
            for (String generic : this.generics) {
                joiner.add(generic);
            }
            sb.append(joiner);
        }
        return sb.toString();
    }

    public String getCanonicalName() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.name);
        if (!this.generics.isEmpty()) {
            StringJoiner joiner = new StringJoiner(", ", "<", ">");
            for (String generic : this.generics) {
                if (generic.contains(" ")) {
                    joiner.add(generic.substring(0, generic.indexOf(" ")));
                } else {
                    joiner.add(generic);
                }
            }
            sb.append(joiner);
        }
        return sb.toString();
    }
}
