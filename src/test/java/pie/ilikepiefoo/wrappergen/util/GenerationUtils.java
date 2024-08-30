package pie.ilikepiefoo.wrappergen.util;

import pie.ilikepiefoo.wrappergen.builder.ConstructorBuilder;
import pie.ilikepiefoo.wrappergen.builder.MethodBuilder;
import pie.ilikepiefoo.wrappergen.presets.FunctionalInterfaceWrapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.StringJoiner;

public class GenerationUtils {

    public static FunctionalInterfaceWrapper createMethodHandler(Method method, TypeVariableMap typeVariableMap) {
        return new FunctionalInterfaceWrapper(method, typeVariableMap);
    }

    public static MethodBuilder createMethodBuilderFromMethod(Method method, TypeVariableMap typeVariableMap) {
        MethodBuilder methodBuilder = new MethodBuilder()
                .setName(method.getName())
                .setReturnType(ReflectionTools.getGenericName(method.getGenericReturnType(), typeVariableMap))
                .setAccessModifier("public")
                .setIncludeMethodBody(false);

        appendArgsAndGenerics(method, typeVariableMap, methodBuilder);

        return methodBuilder;
    }

    public static void appendArgsAndGenerics(Method method, TypeVariableMap typeVariableMap, MethodBuilder methodBuilder) {
        for (var generic : method.getTypeParameters()) {
            methodBuilder.addGenerics(ReflectionTools.getGenericDefinition(generic, typeVariableMap));
        }
        for (var parameter : method.getParameters()) {
            methodBuilder.addArg(ReflectionTools.getParameterArgument(parameter, typeVariableMap));
        }
    }

    public static ConstructorBuilder createConstructorBuilderFromConstructor(Constructor<?> constructor, TypeVariableMap typeVariableMap) {
        ConstructorBuilder constructorBuilder = new ConstructorBuilder();
        for (var parameter : constructor.getParameters()) {
            constructorBuilder.addParameters(ReflectionTools.getParameterArgument(parameter, typeVariableMap));
        }
        for (var typeParameters : constructor.getTypeParameters()) {
            constructorBuilder.addGenerics(typeParameters.toString());
        }
        // Add super call to constructor.
        StringJoiner joiner = new StringJoiner(", ", "super(", ");");
        for (var parameter : constructor.getParameters()) {
            joiner.add(parameter.getName());
        }
        constructorBuilder.addBody(joiner.toString());
        return constructorBuilder;
    }
}
