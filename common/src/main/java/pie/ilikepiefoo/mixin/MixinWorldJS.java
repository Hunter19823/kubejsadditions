package pie.ilikepiefoo.mixin;


import com.mojang.datafixers.util.Pair;
import dev.latvian.mods.kubejs.core.PlayerSelector;
import dev.latvian.mods.kubejs.level.BlockContainerJS;
import dev.latvian.mods.kubejs.level.LevelJS;
import dev.latvian.mods.kubejs.player.PlayerJS;
import dev.latvian.mods.kubejs.server.ServerJS;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Mixin(value = LevelJS.class, remap = false)
public abstract class MixinWorldJS {

    @Shadow
    @Final
    public Level minecraftLevel;

    public void tickChunk(ChunkPos pos) {
        tickChunk(pos.x, pos.z);
    }

    public void tickChunk(int x, int z) {
        if (getServerLevel() != null) {
            getServerLevel().tickChunk(getServerLevel().getChunk(x, z), 1);
        }
    }

    @Nullable
    public ServerLevel getServerLevel() {
        if (minecraftLevel instanceof ServerLevel) {
            return ((ServerLevel) minecraftLevel);
        }
        return null;
    }

    public void tickChunk(int x, int z, int tickCount) {
        if (getServerLevel() != null) {
            getServerLevel().tickChunk(getServerLevel().getChunk(x, z), tickCount);
        }
    }

    public void forceLoadChunk(ChunkPos pos, boolean forceLoad) {
        forceLoadChunk(pos.x, pos.z, forceLoad);
    }

    public void forceLoadChunk(int x, int z, boolean forceLoad) {
        if (getServerLevel() != null) {
            getServerLevel().setChunkForced(x, z, true);
        }
    }

    public List<ChunkPos> getForceLoadedChunks() {
        if (getServerLevel() != null) {
            return getServerLevel().getForcedChunks()
                    .stream()
                    .map(ChunkPos::new)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Nullable
    public Pair<BlockPos, Holder<Biome>> findNearestBiome(Predicate<Holder<Biome>> biomePredicate, BlockPos pos, int i, int j) {
        Pair<BlockPos, Holder<Biome>> biome = null;
        if (getServerLevel() != null) {
            biome = getServerLevel().findNearestBiome(biomePredicate, pos, i, j);
        }
        return biome;
    }

    @Nullable
    public BlockContainerJS findNearestMapFeature(TagKey<ConfiguredStructureFeature<?, ?>> structureFeature, BlockPos pos, int i, boolean bl) {
        BlockPos featurePos = null;
        if (getServerLevel() != null) {
            featurePos = getServerLevel().findNearestMapFeature(structureFeature, pos, i, bl);
        }
        return featurePos == null ? null : getBlock(featurePos);
    }

    @Shadow
    public abstract BlockContainerJS getBlock(BlockPos pos);

    public int countTickableBlocks() {
        if (getServerLevel() != null) {
            return getServerLevel().getBlockTicks().count();
        }
        return -1;
    }

    public void spawnNetherPortal(BlockPos pos, Direction.Axis axis) {
        if (getServerLevel() != null) {
            getServerLevel().getPortalForcer().createPortal(pos, axis);
        }
    }

    @Nullable
    public PlayerJS getRandomPlayer() {
        if (getServerLevel() != null && getServer() != null) {
            return getServer().getPlayer(PlayerSelector.mc(getServerLevel().getRandomPlayer()));
        }
        return null;
    }

    @Shadow
    public abstract @org.jetbrains.annotations.Nullable ServerJS getServer();


}
