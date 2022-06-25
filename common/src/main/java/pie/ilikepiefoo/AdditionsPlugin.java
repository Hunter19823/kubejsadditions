package pie.ilikepiefoo;


import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingsEvent;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.rhino.util.wrap.TypeWrappers;
import net.minecraft.world.level.ChunkPos;
import pie.ilikepiefoo.player.CustomDamageSourceJS;
import pie.ilikepiefoo.util.SectionUtils;

public class AdditionsPlugin extends KubeJSPlugin {
	@Override
	public void addTypeWrappers(ScriptType type, TypeWrappers typeWrappers) {
		typeWrappers.register(ChunkPos.class, SectionUtils::getChunkPos);
	}

	@Override
	public void addBindings(BindingsEvent event) {
		event.add("DamageSource", CustomDamageSourceJS.class);
	}
}
