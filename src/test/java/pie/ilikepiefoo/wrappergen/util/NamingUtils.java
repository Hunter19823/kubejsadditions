package pie.ilikepiefoo.wrappergen.util;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.StringJoiner;

public class NamingUtils {
    public static String getMethodHandlerName(Method method) {
        return method.getName().substring(0, 1).toUpperCase() +
                method.getName().substring(1) +
                getUniqueName(method.getParameters()) +
                "Handler";
    }

    public static String getUniqueName(Parameter[] parameters) {
        StringBuilder name = new StringBuilder();
        for (var parameter : parameters) {
            name.append(CamelCase(parameter.getType().getSimpleName()));
        }
        return name.toString().replaceAll("[^a-zA-Z0-9]", "");
    }

    public static String CamelCase(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    public static String SnakeCase(String name) {
        return name.substring(0, 1).toLowerCase() + name.substring(1);
    }

    public static String getHandlerMethodName(Method method) {
        return "on" +
                CamelCase(method.getName());
    }

    public static String getArgumentCall(List<String> args) {
        StringJoiner joiner = new StringJoiner(", ", "(", ")");
        args.forEach((arg) -> joiner.add(arg.substring(arg.lastIndexOf(" ") + 1)));
        return joiner.toString();
    }

    public static String getFieldName(String handlerName) {
        // Return method handler name with first character lowercase.
        return handlerName.substring(0, 1).toLowerCase() + handlerName.substring(1);
    }

    public static String getShortName(Class<?> clazz) {
        var name = clazz.getCanonicalName();
        if (name.contains(clazz.getPackageName())) {
            return name.substring(clazz.getPackageName().length() + 1);
        }
        return clazz.getSimpleName();
    }
}
