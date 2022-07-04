package pie.ilikepiefoo.mixin;

import com.google.common.collect.ImmutableList;
import dev.latvian.kubejs.entity.LivingEntityJS;
import dev.latvian.kubejs.item.ItemStackJS;
import dev.latvian.kubejs.player.PlayerJS;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.CombatTracker;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Mixin(value = LivingEntityJS.class, remap = false)
public class MixinLivingEntityJS {
	public LivingEntityJS getInstance() {
		return ((LivingEntityJS) (Object) this);
	}

	@Shadow
	@Final
	public LivingEntity minecraftLivingEntity;

	public Brain<?> getBrain() {
		return minecraftLivingEntity.getBrain();
	}

	public void kill() {
		minecraftLivingEntity.kill();
	}

	public boolean canAttackType(EntityType<?> entityType) {
		return minecraftLivingEntity.canAttackType(entityType);
	}

	public boolean canBreatheUnderwater() {
		return minecraftLivingEntity.canBreatheUnderwater();
	}

	@Environment(EnvType.CLIENT)
	public float getSwimAmount(float f) {
		return minecraftLivingEntity.getSwimAmount(f);
	}

	public void baseTick() {
		minecraftLivingEntity.baseTick();
	}

	public boolean canSpawnSoulSpeedParticle() {
		return minecraftLivingEntity.canSpawnSoulSpeedParticle();
	}

	public boolean isBaby() {
		return minecraftLivingEntity.isBaby();
	}

	public float getScale() {
		return minecraftLivingEntity.getScale();
	}

	public boolean rideableUnderWater() {
		return minecraftLivingEntity.rideableUnderWater();
	}

	public Random getRandom() {
		return minecraftLivingEntity.getRandom();
	}

	@Nullable
	public LivingEntityJS getLastHurtByMob() {
		return (minecraftLivingEntity.getLastHurtByMob() == null) ? null : new LivingEntityJS(getInstance().getWorld(), minecraftLivingEntity.getLastHurtByMob());
	}

	public int getLastHurtByMobTimestamp() {
		return minecraftLivingEntity.getLastHurtByMobTimestamp();
	}

	public void setLastHurtByPlayer(@Nullable Player player) {
		minecraftLivingEntity.setLastHurtByPlayer(player);
	}

	public void setLastHurtByMob(@Nullable LivingEntity livingEntity) {
		minecraftLivingEntity.setLastHurtByMob(livingEntity);
	}

	@Nullable
	public LivingEntityJS getLastHurtMob() {
		return minecraftLivingEntity.getLastHurtMob() == null ? null : new LivingEntityJS(getInstance().getWorld(), minecraftLivingEntity.getLastHurtMob());
	}

	public int getLastHurtMobTimestamp() {
		return minecraftLivingEntity.getLastHurtMobTimestamp();
	}

	public void setLastHurtMob(Entity entity) {
		minecraftLivingEntity.setLastHurtMob(entity);
	}

	public int getNoActionTime() {
		return minecraftLivingEntity.getNoActionTime();
	}

	public void setNoActionTime(int i) {
		minecraftLivingEntity.setNoActionTime(i);
	}

	public void addAdditionalSaveData(CompoundTag compoundTag) {
		minecraftLivingEntity.addAdditionalSaveData(compoundTag);
	}

	public void readAdditionalSaveData(CompoundTag compoundTag) {
		minecraftLivingEntity.readAdditionalSaveData(compoundTag);
	}

	public double getVisibilityPercent(@Nullable Entity entity) {
		return minecraftLivingEntity.getVisibilityPercent(entity);
	}

	public boolean canAttack(LivingEntity livingEntity) {
		return minecraftLivingEntity.canAttack(livingEntity);
	}

	public boolean canAttack(LivingEntity livingEntity, TargetingConditions targetingConditions) {
		return minecraftLivingEntity.canAttack(livingEntity, targetingConditions);
	}

	public boolean removeAllEffects() {
		return minecraftLivingEntity.removeAllEffects();
	}

	public Collection<MobEffectInstance> getActiveEffects() {
		return minecraftLivingEntity.getActiveEffects();
	}

	public Map<MobEffect, MobEffectInstance> getActiveEffectsMap() {
		return minecraftLivingEntity.getActiveEffectsMap();
	}

	public boolean hasEffect(MobEffect mobEffect) {
		return minecraftLivingEntity.hasEffect(mobEffect);
	}

	@Nullable
	public MobEffectInstance getEffect(MobEffect mobEffect) {
		return minecraftLivingEntity.getEffect(mobEffect);
	}

	public boolean addEffect(MobEffectInstance mobEffectInstance) {
		return minecraftLivingEntity.addEffect(mobEffectInstance);
	}

	public boolean canBeAffected(MobEffectInstance mobEffectInstance) {
		return minecraftLivingEntity.canBeAffected(mobEffectInstance);
	}

	@Environment(EnvType.CLIENT)
	public void forceAddEffect(MobEffectInstance mobEffectInstance) {
		minecraftLivingEntity.forceAddEffect(mobEffectInstance);
	}

	public boolean isInvertedHealAndHarm() {
		return minecraftLivingEntity.isInvertedHealAndHarm();
	}

	@Nullable
	public MobEffectInstance removeEffectNoUpdate(@Nullable MobEffect mobEffect) {
		return minecraftLivingEntity.removeEffectNoUpdate(mobEffect);
	}

	public boolean removeEffect(MobEffect mobEffect) {
		return minecraftLivingEntity.removeEffect(mobEffect);
	}

	public void heal(float f) {
		minecraftLivingEntity.heal(f);
	}

	public float getHealth() {
		return minecraftLivingEntity.getHealth();
	}

	public void setHealth(float f) {
		minecraftLivingEntity.setHealth(f);
	}

	public boolean isDeadOrDying() {
		return minecraftLivingEntity.isDeadOrDying();
	}

	public boolean hurt(DamageSource damageSource, float f) {
		return minecraftLivingEntity.hurt(damageSource, f);
	}

	@Nullable
	public DamageSource getLastDamageSource() {
		return minecraftLivingEntity.getLastDamageSource();
	}

	public void die(DamageSource damageSource) {
		minecraftLivingEntity.die(damageSource);
	}

	public ResourceLocation getLootTable() {
		return minecraftLivingEntity.getLootTable();
	}

	public void knockback(float f, double d, double e) {
		minecraftLivingEntity.knockback(f, d, e);
	}

	public SoundEvent getEatingSound(ItemStack itemStack) {
		return minecraftLivingEntity.getEatingSound(itemStack);
	}

	public void setOnGround(boolean bl) {
		minecraftLivingEntity.setOnGround(bl);
	}

	public Optional<BlockPos> getLastClimbablePos() {
		return minecraftLivingEntity.getLastClimbablePos();
	}

	public boolean onClimbable() {
		return minecraftLivingEntity.onClimbable();
	}

	public BlockState getFeetBlockState() {
		return minecraftLivingEntity.getFeetBlockState();
	}

	public boolean isAlive() {
		return minecraftLivingEntity.isAlive();
	}

	public boolean causeFallDamage(float f, float g) {
		return minecraftLivingEntity.causeFallDamage(f, g);
	}

	@Environment(EnvType.CLIENT)
	public void animateHurt() {
		minecraftLivingEntity.animateHurt();
	}

	public int getArmorValue() {
		return minecraftLivingEntity.getArmorValue();
	}

	public CombatTracker getCombatTracker() {
		return minecraftLivingEntity.getCombatTracker();
	}

	@Nullable
	public LivingEntityJS getKillCredit() {
		return minecraftLivingEntity.getKillCredit() == null ? null : new LivingEntityJS(getInstance().getWorld(), minecraftLivingEntity.getKillCredit());
	}

	public float getMaxHealth() {
		return minecraftLivingEntity.getMaxHealth();
	}

	public int getArrowCount() {
		return minecraftLivingEntity.getArrowCount();
	}

	public void setArrowCount(int i) {
		minecraftLivingEntity.setArrowCount(i);
	}

	public int getStingerCount() {
		return minecraftLivingEntity.getStingerCount();
	}

	public void setStingerCount(int i) {
		minecraftLivingEntity.setStingerCount(i);
	}

	public void swing(InteractionHand interactionHand) {
		minecraftLivingEntity.swing(interactionHand);
	}

	public void swing(InteractionHand interactionHand, boolean bl) {
		minecraftLivingEntity.swing(interactionHand, bl);
	}

	@Environment(EnvType.CLIENT)
	public void handleEntityEvent(byte b) {
		minecraftLivingEntity.handleEntityEvent(b);
	}

	@Nullable
	public AttributeInstance getAttribute(Attribute attribute) {
		return minecraftLivingEntity.getAttribute(attribute);
	}

	public double getAttributeValue(Attribute attribute) {
		return minecraftLivingEntity.getAttributeValue(attribute);
	}

	public double getAttributeBaseValue(Attribute attribute) {
		return minecraftLivingEntity.getAttributeBaseValue(attribute);
	}

	public AttributeMap getAttributes() {
		return minecraftLivingEntity.getAttributes();
	}

	public MobType getMobType() {
		return minecraftLivingEntity.getMobType();
	}

	public ItemStackJS getMainHandItem() {
		return new ItemStackJS(minecraftLivingEntity.getMainHandItem());
	}

	public ItemStackJS getOffhandItem() {
		return ItemStackJS.of(minecraftLivingEntity.getOffhandItem());
	}

	public boolean isHolding(Item item) {
		return minecraftLivingEntity.isHolding(item);
	}

	public boolean isHolding(Predicate<Item> predicate) {
		return minecraftLivingEntity.isHolding(predicate);
	}

	public ItemStackJS getItemInHand(InteractionHand interactionHand) {
		return new ItemStackJS(minecraftLivingEntity.getItemInHand(interactionHand));
	}

	public void setItemInHand(InteractionHand interactionHand, ItemStack itemStack) {
		minecraftLivingEntity.setItemInHand(interactionHand, itemStack);
	}

	public boolean hasItemInSlot(EquipmentSlot equipmentSlot) {
		return minecraftLivingEntity.hasItemInSlot(equipmentSlot);
	}

	public Iterable<ItemStackJS> getArmorSlots() {
		return StreamSupport.stream(minecraftLivingEntity.getArmorSlots().spliterator(), false).map(ItemStackJS::of).collect(Collectors.toList());
	}

	public ItemStackJS getItemBySlot(EquipmentSlot equipmentSlot) {
		return new ItemStackJS(minecraftLivingEntity.getItemBySlot(equipmentSlot));
	}

	public void setItemSlot(EquipmentSlot equipmentSlot, ItemStack itemStack) {
		minecraftLivingEntity.setItemSlot(equipmentSlot, itemStack);
	}

	public float getArmorCoverPercentage() {
		return minecraftLivingEntity.getArmorCoverPercentage();
	}

	public void setSprinting(boolean bl) {
		minecraftLivingEntity.setSprinting(bl);
	}

	public void push(Entity entity) {
		minecraftLivingEntity.push(entity);
	}

	@Environment(EnvType.CLIENT)
	public boolean shouldShowName() {
		return minecraftLivingEntity.shouldShowName();
	}

	public boolean canStandOnFluid(Fluid fluid) {
		return minecraftLivingEntity.canStandOnFluid(fluid);
	}

	public void travel(Vec3 vec3) {
		minecraftLivingEntity.travel(vec3);
	}

	public void calculateEntityAnimation(LivingEntity livingEntity, boolean bl) {
		minecraftLivingEntity.calculateEntityAnimation(livingEntity, bl);
	}

	public Vec3 handleRelativeFrictionAndCalculateMovement(Vec3 vec3, float f) {
		return minecraftLivingEntity.handleRelativeFrictionAndCalculateMovement(vec3, f);
	}

	public Vec3 getFluidFallingAdjustedMovement(double d, boolean bl, Vec3 vec3) {
		return minecraftLivingEntity.getFluidFallingAdjustedMovement(d, bl, vec3);
	}

	public float getSpeed() {
		return minecraftLivingEntity.getSpeed();
	}

	public void setSpeed(float f) {
		minecraftLivingEntity.setSpeed(f);
	}

	public boolean doHurtTarget(Entity entity) {
		return minecraftLivingEntity.doHurtTarget(entity);
	}

	public void tick() {
		minecraftLivingEntity.tick();
	}

	public void aiStep() {
		minecraftLivingEntity.aiStep();
	}

	public boolean isSensitiveToWater() {
		return minecraftLivingEntity.isSensitiveToWater();
	}

	public void startAutoSpinAttack(int i) {
		minecraftLivingEntity.startAutoSpinAttack(i);
	}

	public boolean isAutoSpinAttack() {
		return minecraftLivingEntity.isAutoSpinAttack();
	}

	public void stopRiding() {
		minecraftLivingEntity.stopRiding();
	}

	public void rideTick() {
		minecraftLivingEntity.rideTick();
	}

	@Environment(EnvType.CLIENT)
	public void lerpTo(double d, double e, double f, float g, float h, int i, boolean bl) {
		minecraftLivingEntity.lerpTo(d, e, f, g, h, i, bl);
	}

	@Environment(EnvType.CLIENT)
	public void lerpHeadTo(float f, int i) {
		minecraftLivingEntity.lerpHeadTo(f, i);
	}

	public void setJumping(boolean bl) {
		minecraftLivingEntity.setJumping(bl);
	}

	public void onItemPickup(ItemEntity itemEntity) {
		minecraftLivingEntity.onItemPickup(itemEntity);
	}

	public void take(Entity entity, int i) {
		minecraftLivingEntity.take(entity, i);
	}

	public boolean canSee(Entity entity) {
		return minecraftLivingEntity.canSee(entity);
	}

	public float getViewYRot(float f) {
		return minecraftLivingEntity.getViewYRot(f);
	}

	@Environment(EnvType.CLIENT)
	public float getAttackAnim(float f) {
		return minecraftLivingEntity.getAttackAnim(f);
	}

	public boolean isEffectiveAi() {
		return minecraftLivingEntity.isEffectiveAi();
	}

	public boolean isPickable() {
		return minecraftLivingEntity.isPickable();
	}

	public boolean isPushable() {
		return minecraftLivingEntity.isPushable();
	}

	public float getYHeadRot() {
		return minecraftLivingEntity.getYHeadRot();
	}

	public void setYHeadRot(float f) {
		minecraftLivingEntity.setYHeadRot(f);
	}

	public void setYBodyRot(float f) {
		minecraftLivingEntity.setYBodyRot(f);
	}

	public float getAbsorptionAmount() {
		return minecraftLivingEntity.getAbsorptionAmount();
	}

	public void setAbsorptionAmount(float f) {
		minecraftLivingEntity.setAbsorptionAmount(f);
	}

	public void onEnterCombat() {
		minecraftLivingEntity.onEnterCombat();
	}

	public void onLeaveCombat() {
		minecraftLivingEntity.onLeaveCombat();
	}

	public HumanoidArm getMainArm() {
		return minecraftLivingEntity.getMainArm();
	}

	public boolean isUsingItem() {
		return minecraftLivingEntity.isUsingItem();
	}

	public InteractionHand getUsedItemHand() {
		return minecraftLivingEntity.getUsedItemHand();
	}

	public void startUsingItem(InteractionHand interactionHand) {
		minecraftLivingEntity.startUsingItem(interactionHand);
	}

	public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
		minecraftLivingEntity.onSyncedDataUpdated(entityDataAccessor);
	}

	public void lookAt(EntityAnchorArgument.Anchor anchor, Vec3 vec3) {
		minecraftLivingEntity.lookAt(anchor, vec3);
	}

	public ItemStackJS getUseItem() {
		return new ItemStackJS(minecraftLivingEntity.getUseItem());
	}

	public int getUseItemRemainingTicks() {
		return minecraftLivingEntity.getUseItemRemainingTicks();
	}

	public int getTicksUsingItem() {
		return minecraftLivingEntity.getTicksUsingItem();
	}

	public void releaseUsingItem() {
		minecraftLivingEntity.releaseUsingItem();
	}

	public void stopUsingItem() {
		minecraftLivingEntity.stopUsingItem();
	}

	public boolean isBlocking() {
		return minecraftLivingEntity.isBlocking();
	}

	public boolean isSuppressingSlidingDownLadder() {
		return minecraftLivingEntity.isSuppressingSlidingDownLadder();
	}

	public boolean isFallFlying() {
		return minecraftLivingEntity.isFallFlying();
	}

	public boolean isVisuallySwimming() {
		return minecraftLivingEntity.isVisuallySwimming();
	}

	@Environment(EnvType.CLIENT)
	public int getFallFlyingTicks() {
		return minecraftLivingEntity.getFallFlyingTicks();
	}

	public boolean randomTeleport(double d, double e, double f, boolean bl) {
		return minecraftLivingEntity.randomTeleport(d, e, f, bl);
	}

	public boolean isAffectedByPotions() {
		return minecraftLivingEntity.isAffectedByPotions();
	}

	public boolean attackable() {
		return minecraftLivingEntity.attackable();
	}

	@Environment(EnvType.CLIENT)
	public void setRecordPlayingNearby(BlockPos blockPos, boolean bl) {
		minecraftLivingEntity.setRecordPlayingNearby(blockPos, bl);
	}

	public boolean canTakeItem(ItemStack itemStack) {
		return minecraftLivingEntity.canTakeItem(itemStack);
	}

	public Packet<?> getAddEntityPacket() {
		return minecraftLivingEntity.getAddEntityPacket();
	}

	public EntityDimensions getDimensions(Pose pose) {
		return minecraftLivingEntity.getDimensions(pose);
	}

	public ImmutableList<Pose> getDismountPoses() {
		return minecraftLivingEntity.getDismountPoses();
	}

	public AABB getLocalBoundsForPose(Pose pose) {
		return minecraftLivingEntity.getLocalBoundsForPose(pose);
	}

	public Optional<BlockPos> getSleepingPos() {
		return minecraftLivingEntity.getSleepingPos();
	}

	public void setSleepingPos(BlockPos blockPos) {
		minecraftLivingEntity.setSleepingPos(blockPos);
	}

	public void clearSleepingPos() {
		minecraftLivingEntity.clearSleepingPos();
	}

	public boolean isSleeping() {
		return minecraftLivingEntity.isSleeping();
	}

	public void startSleeping(BlockPos blockPos) {
		minecraftLivingEntity.startSleeping(blockPos);
	}

	public void stopSleeping() {
		minecraftLivingEntity.stopSleeping();
	}

	@Environment(EnvType.CLIENT)
	@Nullable
	public Direction getBedOrientation() {
		return minecraftLivingEntity.getBedOrientation();
	}

	public boolean isInWall() {
		return minecraftLivingEntity.isInWall();
	}

	public ItemStackJS getProjectile(ItemStack itemStack) {
		return new ItemStackJS(minecraftLivingEntity.getProjectile(itemStack));
	}

	public ItemStackJS eat(Level level, ItemStack itemStack) {
		return new ItemStackJS(minecraftLivingEntity.eat(level, itemStack));
	}

	public void broadcastBreakEvent(EquipmentSlot equipmentSlot) {
		minecraftLivingEntity.broadcastBreakEvent(equipmentSlot);
	}

	public void broadcastBreakEvent(InteractionHand interactionHand) {
		minecraftLivingEntity.broadcastBreakEvent(interactionHand);
	}

	@Environment(EnvType.CLIENT)
	public AABB getBoundingBoxForCulling() {
		return minecraftLivingEntity.getBoundingBoxForCulling();
	}
}
