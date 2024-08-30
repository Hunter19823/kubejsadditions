package pie.ilikepiefoo.wrappergen.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public class MethodBuilder implements JavaFileOutput {
    protected final List<String> args;
    protected final List<String> body;
    protected final List<String> modifiers;
    protected final List<String> exceptions;
    protected final List<String> annotations;
    protected final List<String> generics;
    protected String name;
    protected String returnType;
    protected String accessModifier;
    protected boolean includeMethodBody;

    public MethodBuilder() {
        this.name = "method";
        this.returnType = "void";
        this.args = new ArrayList<>();
        this.body = new ArrayList<>();
        this.accessModifier = "public";
        this.modifiers = new ArrayList<>();
        this.exceptions = new ArrayList<>();
        this.annotations = new ArrayList<>();
        this.generics = new ArrayList<>();
        this.includeMethodBody = true;
    }

    public MethodBuilder addArg(String arg) {
        this.args.add(arg);
        return this;
    }

    public MethodBuilder addArgs(String... args) {
        this.args.addAll(Arrays.asList(args));
        return this;
    }

    public MethodBuilder addModifier(String modifier) {
        this.modifiers.add(modifier);
        return this;
    }

    public MethodBuilder addModifiers(String... modifiers) {
        this.modifiers.addAll(Arrays.asList(modifiers));
        return this;
    }

    public MethodBuilder addBody(String line) {
        this.includeMethodBody = true;
        this.body.add(line);
        return this;
    }

    public MethodBuilder addBody(String... lines) {
        this.includeMethodBody = true;
        this.body.addAll(Arrays.asList(lines));
        return this;
    }

    public MethodBuilder addException(String exception) {
        this.exceptions.add(exception);
        return this;
    }

    public MethodBuilder addExceptions(String... exceptions) {
        this.exceptions.addAll(Arrays.asList(exceptions));
        return this;
    }

    public MethodBuilder addAnnotation(String annotation) {
        this.annotations.add(annotation);
        return this;
    }

    public MethodBuilder addAnnotations(String... annotations) {
        this.annotations.addAll(Arrays.asList(annotations));
        return this;
    }

    public MethodBuilder addGenerics(String... generics) {
        this.generics.addAll(Arrays.asList(generics));
        return this;
    }

    public String getName() {
        return name;
    }

    public MethodBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public String getReturnType() {
        return returnType;
    }

    public MethodBuilder setReturnType(String returnType) {
        this.returnType = returnType;
        return this;
    }

    public List<String> getArgs() {
        return args;
    }

    public List<String> getBody() {
        return body;
    }

    public MethodBuilder setBody(String... body) {
        this.body.clear();
        this.body.addAll(Arrays.asList(body));
        return this;
    }

    public String getAccessModifier() {
        return accessModifier;
    }

    public MethodBuilder setAccessModifier(String accessModifier) {
        this.accessModifier = accessModifier;
        return this;
    }

    public List<String> getModifiers() {
        return modifiers;
    }

    public List<String> getExceptions() {
        return exceptions;
    }

    public List<String> getAnnotations() {
        return annotations;
    }

    public List<String> getGenerics() {
        return generics;
    }

    public boolean isIncludeMethodBody() {
        return includeMethodBody;
    }

    public MethodBuilder setIncludeMethodBody(boolean includeMethodBody) {
        this.includeMethodBody = includeMethodBody;
        return this;
    }

    public String toJavaFile(int indentLevel) {
        String indent = INDENTATION_STRING.repeat(indentLevel);
        StringBuilder method = new StringBuilder();
        StringJoiner joiner;
        for (String annotation : annotations) {
            method.append(indent).append(annotation).append('\n');
        }
        method.append(indent);
        if (!accessModifier.isBlank()) {
            method.append(accessModifier).append(" ");
        }
        for (String modifier : modifiers) {
            method.append(modifier).append(" ");
        }
        if (!generics.isEmpty()) {
            joiner = new StringJoiner(", ", "<", ">");
            for (String generic : generics) {
                joiner.add(generic);
            }
            method.append(joiner);
            method.append(" ");
        }
        method.append(returnType).append(" ");

        method.append(name);
        joiner = new StringJoiner(", ", "(", ")");
        for (String arg : args) {
            joiner.add(arg);
        }
        method.append(joiner);
        if (!exceptions.isEmpty()) {
            joiner = new StringJoiner(", ");
            method.append(" throws ");
            for (String exception : exceptions) {
                method.append(exception);
            }
            method.append(joiner);
        }
        if (!includeMethodBody) {
            method.append(";");
            return method.toString();
        }
        method.append(" {\n");
        for (String line : body) {
            method.append(indent).append(INDENTATION_STRING).append(line).append('\n');
        }
        method.append(indent).append("}");
        return method.toString();
    }


}
