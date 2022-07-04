package pie.ilikepiefoo.mixin;

import dev.latvian.kubejs.entity.EntityJS;
import dev.latvian.kubejs.entity.ItemEntityJS;
import dev.latvian.kubejs.util.UtilsJS;
import dev.latvian.kubejs.world.WorldJS;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.CrashReportCategory;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.Tag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.Team;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mixin(value = EntityJS.class, remap = false)
public abstract class MixinEntityJS {

	public EntityJS getInstance() {
		return ((EntityJS) (Object) this);
	}

	@Shadow
	@Final
	public Entity minecraftEntity;

	@Shadow
	public abstract WorldJS getWorld();

	@Environment(EnvType.CLIENT)
	public boolean isColliding(BlockPos blockPos, BlockState blockState) {
		return minecraftEntity.isColliding(blockPos, blockState);
	}

	@Environment(EnvType.CLIENT)
	public int getTeamColor() {
		return minecraftEntity.getTeamColor();
	}

	public boolean isSpectator() {
		return minecraftEntity.isSpectator();
	}

	public void unRide() {
		minecraftEntity.unRide();
	}

	public void setPacketCoordinates(double d, double e, double f) {
		minecraftEntity.setPacketCoordinates(d, e, f);
	}

	public void setPacketCoordinates(Vec3 vec3) {
		minecraftEntity.setPacketCoordinates(vec3);
	}

	@Environment(EnvType.CLIENT)
	public Vec3 getPacketCoordinates() {
		return minecraftEntity.getPacketCoordinates();
	}

	public EntityType<?> getType() {
		return minecraftEntity.getType();
	}

	public int getId() {
		return minecraftEntity.getId();
	}

	public void setId(int i) {
		minecraftEntity.setId(i);
	}

	public Set<String> getTags() {
		return minecraftEntity.getTags();
	}

	public boolean addTag(String string) {
		return minecraftEntity.addTag(string);
	}

	public boolean removeTag(String string) {
		return minecraftEntity.removeTag(string);
	}

	public void kill() {
		minecraftEntity.kill();
	}

	public SynchedEntityData getEntityData() {
		return minecraftEntity.getEntityData();
	}

	public void remove() {
		minecraftEntity.remove();
	}

	public void setPose(Pose pose) {
		minecraftEntity.setPose(pose);
	}

	public Pose getPose() {
		return minecraftEntity.getPose();
	}

	public boolean closerThan(Entity entity, double d) {
		return minecraftEntity.closerThan(entity, d);
	}

	public void setPos(double d, double e, double f) {
		minecraftEntity.setPos(d, e, f);
	}

	@Environment(EnvType.CLIENT)
	public void turn(double d, double e) {
		minecraftEntity.turn(d, e);
	}

	public void tick() {
		minecraftEntity.tick();
	}

	public void baseTick() {
		minecraftEntity.baseTick();
	}

	public void setPortalCooldown() {
		minecraftEntity.setPortalCooldown();
	}

	public boolean isOnPortalCooldown() {
		return minecraftEntity.isOnPortalCooldown();
	}

	public int getPortalWaitTime() {
		return minecraftEntity.getPortalWaitTime();
	}

	public void setSecondsOnFire(int i) {
		minecraftEntity.setSecondsOnFire(i);
	}

	public void setRemainingFireTicks(int i) {
		minecraftEntity.setRemainingFireTicks(i);
	}

	public int getRemainingFireTicks() {
		return minecraftEntity.getRemainingFireTicks();
	}

	public void clearFire() {
		minecraftEntity.clearFire();
	}

	public boolean isFree(double d, double e, double f) {
		return minecraftEntity.isFree(d, e, f);
	}

	public void setOnGround(boolean bl) {
		minecraftEntity.setOnGround(bl);
	}

	public boolean isOnGround() {
		return minecraftEntity.isOnGround();
	}

	public void move(MoverType moverType, Vec3 vec3) {
		minecraftEntity.move(moverType, vec3);
	}

	public void setLocationFromBoundingbox() {
		minecraftEntity.setLocationFromBoundingbox();
	}

	public void playSound(SoundEvent soundEvent, float f, float g) {
		minecraftEntity.playSound(soundEvent, f, g);
	}

	public boolean isSilent() {
		return minecraftEntity.isSilent();
	}

	public void setSilent(boolean bl) {
		minecraftEntity.setSilent(bl);
	}

	public boolean isNoGravity() {
		return minecraftEntity.isNoGravity();
	}

	public void setNoGravity(boolean bl) {
		minecraftEntity.setNoGravity(bl);
	}

	public boolean fireImmune() {
		return minecraftEntity.fireImmune();
	}

	public boolean causeFallDamage(float f, float g) {
		return minecraftEntity.causeFallDamage(f, g);
	}

	public boolean isInWater() {
		return minecraftEntity.isInWater();
	}

	public boolean isInWaterOrRain() {
		return minecraftEntity.isInWaterOrRain();
	}

	public boolean isInWaterRainOrBubble() {
		return minecraftEntity.isInWaterRainOrBubble();
	}

	public boolean isInWaterOrBubble() {
		return minecraftEntity.isInWaterOrBubble();
	}

	public boolean isUnderWater() {
		return minecraftEntity.isUnderWater();
	}

	public void updateSwimming() {
		minecraftEntity.updateSwimming();
	}

	public boolean canSpawnSprintParticle() {
		return minecraftEntity.canSpawnSprintParticle();
	}

	public boolean isEyeInFluid(Tag<Fluid> tag) {
		return minecraftEntity.isEyeInFluid(tag);
	}

	public boolean isInLava() {
		return minecraftEntity.isInLava();
	}

	public void moveRelative(float f, Vec3 vec3) {
		minecraftEntity.moveRelative(f, vec3);
	}

	public float getBrightness() {
		return minecraftEntity.getBrightness();
	}

	public void setLevel(Level level) {
		minecraftEntity.setLevel(level);
	}

	public void absMoveTo(double d, double e, double f, float g, float h) {
		minecraftEntity.absMoveTo(d, e, f, g, h);
	}

	public void absMoveTo(double d, double e, double f) {
		minecraftEntity.absMoveTo(d, e, f);
	}

	public void moveTo(Vec3 vec3) {
		minecraftEntity.moveTo(vec3);
	}

	public void moveTo(double d, double e, double f) {
		minecraftEntity.moveTo(d, e, f);
	}

	public void moveTo(BlockPos blockPos, float f, float g) {
		minecraftEntity.moveTo(blockPos, f, g);
	}

	public void moveTo(double d, double e, double f, float g, float h) {
		minecraftEntity.moveTo(d, e, f, g, h);
	}

	public void setPosAndOldPos(double d, double e, double f) {
		minecraftEntity.setPosAndOldPos(d, e, f);
	}

	public float distanceTo(Entity entity) {
		return minecraftEntity.distanceTo(entity);
	}

	public double distanceToSqr(double d, double e, double f) {
		return minecraftEntity.distanceToSqr(d, e, f);
	}

	public double distanceToSqr(Entity entity) {
		return minecraftEntity.distanceToSqr(entity);
	}

	public double distanceToSqr(Vec3 vec3) {
		return minecraftEntity.distanceToSqr(vec3);
	}

	public void playerTouch(Player player) {
		minecraftEntity.playerTouch(player);
	}

	public void push(Entity entity) {
		minecraftEntity.push(entity);
	}

	public void push(double d, double e, double f) {
		minecraftEntity.push(d, e, f);
	}

	public boolean hurt(DamageSource damageSource, float f) {
		return minecraftEntity.hurt(damageSource, f);
	}

	public Vec3 getViewVector(float f) {
		return minecraftEntity.getViewVector(f);
	}

	public float getViewXRot(float f) {
		return minecraftEntity.getViewXRot(f);
	}

	public float getViewYRot(float f) {
		return minecraftEntity.getViewYRot(f);
	}

	public Vec3 getUpVector(float f) {
		return minecraftEntity.getUpVector(f);
	}

	public Vec3 getEyePosition(float f) {
		return minecraftEntity.getEyePosition(f);
	}

	@Environment(EnvType.CLIENT)
	public Vec3 getLightProbePosition(float f) {
		return minecraftEntity.getLightProbePosition(f);
	}

	@Environment(EnvType.CLIENT)
	public Vec3 getPosition(float f) {
		return minecraftEntity.getPosition(f);
	}

	public HitResult pick(double d, float f, boolean bl) {
		return minecraftEntity.pick(d, f, bl);
	}

	public boolean isPickable() {
		return minecraftEntity.isPickable();
	}

	public boolean isPushable() {
		return minecraftEntity.isPushable();
	}

	public void awardKillScore(Entity entity, int i, DamageSource damageSource) {
		minecraftEntity.awardKillScore(entity, i, damageSource);
	}

	@Environment(EnvType.CLIENT)
	public boolean shouldRender(double d, double e, double f) {
		return minecraftEntity.shouldRender(d, e, f);
	}

	@Environment(EnvType.CLIENT)
	public boolean shouldRenderAtSqrDistance(double d) {
		return minecraftEntity.shouldRenderAtSqrDistance(d);
	}

	public boolean saveAsPassenger(CompoundTag compoundTag) {
		return minecraftEntity.saveAsPassenger(compoundTag);
	}

	public boolean save(CompoundTag compoundTag) {
		return minecraftEntity.save(compoundTag);
	}

	public CompoundTag saveWithoutId(CompoundTag compoundTag) {
		return minecraftEntity.saveWithoutId(compoundTag);
	}

	public void load(CompoundTag compoundTag) {
		minecraftEntity.load(compoundTag);
	}

	/**
	 * Access widened by architectury-1.32.63.jar to accessible
	 */
	@Nullable
	public String getEncodeId() {
		return minecraftEntity.getEncodeId();
	}

	@Nullable
	public ItemEntityJS spawnAtLocation(ItemLike itemLike) {
		ItemEntity item = minecraftEntity.spawnAtLocation(itemLike);

		return (item == null) ? null : new ItemEntityJS(getWorld(),item);
	}

	@Nullable
	public ItemEntityJS spawnAtLocation(ItemLike itemLike, int i) {
		ItemEntity item = minecraftEntity.spawnAtLocation(itemLike,i);

		return (item == null) ? null : new ItemEntityJS(getWorld(),item);
	}

	@Nullable
	public ItemEntityJS spawnAtLocation(ItemStack itemStack) {
		ItemEntity item = minecraftEntity.spawnAtLocation(itemStack);

		return (item == null) ? null : new ItemEntityJS(getWorld(),item);
	}

	@Nullable
	public ItemEntityJS spawnAtLocation(ItemStack itemStack, float f) {
		ItemEntity item = minecraftEntity.spawnAtLocation(itemStack, f);

		return (item == null) ? null : new ItemEntityJS(getWorld(),item);
	}

	public boolean isAlive() {
		return minecraftEntity.isAlive();
	}

	public boolean isInWall() {
		return minecraftEntity.isInWall();
	}

	public InteractionResult interact(Player player,
									  InteractionHand interactionHand) {
		return minecraftEntity.interact(player, interactionHand);
	}

	public boolean canCollideWith(Entity entity) {
		return minecraftEntity.canCollideWith(entity);
	}

	public boolean canBeCollidedWith() {
		return minecraftEntity.canBeCollidedWith();
	}

	public void rideTick() {
		minecraftEntity.rideTick();
	}

	public void positionRider(Entity entity) {
		minecraftEntity.positionRider(entity);
	}

	@Environment(EnvType.CLIENT)
	public void onPassengerTurned(Entity entity) {
		minecraftEntity.onPassengerTurned(entity);
	}

	public double getMyRidingOffset() {
		return minecraftEntity.getMyRidingOffset();
	}

	public double getPassengersRidingOffset() {
		return minecraftEntity.getPassengersRidingOffset();
	}

	public boolean startRiding(Entity entity) {
		return minecraftEntity.startRiding(entity);
	}

	@Environment(EnvType.CLIENT)
	public boolean showVehicleHealth() {
		return minecraftEntity.showVehicleHealth();
	}

	public boolean startRiding(Entity entity, boolean bl) {
		return minecraftEntity.startRiding(entity, bl);
	}

	public void ejectPassengers() {
		minecraftEntity.ejectPassengers();
	}

	public void removeVehicle() {
		minecraftEntity.removeVehicle();
	}

	public void stopRiding() {
		minecraftEntity.stopRiding();
	}

	@Environment(EnvType.CLIENT)
	public void lerpTo(double d, double e, double f, float g, float h, int i, boolean bl) {
		minecraftEntity.lerpTo(d, e, f, g, h, i, bl);
	}

	@Environment(EnvType.CLIENT)
	public void lerpHeadTo(float f, int i) {
		minecraftEntity.lerpHeadTo(f, i);
	}

	public float getPickRadius() {
		return minecraftEntity.getPickRadius();
	}

	public Vec3 getLookAngle() {
		return minecraftEntity.getLookAngle();
	}

	public Vec2 getRotationVector() {
		return minecraftEntity.getRotationVector();
	}

	@Environment(EnvType.CLIENT)
	public Vec3 getForward() {
		return minecraftEntity.getForward();
	}

	public void handleInsidePortal(BlockPos blockPos) {
		minecraftEntity.handleInsidePortal(blockPos);
	}

	public int getDimensionChangingDelay() {
		return minecraftEntity.getDimensionChangingDelay();
	}

	@Environment(EnvType.CLIENT)
	public void lerpMotion(double d, double e, double f) {
		minecraftEntity.lerpMotion(d, e, f);
	}

	@Environment(EnvType.CLIENT)
	public void handleEntityEvent(byte b) {
		minecraftEntity.handleEntityEvent(b);
	}

	@Environment(EnvType.CLIENT)
	public void animateHurt() {
		minecraftEntity.animateHurt();
	}

	public Iterable<ItemStack> getHandSlots() {
		return minecraftEntity.getHandSlots();
	}

	public Iterable<ItemStack> getArmorSlots() {
		return minecraftEntity.getArmorSlots();
	}

	public Iterable<ItemStack> getAllSlots() {
		return minecraftEntity.getAllSlots();
	}

	public void setItemSlot(EquipmentSlot equipmentSlot, ItemStack itemStack) {
		minecraftEntity.setItemSlot(equipmentSlot, itemStack);
	}

	public boolean isOnFire() {
		return minecraftEntity.isOnFire();
	}

	public boolean isPassenger() {
		return minecraftEntity.isPassenger();
	}

	public boolean isVehicle() {
		return minecraftEntity.isVehicle();
	}

	public boolean rideableUnderWater() {
		return minecraftEntity.rideableUnderWater();
	}

	public void setShiftKeyDown(boolean bl) {
		minecraftEntity.setShiftKeyDown(bl);
	}

	public boolean isShiftKeyDown() {
		return minecraftEntity.isShiftKeyDown();
	}

	public boolean isSteppingCarefully() {
		return minecraftEntity.isSteppingCarefully();
	}

	public boolean isSuppressingBounce() {
		return minecraftEntity.isSuppressingBounce();
	}

	public boolean isDiscrete() {
		return minecraftEntity.isDiscrete();
	}

	public boolean isDescending() {
		return minecraftEntity.isDescending();
	}

	public boolean isCrouching() {
		return minecraftEntity.isCrouching();
	}

	public boolean isSprinting() {
		return minecraftEntity.isSprinting();
	}

	public void setSprinting(boolean bl) {
		minecraftEntity.setSprinting(bl);
	}

	public boolean isSwimming() {
		return minecraftEntity.isSwimming();
	}

	public boolean isVisuallySwimming() {
		return minecraftEntity.isVisuallySwimming();
	}

	@Environment(EnvType.CLIENT)
	public boolean isVisuallyCrawling() {
		return minecraftEntity.isVisuallyCrawling();
	}

	public void setSwimming(boolean bl) {
		minecraftEntity.setSwimming(bl);
	}

	public boolean isGlowing() {
		return minecraftEntity.isGlowing();
	}

	public void setGlowing(boolean bl) {
		minecraftEntity.setGlowing(bl);
	}

	public boolean isInvisible() {
		return minecraftEntity.isInvisible();
	}

	@Environment(EnvType.CLIENT)
	public boolean isInvisibleTo(Player player) {
		return minecraftEntity.isInvisibleTo(player);
	}

	@Nullable
	public Team getTeam() {
		return minecraftEntity.getTeam();
	}

	public boolean isAlliedTo(Entity entity) {
		return minecraftEntity.isAlliedTo(entity);
	}

	public boolean isAlliedTo(Team team) {
		return minecraftEntity.isAlliedTo(team);
	}

	public void setInvisible(boolean bl) {
		minecraftEntity.setInvisible(bl);
	}

	public int getMaxAirSupply() {
		return minecraftEntity.getMaxAirSupply();
	}

	public int getAirSupply() {
		return minecraftEntity.getAirSupply();
	}

	public void setAirSupply(int i) {
		minecraftEntity.setAirSupply(i);
	}

	public void thunderHit(ServerLevel serverLevel, LightningBolt lightningBolt) {
		minecraftEntity.thunderHit(serverLevel, lightningBolt);
	}

	public void onAboveBubbleCol(boolean bl) {
		minecraftEntity.onAboveBubbleCol(bl);
	}

	public void onInsideBubbleColumn(boolean bl) {
		minecraftEntity.onInsideBubbleColumn(bl);
	}

	public void killed(ServerLevel serverLevel, LivingEntity livingEntity) {
		minecraftEntity.killed(serverLevel, livingEntity);
	}

	public void makeStuckInBlock(BlockState blockState, Vec3 vec3) {
		minecraftEntity.makeStuckInBlock(blockState, vec3);
	}

	public Component getName() {
		return minecraftEntity.getName();
	}

	public boolean is(Entity entity) {
		return minecraftEntity.is(entity);
	}

	public float getYHeadRot() {
		return minecraftEntity.getYHeadRot();
	}

	public void setYHeadRot(float f) {
		minecraftEntity.setYHeadRot(f);
	}

	public void setYBodyRot(float f) {
		minecraftEntity.setYBodyRot(f);
	}

	public boolean isAttackable() {
		return minecraftEntity.isAttackable();
	}

	public boolean skipAttackInteraction(Entity entity) {
		return minecraftEntity.skipAttackInteraction(entity);
	}

	@Override
	public String toString() {
		return minecraftEntity.toString();
	}

	public boolean isInvulnerableTo(DamageSource damageSource) {
		return minecraftEntity.isInvulnerableTo(damageSource);
	}

	public boolean isInvulnerable() {
		return minecraftEntity.isInvulnerable();
	}

	public void setInvulnerable(boolean bl) {
		minecraftEntity.setInvulnerable(bl);
	}

	public void copyPosition(Entity entity) {
		minecraftEntity.copyPosition(entity);
	}

	public void restoreFrom(Entity entity) {
		minecraftEntity.restoreFrom(entity);
	}

	@Nullable
	public EntityJS changeDimension(ServerLevel serverLevel) {
		Entity entity = minecraftEntity.changeDimension(serverLevel);

		return entity == null ? null : new EntityJS(UtilsJS.getWorld(entity.level), entity);
	}

	public boolean canChangeDimensions() {
		return minecraftEntity.canChangeDimensions();
	}

	public float getBlockExplosionResistance(Explosion explosion,
											 BlockGetter blockGetter,
											 BlockPos blockPos,
											 BlockState blockState,
											 FluidState fluidState,
											 float f) {
		return minecraftEntity.getBlockExplosionResistance(explosion, blockGetter, blockPos, blockState, fluidState, f);
	}

	public boolean shouldBlockExplode(Explosion explosion,
									  BlockGetter blockGetter,
									  BlockPos blockPos,
									  BlockState blockState,
									  float f) {
		return minecraftEntity.shouldBlockExplode(explosion, blockGetter, blockPos, blockState, f);
	}

	public int getMaxFallDistance() {
		return minecraftEntity.getMaxFallDistance();
	}

	public boolean isIgnoringBlockTriggers() {
		return minecraftEntity.isIgnoringBlockTriggers();
	}

	public void fillCrashReportCategory(CrashReportCategory crashReportCategory) {
		minecraftEntity.fillCrashReportCategory(crashReportCategory);
	}

	@Environment(EnvType.CLIENT)
	public boolean displayFireAnimation() {
		return minecraftEntity.displayFireAnimation();
	}

	public void setUUID(UUID uUID) {
		minecraftEntity.setUUID(uUID);
	}

	public UUID getUUID() {
		return minecraftEntity.getUUID();
	}

	public String getStringUUID() {
		return minecraftEntity.getStringUUID();
	}

	public String getScoreboardName() {
		return minecraftEntity.getScoreboardName();
	}

	public boolean isPushedByFluid() {
		return minecraftEntity.isPushedByFluid();
	}

	public Component getDisplayName() {
		return minecraftEntity.getDisplayName();
	}

	public void setCustomName(@Nullable Component component) {
		minecraftEntity.setCustomName(component);
	}

	@Nullable
	public Component getCustomName() {
		return minecraftEntity.getCustomName();
	}

	public boolean hasCustomName() {
		return minecraftEntity.hasCustomName();
	}

	public void setCustomNameVisible(boolean bl) {
		minecraftEntity.setCustomNameVisible(bl);
	}

	public boolean isCustomNameVisible() {
		return minecraftEntity.isCustomNameVisible();
	}

	public void teleportToWithTicket(double d, double e, double f) {
		minecraftEntity.teleportToWithTicket(d, e, f);
	}

	public void teleportTo(double d, double e, double f) {
		minecraftEntity.teleportTo(d, e, f);
	}

	@Environment(EnvType.CLIENT)
	public boolean shouldShowName() {
		return minecraftEntity.shouldShowName();
	}

	public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
		minecraftEntity.onSyncedDataUpdated(entityDataAccessor);
	}

	public void refreshDimensions() {
		minecraftEntity.refreshDimensions();
	}

	public Direction getDirection() {
		return minecraftEntity.getDirection();
	}

	public Direction getMotionDirection() {
		return minecraftEntity.getMotionDirection();
	}

	public boolean broadcastToPlayer(ServerPlayer serverPlayer) {
		return minecraftEntity.broadcastToPlayer(serverPlayer);
	}

	public AABB getBoundingBox() {
		return minecraftEntity.getBoundingBox();
	}

	@Environment(EnvType.CLIENT)
	public AABB getBoundingBoxForCulling() {
		return minecraftEntity.getBoundingBoxForCulling();
	}

	public void setBoundingBox(AABB aABB) {
		minecraftEntity.setBoundingBox(aABB);
	}

	@Environment(EnvType.CLIENT)
	public float getEyeHeight(Pose pose) {
		return minecraftEntity.getEyeHeight(pose);
	}

	public float getEyeHeight() {
		return minecraftEntity.getEyeHeight();
	}

	@Environment(EnvType.CLIENT)
	public Vec3 getLeashOffset() {
		return minecraftEntity.getLeashOffset();
	}

	public boolean setSlot(int i, ItemStack itemStack) {
		return minecraftEntity.setSlot(i, itemStack);
	}

	public void sendMessage(Component component, UUID uUID) {
		minecraftEntity.sendMessage(component, uUID);
	}

	public WorldJS getCommandSenderWorld() {
		return UtilsJS.getWorld(minecraftEntity.getCommandSenderWorld());
	}

	@Nullable
	public MinecraftServer getServer() {
		return minecraftEntity.getServer();
	}

	public InteractionResult interactAt(Player player,
										Vec3 vec3,
										InteractionHand interactionHand) {
		return minecraftEntity.interactAt(player, vec3, interactionHand);
	}

	public boolean ignoreExplosion() {
		return minecraftEntity.ignoreExplosion();
	}

	public void doEnchantDamageEffects(LivingEntity livingEntity, Entity entity) {
		minecraftEntity.doEnchantDamageEffects(livingEntity, entity);
	}

	public void startSeenByPlayer(ServerPlayer serverPlayer) {
		minecraftEntity.startSeenByPlayer(serverPlayer);
	}

	public void stopSeenByPlayer(ServerPlayer serverPlayer) {
		minecraftEntity.stopSeenByPlayer(serverPlayer);
	}

	public float rotate(Rotation rotation) {
		return minecraftEntity.rotate(rotation);
	}

	public float mirror(Mirror mirror) {
		return minecraftEntity.mirror(mirror);
	}

	public boolean onlyOpCanSetNbt() {
		return minecraftEntity.onlyOpCanSetNbt();
	}

	public boolean checkAndResetForcedChunkAdditionFlag() {
		return minecraftEntity.checkAndResetForcedChunkAdditionFlag();
	}

	public boolean checkAndResetUpdateChunkPos() {
		return minecraftEntity.checkAndResetUpdateChunkPos();
	}

	@Nullable
	public EntityJS getControllingPassenger() {
		return minecraftEntity.getControllingPassenger() == null ? null : new EntityJS(getWorld(), minecraftEntity.getControllingPassenger());
	}

	public List<EntityJS> getPassengers() {
		return minecraftEntity.getPassengers().stream().map((entity -> new EntityJS(getWorld(),entity))).collect(Collectors.toList());
	}

	public boolean hasPassenger(Entity entity) {
		return minecraftEntity.hasPassenger(entity);
	}

	public boolean hasPassenger(Class<? extends Entity> class_) {
		return minecraftEntity.hasPassenger(class_);
	}

	public Collection<EntityJS> getIndirectPassengers() {
		return minecraftEntity.getIndirectPassengers().stream().map((entity -> new EntityJS(getWorld(),entity))).collect(Collectors.toList());
	}

	public Stream<EntityJS> getSelfAndPassengers() {
		return minecraftEntity.getSelfAndPassengers().map((entity -> new EntityJS(getWorld(),entity)));
	}

	public boolean hasOnePlayerPassenger() {
		return minecraftEntity.hasOnePlayerPassenger();
	}

	public EntityJS getRootVehicle() {
		return new EntityJS(getWorld(), minecraftEntity.getRootVehicle());
	}

	public boolean isPassengerOfSameVehicle(Entity entity) {
		return minecraftEntity.isPassengerOfSameVehicle(entity);
	}

	@Environment(EnvType.CLIENT)
	public boolean hasIndirectPassenger(Entity entity) {
		return minecraftEntity.hasIndirectPassenger(entity);
	}

	public boolean isControlledByLocalInstance() {
		return minecraftEntity.isControlledByLocalInstance();
	}

	public Vec3 getDismountLocationForPassenger(LivingEntity livingEntity) {
		return minecraftEntity.getDismountLocationForPassenger(livingEntity);
	}

	@Nullable
	public EntityJS getVehicle() {
		return (minecraftEntity.getVehicle() == null) ? null : new EntityJS(getWorld(), minecraftEntity.getVehicle());
	}

	public PushReaction getPistonPushReaction() {
		return minecraftEntity.getPistonPushReaction();
	}

	public SoundSource getSoundSource() {
		return minecraftEntity.getSoundSource();
	}

	public CommandSourceStack createCommandSourceStack() {
		return minecraftEntity.createCommandSourceStack();
	}

	public boolean hasPermissions(int i) {
		return minecraftEntity.hasPermissions(i);
	}

	public boolean acceptsSuccess() {
		return minecraftEntity.acceptsSuccess();
	}

	public boolean acceptsFailure() {
		return minecraftEntity.acceptsFailure();
	}

	public boolean shouldInformAdmins() {
		return minecraftEntity.shouldInformAdmins();
	}

	public void lookAt(EntityAnchorArgument.Anchor anchor, Vec3 vec3) {
		minecraftEntity.lookAt(anchor, vec3);
	}

	public boolean updateFluidHeightAndDoFluidPushing(Tag<Fluid> tag, double d) {
		return minecraftEntity.updateFluidHeightAndDoFluidPushing(tag, d);
	}

	public double getFluidHeight(Tag<Fluid> tag) {
		return minecraftEntity.getFluidHeight(tag);
	}

	public double getFluidJumpThreshold() {
		return minecraftEntity.getFluidJumpThreshold();
	}

	public float getBbWidth() {
		return minecraftEntity.getBbWidth();
	}

	public float getBbHeight() {
		return minecraftEntity.getBbHeight();
	}

	public Packet<?> getAddEntityPacket() {
		return minecraftEntity.getAddEntityPacket();
	}

	public EntityDimensions getDimensions(Pose pose) {
		return minecraftEntity.getDimensions(pose);
	}

	public Vec3 position() {
		return minecraftEntity.position();
	}

	public BlockPos blockPosition() {
		return minecraftEntity.blockPosition();
	}

	public Vec3 getDeltaMovement() {
		return minecraftEntity.getDeltaMovement();
	}

	public void setDeltaMovement(Vec3 vec3) {
		minecraftEntity.setDeltaMovement(vec3);
	}

	public void setDeltaMovement(double d, double e, double f) {
		minecraftEntity.setDeltaMovement(d, e, f);
	}

	public double getX() {
		return minecraftEntity.getX();
	}

	public double getX(double d) {
		return minecraftEntity.getX(d);
	}

	public double getRandomX(double d) {
		return minecraftEntity.getRandomX(d);
	}

	public double getY() {
		return minecraftEntity.getY();
	}

	public double getY(double d) {
		return minecraftEntity.getY(d);
	}

	public double getRandomY() {
		return minecraftEntity.getRandomY();
	}

	public double getEyeY() {
		return minecraftEntity.getEyeY();
	}

	public double getZ() {
		return minecraftEntity.getZ();
	}

	public double getZ(double d) {
		return minecraftEntity.getZ(d);
	}

	public double getRandomZ(double d) {
		return minecraftEntity.getRandomZ(d);
	}

	public void setPosRaw(double d, double e, double f) {
		minecraftEntity.setPosRaw(d, e, f);
	}

	public void checkDespawn() {
		minecraftEntity.checkDespawn();
	}

	@Environment(EnvType.CLIENT)
	public Vec3 getRopeHoldPosition(float f) {
		return minecraftEntity.getRopeHoldPosition(f);
	}
}
