package pie.ilikepiefoo.mixin;

import com.google.common.collect.ImmutableMap;
import dev.latvian.kubejs.item.ItemStackJS;
import dev.latvian.kubejs.world.BlockContainerJS;
import dev.latvian.kubejs.world.ExplosionJS;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.CrashReportCategory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Position;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.Tag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Mixin(value = BlockContainerJS.class, remap = false)
public abstract class MixinBlockContainerJS {

	@Shadow
	@Final
	public Level minecraftLevel;
	@Shadow
	@Final
	private BlockPos pos;

	@Shadow
	public abstract BlockPos getPos();

	@Shadow
	@Nullable
	public abstract BlockEntity getEntity();

	@Shadow
	@Nullable
	public abstract CompoundTag getEntityData();

	public void initCache() {
		getBlockState().initCache();
	}

	public Block getBlock() {
		return getBlockState().getBlock();
	}

	public Material getMaterial() {
		return getBlockState().getMaterial();
	}

	public boolean isValidSpawn(BlockGetter blockGetter,
								BlockPos blockPos,
								EntityType<?> entityType) {
		return getBlockState().isValidSpawn(blockGetter, blockPos, entityType);
	}

	public boolean propagatesSkylightDown(BlockGetter blockGetter, BlockPos blockPos) {
		return getBlockState().propagatesSkylightDown(blockGetter, blockPos);
	}

	public int getLightBlock(BlockGetter blockGetter, BlockPos blockPos) {
		return getBlockState().getLightBlock(blockGetter, blockPos);
	}

	public VoxelShape getFaceOcclusionShape(BlockGetter blockGetter,
											BlockPos blockPos,
											Direction direction) {
		return getBlockState().getFaceOcclusionShape(blockGetter, blockPos, direction);
	}

	public VoxelShape getOcclusionShape(BlockGetter blockGetter,
										BlockPos blockPos) {
		return getBlockState().getOcclusionShape(blockGetter, blockPos);
	}

	public boolean hasLargeCollisionShape() {
		return getBlockState().hasLargeCollisionShape();
	}

	public boolean useShapeForLightOcclusion() {
		return getBlockState().useShapeForLightOcclusion();
	}

	public int getLightEmission() {
		return getBlockState().getLightEmission();
	}

	public boolean isAir() {
		return getBlockState().isAir();
	}

	public MaterialColor getMapColor(BlockGetter blockGetter,
									 BlockPos blockPos) {
		return getBlockState().getMapColor(blockGetter, blockPos);
	}

	public RenderShape getRenderShape() {
		return getBlockState().getRenderShape();
	}

	@Environment(EnvType.CLIENT)
	public boolean emissiveRendering(BlockGetter blockGetter, BlockPos blockPos) {
		return getBlockState().emissiveRendering(blockGetter, blockPos);
	}

	@Environment(EnvType.CLIENT)
	public float getShadeBrightness(BlockGetter blockGetter, BlockPos blockPos) {
		return getBlockState().getShadeBrightness(blockGetter, blockPos);
	}

	public boolean isRedstoneConductor(BlockGetter blockGetter, BlockPos blockPos) {
		return getBlockState().isRedstoneConductor(blockGetter, blockPos);
	}

	public boolean isSignalSource() {
		return getBlockState().isSignalSource();
	}

	public int getSignal(BlockGetter blockGetter, BlockPos blockPos, Direction direction) {
		return getBlockState().getSignal(blockGetter, blockPos, direction);
	}

	public boolean hasAnalogOutputSignal() {
		return getBlockState().hasAnalogOutputSignal();
	}

	public int getAnalogOutputSignal(Level level, BlockPos blockPos) {
		return getBlockState().getAnalogOutputSignal(level, blockPos);
	}

	public float getDestroySpeed(BlockGetter blockGetter, BlockPos blockPos) {
		return getBlockState().getDestroySpeed(blockGetter, blockPos);
	}

	public float getDestroyProgress(Player player,
									BlockGetter blockGetter,
									BlockPos blockPos) {
		return getBlockState().getDestroyProgress(player, blockGetter, blockPos);
	}

	public int getDirectSignal(BlockGetter blockGetter, BlockPos blockPos, Direction direction) {
		return getBlockState().getDirectSignal(blockGetter, blockPos, direction);
	}

	public PushReaction getPistonPushReaction() {
		return getBlockState().getPistonPushReaction();
	}

	public boolean isSolidRender(BlockGetter blockGetter, BlockPos blockPos) {
		return getBlockState().isSolidRender(blockGetter, blockPos);
	}

	public boolean canOcclude() {
		return getBlockState().canOcclude();
	}

	@Environment(EnvType.CLIENT)
	public boolean skipRendering(BlockState blockState, Direction direction) {
		return getBlockState().skipRendering(blockState, direction);
	}

	public VoxelShape getShape(BlockGetter blockGetter, BlockPos blockPos) {
		return getBlockState().getShape(blockGetter, blockPos);
	}

	public VoxelShape getShape(BlockGetter blockGetter,
							   BlockPos blockPos,
							   CollisionContext collisionContext) {
		return getBlockState().getShape(blockGetter, blockPos, collisionContext);
	}

	public VoxelShape getCollisionShape(BlockGetter blockGetter,
										BlockPos blockPos) {
		return getBlockState().getCollisionShape(blockGetter, blockPos);
	}

	public VoxelShape getCollisionShape(BlockGetter blockGetter,
										BlockPos blockPos,
										CollisionContext collisionContext) {
		return getBlockState().getCollisionShape(blockGetter, blockPos, collisionContext);
	}

	public VoxelShape getBlockSupportShape(BlockGetter blockGetter,
										   BlockPos blockPos) {
		return getBlockState().getBlockSupportShape(blockGetter, blockPos);
	}

	public VoxelShape getVisualShape(BlockGetter blockGetter,
									 BlockPos blockPos,
									 CollisionContext collisionContext) {
		return getBlockState().getVisualShape(blockGetter, blockPos, collisionContext);
	}

	public VoxelShape getInteractionShape(BlockGetter blockGetter,
										  BlockPos blockPos) {
		return getBlockState().getInteractionShape(blockGetter, blockPos);
	}

	public boolean entityCanStandOn(BlockGetter blockGetter, BlockPos blockPos, Entity entity) {
		return getBlockState().entityCanStandOn(blockGetter, blockPos, entity);
	}

	public boolean entityCanStandOnFace(BlockGetter blockGetter,
										BlockPos blockPos,
										Entity entity,
										Direction direction) {
		return getBlockState().entityCanStandOnFace(blockGetter, blockPos, entity, direction);
	}

	public Vec3 getOffset(BlockGetter blockGetter, BlockPos blockPos) {
		return getBlockState().getOffset(blockGetter, blockPos);
	}

	public boolean triggerEvent(Level level, BlockPos blockPos, int i, int j) {
		return getBlockState().triggerEvent(level, blockPos, i, j);
	}

	public void neighborChanged(Level level, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl) {
		getBlockState().neighborChanged(level, blockPos, block, blockPos2, bl);
	}

	public void updateNeighbourShapes(LevelAccessor levelAccessor, BlockPos blockPos, int i) {
		getBlockState().updateNeighbourShapes(levelAccessor, blockPos, i);
	}

	public void updateNeighbourShapes(LevelAccessor levelAccessor, BlockPos blockPos, int i, int j) {
		getBlockState().updateNeighbourShapes(levelAccessor, blockPos, i, j);
	}

	public void updateIndirectNeighbourShapes(LevelAccessor levelAccessor, BlockPos blockPos, int i) {
		getBlockState().updateIndirectNeighbourShapes(levelAccessor, blockPos, i);
	}

	public void updateIndirectNeighbourShapes(LevelAccessor levelAccessor, BlockPos blockPos, int i, int j) {
		getBlockState().updateIndirectNeighbourShapes(levelAccessor, blockPos, i, j);
	}

	public void onPlace(Level level, BlockPos blockPos, BlockState blockState, boolean bl) {
		getBlockState().onPlace(level, blockPos, blockState, bl);
	}

	public void onRemove(Level level, BlockPos blockPos, BlockState blockState, boolean bl) {
		getBlockState().onRemove(level, blockPos, blockState, bl);
	}

	public void tick(ServerLevel serverLevel, BlockPos blockPos, Random random) {
		getBlockState().tick(serverLevel, blockPos, random);
	}

	public void randomTick(ServerLevel serverLevel, BlockPos blockPos, Random random) {
		getBlockState().randomTick(serverLevel, blockPos, random);
	}

	public void entityInside(Level level, BlockPos blockPos, Entity entity) {
		getBlockState().entityInside(level, blockPos, entity);
	}

	public void spawnAfterBreak(ServerLevel serverLevel, BlockPos blockPos, ItemStack itemStack) {
		getBlockState().spawnAfterBreak(serverLevel, blockPos, itemStack);
	}

	public List<ItemStackJS> getDrops(LootContext.Builder builder) {
		return getBlockState().getDrops(builder).parallelStream().map(ItemStackJS::new).collect(Collectors.toList());
	}

	public InteractionResult use(Level level,
								 Player player,
								 InteractionHand interactionHand,
								 BlockHitResult blockHitResult) {
		return getBlockState().use(level, player, interactionHand, blockHitResult);
	}

	public void attack(Level level, BlockPos blockPos, Player player) {
		getBlockState().attack(level, blockPos, player);
	}

	public boolean isSuffocating(BlockGetter blockGetter, BlockPos blockPos) {
		return getBlockState().isSuffocating(blockGetter, blockPos);
	}

	@Environment(EnvType.CLIENT)
	public boolean isViewBlocking(BlockGetter blockGetter, BlockPos blockPos) {
		return getBlockState().isViewBlocking(blockGetter, blockPos);
	}

	public BlockState updateShape(Direction direction,
								  BlockState blockState,
								  LevelAccessor levelAccessor,
								  BlockPos blockPos,
								  BlockPos blockPos2) {
		return getBlockState().updateShape(direction, blockState, levelAccessor, blockPos, blockPos2);
	}

	public boolean isPathfindable(BlockGetter blockGetter,
								  BlockPos blockPos,
								  PathComputationType pathComputationType) {
		return getBlockState().isPathfindable(blockGetter, blockPos, pathComputationType);
	}

	public boolean canBeReplaced(BlockPlaceContext blockPlaceContext) {
		return getBlockState().canBeReplaced(blockPlaceContext);
	}

	public boolean canBeReplaced(Fluid fluid) {
		return getBlockState().canBeReplaced(fluid);
	}

	public boolean canSurvive(LevelReader levelReader, BlockPos blockPos) {
		return getBlockState().canSurvive(levelReader, blockPos);
	}

	public boolean hasPostProcess(BlockGetter blockGetter, BlockPos blockPos) {
		return getBlockState().hasPostProcess(blockGetter, blockPos);
	}

	@Nullable
	public MenuProvider getMenuProvider(Level level, BlockPos blockPos) {
		return getBlockState().getMenuProvider(level, blockPos);
	}

	public boolean is(Tag<Block> tag,
					  Predicate<BlockBehaviour.BlockStateBase> predicate) {
		return getBlockState().is(tag, predicate);
	}

	public boolean is(Block block) {
		return getBlockState().is(block);
	}

	public FluidState getFluidState() {
		return getBlockState().getFluidState();
	}

	public float getVolume() {
		return getSoundType().getVolume();
	}

	public float getPitch() {
		return getSoundType().getPitch();
	}

	public boolean isRandomlyTicking(BlockState blockState) {
		return getBlock().isRandomlyTicking(blockState);
	}

	public boolean propagatesSkylightDown(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
		return getBlock().propagatesSkylightDown(blockState, blockGetter, blockPos);
	}

	@Environment(EnvType.CLIENT)
	public void animateTick(BlockState blockState, Level level, BlockPos blockPos, Random random) {
		getBlock().animateTick(blockState, level, blockPos, random);
	}

	public void destroy(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState) {
		getBlock().destroy(levelAccessor, blockPos, blockState);
	}

	public void wasExploded(Level level, BlockPos blockPos, Explosion explosion) {
		getBlock().wasExploded(level, blockPos, explosion);
	}

	public void stepOn(Level level, BlockPos blockPos, Entity entity) {
		getBlock().stepOn(level, blockPos, entity);
	}

	@Nullable
	public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
		return getBlock().getStateForPlacement(blockPlaceContext);
	}

	public void playerDestroy(Level level,
							  Player player,
							  BlockPos blockPos,
							  BlockState blockState,
							  @Nullable BlockEntity blockEntity,
							  ItemStack itemStack) {
		getBlock().playerDestroy(level, player, blockPos, blockState, blockEntity, itemStack);
	}

	public void setPlacedBy(Level level,
							BlockPos blockPos,
							BlockState blockState,
							@Nullable LivingEntity livingEntity,
							ItemStack itemStack) {
		getBlock().setPlacedBy(level, blockPos, blockState, livingEntity, itemStack);
	}

	public boolean isPossibleToRespawnInThis() {
		return getBlock().isPossibleToRespawnInThis();
	}

	@Environment(EnvType.CLIENT)
	public MutableComponent getName() {
		return getBlock().getName();
	}

	public String getDescriptionId() {
		return getBlock().getDescriptionId();
	}

	public void fallOn(Level level, BlockPos blockPos, Entity entity, float f) {
		getBlock().fallOn(level, blockPos, entity, f);
	}

	public void updateEntityAfterFallOn(BlockGetter blockGetter, Entity entity) {
		getBlock().updateEntityAfterFallOn(blockGetter, entity);
	}

	@Environment(EnvType.CLIENT)
	public ItemStack getCloneItemStack(BlockGetter blockGetter, BlockPos blockPos, BlockState blockState) {
		return getBlock().getCloneItemStack(blockGetter, blockPos, blockState);
	}

	public void fillItemCategory(CreativeModeTab creativeModeTab,
								 NonNullList<ItemStack> nonNullList) {
		getBlock().fillItemCategory(creativeModeTab, nonNullList);
	}

	public float getFriction() {
		return getBlock().getFriction();
	}

	public float getSpeedFactor() {
		return getBlock().getSpeedFactor();
	}

	public float getJumpFactor() {
		return getBlock().getJumpFactor();
	}

	public void playerWillDestroy(Level level, BlockPos blockPos, BlockState blockState, Player player) {
		getBlock().playerWillDestroy(level, blockPos, blockState, player);
	}

	public void handleRain(Level level, BlockPos blockPos) {
		getBlock().handleRain(level, blockPos);
	}

	public boolean dropFromExplosion(Explosion explosion) {
		return getBlock().dropFromExplosion(explosion);
	}

	public StateDefinition<Block, BlockState> getStateDefinition() {
		return getBlock().getStateDefinition();
	}

	public BlockState defaultBlockState() {
		return getBlock().defaultBlockState();
	}

	public SoundType getSoundType(BlockState blockState) {
		return getBlock().getSoundType(blockState);
	}

	public Item asItem() {
		return getBlock().asItem();
	}

	public boolean hasDynamicShape() {
		return getBlock().hasDynamicShape();
	}

	@Environment(EnvType.CLIENT)
	public void appendHoverText(ItemStack itemStack,
								@Nullable BlockGetter blockGetter,
								List<Component> list,
								TooltipFlag tooltipFlag) {
		getBlock().appendHoverText(itemStack, blockGetter, list, tooltipFlag);
	}

	@Deprecated
	public void updateIndirectNeighbourShapes(BlockState blockState, LevelAccessor levelAccessor, BlockPos blockPos, int i, int j) {
		getBlock().updateIndirectNeighbourShapes(blockState, levelAccessor, blockPos, i, j);
	}

	@Deprecated
	public boolean isPathfindable(BlockState blockState,
								  BlockGetter blockGetter,
								  BlockPos blockPos,
								  PathComputationType pathComputationType) {
		return getBlock().isPathfindable(blockState, blockGetter, blockPos, pathComputationType);
	}

	@Deprecated
	public BlockState updateShape(BlockState blockState,
								  Direction direction,
								  BlockState blockState2,
								  LevelAccessor levelAccessor,
								  BlockPos blockPos,
								  BlockPos blockPos2) {
		return getBlock().updateShape(blockState, direction, blockState2, levelAccessor, blockPos, blockPos2);
	}

	@Environment(EnvType.CLIENT)
	@Deprecated
	public boolean skipRendering(BlockState blockState, BlockState blockState2, Direction direction) {
		return getBlock().skipRendering(blockState, blockState2, direction);
	}

	@Deprecated
	public void neighborChanged(BlockState blockState, Level level, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl) {
		getBlock().neighborChanged(blockState, level, blockPos, block, blockPos2, bl);
	}

	@Deprecated
	public void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
		getBlock().onPlace(blockState, level, blockPos, blockState2, bl);
	}

	@Deprecated
	public void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
		getBlock().onRemove(blockState, level, blockPos, blockState2, bl);
	}

	@Deprecated
	public InteractionResult use(BlockState blockState,
								 Level level,
								 BlockPos blockPos,
								 Player player,
								 InteractionHand interactionHand,
								 BlockHitResult blockHitResult) {
		return getBlock().use(blockState, level, blockPos, player, interactionHand, blockHitResult);
	}

	@Deprecated
	public boolean triggerEvent(BlockState blockState, Level level, BlockPos blockPos, int i, int j) {
		return getBlock().triggerEvent(blockState, level, blockPos, i, j);
	}

	@Deprecated
	public RenderShape getRenderShape(BlockState blockState) {
		return getBlock().getRenderShape(blockState);
	}

	@Deprecated
	public boolean useShapeForLightOcclusion(BlockState blockState) {
		return getBlock().useShapeForLightOcclusion(blockState);
	}

	@Deprecated
	public boolean isSignalSource(BlockState blockState) {
		return getBlock().isSignalSource(blockState);
	}

	@Deprecated
	public PushReaction getPistonPushReaction(BlockState blockState) {
		return getBlock().getPistonPushReaction(blockState);
	}

	@Deprecated
	public FluidState getFluidState(BlockState blockState) {
		return getBlock().getFluidState(blockState);
	}

	@Deprecated
	public boolean hasAnalogOutputSignal(BlockState blockState) {
		return getBlock().hasAnalogOutputSignal(blockState);
	}

	public BlockBehaviour.OffsetType getOffsetType() {
		return getBlock().getOffsetType();
	}

	@Deprecated
	public BlockState rotate(BlockState blockState, Rotation rotation) {
		return getBlock().rotate(blockState, rotation);
	}

	@Deprecated
	public BlockState mirror(BlockState blockState, Mirror mirror) {
		return getBlock().mirror(blockState, mirror);
	}

	@Deprecated
	public boolean canBeReplaced(BlockState blockState, BlockPlaceContext blockPlaceContext) {
		return getBlock().canBeReplaced(blockState, blockPlaceContext);
	}

	@Deprecated
	public boolean canBeReplaced(BlockState blockState, Fluid fluid) {
		return getBlock().canBeReplaced(blockState, fluid);
	}

	@Deprecated
	public List<ItemStack> getDrops(BlockState blockState, LootContext.Builder builder) {
		return getBlock().getDrops(blockState, builder);
	}

	@Environment(EnvType.CLIENT)
	@Deprecated
	public long getSeed(BlockState blockState, BlockPos blockPos) {
		return getBlock().getSeed(blockState, blockPos);
	}

	@Deprecated
	public VoxelShape getOcclusionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
		return getBlock().getOcclusionShape(blockState, blockGetter, blockPos);
	}

	@Deprecated
	public VoxelShape getBlockSupportShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
		return getBlock().getBlockSupportShape(blockState, blockGetter, blockPos);
	}

	@Deprecated
	public VoxelShape getInteractionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
		return getBlock().getInteractionShape(blockState, blockGetter, blockPos);
	}

	@Deprecated
	public int getLightBlock(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
		return getBlock().getLightBlock(blockState, blockGetter, blockPos);
	}

	@Deprecated
	@Nullable
	public MenuProvider getMenuProvider(BlockState blockState, Level level, BlockPos blockPos) {
		return getBlock().getMenuProvider(blockState, level, blockPos);
	}

	@Deprecated
	public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
		return getBlock().canSurvive(blockState, levelReader, blockPos);
	}

	@Environment(EnvType.CLIENT)
	@Deprecated
	public float getShadeBrightness(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
		return getBlock().getShadeBrightness(blockState, blockGetter, blockPos);
	}

	@Deprecated
	public int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos blockPos) {
		return getBlock().getAnalogOutputSignal(blockState, level, blockPos);
	}

	@Deprecated
	public VoxelShape getShape(BlockState blockState,
							   BlockGetter blockGetter,
							   BlockPos blockPos,
							   CollisionContext collisionContext) {
		return getBlock().getShape(blockState, blockGetter, blockPos, collisionContext);
	}

	@Deprecated
	public VoxelShape getCollisionShape(BlockState blockState,
										BlockGetter blockGetter,
										BlockPos blockPos,
										CollisionContext collisionContext) {
		return getBlock().getCollisionShape(blockState, blockGetter, blockPos, collisionContext);
	}

	@Deprecated
	public VoxelShape getVisualShape(BlockState blockState,
									 BlockGetter blockGetter,
									 BlockPos blockPos,
									 CollisionContext collisionContext) {
		return getBlock().getVisualShape(blockState, blockGetter, blockPos, collisionContext);
	}

	@Deprecated
	public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, Random random) {
		getBlock().randomTick(blockState, serverLevel, blockPos, random);
	}

	@Deprecated
	public void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, Random random) {
		getBlock().tick(blockState, serverLevel, blockPos, random);
	}

	@Deprecated
	public float getDestroyProgress(BlockState blockState, Player player, BlockGetter blockGetter, BlockPos blockPos) {
		return getBlock().getDestroyProgress(blockState, player, blockGetter, blockPos);
	}

	@Deprecated
	public void spawnAfterBreak(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, ItemStack itemStack) {
		getBlock().spawnAfterBreak(blockState, serverLevel, blockPos, itemStack);
	}

	@Deprecated
	public void attack(BlockState blockState, Level level, BlockPos blockPos, Player player) {
		getBlock().attack(blockState, level, blockPos, player);
	}

	@Deprecated
	public int getSignal(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, Direction direction) {
		return getBlock().getSignal(blockState, blockGetter, blockPos, direction);
	}

	@Deprecated
	public void entityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity) {
		getBlock().entityInside(blockState, level, blockPos, entity);
	}

	@Deprecated
	public int getDirectSignal(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, Direction direction) {
		return getBlock().getDirectSignal(blockState, blockGetter, blockPos, direction);
	}

	public boolean isEntityBlock() {
		return getBlock().isEntityBlock();
	}

	public ResourceLocation getLootTable() {
		return getBlock().getLootTable();
	}

	public MaterialColor defaultMaterialColor() {
		return getBlock().defaultMaterialColor();
	}

	@Environment(EnvType.CLIENT)
	public SoundEvent getBreakSound() {
		return getSoundType().getBreakSound();
	}

	public SoundEvent getStepSound() {
		return getSoundType().getStepSound();
	}

	public SoundEvent getPlaceSound() {
		return getSoundType().getPlaceSound();
	}

	@Environment(EnvType.CLIENT)
	public SoundEvent getHitSound() {
		return getSoundType().getHitSound();
	}

	public SoundEvent getFallSound() {
		return getSoundType().getFallSound();
	}

	public boolean isRandomlyTicking() {
		return getBlockState().isRandomlyTicking();
	}

	@Environment(EnvType.CLIENT)
	public long getSeed(BlockPos blockPos) {
		return getBlockState().getSeed(blockPos);
	}

	public SoundType getSoundType() {
		return getBlockState().getSoundType();
	}

	public void onProjectileHit(Level level,
								BlockState blockState,
								BlockHitResult blockHitResult,
								Projectile projectile) {
		getBlockState().onProjectileHit(level, blockState, blockHitResult, projectile);
	}

	public boolean isFaceSturdy(BlockGetter blockGetter, BlockPos blockPos, Direction direction) {
		return getBlockState().isFaceSturdy(blockGetter, blockPos, direction);
	}

	public boolean isFaceSturdy(BlockGetter blockGetter,
								BlockPos blockPos,
								Direction direction,
								SupportType supportType) {
		return getBlockState().isFaceSturdy(blockGetter, blockPos, direction, supportType);
	}

	public boolean isCollisionShapeFullBlock(BlockGetter blockGetter, BlockPos blockPos) {
		return getBlockState().isCollisionShapeFullBlock(blockGetter, blockPos);
	}

	public boolean requiresCorrectToolForDrops() {
		return getBlockState().requiresCorrectToolForDrops();
	}

	public <T extends Comparable<T>> BlockState cycle(Property<T> property) {
		return getBlockState().cycle(property);
	}

	public Collection<Property<?>> getProperties() {
		return getBlockState().getProperties();
	}

	public <T extends Comparable<T>> boolean hasProperty(Property<T> property) {
		return getBlockState().hasProperty(property);
	}

	public <T extends Comparable<T>> T getValue(Property<T> property) {
		return getBlockState().getValue(property);
	}

	public <T extends Comparable<T>> Optional<T> getOptionalValue(Property<T> property) {
		return getBlockState().getOptionalValue(property);
	}

	public <T extends Comparable<T>, V extends T> BlockState setValue(Property<T> property, V comparable) {
		return getBlockState().setValue(property, comparable);
	}

	public ImmutableMap<Property<?>, Comparable<?>> getValues() {
		return getBlockState().getValues();
	}

	public boolean isSource() {
		return getFluidState().isSource();
	}

	public float getHeight(BlockGetter blockGetter, BlockPos blockPos) {
		return getFluidState().getHeight(blockGetter, blockPos);
	}

	public float getOwnHeight() {
		return getFluidState().getOwnHeight();
	}

	public int getAmount() {
		return getFluidState().getAmount();
	}

	@Environment(EnvType.CLIENT)
	public boolean shouldRenderBackwardUpFace(BlockGetter blockGetter, BlockPos blockPos) {
		return getFluidState().shouldRenderBackwardUpFace(blockGetter, blockPos);
	}

	public void tick(Level level, BlockPos blockPos) {
		getFluidState().tick(level, blockPos);
	}

	@Environment(EnvType.CLIENT)
	public void animateTick(Level level, BlockPos blockPos, Random random) {
		getFluidState().animateTick(level, blockPos, random);
	}

	public void randomTick(Level level, BlockPos blockPos, Random random) {
		getFluidState().randomTick(level, blockPos, random);
	}

	public Vec3 getFlow(BlockGetter blockGetter, BlockPos blockPos) {
		return getFluidState().getFlow(blockGetter, blockPos);
	}

	public BlockState createLegacyBlock() {
		return getFluidState().createLegacyBlock();
	}

	@Environment(EnvType.CLIENT)
	@Nullable
	public ParticleOptions getDripParticle() {
		return getFluidState().getDripParticle();
	}

	public boolean is(Tag<Fluid> tag) {
		return getFluidState().is(tag);
	}

	public float getExplosionResistance() {
		return getFluidState().getExplosionResistance();
	}

	public boolean canBeReplacedWith(BlockGetter blockGetter, BlockPos blockPos, Fluid fluid, Direction direction) {
		return getFluidState().canBeReplacedWith(blockGetter, blockPos, fluid, direction);
	}

	public void populateNeighbours(Map<Map<Property<?>, Comparable<?>>, FluidState> map) {
		getFluidState().populateNeighbours(map);
	}

	@Shadow
	public abstract ExplosionJS createExplosion();

	@Shadow
	public abstract BlockState getBlockState();

	public void setLevelAndPosition(Level level, BlockPos blockPos) {
		Objects.requireNonNull(getEntity()).setLevelAndPosition(level, blockPos);
	}

	public void load(BlockState blockState, CompoundTag compoundTag) {
		Objects.requireNonNull(getEntity()).load(blockState, compoundTag);
	}

	public CompoundTag save(CompoundTag compoundTag) {
		return Objects.requireNonNull(getEntity()).save(compoundTag);
	}

	public void setChanged() {
		Objects.requireNonNull(getEntity()).setChanged();
	}

	@Environment(EnvType.CLIENT)
	public double getViewDistance() {
		return Objects.requireNonNull(getEntity()).getViewDistance();
	}

	@Nullable
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return Objects.requireNonNull(getEntity()).getUpdatePacket();
	}

	public CompoundTag getUpdateTag() {
		return Objects.requireNonNull(getEntity()).getUpdateTag();
	}

	public boolean isRemoved() {
		return Objects.requireNonNull(getEntity()).isRemoved();
	}

	public void setRemoved() {
		Objects.requireNonNull(getEntity()).setRemoved();
	}

	public void clearRemoved() {
		Objects.requireNonNull(getEntity()).clearRemoved();
	}

	public boolean triggerEvent(int i, int j) {
		return Objects.requireNonNull(getEntity()).triggerEvent(i, j);
	}

	public void clearCache() {
		Objects.requireNonNull(getEntity()).clearCache();
	}

	public void fillCrashReportCategory(CrashReportCategory crashReportCategory) {
		Objects.requireNonNull(getEntity()).fillCrashReportCategory(crashReportCategory);
	}

	public void setPosition(BlockPos blockPos) {
		Objects.requireNonNull(getEntity()).setPosition(blockPos);
	}

	public boolean onlyOpCanSetNbt() {
		return Objects.requireNonNull(getEntity()).onlyOpCanSetNbt();
	}

	public void mirror(Mirror mirror) {
		Objects.requireNonNull(getEntity()).mirror(mirror);
	}

	public BlockEntityType<?> getType() {
		return Objects.requireNonNull(getEntity()).getType();
	}

	public void logInvalidState() {
		Objects.requireNonNull(getEntity()).logInvalidState();
	}

	public BlockContainerJS getInstance() {
		return ((BlockContainerJS) (Object) this);
	}

	public BlockContainerJS offset(double d, double e, double f) {
		return new BlockContainerJS(minecraftLevel, pos.offset(d, e, f));
	}

	public BlockContainerJS offset(Vec3i vec3i) {
		return new BlockContainerJS(minecraftLevel, pos.offset(vec3i));
	}

	public BlockContainerJS subtract(Vec3i vec3i) {
		return new BlockContainerJS(minecraftLevel, pos.subtract(vec3i));
	}

	public BlockContainerJS above(int i) {
		return new BlockContainerJS(minecraftLevel, pos.above(i));
	}

	public BlockContainerJS below(int i) {
		return new BlockContainerJS(minecraftLevel, pos.below(i));
	}

	public BlockContainerJS north(int i) {
		return new BlockContainerJS(minecraftLevel, pos.north(i));
	}

	public BlockContainerJS south(int i) {
		return new BlockContainerJS(minecraftLevel, pos.south(i));
	}

	public BlockContainerJS west(int i) {
		return new BlockContainerJS(minecraftLevel, pos.west(i));
	}

	public BlockContainerJS east(int i) {
		return new BlockContainerJS(minecraftLevel, pos.east(i));
	}

	public BlockContainerJS relative(Direction direction) {
		return new BlockContainerJS(minecraftLevel, pos.relative(direction));
	}

	public BlockContainerJS relative(Direction direction, int i) {
		return new BlockContainerJS(minecraftLevel, pos.relative(direction, i));
	}

	public BlockContainerJS relative(Direction.Axis axis, int i) {
		return new BlockContainerJS(minecraftLevel, pos.relative(axis, i));
	}

	public BlockContainerJS rotate(Rotation rotation) {
		return new BlockContainerJS(minecraftLevel, pos.rotate(rotation));
	}

	public BlockContainerJS cross(Vec3i vec3i) {
		return new BlockContainerJS(minecraftLevel, pos.cross(vec3i));
	}

	public int compareTo(Vec3i vec3i) {
		return pos.compareTo(vec3i);
	}

	public boolean closerThan(Vec3i vec3i, double d) {
		return pos.closerThan(vec3i, d);
	}

	public boolean closerThan(Position position, double d) {
		return pos.closerThan(position, d);
	}

	public double distSqr(Vec3i vec3i) {
		return pos.distSqr(vec3i);
	}

	public double distSqr(Position position, boolean bl) {
		return pos.distSqr(position, bl);
	}

	public double distSqr(double d, double e, double f, boolean bl) {
		return pos.distSqr(d, e, f, bl);
	}

	public int distManhattan(Vec3i vec3i) {
		return pos.distManhattan(vec3i);
	}

	public int get(Direction.Axis axis) {
		return pos.get(axis);
	}
}
