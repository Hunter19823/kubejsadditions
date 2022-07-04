package pie.ilikepiefoo.mixin;

import com.google.common.collect.ImmutableList;
import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.util.Either;
import dev.latvian.kubejs.entity.ItemEntityJS;
import dev.latvian.kubejs.item.InventoryJS;
import dev.latvian.kubejs.item.ItemStackJS;
import dev.latvian.kubejs.player.PlayerJS;
import dev.latvian.kubejs.world.BlockContainerJS;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stat;
import net.minecraft.util.Unit;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.inventory.PlayerEnderChestContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.BaseCommandBlock;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.CommandBlockEntity;
import net.minecraft.world.level.block.entity.JigsawBlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.Scoreboard;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.UUID;

@Mixin(value = PlayerJS.class, remap = false)
public class MixinPlayerJS {

	@Shadow
	@Final
	public Player minecraftPlayer;

	public PlayerJS<?> getInstance() {
		return ((PlayerJS<?>)(Object) this);
	}

	public boolean blockActionRestricted(Level level, BlockPos blockPos, GameType gameType) {
		return minecraftPlayer.blockActionRestricted(level, blockPos, gameType);
	}

	public void tick() {
		minecraftPlayer.tick();
	}

	public boolean isSecondaryUseActive() {
		return minecraftPlayer.isSecondaryUseActive();
	}

	public int getPortalWaitTime() {
		return minecraftPlayer.getPortalWaitTime();
	}

	public int getDimensionChangingDelay() {
		return minecraftPlayer.getDimensionChangingDelay();
	}

	public void playSound(SoundEvent soundEvent, float f, float g) {
		minecraftPlayer.playSound(soundEvent, f, g);
	}

	public void playNotifySound(SoundEvent soundEvent, SoundSource soundSource, float f, float g) {
		minecraftPlayer.playNotifySound(soundEvent, soundSource, f, g);
	}

	public SoundSource getSoundSource() {
		return minecraftPlayer.getSoundSource();
	}

	@Environment(EnvType.CLIENT)
	public void handleEntityEvent(byte b) {
		minecraftPlayer.handleEntityEvent(b);
	}

	/**
	 * Access widened by architectury-1.32.63.jar to accessible
	 */
	public void closeContainer() {
		minecraftPlayer.closeContainer();
	}

	public void rideTick() {
		minecraftPlayer.rideTick();
	}

	@Environment(EnvType.CLIENT)
	public void resetPos() {
		minecraftPlayer.resetPos();
	}

	public void aiStep() {
		minecraftPlayer.aiStep();
	}

	public int getScore() {
		return minecraftPlayer.getScore();
	}

	public void setScore(int i) {
		minecraftPlayer.setScore(i);
	}

	public void increaseScore(int i) {
		minecraftPlayer.increaseScore(i);
	}

	public void die(DamageSource damageSource) {
		minecraftPlayer.die(damageSource);
	}

	public boolean drop(boolean bl) {
		return minecraftPlayer.drop(bl);
	}

	@Nullable
	public ItemEntityJS drop(ItemStack itemStack, boolean bl) {
		ItemEntity entity = minecraftPlayer.drop(itemStack, bl);
		if(entity == null)
			return null;
		return new ItemEntityJS(getInstance().getWorld(), entity);
	}

	@Nullable
	public ItemEntityJS drop(ItemStack itemStack, boolean bl, boolean bl2) {
		ItemEntity entity = minecraftPlayer.drop(itemStack, bl, bl2);
		if(entity == null)
			return null;
		return new ItemEntityJS(getInstance().getWorld(), entity);
	}

	public float getDestroySpeed(BlockState blockState) {
		return minecraftPlayer.getDestroySpeed(blockState);
	}

	public boolean hasCorrectToolForDrops(BlockState blockState) {
		return minecraftPlayer.hasCorrectToolForDrops(blockState);
	}

	public void readAdditionalSaveData(CompoundTag compoundTag) {
		minecraftPlayer.readAdditionalSaveData(compoundTag);
	}

	public void addAdditionalSaveData(CompoundTag compoundTag) {
		minecraftPlayer.addAdditionalSaveData(compoundTag);
	}

	public boolean isInvulnerableTo(DamageSource damageSource) {
		return minecraftPlayer.isInvulnerableTo(damageSource);
	}

	public boolean hurt(DamageSource damageSource, float f) {
		return minecraftPlayer.hurt(damageSource, f);
	}

	public boolean canHarmPlayer(Player player) {
		return minecraftPlayer.canHarmPlayer(player);
	}

	public void openTextEdit(SignBlockEntity signBlockEntity) {
		minecraftPlayer.openTextEdit(signBlockEntity);
	}

	public void openMinecartCommandBlock(BaseCommandBlock baseCommandBlock) {
		minecraftPlayer.openMinecartCommandBlock(baseCommandBlock);
	}

	public void openCommandBlock(CommandBlockEntity commandBlockEntity) {
		minecraftPlayer.openCommandBlock(commandBlockEntity);
	}

	public void openStructureBlock(StructureBlockEntity structureBlockEntity) {
		minecraftPlayer.openStructureBlock(structureBlockEntity);
	}

	public void openJigsawBlock(JigsawBlockEntity jigsawBlockEntity) {
		minecraftPlayer.openJigsawBlock(jigsawBlockEntity);
	}

	public void openHorseInventory(AbstractHorse abstractHorse, Container container) {
		minecraftPlayer.openHorseInventory(abstractHorse, container);
	}

	public OptionalInt openMenu(@Nullable MenuProvider menuProvider) {
		return minecraftPlayer.openMenu(menuProvider);
	}

	public void sendMerchantOffers(int i, MerchantOffers merchantOffers, int j, int k, boolean bl, boolean bl2) {
		minecraftPlayer.sendMerchantOffers(i, merchantOffers, j, k, bl, bl2);
	}

	public void openItemGui(ItemStack itemStack, InteractionHand interactionHand) {
		minecraftPlayer.openItemGui(itemStack, interactionHand);
	}

	public InteractionResult interactOn(Entity entity, InteractionHand interactionHand) {
		return minecraftPlayer.interactOn(entity, interactionHand);
	}

	public double getMyRidingOffset() {
		return minecraftPlayer.getMyRidingOffset();
	}

	public void removeVehicle() {
		minecraftPlayer.removeVehicle();
	}

	public boolean isAffectedByFluids() {
		return minecraftPlayer.isAffectedByFluids();
	}

	public void attack(Entity entity) {
		minecraftPlayer.attack(entity);
	}

	public void disableShield(boolean bl) {
		minecraftPlayer.disableShield(bl);
	}

	public void crit(Entity entity) {
		minecraftPlayer.crit(entity);
	}

	public void magicCrit(Entity entity) {
		minecraftPlayer.magicCrit(entity);
	}

	public void sweepAttack() {
		minecraftPlayer.sweepAttack();
	}

	@Environment(EnvType.CLIENT)
	public void respawn() {
		minecraftPlayer.respawn();
	}

	public void remove() {
		minecraftPlayer.remove();
	}

	public boolean isLocalPlayer() {
		return minecraftPlayer.isLocalPlayer();
	}

	public GameProfile getGameProfile() {
		return minecraftPlayer.getGameProfile();
	}

	public Either<Player.BedSleepingProblem, Unit> startSleepInBed(BlockPos blockPos) {
		return minecraftPlayer.startSleepInBed(blockPos);
	}

	public void stopSleepInBed(boolean bl, boolean bl2) {
		minecraftPlayer.stopSleepInBed(bl, bl2);
	}

	public void stopSleeping() {
		minecraftPlayer.stopSleeping();
	}

	public boolean isSleepingLongEnough() {
		return minecraftPlayer.isSleepingLongEnough();
	}

	public int getSleepTimer() {
		return minecraftPlayer.getSleepTimer();
	}

	public void displayClientMessage(Component component, boolean bl) {
		minecraftPlayer.displayClientMessage(component, bl);
	}

	public void awardStat(ResourceLocation resourceLocation) {
		minecraftPlayer.awardStat(resourceLocation);
	}

	public void awardStat(ResourceLocation resourceLocation, int i) {
		minecraftPlayer.awardStat(resourceLocation, i);
	}

	public void awardStat(Stat<?> stat) {
		minecraftPlayer.awardStat(stat);
	}

	public void awardStat(Stat<?> stat, int i) {
		minecraftPlayer.awardStat(stat, i);
	}

	public void resetStat(Stat<?> stat) {
		minecraftPlayer.resetStat(stat);
	}

	public int awardRecipes(Collection<Recipe<?>> collection) {
		return minecraftPlayer.awardRecipes(collection);
	}

	public void awardRecipesByKey(ResourceLocation[] resourceLocations) {
		minecraftPlayer.awardRecipesByKey(resourceLocations);
	}

	public int resetRecipes(Collection<Recipe<?>> collection) {
		return minecraftPlayer.resetRecipes(collection);
	}

	public void jumpFromGround() {
		minecraftPlayer.jumpFromGround();
	}

	public void travel(Vec3 vec3) {
		minecraftPlayer.travel(vec3);
	}

	public void updateSwimming() {
		minecraftPlayer.updateSwimming();
	}

	public float getSpeed() {
		return minecraftPlayer.getSpeed();
	}

	public void checkMovementStatistics(double d, double e, double f) {
		minecraftPlayer.checkMovementStatistics(d, e, f);
	}

	public boolean causeFallDamage(float f, float g) {
		return minecraftPlayer.causeFallDamage(f, g);
	}

	public boolean tryToStartFallFlying() {
		return minecraftPlayer.tryToStartFallFlying();
	}

	public void startFallFlying() {
		minecraftPlayer.startFallFlying();
	}

	public void stopFallFlying() {
		minecraftPlayer.stopFallFlying();
	}

	public void killed(ServerLevel serverLevel, LivingEntity livingEntity) {
		minecraftPlayer.killed(serverLevel, livingEntity);
	}

	public void makeStuckInBlock(BlockState blockState, Vec3 vec3) {
		minecraftPlayer.makeStuckInBlock(blockState, vec3);
	}

	public void giveExperiencePoints(int i) {
		minecraftPlayer.giveExperiencePoints(i);
	}

	public int getEnchantmentSeed() {
		return minecraftPlayer.getEnchantmentSeed();
	}

	public void onEnchantmentPerformed(ItemStack itemStack, int i) {
		minecraftPlayer.onEnchantmentPerformed(itemStack, i);
	}

	public void giveExperienceLevels(int i) {
		minecraftPlayer.giveExperienceLevels(i);
	}

	public int getXpNeededForNextLevel() {
		return minecraftPlayer.getXpNeededForNextLevel();
	}

	public void causeFoodExhaustion(float f) {
		minecraftPlayer.causeFoodExhaustion(f);
	}

	public void eat(int i, float f) {
		getFoodData().eat(i, f);
	}

	public void eat(Item item, ItemStack itemStack) {
		getFoodData().eat(item, itemStack);
	}

	public void tick(Player player) {
		getFoodData().tick(player);
	}

	public int getFoodLevel() {
		return getFoodData().getFoodLevel();
	}

	public boolean needsFood() {
		return getFoodData().needsFood();
	}

	public void addExhaustion(float f) {
		getFoodData().addExhaustion(f);
	}

	public float getSaturationLevel() {
		return getFoodData().getSaturationLevel();
	}

	public void setFoodLevel(int i) {
		getFoodData().setFoodLevel(i);
	}

	@Environment(EnvType.CLIENT)
	public void setSaturation(float f) {
		getFoodData().setSaturation(f);
	}

	public FoodData getFoodData() {
		return minecraftPlayer.getFoodData();
	}

	public boolean canEat(boolean bl) {
		return minecraftPlayer.canEat(bl);
	}

	public boolean isHurt() {
		return minecraftPlayer.isHurt();
	}

	public boolean mayBuild() {
		return minecraftPlayer.mayBuild();
	}

	public boolean mayUseItemAt(BlockPos blockPos, Direction direction, ItemStack itemStack) {
		return minecraftPlayer.mayUseItemAt(blockPos, direction, itemStack);
	}

	@Environment(EnvType.CLIENT)
	public boolean shouldShowName() {
		return minecraftPlayer.shouldShowName();
	}

	public void onUpdateAbilities() {
		minecraftPlayer.onUpdateAbilities();
	}

	public void setGameMode(GameType gameType) {
		minecraftPlayer.setGameMode(gameType);
	}

	public Component getName() {
		return minecraftPlayer.getName();
	}

	public PlayerEnderChestContainer getEnderChestInventory() {
		return minecraftPlayer.getEnderChestInventory();
	}

	public ItemStack getItemBySlot(EquipmentSlot equipmentSlot) {
		return minecraftPlayer.getItemBySlot(equipmentSlot);
	}

	public void setItemSlot(EquipmentSlot equipmentSlot, ItemStack itemStack) {
		minecraftPlayer.setItemSlot(equipmentSlot, itemStack);
	}

	public boolean addItem(ItemStack itemStack) {
		return minecraftPlayer.addItem(itemStack);
	}

	public Iterable<ItemStack> getHandSlots() {
		return minecraftPlayer.getHandSlots();
	}

	public Iterable<ItemStack> getArmorSlots() {
		return minecraftPlayer.getArmorSlots();
	}

	public boolean setEntityOnShoulder(CompoundTag compoundTag) {
		return minecraftPlayer.setEntityOnShoulder(compoundTag);
	}

	public boolean isSpectator() {
		return minecraftPlayer.isSpectator();
	}

	public boolean isSwimming() {
		return minecraftPlayer.isSwimming();
	}

	public boolean isCreative() {
		return minecraftPlayer.isCreative();
	}

	public boolean isPushedByFluid() {
		return minecraftPlayer.isPushedByFluid();
	}

	public Scoreboard getScoreboard() {
		return minecraftPlayer.getScoreboard();
	}

	public Component getDisplayName() {
		return minecraftPlayer.getDisplayName();
	}

	public String getScoreboardName() {
		return minecraftPlayer.getScoreboardName();
	}

	public float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		return minecraftPlayer.getStandingEyeHeight(pose, entityDimensions);
	}

	public void setAbsorptionAmount(float f) {
		minecraftPlayer.setAbsorptionAmount(f);
	}

	public float getAbsorptionAmount() {
		return minecraftPlayer.getAbsorptionAmount();
	}

	@Environment(EnvType.CLIENT)
	public boolean isModelPartShown(PlayerModelPart playerModelPart) {
		return minecraftPlayer.isModelPartShown(playerModelPart);
	}

	public boolean setSlot(int i, ItemStack itemStack) {
		return minecraftPlayer.setSlot(i, itemStack);
	}

	@Environment(EnvType.CLIENT)
	public boolean isReducedDebugInfo() {
		return minecraftPlayer.isReducedDebugInfo();
	}

	@Environment(EnvType.CLIENT)
	public void setReducedDebugInfo(boolean bl) {
		minecraftPlayer.setReducedDebugInfo(bl);
	}

	public void setRemainingFireTicks(int i) {
		minecraftPlayer.setRemainingFireTicks(i);
	}

	public HumanoidArm getMainArm() {
		return minecraftPlayer.getMainArm();
	}

	public void setMainArm(HumanoidArm humanoidArm) {
		minecraftPlayer.setMainArm(humanoidArm);
	}

	public CompoundTag getShoulderEntityLeft() {
		return minecraftPlayer.getShoulderEntityLeft();
	}

	public CompoundTag getShoulderEntityRight() {
		return minecraftPlayer.getShoulderEntityRight();
	}

	public float getCurrentItemAttackStrengthDelay() {
		return minecraftPlayer.getCurrentItemAttackStrengthDelay();
	}

	public float getAttackStrengthScale(float f) {
		return minecraftPlayer.getAttackStrengthScale(f);
	}

	public void resetAttackStrengthTicker() {
		minecraftPlayer.resetAttackStrengthTicker();
	}

	public ItemCooldowns getCooldowns() {
		return minecraftPlayer.getCooldowns();
	}

	public float getLuck() {
		return minecraftPlayer.getLuck();
	}

	public boolean canUseGameMasterBlocks() {
		return minecraftPlayer.canUseGameMasterBlocks();
	}

	public boolean canTakeItem(ItemStack itemStack) {
		return minecraftPlayer.canTakeItem(itemStack);
	}

	public EntityDimensions getDimensions(Pose pose) {
		return minecraftPlayer.getDimensions(pose);
	}

	public ImmutableList<Pose> getDismountPoses() {
		return minecraftPlayer.getDismountPoses();
	}

	public ItemStack getProjectile(ItemStack itemStack) {
		return minecraftPlayer.getProjectile(itemStack);
	}

	public ItemStack eat(Level level, ItemStack itemStack) {
		return minecraftPlayer.eat(level, itemStack);
	}

	@Environment(EnvType.CLIENT)
	public Vec3 getRopeHoldPosition(float f) {
		return minecraftPlayer.getRopeHoldPosition(f);
	}
}
