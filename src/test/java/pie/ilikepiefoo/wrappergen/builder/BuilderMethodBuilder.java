package pie.ilikepiefoo.wrappergen.builder;

import java.util.StringJoiner;

public class BuilderMethodBuilder extends MethodBuilder {

    @Override
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
