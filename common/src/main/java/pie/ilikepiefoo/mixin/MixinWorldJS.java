package pie.ilikepiefoo.mixin;

import dev.latvian.kubejs.entity.EntityJS;
import dev.latvian.kubejs.player.PlayerJS;
import dev.latvian.kubejs.server.ServerJS;
import dev.latvian.kubejs.world.BlockContainerJS;
import dev.latvian.kubejs.world.WorldJS;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagContainer;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.TickList;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkSource;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.scores.Scoreboard;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mixin (value = WorldJS.class, remap = false)
public abstract class MixinWorldJS {

	@Shadow
	@Final
	public Level minecraftLevel;

	@Shadow
	public abstract BlockContainerJS getBlock(BlockPos pos);

	@Shadow
	public abstract @org.jetbrains.annotations.Nullable ServerJS getServer();

	@Shadow
	public @org.jetbrains.annotations.Nullable
	abstract PlayerJS getPlayer(@org.jetbrains.annotations.Nullable Entity entity);

	public WorldJS getInstance() {
		return ((WorldJS) (Object) this);
	}

	public LevelChunk getChunkAt(BlockPos blockPos) {
		return minecraftLevel.getChunkAt(blockPos);
	}

	public LevelChunk getChunk(int i, int j) {
		return minecraftLevel.getChunk(i, j);
	}

	public ChunkAccess getChunk(int i, int j, ChunkStatus chunkStatus, boolean bl) {
		return minecraftLevel.getChunk(i, j, chunkStatus, bl);
	}

	public boolean setBlock(BlockPos blockPos, BlockState blockState, int i) {
		return minecraftLevel.setBlock(blockPos, blockState, i);
	}

	public boolean setBlock(BlockPos blockPos, BlockState blockState, int i, int j) {
		return minecraftLevel.setBlock(blockPos, blockState, i, j);
	}

	public void onBlockStateChange(BlockPos blockPos,
								   BlockState blockState,
								   BlockState blockState2) {
		minecraftLevel.onBlockStateChange(blockPos, blockState, blockState2);
	}

	public boolean removeBlock(BlockPos blockPos, boolean bl) {
		return minecraftLevel.removeBlock(blockPos, bl);
	}

	public boolean destroyBlock(BlockPos blockPos, boolean bl, @org.jetbrains.annotations.Nullable Entity entity, int i) {
		return minecraftLevel.destroyBlock(blockPos, bl, entity, i);
	}

	public boolean setBlockAndUpdate(BlockPos blockPos, BlockState blockState) {
		return minecraftLevel.setBlockAndUpdate(blockPos, blockState);
	}

	public void sendBlockUpdated(BlockPos blockPos,
								 BlockState blockState,
								 BlockState blockState2,
								 int i) {
		minecraftLevel.sendBlockUpdated(blockPos, blockState, blockState2, i);
	}

	public void setBlocksDirty(BlockPos blockPos,
							   BlockState blockState,
							   BlockState blockState2) {
		minecraftLevel.setBlocksDirty(blockPos, blockState, blockState2);
	}

	public void updateNeighborsAt(BlockPos blockPos, Block block) {
		minecraftLevel.updateNeighborsAt(blockPos, block);
	}

	public void updateNeighborsAtExceptFromFacing(BlockPos blockPos,
												  Block block,
												  Direction direction) {
		minecraftLevel.updateNeighborsAtExceptFromFacing(blockPos, block, direction);
	}

	public void neighborChanged(BlockPos blockPos, Block block, BlockPos blockPos2) {
		minecraftLevel.neighborChanged(blockPos, block, blockPos2);
	}

	public int getHeight(Heightmap.Types types, int i, int j) {
		return minecraftLevel.getHeight(types, i, j);
	}

	public LevelLightEngine getLightEngine() {
		return minecraftLevel.getLightEngine();
	}

	public BlockState getBlockState(BlockPos blockPos) {
		return minecraftLevel.getBlockState(blockPos);
	}

	public FluidState getFluidState(BlockPos blockPos) {
		return minecraftLevel.getFluidState(blockPos);
	}

	public boolean isDay() {
		return minecraftLevel.isDay();
	}

	public boolean isNight() {
		return minecraftLevel.isNight();
	}

	public void playSound(@org.jetbrains.annotations.Nullable Player player,
						  BlockPos blockPos,
						  SoundEvent soundEvent,
						  SoundSource soundSource,
						  float f,
						  float g) {
		minecraftLevel.playSound(player, blockPos, soundEvent, soundSource, f, g);
	}

	public void playSound(@org.jetbrains.annotations.Nullable Player player,
						  double d,
						  double e,
						  double f,
						  SoundEvent soundEvent,
						  SoundSource soundSource,
						  float g,
						  float h) {
		minecraftLevel.playSound(player, d, e, f, soundEvent, soundSource, g, h);
	}

	public void playSound(@org.jetbrains.annotations.Nullable Player player,
						  Entity entity,
						  SoundEvent soundEvent,
						  SoundSource soundSource,
						  float f,
						  float g) {
		minecraftLevel.playSound(player, entity, soundEvent, soundSource, f, g);
	}

	public void playLocalSound(double d,
							   double e,
							   double f,
							   SoundEvent soundEvent,
							   SoundSource soundSource,
							   float g,
							   float h,
							   boolean bl) {
		minecraftLevel.playLocalSound(d, e, f, soundEvent, soundSource, g, h, bl);
	}

	public void addParticle(ParticleOptions particleOptions, double d, double e, double f, double g, double h, double i) {
		minecraftLevel.addParticle(particleOptions, d, e, f, g, h, i);
	}

	@Environment(EnvType.CLIENT)
	public void addParticle(ParticleOptions particleOptions,
							boolean bl,
							double d,
							double e,
							double f,
							double g,
							double h,
							double i) {
		minecraftLevel.addParticle(particleOptions, bl, d, e, f, g, h, i);
	}

	public void addAlwaysVisibleParticle(ParticleOptions particleOptions,
										 double d,
										 double e,
										 double f,
										 double g,
										 double h,
										 double i) {
		minecraftLevel.addAlwaysVisibleParticle(particleOptions, d, e, f, g, h, i);
	}

	public void addAlwaysVisibleParticle(ParticleOptions particleOptions,
										 boolean bl,
										 double d,
										 double e,
										 double f,
										 double g,
										 double h,
										 double i) {
		minecraftLevel.addAlwaysVisibleParticle(particleOptions, bl, d, e, f, g, h, i);
	}

	public float getSunAngle(float f) {
		return minecraftLevel.getSunAngle(f);
	}

	public boolean addBlockEntity(BlockEntity blockEntity) {
		return minecraftLevel.addBlockEntity(blockEntity);
	}

	public void addAllPendingBlockEntities(Collection<BlockEntity> collection) {
		minecraftLevel.addAllPendingBlockEntities(collection);
	}

	public void tickBlockEntities() {
		minecraftLevel.tickBlockEntities();
	}

	public void guardEntityTick(Consumer<Entity> consumer, Entity entity) {
		minecraftLevel.guardEntityTick(consumer, entity);
	}

	public Explosion explode(@org.jetbrains.annotations.Nullable Entity entity,
							 double d,
							 double e,
							 double f,
							 float g,
							 Explosion.BlockInteraction blockInteraction) {
		return minecraftLevel.explode(entity, d, e, f, g, blockInteraction);
	}

	public Explosion explode(@org.jetbrains.annotations.Nullable Entity entity,
							 double d,
							 double e,
							 double f,
							 float g,
							 boolean bl,
							 Explosion.BlockInteraction blockInteraction) {
		return minecraftLevel.explode(entity, d, e, f, g, bl, blockInteraction);
	}

	public Explosion explode(@org.jetbrains.annotations.Nullable Entity entity,
							 @org.jetbrains.annotations.Nullable DamageSource damageSource,
							 @org.jetbrains.annotations.Nullable ExplosionDamageCalculator explosionDamageCalculator,
							 double d,
							 double e,
							 double f,
							 float g,
							 boolean bl,
							 Explosion.BlockInteraction blockInteraction) {
		return minecraftLevel.explode(entity, damageSource, explosionDamageCalculator, d, e, f, g, bl, blockInteraction);
	}

	public String gatherChunkSourceStats() {
		return minecraftLevel.gatherChunkSourceStats();
	}

	@org.jetbrains.annotations.Nullable
	public BlockEntity getBlockEntity(BlockPos blockPos) {
		return minecraftLevel.getBlockEntity(blockPos);
	}

	public void setBlockEntity(BlockPos blockPos, @org.jetbrains.annotations.Nullable BlockEntity blockEntity) {
		minecraftLevel.setBlockEntity(blockPos, blockEntity);
	}

	public void removeBlockEntity(BlockPos blockPos) {
		minecraftLevel.removeBlockEntity(blockPos);
	}

	public boolean isLoaded(BlockPos blockPos) {
		return minecraftLevel.isLoaded(blockPos);
	}

	public boolean loadedAndEntityCanStandOnFace(BlockPos blockPos, Entity entity, Direction direction) {
		return minecraftLevel.loadedAndEntityCanStandOnFace(blockPos, entity, direction);
	}

	public boolean loadedAndEntityCanStandOn(BlockPos blockPos, Entity entity) {
		return minecraftLevel.loadedAndEntityCanStandOn(blockPos, entity);
	}

	public void updateSkyBrightness() {
		minecraftLevel.updateSkyBrightness();
	}

	public void setSpawnSettings(boolean bl, boolean bl2) {
		minecraftLevel.setSpawnSettings(bl, bl2);
	}

	public void close() throws IOException {
		minecraftLevel.close();
	}

	@org.jetbrains.annotations.Nullable
	public BlockGetter getChunkForCollisions(int i, int j) {
		return minecraftLevel.getChunkForCollisions(i, j);
	}

	public List<EntityJS> getEntities(@org.jetbrains.annotations.Nullable Entity entity,
									  AABB aABB,
									  @org.jetbrains.annotations.Nullable Predicate<? super Entity> predicate) {
		return minecraftLevel.getEntities(entity, aABB, predicate).parallelStream().map((entityToMap) -> new EntityJS(getInstance(), entityToMap)).collect(Collectors.toList());
	}

	public <T extends Entity> List<T> getEntities(@org.jetbrains.annotations.Nullable EntityType<T> entityType,
												  AABB aABB,
												  Predicate<? super T> predicate) {
		return minecraftLevel.getEntities(entityType, aABB, predicate);
	}

	public <T extends Entity> List<T> getEntitiesOfClass(Class<? extends T> class_,
														 AABB aABB,
														 @org.jetbrains.annotations.Nullable Predicate<? super T> predicate) {
		return minecraftLevel.getEntitiesOfClass(class_, aABB, predicate);
	}

	public <T extends Entity> List<T> getLoadedEntitiesOfClass(Class<? extends T> class_,
															   AABB aABB,
															   @org.jetbrains.annotations.Nullable Predicate<? super T> predicate) {
		return minecraftLevel.getLoadedEntitiesOfClass(class_, aABB, predicate);
	}

	@org.jetbrains.annotations.Nullable
	public EntityJS getEntity(int i) {
		Entity entity = minecraftLevel.getEntity(i);
		return entity == null ? null : new EntityJS(getInstance(), entity);
	}

	public void blockEntityChanged(BlockPos blockPos, BlockEntity blockEntity) {
		minecraftLevel.blockEntityChanged(blockPos, blockEntity);
	}

	public int getSeaLevel() {
		return minecraftLevel.getSeaLevel();
	}

	public int getDirectSignalTo(BlockPos blockPos) {
		return minecraftLevel.getDirectSignalTo(blockPos);
	}

	public boolean hasSignal(BlockPos blockPos, Direction direction) {
		return minecraftLevel.hasSignal(blockPos, direction);
	}

	public int getSignal(BlockPos blockPos, Direction direction) {
		return minecraftLevel.getSignal(blockPos, direction);
	}

	public boolean hasNeighborSignal(BlockPos blockPos) {
		return minecraftLevel.hasNeighborSignal(blockPos);
	}

	public int getBestNeighborSignal(BlockPos blockPos) {
		return minecraftLevel.getBestNeighborSignal(blockPos);
	}

	@Environment(EnvType.CLIENT)
	public void disconnect() {
		minecraftLevel.disconnect();
	}

	public long getGameTime() {
		return minecraftLevel.getGameTime();
	}

	public long getDayTime() {
		return minecraftLevel.getDayTime();
	}

	public boolean mayInteract(Player player, BlockPos blockPos) {
		return minecraftLevel.mayInteract(player, blockPos);
	}

	public void broadcastEntityEvent(Entity entity, byte b) {
		minecraftLevel.broadcastEntityEvent(entity, b);
	}

	public void blockEvent(BlockPos blockPos, Block block, int i, int j) {
		minecraftLevel.blockEvent(blockPos, block, i, j);
	}

	public LevelData getLevelData() {
		return minecraftLevel.getLevelData();
	}

	public GameRules getGameRules() {
		return minecraftLevel.getGameRules();
	}

	public float getThunderLevel(float f) {
		return minecraftLevel.getThunderLevel(f);
	}

	@Environment(EnvType.CLIENT)
	public void setThunderLevel(float f) {
		minecraftLevel.setThunderLevel(f);
	}

	public float getRainLevel(float f) {
		return minecraftLevel.getRainLevel(f);
	}

	@Environment(EnvType.CLIENT)
	public void setRainLevel(float f) {
		minecraftLevel.setRainLevel(f);
	}

	public boolean isThundering() {
		return minecraftLevel.isThundering();
	}

	public boolean isRaining() {
		return minecraftLevel.isRaining();
	}

	public boolean isRainingAt(BlockPos blockPos) {
		return minecraftLevel.isRainingAt(blockPos);
	}

	public boolean isHumidAt(BlockPos blockPos) {
		return minecraftLevel.isHumidAt(blockPos);
	}

	@org.jetbrains.annotations.Nullable
	public MapItemSavedData getMapData(String string) {
		return minecraftLevel.getMapData(string);
	}

	public void setMapData(MapItemSavedData mapItemSavedData) {
		minecraftLevel.setMapData(mapItemSavedData);
	}

	public int getFreeMapId() {
		return minecraftLevel.getFreeMapId();
	}

	public void globalLevelEvent(int i, BlockPos blockPos, int j) {
		minecraftLevel.globalLevelEvent(i, blockPos, j);
	}

	public CrashReportCategory fillReportDetails(CrashReport crashReport) {
		return minecraftLevel.fillReportDetails(crashReport);
	}

	public void destroyBlockProgress(int i, BlockPos blockPos, int j) {
		minecraftLevel.destroyBlockProgress(i, blockPos, j);
	}

	@Environment(EnvType.CLIENT)
	public void createFireworks(double d,
								double e,
								double f,
								double g,
								double h,
								double i,
								@org.jetbrains.annotations.Nullable CompoundTag compoundTag) {
		minecraftLevel.createFireworks(d, e, f, g, h, i, compoundTag);
	}

	public Scoreboard getScoreboard() {
		return minecraftLevel.getScoreboard();
	}

	public void updateNeighbourForOutputSignal(BlockPos blockPos, Block block) {
		minecraftLevel.updateNeighbourForOutputSignal(blockPos, block);
	}

	public DifficultyInstance getCurrentDifficultyAt(BlockPos blockPos) {
		return minecraftLevel.getCurrentDifficultyAt(blockPos);
	}

	public int getSkyDarken() {
		return minecraftLevel.getSkyDarken();
	}

	public void setSkyFlashTime(int i) {
		minecraftLevel.setSkyFlashTime(i);
	}

	public WorldBorder getWorldBorder() {
		return minecraftLevel.getWorldBorder();
	}

	public void sendPacketToServer(Packet<?> packet) {
		minecraftLevel.sendPacketToServer(packet);
	}

	public DimensionType dimensionType() {
		return minecraftLevel.dimensionType();
	}

	public ResourceKey<Level> dimension() {
		return minecraftLevel.dimension();
	}

	public Random getRandom() {
		return minecraftLevel.getRandom();
	}

	public boolean isStateAtPosition(BlockPos blockPos, Predicate<BlockState> predicate) {
		return minecraftLevel.isStateAtPosition(blockPos, predicate);
	}

	public RecipeManager getRecipeManager() {
		return minecraftLevel.getRecipeManager();
	}

	public TagContainer getTagManager() {
		return minecraftLevel.getTagManager();
	}

	public BlockPos getBlockRandomPos(int i, int j, int k, int l) {
		return minecraftLevel.getBlockRandomPos(i, j, k, l);
	}

	public boolean noSave() {
		return minecraftLevel.noSave();
	}

	public ProfilerFiller getProfiler() {
		return minecraftLevel.getProfiler();
	}

	public Supplier<ProfilerFiller> getProfilerSupplier() {
		return minecraftLevel.getProfilerSupplier();
	}

	public BiomeManager getBiomeManager() {
		return minecraftLevel.getBiomeManager();
	}

	public boolean isDebug() {
		return minecraftLevel.isDebug();
	}

	public long dayTime() {
		return minecraftLevel.dayTime();
	}

	public TickList<Block> getBlockTicks() {
		return minecraftLevel.getBlockTicks();
	}

	public TickList<Fluid> getLiquidTicks() {
		return minecraftLevel.getLiquidTicks();
	}

	public Difficulty getDifficulty() {
		return minecraftLevel.getDifficulty();
	}

	public ChunkSource getChunkSource() {
		return minecraftLevel.getChunkSource();
	}

	public boolean hasChunk(int i, int j) {
		return minecraftLevel.hasChunk(i, j);
	}

	public void blockUpdated(BlockPos blockPos, Block block) {
		minecraftLevel.blockUpdated(blockPos, block);
	}

	public void levelEvent(@org.jetbrains.annotations.Nullable Player player, int i, BlockPos blockPos, int j) {
		minecraftLevel.levelEvent(player, i, blockPos, j);
	}

	public int getHeight() {
		return minecraftLevel.getHeight();
	}

	public void levelEvent(int i, BlockPos blockPos, int j) {
		minecraftLevel.levelEvent(i, blockPos, j);
	}

	public Stream<VoxelShape> getEntityCollisions(@org.jetbrains.annotations.Nullable Entity entity,
												  AABB aABB,
												  Predicate<Entity> predicate) {
		return minecraftLevel.getEntityCollisions(entity, aABB, predicate);
	}

	public boolean isUnobstructed(@org.jetbrains.annotations.Nullable Entity entity,
								  VoxelShape voxelShape) {
		return minecraftLevel.isUnobstructed(entity, voxelShape);
	}

	public BlockPos getHeightmapPos(Heightmap.Types types, BlockPos blockPos) {
		return minecraftLevel.getHeightmapPos(types, blockPos);
	}

	public RegistryAccess registryAccess() {
		return minecraftLevel.registryAccess();
	}

	public Optional<ResourceKey<Biome>> getBiomeName(BlockPos blockPos) {
		return minecraftLevel.getBiomeName(blockPos);
	}

	public List<? extends Player> players() {
		return minecraftLevel.players();
	}

	public List<Entity> getEntities(@org.jetbrains.annotations.Nullable Entity entity,
									AABB aABB) {
		return minecraftLevel.getEntities(entity, aABB);
	}

	public <T extends Entity> List<T> getEntitiesOfClass(Class<? extends T> class_, AABB aABB) {
		return minecraftLevel.getEntitiesOfClass(class_, aABB);
	}

	public <T extends Entity> List<T> getLoadedEntitiesOfClass(Class<? extends T> class_, AABB aABB) {
		return minecraftLevel.getLoadedEntitiesOfClass(class_, aABB);
	}

	@org.jetbrains.annotations.Nullable
	public PlayerJS getNearestPlayer(double d,
								   double e,
								   double f,
								   double g,
								   @org.jetbrains.annotations.Nullable Predicate<Entity> predicate) {
		return getPlayer(minecraftLevel.getNearestPlayer(d, e, f, g, predicate));
	}

	@org.jetbrains.annotations.Nullable
	public PlayerJS getNearestPlayer(Entity entity, double d) {
		return getPlayer(minecraftLevel.getNearestPlayer(entity, d));
	}

	@org.jetbrains.annotations.Nullable
	public PlayerJS getNearestPlayer(double d, double e, double f, double g, boolean bl) {
		return getPlayer(minecraftLevel.getNearestPlayer(d, e, f, g, bl));
	}

	public boolean hasNearbyAlivePlayer(double d, double e, double f, double g) {
		return minecraftLevel.hasNearbyAlivePlayer(d, e, f, g);
	}

	@org.jetbrains.annotations.Nullable
	public PlayerJS getNearestPlayer(TargetingConditions targetingConditions,
								   LivingEntity livingEntity) {
		return getPlayer(minecraftLevel.getNearestPlayer(targetingConditions, livingEntity));
	}

	@org.jetbrains.annotations.Nullable
	public PlayerJS getNearestPlayer(TargetingConditions targetingConditions,
								   LivingEntity livingEntity,
								   double d,
								   double e,
								   double f) {
		return getPlayer(minecraftLevel.getNearestPlayer(targetingConditions, livingEntity, d, e, f));
	}

	@org.jetbrains.annotations.Nullable
	public PlayerJS getNearestPlayer(TargetingConditions targetingConditions,
								   double d,
								   double e,
								   double f) {
		return getPlayer(minecraftLevel.getNearestPlayer(targetingConditions, d, e, f));
	}

	@org.jetbrains.annotations.Nullable
	public <T extends LivingEntity> T getNearestEntity(Class<? extends T> class_,
													   TargetingConditions targetingConditions,
													   @org.jetbrains.annotations.Nullable LivingEntity livingEntity,
													   double d,
													   double e,
													   double f,
													   AABB aABB) {
		return minecraftLevel.getNearestEntity(class_, targetingConditions, livingEntity, d, e, f, aABB);
	}

	@org.jetbrains.annotations.Nullable
	public <T extends LivingEntity> T getNearestLoadedEntity(Class<? extends T> class_,
															 TargetingConditions targetingConditions,
															 @org.jetbrains.annotations.Nullable LivingEntity livingEntity,
															 double d,
															 double e,
															 double f,
															 AABB aABB) {
		return minecraftLevel.getNearestLoadedEntity(class_, targetingConditions, livingEntity, d, e, f, aABB);
	}

	@org.jetbrains.annotations.Nullable
	public <T extends LivingEntity> T getNearestEntity(List<? extends T> list,
													   TargetingConditions targetingConditions,
													   @org.jetbrains.annotations.Nullable LivingEntity livingEntity,
													   double d,
													   double e,
													   double f) {
		return minecraftLevel.getNearestEntity(list, targetingConditions, livingEntity, d, e, f);
	}

	public List<Player> getNearbyPlayers(TargetingConditions targetingConditions,
										 LivingEntity livingEntity,
										 AABB aABB) {
		return minecraftLevel.getNearbyPlayers(targetingConditions, livingEntity, aABB);
	}

	public <T extends LivingEntity> List<T> getNearbyEntities(Class<? extends T> class_,
															  TargetingConditions targetingConditions,
															  LivingEntity livingEntity,
															  AABB aABB) {
		return minecraftLevel.getNearbyEntities(class_, targetingConditions, livingEntity, aABB);
	}

	@org.jetbrains.annotations.Nullable
	public PlayerJS getPlayerByUUID(UUID uUID) {
		return getPlayer(minecraftLevel.getPlayerByUUID(uUID));
	}

	public Biome getBiome(BlockPos blockPos) {
		return minecraftLevel.getBiome(blockPos);
	}

	public Stream<BlockState> getBlockStatesIfLoaded(AABB aABB) {
		return minecraftLevel.getBlockStatesIfLoaded(aABB);
	}

	@Environment(EnvType.CLIENT)
	public int getBlockTint(BlockPos blockPos, ColorResolver colorResolver) {
		return minecraftLevel.getBlockTint(blockPos, colorResolver);
	}

	public Biome getNoiseBiome(int i, int j, int k) {
		return minecraftLevel.getNoiseBiome(i, j, k);
	}

	public Biome getUncachedNoiseBiome(int i, int j, int k) {
		return minecraftLevel.getUncachedNoiseBiome(i, j, k);
	}

	public boolean isEmptyBlock(BlockPos blockPos) {
		return minecraftLevel.isEmptyBlock(blockPos);
	}

	public boolean canSeeSkyFromBelowWater(BlockPos blockPos) {
		return minecraftLevel.canSeeSkyFromBelowWater(blockPos);
	}

	@Deprecated
	public float getBrightness(BlockPos blockPos) {
		return minecraftLevel.getBrightness(blockPos);
	}

	public int getDirectSignal(BlockPos blockPos, Direction direction) {
		return minecraftLevel.getDirectSignal(blockPos, direction);
	}

	public ChunkAccess getChunk(BlockPos blockPos) {
		return minecraftLevel.getChunk(blockPos);
	}

	public ChunkAccess getChunk(int i, int j, ChunkStatus chunkStatus) {
		return minecraftLevel.getChunk(i, j, chunkStatus);
	}

	public boolean isWaterAt(BlockPos blockPos) {
		return minecraftLevel.isWaterAt(blockPos);
	}

	public boolean containsAnyLiquid(AABB aABB) {
		return minecraftLevel.containsAnyLiquid(aABB);
	}

	public int getMaxLocalRawBrightness(BlockPos blockPos) {
		return minecraftLevel.getMaxLocalRawBrightness(blockPos);
	}

	public int getMaxLocalRawBrightness(BlockPos blockPos, int i) {
		return minecraftLevel.getMaxLocalRawBrightness(blockPos, i);
	}

	@Deprecated
	public boolean hasChunkAt(BlockPos blockPos) {
		return minecraftLevel.hasChunkAt(blockPos);
	}

	@Deprecated
	public boolean hasChunksAt(BlockPos blockPos, BlockPos blockPos2) {
		return minecraftLevel.hasChunksAt(blockPos, blockPos2);
	}

	@Deprecated
	public boolean hasChunksAt(int i, int j, int k, int l, int m, int n) {
		return minecraftLevel.hasChunksAt(i, j, k, l, m, n);
	}

	@Environment(EnvType.CLIENT)
	public float getShade(Direction direction, boolean bl) {
		return minecraftLevel.getShade(direction, bl);
	}

	public int getBrightness(LightLayer lightLayer, BlockPos blockPos) {
		return minecraftLevel.getBrightness(lightLayer, blockPos);
	}

	public int getRawBrightness(BlockPos blockPos, int i) {
		return minecraftLevel.getRawBrightness(blockPos, i);
	}

	public boolean canSeeSky(BlockPos blockPos) {
		return minecraftLevel.canSeeSky(blockPos);
	}

	public int getLightEmission(BlockPos blockPos) {
		return minecraftLevel.getLightEmission(blockPos);
	}

	public int getMaxLightLevel() {
		return minecraftLevel.getMaxLightLevel();
	}

	public int getMaxBuildHeight() {
		return minecraftLevel.getMaxBuildHeight();
	}

	public Stream<BlockState> getBlockStates(AABB aABB) {
		return minecraftLevel.getBlockStates(aABB);
	}

	public BlockHitResult clip(ClipContext clipContext) {
		return minecraftLevel.clip(clipContext);
	}

	@org.jetbrains.annotations.Nullable
	public BlockHitResult clipWithInteractionOverride(Vec3 vec3,
													  Vec3 vec32,
													  BlockPos blockPos,
													  VoxelShape voxelShape,
													  BlockState blockState) {
		return minecraftLevel.clipWithInteractionOverride(vec3, vec32, blockPos, voxelShape, blockState);
	}

	public double getBlockFloorHeight(VoxelShape voxelShape,
									  Supplier<VoxelShape> supplier) {
		return minecraftLevel.getBlockFloorHeight(voxelShape, supplier);
	}

	public double getBlockFloorHeight(BlockPos blockPos) {
		return minecraftLevel.getBlockFloorHeight(blockPos);
	}

	public boolean isUnobstructed(BlockState blockState,
								  BlockPos blockPos,
								  CollisionContext collisionContext) {
		return minecraftLevel.isUnobstructed(blockState, blockPos, collisionContext);
	}

	public boolean isUnobstructed(Entity entity) {
		return minecraftLevel.isUnobstructed(entity);
	}

	public boolean noCollision(AABB aABB) {
		return minecraftLevel.noCollision(aABB);
	}

	public boolean noCollision(Entity entity) {
		return minecraftLevel.noCollision(entity);
	}

	public boolean noCollision(Entity entity, AABB aABB) {
		return minecraftLevel.noCollision(entity, aABB);
	}

	public boolean noCollision(@org.jetbrains.annotations.Nullable Entity entity,
							   AABB aABB,
							   Predicate<Entity> predicate) {
		return minecraftLevel.noCollision(entity, aABB, predicate);
	}

	public Stream<VoxelShape> getCollisions(@org.jetbrains.annotations.Nullable Entity entity,
											AABB aABB,
											Predicate<Entity> predicate) {
		return minecraftLevel.getCollisions(entity, aABB, predicate);
	}

	public Stream<VoxelShape> getBlockCollisions(@org.jetbrains.annotations.Nullable Entity entity,
												 AABB aABB) {
		return minecraftLevel.getBlockCollisions(entity, aABB);
	}

	@Environment(EnvType.CLIENT)
	public boolean noBlockCollision(@org.jetbrains.annotations.Nullable Entity entity,
									AABB aABB,
									BiPredicate<BlockState, BlockPos> biPredicate) {
		return minecraftLevel.noBlockCollision(entity, aABB, biPredicate);
	}

	public Stream<VoxelShape> getBlockCollisions(@org.jetbrains.annotations.Nullable Entity entity,
												 AABB aABB,
												 BiPredicate<BlockState, BlockPos> biPredicate) {
		return minecraftLevel.getBlockCollisions(entity, aABB, biPredicate);
	}

	public boolean destroyBlock(BlockPos blockPos, boolean bl) {
		return minecraftLevel.destroyBlock(blockPos, bl);
	}

	public boolean destroyBlock(BlockPos blockPos, boolean bl, @org.jetbrains.annotations.Nullable Entity entity) {
		return minecraftLevel.destroyBlock(blockPos, bl, entity);
	}

	public boolean addFreshEntity(Entity entity) {
		return minecraftLevel.addFreshEntity(entity);
	}

	public float getMoonBrightness() {
		return minecraftLevel.getMoonBrightness();
	}

	public float getTimeOfDay(float f) {
		return minecraftLevel.getTimeOfDay(f);
	}

	@Environment(EnvType.CLIENT)
	public int getMoonPhase() {
		return minecraftLevel.getMoonPhase();
	}

	@Nullable
	public ServerLevel getServerLevel() {
		if(minecraftLevel instanceof ServerLevel) {
			return ((ServerLevel) minecraftLevel);
		}
		return null;
	}

	public boolean isClientSide() {
		return minecraftLevel.isClientSide();
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
