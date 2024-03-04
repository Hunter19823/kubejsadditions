package pie.ilikepiefoo.wrappergen.presets;

import pie.ilikepiefoo.wrappergen.builder.BuilderMethodBuilder;
import pie.ilikepiefoo.wrappergen.builder.MethodBuilder;
import pie.ilikepiefoo.wrappergen.builder.WrapperClassBuilder;
import pie.ilikepiefoo.wrappergen.util.GenerationUtils;
import pie.ilikepiefoo.wrappergen.util.NamingUtils;
import pie.ilikepiefoo.wrappergen.util.ReflectionTools;
import pie.ilikepiefoo.wrappergen.util.TypeVariableMap;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public class BuilderClassBuilder extends WrapperClassBuilder {
    protected final List<MethodBuilder> getterMethods;
    protected final List<MethodBuilder> methodOverrides;

    public BuilderClassBuilder(String name) {
        super(name);
        this.getterMethods = new ArrayList<>();
        this.methodOverrides = new ArrayList<>();
    }

    @Override
    public WrapperClassBuilder addClassImplementation(Class<?> subject) {
        if (Modifier.isFinal(subject.getModifiers())) {
            throw new UnsupportedOperationException("Cannot inherit from a final class.");
        }
        if (Modifier.isStatic(subject.getModifiers())) {
            throw new UnsupportedOperationException("Cannot inherit from a static class.");
        }
        if (subject.isEnum()) {
            throw new UnsupportedOperationException("Cannot inherit from an enum.");
        }
        if (!subject.isInterface() && !this.classBuilder.getSuperClass().isBlank()) {
            throw new IllegalArgumentException("Class already has a superclass.");
        }
        TypeVariableMap typeVariableMap = new TypeVariableMap(subject);

        StringBuilder sb = new StringBuilder();
        sb.append(NamingUtils.getShortName(subject));
        if (subject.getTypeParameters().length > 0) {
            StringJoiner joiner = new StringJoiner(", ", "<", ">");
            for (int i = 0; i < subject.getTypeParameters().length; i++) {
                joiner.add(subject.getTypeParameters()[i].getTypeName());
                this.classBuilder.addGenerics(ReflectionTools.getGenericDefinition(subject.getTypeParameters()[i], typeVariableMap));
            }
            sb.append(joiner);
        }
        if (!subject.isInterface()) {
            var constructors = Arrays.stream(subject.getConstructors())
                    .filter(constructor -> !Modifier.isPrivate(constructor.getModifiers()))
                    .toList();
            for (var constructor : constructors) {
                var constructorBuilder = GenerationUtils.createConstructorBuilderFromConstructor(
                        constructor,
                        typeVariableMap
                ).setAccessModifier("public").setName(this.classBuilder.getName());
                for (var annotation : constructor.getAnnotations()) {
                    constructorBuilder.addAnnotations(annotation.annotationType().getSimpleName());
                    this.importBuilder.addImport(annotation.annotationType());
                }
                for (var parameter : constructor.getParameters()) {
                    this.importBuilder.addImport(parameter.getType());
                }
//                this.constructorBuilders.add(constructorBuilder);
            }
        }
        this.importBuilder.addImport(subject);

        Arrays.stream(subject.getMethods())
                .filter(method -> !Modifier.isStatic(method.getModifiers()) &&
                        !Modifier.isFinal(method.getModifiers()) &&
                        !Modifier.isPrivate(method.getModifiers()))
                .map(method -> new MethodWrapper(method, typeVariableMap))
                .forEachOrdered((methodWrapper) -> {
                    try {
                        this.addMethodWrapper(methodWrapper);
                    } catch (IllegalArgumentException e) {
                        System.err.println(e.getMessage());
                    }
                });

        return this;
    }

    public BuilderClassBuilder addMethodWrapper(MethodWrapper methodWrapper) {
        if (this.innerClassBuilders.containsKey(methodWrapper.getWrapperType().getName())) {
            throw new IllegalArgumentException("Inner class already exists with name " +
                    methodWrapper.getWrapperType().getName() +
                    " already exists.");
        }
        this.addField(
                methodWrapper
                        .getField()
                        .setType(methodWrapper
                                .getWrapperType()
                                .getCanonicalName()
                        )
        );
        var builderMethod = new BuilderMethodBuilder();
        builderMethod
                .setName("set" + methodWrapper.getWrapperType().getName())
                .setReturnType(this.getClassBuilder().getCanonicalName())
                .addArg(methodWrapper.getWrapperType().getCanonicalName() + " " + NamingUtils.SnakeCase(methodWrapper.getWrapperType().getName()))
                .addBody("this." + methodWrapper.getField().getName() + " = " + NamingUtils.SnakeCase(methodWrapper.getWrapperType().getName()) + ";")
                .addBody("return this;");

        this.addMethod(builderMethod);

        methodWrapper.getOverrideMethod().setBody(
                methodWrapper.getOverrideMethodBodyWithBuilder(methodWrapper.getMethod(), builderMethod)
        );
        this.methodOverrides.add(methodWrapper.getOverrideMethod());

        this.addInnerClass(methodWrapper.getWrapperType());
        this.importBuilder.addImports(methodWrapper.getRequiredImports());

        return this;
    }
}
