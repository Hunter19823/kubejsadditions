package pie.ilikepiefoo.wrapper;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StructureStartWrapper {
	private final StructureStart data;

	public StructureStartWrapper(StructureStart data) {
		this.data = data;
	}

	public static StructureStartWrapper of(StructureStart data) {
		return new StructureStartWrapper(data);
	}

	public static Stream<StructureStartWrapper> of(Stream<StructureStart> data) {
		return data.map(StructureStartWrapper::of);
	}

	public static Iterable<StructureStartWrapper> of(Iterable<StructureStart> data) {
		return () -> StreamSupport.stream(data.spliterator(), false).map(StructureStartWrapper::of).iterator();
	}

	public static Optional<StructureStartWrapper> of(Optional<StructureStart> data) {
		return data.map(StructureStartWrapper::of);
	}

	public StructureStart getData() {
		return this.data;
	}

	public BoundingBox getBoundingBox() {
		return data.getBoundingBox();
	}

	public void placeInChunk(WorldGenLevel worldGenLevel, StructureFeatureManager structureFeatureManager, ChunkGenerator chunkGenerator, Random random, BoundingBox boundingBox, ChunkPos chunkPos) {
		data.placeInChunk(worldGenLevel, structureFeatureManager, chunkGenerator, random, boundingBox, chunkPos);
	}

	public CompoundTag createTag(StructurePieceSerializationContext structurePieceSerializationContext, ChunkPos chunkPos) {
		return data.createTag(structurePieceSerializationContext, chunkPos);
	}

	public boolean isValid() {
		return data.isValid();
	}

	public ChunkPos getChunkPos() {
		return data.getChunkPos();
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

	public ConfiguredStructureFeature<?, ?> getFeature() {
		return data.getFeature();
	}

	public List<StructurePiece> getPieces() {
		return data.getPieces();
	}
}