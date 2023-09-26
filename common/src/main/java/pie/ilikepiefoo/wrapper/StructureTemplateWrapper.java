package pie.ilikepiefoo.wrapper;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StructureTemplateWrapper {
    private final StructureTemplate data;

    public StructureTemplateWrapper(StructureTemplate data) {
        this.data = data;
    }

    public static Stream<StructureTemplateWrapper> of(Stream<StructureTemplate> data) {
        return data.map(StructureTemplateWrapper::of);
    }

    public static StructureTemplateWrapper of(StructureTemplate data) {
        return new StructureTemplateWrapper(data);
    }

    public static Iterable<StructureTemplateWrapper> of(Iterable<StructureTemplate> data) {
        return () -> StreamSupport.stream(data.spliterator(), false).map(StructureTemplateWrapper::of).iterator();
    }

    public static Optional<StructureTemplateWrapper> of(Optional<StructureTemplate> data) {
        return data.map(StructureTemplateWrapper::of);
    }

    public String getAuthor() {
        return getData().getAuthor();
    }

    public void setAuthor(String string) {
        getData().setAuthor(string);
    }

    public StructureTemplate getData() {
        return this.data;
    }

    public void fillFromWorld(Level level, BlockPos blockPos, BlockPos blockPos2, boolean bl, @Nullable Block block) {
        getData().fillFromWorld(level, blockPos, blockPos2, bl, block);
    }

    public List<StructureTemplate.StructureBlockInfo> filterBlocks(BlockPos blockPos, StructurePlaceSettings structurePlaceSettings, Block block) {
        return getData().filterBlocks(blockPos, structurePlaceSettings, block);
    }

    public List<StructureTemplate.StructureBlockInfo> filterBlocks(BlockPos blockPos, StructurePlaceSettings structurePlaceSettings, Block block, boolean bl) {
        return getData().filterBlocks(blockPos, structurePlaceSettings, block, bl);
    }

    public BlockPos calculateConnectedPosition(StructurePlaceSettings structurePlaceSettings, BlockPos blockPos, StructurePlaceSettings structurePlaceSettings2, BlockPos blockPos2) {
        return getData().calculateConnectedPosition(structurePlaceSettings, blockPos, structurePlaceSettings2, blockPos2);
    }

    public boolean placeInWorld(ServerLevelAccessor serverLevelAccessor, BlockPos blockPos, BlockPos blockPos2, StructurePlaceSettings structurePlaceSettings, Random random, int i) {
        return getData().placeInWorld(serverLevelAccessor, blockPos, blockPos2, structurePlaceSettings, random, i);
    }

    public BlockPos getZeroPositionWithTransform(BlockPos blockPos, Mirror mirror, Rotation rotation) {
        return getData().getZeroPositionWithTransform(blockPos, mirror, rotation);
    }

    public BoundingBox getBoundingBox(StructurePlaceSettings structurePlaceSettings, BlockPos blockPos) {
        return getData().getBoundingBox(structurePlaceSettings, blockPos);
    }

    public BoundingBox getBoundingBox(BlockPos blockPos, Rotation rotation, BlockPos blockPos2, Mirror mirror) {
        return getData().getBoundingBox(blockPos, rotation, blockPos2, mirror);
    }

    public CompoundTag save(CompoundTag compoundTag) {
        return getData().save(compoundTag);
    }

    public void load(CompoundTag compoundTag) {
        getData().load(compoundTag);
    }

    public Vec3i getSize() {
        return data.getSize();
    }

    public void fillFromWorld(Level level, BlockPos blockPos, Vec3i vec3i, boolean bl, @Nullable Block block) {
        data.fillFromWorld(level, blockPos, vec3i, bl, block);
    }

    public Vec3i getSize(Rotation rotation) {
        return data.getSize(rotation);
    }
}