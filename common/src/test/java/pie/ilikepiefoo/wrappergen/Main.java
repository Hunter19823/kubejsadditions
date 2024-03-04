package pie.ilikepiefoo.wrappergen;

import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import mezz.jei.api.ingredients.IIngredientTypeWithSubtypes;
import mezz.jei.api.ingredients.ITypedIngredient;
import mezz.jei.api.recipe.advanced.IRecipeManagerPlugin;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.category.extensions.IRecipeCategoryDecorator;
import mezz.jei.api.recipe.transfer.IRecipeTransferInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pie.ilikepiefoo.wrappergen.builder.WrapperClassBuilder;
import pie.ilikepiefoo.wrappergen.presets.BuilderClassBuilder;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static final Logger LOG = LogManager.getLogger();

    public static void main(String[] args) {
        Map<Class<?>, String> classMap = new HashMap<>();
        String examplePackage = "pie.ilikepiefoo.compat.jei.builder";
        classMap.put(IIngredientTypeWithSubtypes.class, examplePackage);
        classMap.put(IRecipeCategory.class, examplePackage);
        classMap.put(IRecipeTransferInfo.class, examplePackage);
        classMap.put(IGuiContainerHandler.class, examplePackage);
        classMap.put(IRecipeManagerPlugin.class, examplePackage);
        classMap.put(IRecipeCategoryDecorator.class, examplePackage);
        classMap.put(ITypedIngredient.class, examplePackage);

        for (Map.Entry<Class<?>, String> entry : classMap.entrySet()) {
            generateBuilderClass(entry.getKey(), entry.getValue());
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