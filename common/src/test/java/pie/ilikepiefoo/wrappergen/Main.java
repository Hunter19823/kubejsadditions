package pie.ilikepiefoo.wrappergen;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pie.ilikepiefoo.wrappergen.builder.WrapperClassBuilder;
import pie.ilikepiefoo.wrappergen.presets.BuilderClassBuilder;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Main {
    public static final Logger LOG = LogManager.getLogger();

    public static void main(String[] args) {
        Map<Class<?>, String> classMap = new HashMap<>();
        String examplePackage = "pie.ilikepiefoo.wrappergen.example";
        classMap.put(TreeMap.class, examplePackage);
        for (Map.Entry<Class<?>, String> entry : classMap.entrySet()) {
            generateWrapperClass(entry.getKey(), entry.getValue());
            generateBuilderClass(entry.getKey(), entry.getValue());
        }
    }

    public static void generateWrapperClass(Class<?> subject, String packageName) {
        WrapperClassBuilder builder = new WrapperClassBuilder(subject.getSimpleName() + "Wrapper");
        builder.setPackageName(packageName);
        builder.addClassImplementation(subject);
        File file = new File("common/src/main/java/" + packageName.replace(".", "/") + "/" + subject.getSimpleName() + "Wrapper" + ".java");
        try (java.io.FileWriter writer = new java.io.FileWriter(file)) {
            writer.write(builder.toJavaFile(0));
        } catch (java.io.IOException e) {
            LOG.error("Failed to write file: " + file.getAbsolutePath(), e);
        }
    }

    public static void generateBuilderClass(Class<?> subject, String packageName) {
        WrapperClassBuilder builder = new BuilderClassBuilder(subject.getSimpleName() + "Builder");
        builder.setPackageName(packageName);
        builder.addClassImplementation(subject);
        File file = new File("common/src/main/java/" + packageName.replace(".", "/") + "/" + subject.getSimpleName() + "Builder" + ".java");
        try (java.io.FileWriter writer = new java.io.FileWriter(file)) {
            writer.write(builder.toJavaFile(0));
        } catch (java.io.IOException e) {
            LOG.error("Failed to write file: " + file.getAbsolutePath(), e);
        }
    }

}