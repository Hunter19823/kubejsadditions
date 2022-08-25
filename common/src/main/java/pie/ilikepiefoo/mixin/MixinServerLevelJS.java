package pie.ilikepiefoo.mixin;

import dev.architectury.registry.registries.Registries;
import dev.latvian.mods.kubejs.level.ServerLevelJS;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
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
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raids;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.chunk.FeatureAccess;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.dimension.end.EndDragonFight;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.structure.StructureCheckResult;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.portal.PortalForcer;
import net.minecraft.world.level.storage.DimensionDataStorage;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import pie.ilikepiefoo.wrapper.StructureStartWrapper;
import pie.ilikepiefoo.wrapper.StructureTemplateWrapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Mixin(ServerLevelJS.class)
public class MixinServerLevelJS {
	public ServerLevelJS getInstance() {
		return ((ServerLevelJS) (Object) this);
	}

	private ServerLevel getMinecraftLeveInstance() {
		return (ServerLevel) ((ServerLevelJS) (Object) this).minecraftLevel;
	}

	public StructureFeatureManager getStructureFeatureManager() {
		return getMinecraftLeveInstance().structureFeatureManager();
	}

	public ServerScoreboard getScoreboard() {
		return getMinecraftLeveInstance().getScoreboard();
	}

	public List<? extends EnderDragon> getDragons() {
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
	public Optional<StructureTemplateWrapper> getStructureTemplate(ResourceLocation resourceLocation) {
		return StructureTemplateWrapper.of(getStructureManager().get(resourceLocation));
	}

	public StructureTemplateWrapper readStructure(CompoundTag compoundTag) {
		return StructureTemplateWrapper.of(getStructureManager().readStructure(compoundTag));
	}

	public StructureFeatureManager forWorldGenRegion(WorldGenRegion worldGenRegion) {
		return getStructureFeatureManager().forWorldGenRegion(worldGenRegion);
	}

	public List<StructureStartWrapper> startsForFeature(SectionPos sectionPos, ConfiguredStructureFeature<?, ?> structureFeature) {
		return getStructureFeatureManager().startsForFeature(sectionPos, structureFeature).stream().map(StructureStartWrapper::of).toList();
	}

	@Nullable
	public StructureStartWrapper getStartForFeature(SectionPos sectionPos, ConfiguredStructureFeature<?, ?> structureFeature, FeatureAccess featureAccess) {
		return StructureStartWrapper.of(getStructureFeatureManager().getStartForFeature(sectionPos, structureFeature, featureAccess));
	}

	public List<StructureStartWrapper> startsForFeature(SectionPos sectionPos, Predicate<ConfiguredStructureFeature<?, ?>> predicate) {
		return getStructureFeatureManager().startsForFeature(sectionPos, predicate).stream().map(StructureStartWrapper::of).toList();
	}

	public void fillStartsForFeature(ConfiguredStructureFeature<?, ?> configuredStructureFeature, LongSet longSet, Consumer<StructureStart> consumer) {
		getStructureFeatureManager().fillStartsForFeature(configuredStructureFeature, longSet, consumer);
	}

	public void setStartForFeature(SectionPos sectionPos, ConfiguredStructureFeature<?, ?> configuredStructureFeature, StructureStart structureStart, FeatureAccess featureAccess) {
		getStructureFeatureManager().setStartForFeature(sectionPos, configuredStructureFeature, structureStart, featureAccess);
	}

	public void addReferenceForFeature(SectionPos sectionPos, ConfiguredStructureFeature<?, ?> configuredStructureFeature, long l, FeatureAccess featureAccess) {
		getStructureFeatureManager().addReferenceForFeature(sectionPos, configuredStructureFeature, l, featureAccess);
	}

	public boolean shouldGenerateFeatures() {
		return getStructureFeatureManager().shouldGenerateFeatures();
	}

	public StructureStartWrapper getStructureAt(BlockPos blockPos, ConfiguredStructureFeature<?, ?> configuredStructureFeature) {
		return StructureStartWrapper.of(getStructureFeatureManager().getStructureAt(blockPos, configuredStructureFeature));
	}

	public StructureStartWrapper getStructureWithPieceAt(BlockPos blockPos, ResourceKey<ConfiguredStructureFeature<?, ?>> resourceKey) {
		return StructureStartWrapper.of(getStructureFeatureManager().getStructureWithPieceAt(blockPos, resourceKey));
	}

	public StructureStartWrapper getStructureWithPieceAt(BlockPos blockPos, ConfiguredStructureFeature<?, ?> configuredStructureFeature) {
		return StructureStartWrapper.of(getStructureFeatureManager().getStructureWithPieceAt(blockPos, configuredStructureFeature));
	}

	public boolean structureHasPieceAt(BlockPos blockPos, StructureStart structureStart) {
		return getStructureFeatureManager().structureHasPieceAt(blockPos, structureStart);
	}

	public boolean hasAnyStructureAt(BlockPos blockPos) {
		return getStructureFeatureManager().hasAnyStructureAt(blockPos);
	}

	public Map<ConfiguredStructureFeature<?, ?>, LongSet> getAllStructuresAt(BlockPos blockPos) {
		return getStructureFeatureManager().getAllStructuresAt(blockPos);
	}

	public StructureCheckResult checkStructurePresence(BlockPos blockPos, ConfiguredStructureFeature<?, ?> configuredStructureFeature, boolean bl) {
		return getStructureFeatureManager().checkStructurePresence(new ChunkPos(blockPos), configuredStructureFeature, bl);
	}

	public void addReference(StructureStart structureStart) {
		getStructureFeatureManager().addReference(structureStart);
	}

	public RegistryAccess registryAccess() {
		return getStructureFeatureManager().registryAccess();
	}
}