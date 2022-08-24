package pie.ilikepiefoo.wrapper;

import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StructureStartWrapper<T extends FeatureConfiguration> {
	private final StructureStart<T> data;

	public StructureStartWrapper(StructureStart<T> data) {
		this.data = data;
	}

	public static <T extends FeatureConfiguration> StructureStartWrapper<T> of(StructureStart<T> data) {
		return new StructureStartWrapper<T>(data);
	}

	public static <T extends FeatureConfiguration> Iterable<StructureStartWrapper<T>> of(Iterable<StructureStart<T>> data) {
		return () -> StreamSupport.stream(data.spliterator(), false).map(StructureStartWrapper::of).iterator();
	}

	public static <T extends FeatureConfiguration> Optional<StructureStartWrapper<T>> of(Optional<StructureStart<T>> data) {
		return data.map(StructureStartWrapper::of);
	}

	public static Stream<? extends StructureStartWrapper<?>> of(Stream<? extends StructureStart<?>> startsForFeature) {
		return startsForFeature.map(StructureStartWrapper::of);
	}

	public StructureStart<T> getData() {
		return this.data;
	}

	public void generatePieces(RegistryAccess registryAccess, ChunkGenerator chunkGenerator, StructureManager structureManager, int i, int j, Biome biome, T featureConfiguration) {
		data.generatePieces(registryAccess, chunkGenerator, structureManager, i, j, biome, featureConfiguration);
	}

	public BoundingBox getBoundingBox() {
		return data.getBoundingBox();
	}

	public List<StructurePiece> getPieces() {
		return data.getPieces();
	}

	public void placeInChunk(WorldGenLevel worldGenLevel, StructureFeatureManager structureFeatureManager, ChunkGenerator chunkGenerator, Random random, BoundingBox boundingBox, ChunkPos chunkPos) {
		data.placeInChunk(worldGenLevel, structureFeatureManager, chunkGenerator, random, boundingBox, chunkPos);
	}

	public CompoundTag createTag(int i, int j) {
		return data.createTag(i, j);
	}

	public boolean isValid() {
		return data.isValid();
	}

	public int getChunkX() {
		return data.getChunkX();
	}

	public int getChunkZ() {
		return data.getChunkZ();
	}

	public BlockPos getLocatePos() {
		return data.getLocatePos();
	}

	public boolean canBeReferenced() {
		return data.canBeReferenced();
	}

	public void addReference() {
		data.addReference();
	}

	public int getReferences() {
		return data.getReferences();
	}

	public StructureFeature<?> getFeature() {
		return data.getFeature();
	}
}