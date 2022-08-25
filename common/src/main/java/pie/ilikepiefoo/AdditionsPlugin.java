package pie.ilikepiefoo;


import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingsEvent;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.rhino.util.wrap.TypeWrappers;
import net.minecraft.data.worldgen.StructureFeatures;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import pie.ilikepiefoo.player.CustomDamageSourceJS;
import pie.ilikepiefoo.util.SectionUtils;
import pie.ilikepiefoo.wrapper.StructureStartWrapper;
import pie.ilikepiefoo.wrapper.StructureTemplateWrapper;

public class AdditionsPlugin extends KubeJSPlugin {
	@Override
	public void addTypeWrappers(ScriptType type, TypeWrappers typeWrappers) {
		typeWrappers.register(ChunkPos.class, SectionUtils::getChunkPos);
		typeWrappers.register(StructureStart.class, o -> {
		    if (o instanceof StructureStart) {
		        return (StructureStart) o;
		    } else if (o instanceof StructureStartWrapper){
		        return ((StructureStartWrapper) o).getData();
		    }

		    return null;
		});

		typeWrappers.register(StructureTemplate.class, o -> {
		    if (o instanceof StructureTemplate) {
		        return (StructureTemplate) o;
		    } else if (o instanceof StructureTemplateWrapper){
		        return ((StructureTemplateWrapper) o).getData();
		    }

		    return null;
		});

	}

	@Override
	public void addBindings(BindingsEvent event) {
		event.add("DamageSource", CustomDamageSourceJS.class);
		event.add("Features", StructureFeatures.class);
		event.add("Feature", StructureFeature.class);
	}
}
