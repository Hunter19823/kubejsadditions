package pie.ilikepiefoo;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.level.BlockContainerJS;
import dev.latvian.mods.kubejs.script.BindingsEvent;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.rhino.util.wrap.TypeWrappers;
import net.minecraft.core.BlockPos;
import net.minecraft.data.worldgen.Structures;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.feature.Feature;
import pie.ilikepiefoo.player.CustomDamageSourceJS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdditionsPlugin extends KubeJSPlugin {
	public static final List<EmptyHandler> STARTUP_HANDLERS = Collections.synchronizedList(new ArrayList<>());
	public static final List<EmptyHandler> REGISTER_EVENT_HANDLER = Collections.synchronizedList(new ArrayList<>());

	@Override
	public void initStartup() {
		STARTUP_HANDLERS.forEach(EmptyHandler::handle);
	}

	/**
	 * Call {@link EventGroup#register()} of events your mod adds
	 */
	@Override
	public void registerEvents() {
		REGISTER_EVENT_HANDLER.forEach(EmptyHandler::handle);
	}

	@Override
	public void registerBindings(BindingsEvent event) {
		event.add("DamageSource", CustomDamageSourceJS.class);
		event.add("Structures", Structures.class);
		event.add("Feature", Feature.class);
	}

	@Override
	public void registerTypeWrappers(ScriptType type, TypeWrappers typeWrappers) {
		typeWrappers.registerSimple(ChunkPos.class, o -> {
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
	}

	public interface EmptyHandler {
		void handle();
	}
}
