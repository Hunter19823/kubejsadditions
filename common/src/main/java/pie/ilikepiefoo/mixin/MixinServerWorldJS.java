package pie.ilikepiefoo.mixin;

import dev.latvian.kubejs.player.PlayerDataJS;
import dev.latvian.kubejs.script.ScriptType;
import dev.latvian.kubejs.world.ServerWorldJS;
import dev.latvian.kubejs.world.WorldJS;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ServerScoreboard;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raids;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.chunk.FeatureAccess;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.dimension.end.EndDragonFight;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.portal.PortalForcer;
import net.minecraft.world.level.storage.DimensionDataStorage;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import pie.ilikepiefoo.wrapper.StructureStartWrapper;
import pie.ilikepiefoo.wrapper.StructureTemplateWrapper;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Mixin(ServerWorldJS.class)
public class MixinServerWorldJS {
	public ServerWorldJS getInstance() {
		return ((ServerWorldJS) (Object) this);
	}
	private ServerLevel getMinecraftLeveInstance() {
		return (ServerLevel) ((ServerWorldJS) (Object) this).minecraftLevel;
	}

	public StructureFeatureManager getStructureFeatureManager() {
		return getMinecraftLeveInstance().structureFeatureManager();
	}

	public ServerScoreboard getScoreboard() {
		return getMinecraftLeveInstance().getScoreboard();
	}

	public List<EnderDragon> getDragons() {
		return getMinecraftLeveInstance().getDragons();
	}

	public ServerChunkCache getChunkSource() {
		return getMinecraftLeveInstance().getChunkSource();
	}

	public PortalForcer getPortalForcer() {
		return getMinecraftLeveInstance().getPortalForcer();
	}

	public StructureManager getStructureManager() {
		return getMinecraftLeveInstance().getStructureManager();
	}

	public DimensionDataStorage getDataStorage() {
		return getMinecraftLeveInstance().getDataStorage();
	}

	public void setDefaultSpawnPos(BlockPos blockPos, float f) {
		getMinecraftLeveInstance().setDefaultSpawnPos(blockPos, f);
	}

	public BlockPos getSharedSpawnPos() {
		return getMinecraftLeveInstance().getSharedSpawnPos();
	}

	public float getSharedSpawnAngle() {
		return getMinecraftLeveInstance().getSharedSpawnAngle();
	}

	public boolean setChunkForced(int i, int j, boolean bl) {
		return getMinecraftLeveInstance().setChunkForced(i, j, bl);
	}

	public PoiManager getPoiManager() {
		return getMinecraftLeveInstance().getPoiManager();
	}

	public boolean isVillage(BlockPos blockPos) {
		return getMinecraftLeveInstance().isVillage(blockPos);
	}

	public boolean isVillage(SectionPos sectionPos) {
		return getMinecraftLeveInstance().isVillage(sectionPos);
	}

	public boolean isCloseToVillage(BlockPos blockPos, int i) {
		return getMinecraftLeveInstance().isCloseToVillage(blockPos, i);
	}

	public Raids getRaids() {
		return getMinecraftLeveInstance().getRaids();
	}

	public boolean isRaided(BlockPos blockPos) {
		return getMinecraftLeveInstance().isRaided(blockPos);
	}

	@Environment(EnvType.CLIENT)
	public float getShade(Direction direction, boolean bl) {
		return getMinecraftLeveInstance().getShade(direction, bl);
	}

	public boolean isFlat() {
		return getMinecraftLeveInstance().isFlat();
	}

	@Nullable
	public EndDragonFight dragonFight() {
		return getMinecraftLeveInstance().dragonFight();
	}

	public Raid getRaid(int i) {
		return getRaids().get(i);
	}

	@Nullable
	public Raid createOrExtendRaid(ServerPlayer serverPlayer) {
		return getRaids().createOrExtendRaid(serverPlayer);
	}

	@Nullable
	public Raid getNearbyRaid(BlockPos blockPos, int i) {
		return getRaids().getNearbyRaid(blockPos, i);
	}

	public long getPoiCountInRange(Predicate<PoiType> predicate, BlockPos blockPos, int i, PoiManager.Occupancy occupancy) {
		return getPoiManager().getCountInRange(predicate, blockPos, i, occupancy);
	}

	public boolean poiExistsAtPosition(PoiType poiType, BlockPos blockPos) {
		return getPoiManager().existsAtPosition(poiType, blockPos);
	}

	public Stream<PoiRecord> getPoiInSquare(Predicate<PoiType> predicate, BlockPos blockPos, int i, PoiManager.Occupancy occupancy) {
		return getPoiManager().getInSquare(predicate, blockPos, i, occupancy);
	}

	public Stream<PoiRecord> getPoiInRange(Predicate<PoiType> predicate, BlockPos blockPos, int i, PoiManager.Occupancy occupancy) {
		return getPoiManager().getInRange(predicate, blockPos, i, occupancy);
	}

	public Stream<PoiRecord> getPoiInChunk(Predicate<PoiType> predicate, ChunkPos chunkPos, PoiManager.Occupancy occupancy) {
		return getPoiManager().getInChunk(predicate, chunkPos, occupancy);
	}

	public Stream<BlockPos> findAllPoi(Predicate<PoiType> predicate, Predicate<BlockPos> predicate2, BlockPos blockPos, int i, PoiManager.Occupancy occupancy) {
		return getPoiManager().findAll(predicate, predicate2, blockPos, i, occupancy);
	}

	public Stream<BlockPos> findAllClosestFirstPoi(Predicate<PoiType> predicate, Predicate<BlockPos> predicate2, BlockPos blockPos, int i, PoiManager.Occupancy occupancy) {
		return getPoiManager().findAllClosestFirst(predicate, predicate2, blockPos, i, occupancy);
	}

	public Optional<BlockPos> findPoi(Predicate<PoiType> predicate, Predicate<BlockPos> predicate2, BlockPos blockPos, int i, PoiManager.Occupancy occupancy) {
		return getPoiManager().find(predicate, predicate2, blockPos, i, occupancy);
	}

	public Optional<BlockPos> findClosestPoi(Predicate<PoiType> predicate, BlockPos blockPos, int i, PoiManager.Occupancy occupancy) {
		return getPoiManager().findClosest(predicate, blockPos, i, occupancy);
	}

	public Optional<BlockPos> getRandomPoi(Predicate<PoiType> predicate, Predicate<BlockPos> predicate2, PoiManager.Occupancy occupancy, BlockPos blockPos, int i, Random random) {
		return getPoiManager().getRandom(predicate, predicate2, occupancy, blockPos, i, random);
	}

	public boolean poiExists(BlockPos blockPos, Predicate<PoiType> predicate) {
		return getPoiManager().exists(blockPos, predicate);
	}

	public Optional<PoiType> getType(BlockPos blockPos) {
		return getPoiManager().getType(blockPos);
	}

	public int sectionsToVillage(SectionPos sectionPos) {
		return getPoiManager().sectionsToVillage(sectionPos);
	}

	public void checkConsistencyWithBlocks(ChunkPos chunkPos, LevelChunkSection levelChunkSection) {
		getPoiManager().checkConsistencyWithBlocks(chunkPos, levelChunkSection);
	}

	public void ensureLoadedAndValid(LevelReader levelReader, BlockPos blockPos, int i) {
		getPoiManager().ensureLoadedAndValid(levelReader, blockPos, i);
	}

	public StructureTemplateWrapper getOrCreateStructureTemplate(ResourceLocation resourceLocation) {
		return StructureTemplateWrapper.of(getStructureManager().getOrCreate(resourceLocation));
	}

	@Nullable
	public StructureTemplateWrapper getStructureTemplate(ResourceLocation resourceLocation) {
		return StructureTemplateWrapper.of(getStructureManager().get(resourceLocation));
	}

	public StructureTemplateWrapper readStructure(CompoundTag compoundTag) {
		return StructureTemplateWrapper.of(getStructureManager().readStructure(compoundTag));
	}

	public StructureFeatureManager forWorldGenRegion(WorldGenRegion worldGenRegion) {
		return getStructureFeatureManager().forWorldGenRegion(worldGenRegion);
	}

	public Stream<? extends StructureStartWrapper<?>> startsForFeature(SectionPos sectionPos, StructureFeature<?> structureFeature) {
		return StructureStartWrapper.of(getStructureFeatureManager().startsForFeature(sectionPos, structureFeature));
	}

	@Nullable
	public StructureStartWrapper<?> getStartForFeature(SectionPos sectionPos, StructureFeature<?> structureFeature, FeatureAccess featureAccess) {
		return StructureStartWrapper.of(getStructureFeatureManager().getStartForFeature(sectionPos, structureFeature, featureAccess));
	}

	public void setStartForFeature(SectionPos sectionPos, StructureFeature<?> structureFeature, StructureStart<?> structureStart, FeatureAccess featureAccess) {
		getStructureFeatureManager().setStartForFeature(sectionPos, structureFeature, structureStart, featureAccess);
	}

	public void addReferenceForFeature(SectionPos sectionPos, StructureFeature<?> structureFeature, long l, FeatureAccess featureAccess) {
		getStructureFeatureManager().addReferenceForFeature(sectionPos, structureFeature, l, featureAccess);
	}

	public boolean shouldGenerateFeatures() {
		return getStructureFeatureManager().shouldGenerateFeatures();
	}

	public StructureStartWrapper<?> getStructureAt(BlockPos blockPos, boolean bl, StructureFeature<?> structureFeature) {
		return StructureStartWrapper.of(getStructureFeatureManager().getStructureAt(blockPos, bl, structureFeature));
	}
}