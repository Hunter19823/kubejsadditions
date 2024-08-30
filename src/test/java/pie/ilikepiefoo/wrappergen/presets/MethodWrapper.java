package pie.ilikepiefoo.wrappergen.presets;

import pie.ilikepiefoo.wrappergen.builder.BuilderMethodBuilder;
import pie.ilikepiefoo.wrappergen.builder.FieldBuilder;
import pie.ilikepiefoo.wrappergen.builder.MethodBuilder;
import pie.ilikepiefoo.wrappergen.util.GenerationUtils;
import pie.ilikepiefoo.wrappergen.util.MethodHandler;
import pie.ilikepiefoo.wrappergen.util.MethodHotSwapHandler;
import pie.ilikepiefoo.wrappergen.util.NamingUtils;
import pie.ilikepiefoo.wrappergen.util.ReflectionTools;
import pie.ilikepiefoo.wrappergen.util.TypeVariableMap;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

public class MethodWrapper {
    public final TypeVariableMap typeVariableMap;
    private final Method method;
    private final FieldBuilder field;
    private final FunctionalInterfaceWrapper wrapperType;
    private final MethodBuilder overrideMethod;
    private final Set<String> requiredImports;

    public MethodWrapper(Method method, TypeVariableMap typeVariableMap) {
        this.method = method;
        this.typeVariableMap = typeVariableMap;
        this.wrapperType = GenerationUtils.createMethodHandler(method, typeVariableMap);
        this.field = new FieldBuilder()
                .setName(NamingUtils.getFieldName(this.wrapperType.getName()))
                .setType("%s<%s>".formatted(
                        MethodHandler.class.getSimpleName(),
                        this.wrapperType.getCanonicalName()
                ))
                .setAccessModifier("public");
        this.overrideMethod = GenerationUtils.createMethodBuilderFromMethod(method, typeVariableMap)
                .addAnnotation("@Override");
        this.overrideMethod.addBody(getOverrideMethodBody(method));
        this.requiredImports = new HashSet<>();
        for (var parameter : method.getParameters()) {
            var type = ReflectionTools.getImportName(parameter.getType());
            if (type != null) {
                this.requiredImports.add(type);
            }
        }
    }

    public String getOverrideMethodBody(Method method) {
        StringBuilder sb = new StringBuilder();
        if (!method.getReturnType().equals(Void.TYPE)) {
            sb.append("return ");
        } else {
            sb.append("if");
        }
        sb.append("(this.%s != null && this.%s.getHandler() != null)".formatted(
                this.field.getName(),
                this.field.getName()
        ));
        if (!method.getReturnType().equals(Void.TYPE)) {
            sb.append(" ? this.%s.getHandler().%s%s : super.%s%s;".formatted(
                    this.field.getName(),
                    NamingUtils.getHandlerMethodName(this.method),
                    NamingUtils.getArgumentCall(this.overrideMethod.getArgs()),
                    this.method.getName(),
                    NamingUtils.getArgumentCall(this.overrideMethod.getArgs())
            ));
        } else {
            sb.append(" {");
            sb.append("this.%s.getHandler().%s%s;".formatted(
                    this.field.getName(),
                    NamingUtils.getHandlerMethodName(this.method),
                    NamingUtils.getArgumentCall(this.overrideMethod.getArgs())
            ));
            sb.append("} else {");
            sb.append("super.%s%s;".formatted(
                    this.method.getName(),
                    NamingUtils.getArgumentCall(this.overrideMethod.getArgs())
            ));
            sb.append("}");
        }
        return sb.toString();
    }

    public String getOverrideMethodBodyWithBuilder(Method method, BuilderMethodBuilder builderMethodBuilder) {
        StringBuilder sb = new StringBuilder();
        if (!method.getReturnType().equals(Void.TYPE)) {
            sb.append("return ");
        } else {
            sb.append("if");
        }
        sb.append("(this.builder != null && this.builder.%s() != null)".formatted(
                this.field.getName()
        ));
        if (!method.getReturnType().equals(Void.TYPE)) {
            sb.append(" ? this.builder.%s.%s%s : super.%s%s;".formatted(
                    this.field.getName(),
                    NamingUtils.getHandlerMethodName(this.method),
                    NamingUtils.getArgumentCall(this.overrideMethod.getArgs()),
                    this.method.getName(),
                    NamingUtils.getArgumentCall(this.overrideMethod.getArgs())
            ));
        } else {
            sb.append(" {");
            sb.append("this.builder.%s.%s%s;".formatted(
                    this.field.getName(),
                    NamingUtils.getHandlerMethodName(this.method),
                    NamingUtils.getArgumentCall(this.overrideMethod.getArgs())
            ));
            sb.append("} else {");
            sb.append("super.%s%s;".formatted(
                    this.method.getName(),
                    NamingUtils.getArgumentCall(this.overrideMethod.getArgs())
            ));
            sb.append("}");
        }
        return sb.toString();
    }


    public String getConstructorDeclaration() {
        if (Modifier.isAbstract(method.getModifiers())) {
            return "this.%s = new %s<>();".formatted(field.getName(),
                    MethodHotSwapHandler.class.getSimpleName()
            );
        }
        if (method.isDefault() && method.getDeclaringClass().isInterface()) {
            return "this.%s = new %s<>(this::%s);".formatted(field.getName(),
                    MethodHotSwapHandler.class.getSimpleName(),
                    this.method.getName()
            );
        }
        return "this.%s = new %s<>(super::%s);".formatted(field.getName(),
                MethodHotSwapHandler.class.getSimpleName(),
                this.method.getName()
        );
    }

    public Method getMethod() {
        return method;
    }

    public FieldBuilder getField() {
        return field;
    }

    public FunctionalInterfaceWrapper getWrapperType() {
        return wrapperType;
    }

    public MethodBuilder getOverrideMethod() {
        return overrideMethod;
    }

    public Set<String> getRequiredImports() {
        return requiredImports;
    }

}
