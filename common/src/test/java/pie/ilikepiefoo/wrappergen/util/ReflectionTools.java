package pie.ilikepiefoo.wrappergen.util;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.StringJoiner;

public class ReflectionTools {

    public static String getImportName(Class<?> type) {
        while (type.isArray()) {
            type = type.getComponentType();
        }
        if (type.isPrimitive() ||
                type == Object.class ||
                type == String.class ||
                type == Class.class) {
            return null;
        }
        return type.getCanonicalName();
    }

    public static String getParameterArgument(Parameter parameter, TypeVariableMap typeVariableMap) {
        String typeName = ReflectionTools.getGenericName(parameter.getParameterizedType(), typeVariableMap);
        if (parameter.isVarArgs()) {
            // Remove the array brackets as we are using varargs.
            typeName = typeName.substring(0, typeName.length() - 2);
            typeName += "...";
        }
        return typeName +
                " " +
                parameter.getName();
    }

    public static String getGenericDefinition(Type type, TypeVariableMap typeVariableMap) {
        return getGenericDefinition(type, typeVariableMap, false);
    }

    public static String getGenericName(Type type, TypeVariableMap typeVariableMap) {
        return getGenericDefinition(type, typeVariableMap, true);
    }

    private static String getGenericDefinition(Type type, TypeVariableMap typeVariableMap, boolean isDefiningTypeVariable) {
        if (type instanceof TypeVariable<?> variable) {
            type = typeVariableMap.get(variable);
        }
        if (type instanceof Class<?> clazz) {
            return clazz.getCanonicalName();
        }
        if (type instanceof TypeVariable<?> variable) {
            if (isDefiningTypeVariable) {
                return variable.getName();
            }
            var nonObjectBounds = Arrays.stream(variable.getBounds()).filter(b -> b != Object.class).toArray(Type[]::new);
            if (nonObjectBounds.length == 0) {
                return variable.getName();
            }
            StringJoiner joiner = new StringJoiner(" & ", " extends ", "");
            for (Type bound : nonObjectBounds) {
                joiner.add(getGenericDefinition(bound, typeVariableMap, true));
            }
            return variable.getName() + joiner;
        }
        if (type instanceof WildcardType wildcardType) {
            if (wildcardType.getLowerBounds().length > 0 && wildcardType.getLowerBounds()[0] != Object.class) {
                return "? super " + getGenericDefinition(wildcardType.getLowerBounds()[0], typeVariableMap, isDefiningTypeVariable);
            }
            if (wildcardType.getUpperBounds().length > 0 && wildcardType.getUpperBounds()[0] != Object.class) {
                return "? extends " + getGenericDefinition(wildcardType.getUpperBounds()[0], typeVariableMap, isDefiningTypeVariable);
            }
            return "?";
        }
        if (type instanceof GenericArrayType genericArrayType) {
            return getGenericDefinition(genericArrayType.getGenericComponentType(), typeVariableMap, isDefiningTypeVariable) + "[]";
        }
        if (type instanceof ParameterizedType parameterizedType) {
            StringJoiner joiner = new StringJoiner(", ", "<", ">");
            for (var arg : parameterizedType.getActualTypeArguments()) {
                joiner.add(getGenericDefinition(arg, typeVariableMap, isDefiningTypeVariable));
            }
            return getGenericDefinition(parameterizedType.getRawType(), typeVariableMap, isDefiningTypeVariable) + joiner;
        }
        return type.getTypeName();
    }

    public static Set<Type> getDependencies(Type type) {
        Set<Type> dependencies = new HashSet<>();
        Stack<Type> unprocessed = new Stack<>();
        unprocessed.push(type);
        while (!unprocessed.isEmpty()) {
            Type current = unprocessed.pop();
            if (dependencies.contains(current)) {
                continue;
            }
            dependencies.add(current);
            if (current instanceof Class<?> clazz) {
                if (clazz.isArray()) {
                    unprocessed.push(clazz.getComponentType());
                }
                for (var typeParam : clazz.getTypeParameters()) {
                    unprocessed.addAll(Arrays.asList(typeParam.getBounds()));
                }
            } else if (current instanceof TypeVariable<?> variable) {
                unprocessed.addAll(Arrays.asList(variable.getBounds()));
            } else if (current instanceof ParameterizedType parameterizedType) {
                unprocessed.addAll(Arrays.asList(parameterizedType.getActualTypeArguments()));
                unprocessed.push(parameterizedType.getRawType());
                unprocessed.push(parameterizedType.getOwnerType());
            } else if (current instanceof WildcardType wildcardType) {
                unprocessed.addAll(Arrays.asList(wildcardType.getLowerBounds()));
                unprocessed.addAll(Arrays.asList(wildcardType.getUpperBounds()));
            } else if (current instanceof GenericArrayType genericArrayType) {
                unprocessed.push(genericArrayType.getGenericComponentType());
            }
        }
        return dependencies;
    }

}
