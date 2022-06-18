package pie.ilikepiefoo.mixin;

import dev.latvian.kubejs.player.PlayerJS;
import dev.latvian.kubejs.server.ServerJS;
import dev.latvian.kubejs.world.BlockContainerJS;
import dev.latvian.kubejs.world.WorldJS;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mixin (value = WorldJS.class, remap = false)
public abstract class MixinWorldJS {

	@Shadow
	@Final
	public Level minecraftLevel;

	@Shadow
	public abstract BlockContainerJS getBlock(BlockPos pos);

	@Shadow
	public abstract @org.jetbrains.annotations.Nullable ServerJS getServer();

	@Nullable
	public ServerLevel getServerLevel() {
		if(minecraftLevel instanceof ServerLevel) {
			return ((ServerLevel) minecraftLevel);
		}
		return null;
	}

	public void tickChunk(int x, int z) {
		if(getServerLevel() != null) {
			getServerLevel().tickChunk(getServerLevel().getChunk(x,z),1);
		}
	}

	public void tickChunk(ChunkPos pos) {
		tickChunk(pos.x, pos.z);
	}

	public void tickChunk(int x, int z, int tickCount) {
		if(getServerLevel() != null) {
			getServerLevel().tickChunk(getServerLevel().getChunk(x,z),tickCount);
		}
	}

	public void forceLoadChunk(ChunkPos pos, boolean forceLoad) {
		forceLoadChunk(pos.x, pos.z, forceLoad);
	}

	public void forceLoadChunk(int x, int z, boolean forceLoad) {
		if(getServerLevel() != null) {
			getServerLevel().setChunkForced(x,z,true);
		}
	}

	public List<ChunkPos> getForceLoadedChunks() {
		if(getServerLevel() != null) {
			return getServerLevel().getForcedChunks()
					.stream()
					.map(ChunkPos::new)
					.collect(Collectors.toList());
		}
		return Collections.emptyList();
	}

	@Nullable
	public BlockContainerJS findNearestBiome(Biome biome, BlockPos pos, int i, int j) {
		BlockPos biomePos = null;
		if(getServerLevel() != null) {
			biomePos = getServerLevel().findNearestBiome(biome, pos, i, j);
		}
		return biomePos == null ? null : getBlock(biomePos);
	}

	@Nullable
	public BlockContainerJS findNearestMapFeature(StructureFeature<?> structureFeature, BlockPos pos, int i, boolean bl) {
		BlockPos featurePos = null;
		if(getServerLevel() != null) {
			featurePos = getServerLevel().findNearestMapFeature(structureFeature, pos, i, bl);
		}
		return featurePos == null ? null : getBlock(featurePos);
	}

	public int countTickableBlocks() {
		if(getServerLevel() != null) {
			return getServerLevel().getBlockTicks().size();
		}
		return -1;
	}

	public void spawnNetherPortal(BlockPos pos, Direction.Axis axis) {
		if(getServerLevel() != null) {
			getServerLevel().getPortalForcer().createPortal(pos, axis);
		}
	}

	@Nullable
	public PlayerJS getRandomPlayer() {
		if(getServerLevel() != null && getServer() != null) {
			return getServer().getPlayer(getServerLevel().getRandomPlayer());
		}
		return null;
	}


}
