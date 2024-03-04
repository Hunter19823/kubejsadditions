package pie.ilikepiefoo.wrappergen.presets;

import pie.ilikepiefoo.wrappergen.builder.ClassBuilder;
import pie.ilikepiefoo.wrappergen.builder.MethodBuilder;
import pie.ilikepiefoo.wrappergen.util.NamingUtils;
import pie.ilikepiefoo.wrappergen.util.ReflectionTools;
import pie.ilikepiefoo.wrappergen.util.TypeVariableMap;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

import static pie.ilikepiefoo.wrappergen.util.GenerationUtils.createMethodBuilderFromMethod;

public class FunctionalInterfaceWrapper extends ClassBuilder {
    protected Method method;
    protected TypeVariableMap typeVariableMap;
    protected MethodBuilder methodBuilder;
    protected List<String> genericNames = new ArrayList<>();

    public FunctionalInterfaceWrapper(Method method, TypeVariableMap typeVariableMap) {
        this.method = method;
        this.typeVariableMap = typeVariableMap;
        // CamelCase the method name and add Handler to the end.
        this.setImports("");
        this.setName(NamingUtils.getMethodHandlerName(method));
        this.addAnnotations("@FunctionalInterface");
        this.setAccessModifier("public");
        this.setStructureType("interface");

        // Add the method to the interface.
        this.methodBuilder = createMethodBuilderFromMethod(method, typeVariableMap)
                .setAccessModifier("")
                .setName(NamingUtils.getHandlerMethodName(method));
        Set<Type> METHOD_TYPE_PARAMETERS = new HashSet<>(Arrays.asList(method.getTypeParameters()));
        List<TypeVariable<?>> genericTypes = new ArrayList<>();

        METHOD_TYPE_PARAMETERS.addAll(Arrays.asList(method.getTypeParameters()));
        for (var parameterType : method.getGenericParameterTypes()) {
            // Only add generics to class if the method does not contain that generic parameter.
            ReflectionTools
                    .getDependencies(parameterType)
                    .stream()
                    .filter((type) -> type instanceof TypeVariable<?>)
                    .map((type) -> (TypeVariable<?>) type)
                    .filter((type) -> !METHOD_TYPE_PARAMETERS.contains(type))
                    .filter(typeVariableMap::isTypeVariable)
                    .forEachOrdered(genericTypes::add);
        }
        ReflectionTools
                .getDependencies(method.getGenericReturnType())
                .stream()
                .filter((type) -> type instanceof TypeVariable<?>)
                .map((type) -> (TypeVariable<?>) type)
                .filter((type) -> !METHOD_TYPE_PARAMETERS.contains(type))
                .filter(typeVariableMap::isTypeVariable)
                .forEachOrdered(genericTypes::add);
        Set<TypeVariable<?>> seen = new HashSet<>();
        for (TypeVariable<?> typeVariable : genericTypes) {
            // filter out duplicates.
            if (seen.contains(typeVariable)) {
                continue;
            }
            seen.add(typeVariable);
            genericNames.add(ReflectionTools.getGenericName(typeVariable, typeVariableMap));
            this.addGenerics(ReflectionTools.getGenericDefinition(typeVariable, typeVariableMap));
        }

        this.addBody(methodBuilder.toJavaFile(2));
    }

    public MethodBuilder getMethodBuilder() {
        return methodBuilder;
    }

    public String getCanonicalName() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.name);
        if (this.genericNames.isEmpty()) {
            return sb.toString();
        }
        StringJoiner joiner = new StringJoiner(", ", "<", ">");
        for (String generic : this.genericNames) {
            joiner.add(generic);
        }
        sb.append(joiner);
        return sb.toString();
    }
}
