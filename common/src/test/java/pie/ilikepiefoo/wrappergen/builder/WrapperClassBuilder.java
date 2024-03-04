package pie.ilikepiefoo.wrappergen.builder;

import pie.ilikepiefoo.wrappergen.presets.MethodWrapper;
import pie.ilikepiefoo.wrappergen.util.GenerationUtils;
import pie.ilikepiefoo.wrappergen.util.NamingUtils;
import pie.ilikepiefoo.wrappergen.util.ReflectionTools;
import pie.ilikepiefoo.wrappergen.util.TypeVariableMap;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.StringJoiner;
import java.util.TreeMap;

public class WrapperClassBuilder implements JavaFileOutput {
    protected final ClassBuilder classBuilder;
    protected final ImportBuilder importBuilder;
    protected final SortedMap<String, FieldBuilder> fieldBuilders;
    protected final List<ConstructorBuilder> constructorBuilders;
    protected final List<MethodBuilder> methodBuilders;
    protected final SortedMap<String, ClassBuilder> innerClassBuilders;
    protected final List<String> constructorDeclarations;

    public WrapperClassBuilder(String name) {
        this.importBuilder = new ImportBuilder();
        this.classBuilder = new ClassBuilder().setName(name);
        this.fieldBuilders = new TreeMap<>();
        this.constructorBuilders = new ArrayList<>();
        this.methodBuilders = new ArrayList<>();
        this.innerClassBuilders = new TreeMap<>();
        this.constructorDeclarations = new ArrayList<>();
    }

    public WrapperClassBuilder setPackageName(String packageName) {
        this.importBuilder.setPackageName(packageName);
        return this;
    }

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
            this.classBuilder.setSuperClass(sb.toString());
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
                this.constructorBuilders.add(constructorBuilder);
            }
        } else {
            this.classBuilder.addInterfaces(sb.toString());
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

    public WrapperClassBuilder addMethodWrapper(MethodWrapper methodWrapper) {
        if (this.innerClassBuilders.containsKey(methodWrapper.getWrapperType().getName())) {
            throw new IllegalArgumentException("Inner class already exists with name " +
                    methodWrapper.getWrapperType().getName() +
                    " already exists.");
        }
        this.addField(methodWrapper.getField());
        this.constructorDeclarations.add(methodWrapper.getConstructorDeclaration());
        this.addMethod(methodWrapper.getOverrideMethod());
        this.addInnerClass(methodWrapper.getWrapperType());
        this.importBuilder.addImports(methodWrapper.getRequiredImports());

        return this;
    }

    public WrapperClassBuilder addField(FieldBuilder... fieldBuilders) {
        for (var field : fieldBuilders) {
            if (this.fieldBuilders.containsKey(field.getName())) {
                throw new IllegalArgumentException("Field with name " +
                        field.getName() +
                        " already exists.");
            }
            this.fieldBuilders.put(field.getName(), field);
        }
        return this;
    }

    public WrapperClassBuilder addMethod(MethodBuilder... methodBuilders) {
        this.methodBuilders.addAll(List.of(methodBuilders));
        return this;
    }

    public WrapperClassBuilder addInnerClass(ClassBuilder... innerClassBuilders) {
        for (ClassBuilder innerClassBuilder : innerClassBuilders) {
            if (this.innerClassBuilders.containsKey(innerClassBuilder.getName())) {
                throw new IllegalArgumentException("Inner class with name " +
                        innerClassBuilder.getName() +
                        " already exists.");
            }
            this.innerClassBuilders.put(innerClassBuilder.getName(), innerClassBuilder);
        }
        return this;
    }

    public ImportBuilder getImportBuilder() {
        return importBuilder;
    }

    public ClassBuilder getClassBuilder() {
        return classBuilder;
    }

    public Map<String, FieldBuilder> getFieldBuilders() {
        return fieldBuilders;
    }

    public List<ConstructorBuilder> getConstructorBuilders() {
        return constructorBuilders;
    }

    public List<MethodBuilder> getMethodBuilders() {
        return methodBuilders;
    }

    public Map<String, ClassBuilder> getInnerClassBuilders() {
        return innerClassBuilders;
    }

    public List<String> getConstructorDeclarations() {
        return constructorDeclarations;
    }

    @Override
    public String toJavaFile(int indentLevel) {
        var temp = this.classBuilder.getBody();
        this.classBuilder.setImports(this.importBuilder.toJavaFile(indentLevel));
        this.methodBuilders.sort(Comparator.comparing(MethodBuilder::getName));
        this.constructorBuilders.sort(Comparator.comparing(ConstructorBuilder::getName));
        for (FieldBuilder fieldBuilder : this.fieldBuilders.values()) {
            temp.add(fieldBuilder.toJavaFile(indentLevel + 1));
        }
        if (this.constructorBuilders.isEmpty()) {
            this.addConstructor(new ConstructorBuilder().setName(this.classBuilder.getName()));
        }
        for (ConstructorBuilder constructorBuilder : this.constructorBuilders) {
            temp.add(constructorBuilder.toJavaFile(indentLevel + 1));
        }
        for (MethodBuilder methodBuilder : this.methodBuilders) {
            temp.add(methodBuilder.toJavaFile(indentLevel + 1));
        }
        for (ClassBuilder innerClassBuilder : this.innerClassBuilders.values()) {
            temp.add(innerClassBuilder.toJavaFile(indentLevel + 1));
        }
        String output = this.classBuilder.toJavaFile(indentLevel);
        this.classBuilder.setBody(temp);
        return output;
    }

    public WrapperClassBuilder addConstructor(ConstructorBuilder... constructorBuilders) {
        this.constructorBuilders.addAll(List.of(constructorBuilders));
        return this;
    }

}
