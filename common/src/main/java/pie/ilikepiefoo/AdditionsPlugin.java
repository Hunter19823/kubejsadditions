package pie.ilikepiefoo;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingsEvent;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.rhino.util.wrap.TypeWrappers;
import net.minecraft.core.BlockPos;
import net.minecraft.data.worldgen.Structures;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import pie.ilikepiefoo.player.CustomDamageSourceJS;
import pie.ilikepiefoo.wrapper.StructureStartWrapper;
import pie.ilikepiefoo.wrapper.StructureTemplateWrapper;

public class AdditionsPlugin extends KubeJSPlugin {
	@Override
	public void registerTypeWrappers(ScriptType type, TypeWrappers typeWrappers) {
		typeWrappers.registerSimple(ChunkPos.class, o -> {
			if (o instanceof ChunkPos chunkPos) {
				return chunkPos;
			} else if (o instanceof BlockPos blockPos) {
				return new ChunkPos(blockPos);
			} else if (o instanceof Long pos) {
				return new ChunkPos(pos);
			}
			return ChunkPos.ZERO;
		});
	}

	@Override
	public void registerBindings(BindingsEvent event) {
		event.add("DamageSource", CustomDamageSourceJS.class);
		event.add("Structures", Structures.class);
//		event.add("Feature", StructureFeature.class); //TODO: Not sure what the equivalent is
	}
}
