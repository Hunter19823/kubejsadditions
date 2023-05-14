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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdditionsPlugin extends KubeJSPlugin {
	public static final List<EmptyHandler> STARTUP_HANDLERS = Collections.synchronizedList(new ArrayList<>());

	static {
		// Force the mod classes to load so that the static blocks are executed.
		try {
			Class.forName("pie.ilikepiefoo.KubeJSAdditions");
			Class.forName("pie.ilikepiefoo.fabric.KubeJSAdditionsFabric");
		} catch (Throwable ignored) {
		}
	}

	@Override
	public void initStartup() {
		STARTUP_HANDLERS.forEach(EmptyHandler::handle);
	}

	@Override
	public void addTypeWrappers(ScriptType type, TypeWrappers typeWrappers) {
		typeWrappers.register(ChunkPos.class, SectionUtils::getChunkPos);
		typeWrappers.register(StructureStart.class, o -> {
			if (o instanceof StructureStart) {
				return (StructureStart) o;
			} else if (o instanceof StructureStartWrapper) {
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

	@FunctionalInterface
	public interface EmptyHandler {
		void handle();
	}
}
