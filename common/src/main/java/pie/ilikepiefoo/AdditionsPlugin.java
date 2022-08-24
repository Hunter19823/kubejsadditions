package pie.ilikepiefoo;

import dev.latvian.kubejs.KubeJSPlugin;
import dev.latvian.kubejs.script.ScriptType;
import dev.latvian.mods.rhino.util.wrap.TypeWrappers;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import pie.ilikepiefoo.wrapper.StructureStartWrapper;
import pie.ilikepiefoo.wrapper.StructureTemplateWrapper;

public class AdditionsPlugin extends KubeJSPlugin {
	@Override
	public void addTypeWrappers(ScriptType type, TypeWrappers typeWrappers) {
		typeWrappers.register(StructureStart.class, (obj) -> {
			if(obj instanceof StructureStart){
				return (StructureStart<?>) obj;
			} else if(obj instanceof StructureStartWrapper) {
				return ((StructureStartWrapper<?>) obj).getData();
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
}
