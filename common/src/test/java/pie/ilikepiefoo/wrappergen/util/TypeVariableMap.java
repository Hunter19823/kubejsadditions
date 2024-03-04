package pie.ilikepiefoo.wrappergen.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class TypeVariableMap {
    private final Map<TypeVariable<?>, Type> typeVariableMap;

    public TypeVariableMap() {
        this.typeVariableMap = Map.of();
    }

    public TypeVariableMap(Class<?> subject) {
        this.typeVariableMap = createTypeVariableMap(subject);
    }

    public static Map<TypeVariable<?>, Type> createTypeVariableMap(Class<?> subject) {
        HashMap<TypeVariable<?>, Type> typeVariableMap = new HashMap<>();
        Stack<Class<?>> unprocessed = new Stack<>();
        unprocessed.push(subject);
        while (!unprocessed.isEmpty()) {
            Class<?> current = unprocessed.pop();
            if (current.getSuperclass() != null) {
                unprocessed.push(current.getSuperclass());
                if (current.getGenericSuperclass() instanceof ParameterizedType parameterizedType) {
                    remapTypeVariables(typeVariableMap, parameterizedType);
                }
            }
            unprocessed.addAll(Arrays.asList(current.getInterfaces()));
            for (Type type : current.getGenericInterfaces()) {
                if (!(type instanceof ParameterizedType parameterizedType)) {
                    continue;
                }
                remapTypeVariables(typeVariableMap, parameterizedType);
            }
            unprocessed.addAll(Arrays.asList(current.getInterfaces()));
        }
        return typeVariableMap;
    }

    private static void remapTypeVariables(HashMap<TypeVariable<?>, Type> typeVariableMap, ParameterizedType parameterizedType) {
        TypeVariable<?>[] typeVariables = ((Class<?>) parameterizedType.getRawType()).getTypeParameters();
        Type[] actualTypes = parameterizedType.getActualTypeArguments();
        TYPE_LOOP:
        for (int i = 0; i < typeVariables.length; i++) {
            if (typeVariableMap.containsKey(typeVariables[i])) {
                continue TYPE_LOOP;
            }
            if (!(actualTypes[i] instanceof TypeVariable<?> actualType)) {
                typeVariableMap.put(typeVariables[i], actualTypes[i]);
                continue TYPE_LOOP;
            }
            while (typeVariableMap.containsKey(actualType)) {
                var tempType = typeVariableMap.get(actualType);
                if (tempType instanceof TypeVariable<?> tempTypeVariable) {
                    actualType = tempTypeVariable;
                } else {
                    typeVariableMap.put(typeVariables[i], tempType);
                    continue TYPE_LOOP;
                }
            }
            typeVariableMap.put(typeVariables[i], actualType);
        }
    }

    public boolean isTypeVariable(TypeVariable<?> typeVariable) {
        return get(typeVariable) instanceof TypeVariable<?>;
    }

    public Type get(TypeVariable<?> typeVariable) {
        return typeVariableMap.getOrDefault(typeVariable, typeVariable);
    }

}
