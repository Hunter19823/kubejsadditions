package pie.ilikepiefoo;


import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.rhino.util.wrap.TypeWrappers;
import net.minecraft.world.level.ChunkPos;
import pie.ilikepiefoo.util.SectionUtils;

public class AdditionsPlugin extends KubeJSPlugin {
	@Override
	public void addTypeWrappers(ScriptType type, TypeWrappers typeWrappers) {
		typeWrappers.register(ChunkPos.class, SectionUtils::getChunkPos);
	}
}
