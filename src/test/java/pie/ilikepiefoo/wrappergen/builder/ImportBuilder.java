package pie.ilikepiefoo.wrappergen.builder;

import pie.ilikepiefoo.wrappergen.util.ReflectionTools;

import java.util.Arrays;
import java.util.Collection;
import java.util.TreeSet;

public class ImportBuilder implements JavaFileOutput {
    private final TreeSet<String> imports;
    private String packageName;

    public ImportBuilder() {
        this.packageName = this.getClass().getPackageName();
        this.imports = new TreeSet<>();
    }

    public ImportBuilder addImport(String importName) {
        this.imports.add(importName);
        return this;
    }

    public ImportBuilder addImport(Class<?> target) {
        if (ReflectionTools.getImportName(target) != null) {
            this.imports.add(ReflectionTools.getImportName(target));
        }
        return this;
    }

    public ImportBuilder addImports(String[] importNames) {
        this.imports.addAll(Arrays.asList(importNames));
        return this;
    }

    public ImportBuilder addImports(Collection<String> importNames) {
        this.imports.addAll(importNames);
        return this;
    }

    public String getPackageName() {
        return packageName;
    }

    public ImportBuilder setPackageName(String packageName) {
        this.packageName = packageName;
        return this;
    }

    public TreeSet<String> getImports() {
        return imports;
    }

    @Override
    public String toJavaFile(int indentLevel) {
        StringBuilder sb = new StringBuilder();
        if (!this.packageName.isBlank()) {
            sb.append("package ").append(this.packageName).append(";\n\n");
        }
        for (String className : this.imports) {
            sb.append("import ").append(className).append(";\n");
        }
        return sb.toString();
    }

}
