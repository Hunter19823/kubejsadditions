package pie.ilikepiefoo.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;

public class SectionUtils {
	public static ChunkPos getChunkPos(Object o) {
		if (o instanceof ChunkPos chunkPos) {
			return chunkPos;
		} else if (o instanceof BlockPos blockPos) {
			return new ChunkPos(blockPos);
		} else if (o instanceof Long pos) {
			return new ChunkPos(pos);
		}
		return ChunkPos.ZERO;
	}
}
