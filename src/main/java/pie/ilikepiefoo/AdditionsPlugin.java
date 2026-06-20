package pie.ilikepiefoo;

import dev.latvian.mods.kubejs.event.EventGroupRegistry;
import dev.latvian.mods.kubejs.level.BlockContainerJS;
import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.kubejs.script.TypeWrapperRegistry;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.neoforged.fml.ModList;
import pie.ilikepiefoo.compat.jade.JadeEvents;
import pie.ilikepiefoo.compat.jei.JEIDrawableWrapper;
import pie.ilikepiefoo.compat.jei.JEIEvents;
import pie.ilikepiefoo.compat.jei.events.JEIEventJS;
import pie.ilikepiefoo.events.AdditionalEvents;
import pie.ilikepiefoo.events.custom.ArchEventRegisterEventJS;

public class AdditionsPlugin implements KubeJSPlugin {

    @Override
    public void initStartup() {
        AdditionalEvents.ARCH_EVENT_REGISTER.post(ScriptType.STARTUP, new ArchEventRegisterEventJS());
    }

    @Override
    public void registerEvents(EventGroupRegistry registry) {
        AdditionalEvents.register(registry);
        JEIEvents.register(registry);
        JadeEvents.register(registry);
    }

    @Override
    public void registerTypeWrappers(TypeWrapperRegistry registry) {
        registry.register(ChunkPos.class, o -> {
            if (o instanceof ChunkPos chunkPos) {
                return chunkPos;
            } else if (o instanceof BlockPos blockPos) {
                return new ChunkPos(blockPos);
            } else if (o instanceof Long pos) {
                return new ChunkPos(pos);
            } else if (o instanceof BlockContainerJS containerJS) {
                return new ChunkPos(containerJS.getPos());
            } else if (o instanceof Entity entity) {
                return entity.chunkPosition();
            }
            return ChunkPos.ZERO;
        });

        if (ModList.get().isLoaded("jei")) {
            if (registry.scriptType() == ScriptType.CLIENT) {
                registry.register(IDrawable.class, JEIDrawableWrapper::of);
            }
            registry.register(RecipeType.class, (object) -> {
                if (object instanceof RecipeType<?> recipeType) {
                    return recipeType;
                }
                if (object instanceof IRecipeCategory<?> category) {
                    return category.getRecipeType();
                }
                if (JEIEventJS.JEI_HELPERS == null) {
                    return null;
                }
                if (object instanceof String recipeType) {
                    return JEIEventJS.JEI_HELPERS.getRecipeType(ResourceLocation.parse(recipeType)).orElse(null);
                }
                if (object instanceof ResourceLocation recipeType) {
                    return JEIEventJS.JEI_HELPERS.getRecipeType(recipeType).orElse(null);
                }
                return null;
            });
        }
    }

    public interface EmptyHandler {
        void handle();

    }

}
