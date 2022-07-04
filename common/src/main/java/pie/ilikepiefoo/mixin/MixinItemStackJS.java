package pie.ilikepiefoo.mixin;

import com.google.common.collect.Multimap;
import dev.latvian.kubejs.item.ItemStackJS;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagContainer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Random;
import java.util.function.Consumer;

@Mixin(value = ItemStackJS.class, remap = false)
public class MixinItemStackJS {
	@Shadow
	@Final
	private ItemStack stack;

	public ItemStack finishUsingItem(Level level, LivingEntity livingEntity) {
		return stack.finishUsingItem(level, livingEntity);
	}

	public int getBaseRepairCost() {
		return stack.getBaseRepairCost();
	}

	public ItemStack split(int i) {
		return stack.split(i);
	}

	public Item getItem() {
		return stack.getItem();
	}

	public InteractionResult useOn(UseOnContext useOnContext) {
		return stack.useOn(useOnContext);
	}

	public float getDestroySpeed(BlockState blockState) {
		return stack.getDestroySpeed(blockState);
	}

	public InteractionResultHolder<ItemStack> use(Level level,
												  Player player,
												  InteractionHand interactionHand) {
		return stack.use(level, player, interactionHand);
	}

	public CompoundTag save(CompoundTag compoundTag) {
		return stack.save(compoundTag);
	}

	public int getMaxStackSize() {
		return stack.getMaxStackSize();
	}

	public boolean isStackable() {
		return stack.isStackable();
	}

	public boolean isDamageableItem() {
		return stack.isDamageableItem();
	}

	public boolean isDamaged() {
		return stack.isDamaged();
	}

	public int getDamageValue() {
		return stack.getDamageValue();
	}

	public void setDamageValue(int i) {
		stack.setDamageValue(i);
	}

	public int getMaxDamage() {
		return stack.getMaxDamage();
	}

	public boolean hurt(int i, Random random, @Nullable ServerPlayer serverPlayer) {
		return stack.hurt(i, random, serverPlayer);
	}

	public <T extends LivingEntity> void hurtAndBreak(int i, T livingEntity, Consumer<T> consumer) {
		stack.hurtAndBreak(i, livingEntity, consumer);
	}

	public void hurtEnemy(LivingEntity livingEntity, Player player) {
		stack.hurtEnemy(livingEntity, player);
	}

	public void mineBlock(Level level,
						  BlockState blockState,
						  BlockPos blockPos,
						  Player player) {
		stack.mineBlock(level, blockState, blockPos, player);
	}

	public boolean isCorrectToolForDrops(BlockState blockState) {
		return stack.isCorrectToolForDrops(blockState);
	}

	public InteractionResult interactLivingEntity(Player player,
												  LivingEntity livingEntity,
												  InteractionHand interactionHand) {
		return stack.interactLivingEntity(player, livingEntity, interactionHand);
	}

	public boolean sameItem(ItemStack itemStack) {
		return stack.sameItem(itemStack);
	}

	public boolean sameItemStackIgnoreDurability(ItemStack itemStack) {
		return stack.sameItemStackIgnoreDurability(itemStack);
	}

	public String getDescriptionId() {
		return stack.getDescriptionId();
	}

	public void inventoryTick(Level level, Entity entity, int i, boolean bl) {
		stack.inventoryTick(level, entity, i, bl);
	}

	public void onCraftedBy(Level level, Player player, int i) {
		stack.onCraftedBy(level, player, i);
	}

	public int getUseDuration() {
		return stack.getUseDuration();
	}

	public UseAnim getUseAnimation() {
		return stack.getUseAnimation();
	}

	public void releaseUsing(Level level, LivingEntity livingEntity, int i) {
		stack.releaseUsing(level, livingEntity, i);
	}

	public boolean useOnRelease() {
		return stack.useOnRelease();
	}

	public boolean hasTag() {
		return stack.hasTag();
	}

	public void removeTagKey(String string) {
		stack.removeTagKey(string);
	}

	public ListTag getEnchantmentTags() {
		return stack.getEnchantmentTags();
	}

	public Component getHoverName() {
		return stack.getHoverName();
	}

	public ItemStack setHoverName(@Nullable Component component) {
		return stack.setHoverName(component);
	}

	public void resetHoverName() {
		stack.resetHoverName();
	}

	public boolean hasCustomHoverName() {
		return stack.hasCustomHoverName();
	}

	public void hideTooltipPart(ItemStack.TooltipPart tooltipPart) {
		stack.hideTooltipPart(tooltipPart);
	}

	public boolean hasFoil() {
		return stack.hasFoil();
	}

	public Rarity getRarity() {
		return stack.getRarity();
	}

	public boolean isEnchantable() {
		return stack.isEnchantable();
	}

	public boolean isEnchanted() {
		return stack.isEnchanted();
	}

	public boolean isFramed() {
		return stack.isFramed();
	}

	public void setEntityRepresentation(@Nullable Entity entity) {
		stack.setEntityRepresentation(entity);
	}

	@Nullable
	public ItemFrame getFrame() {
		return stack.getFrame();
	}

	@Nullable
	public Entity getEntityRepresentation() {
		return stack.getEntityRepresentation();
	}

	public void setRepairCost(int i) {
		stack.setRepairCost(i);
	}

	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(
			EquipmentSlot equipmentSlot) {
		return stack.getAttributeModifiers(equipmentSlot);
	}

	public void addAttributeModifier(Attribute attribute,
									 AttributeModifier attributeModifier,
									 @Nullable EquipmentSlot equipmentSlot) {
		stack.addAttributeModifier(attribute, attributeModifier, equipmentSlot);
	}

	public Component getDisplayName() {
		return stack.getDisplayName();
	}

	public boolean hasAdventureModeBreakTagForBlock(TagContainer tagContainer,
													BlockInWorld blockInWorld) {
		return stack.hasAdventureModeBreakTagForBlock(tagContainer, blockInWorld);
	}

	public boolean hasAdventureModePlaceTagForBlock(TagContainer tagContainer,
													BlockInWorld blockInWorld) {
		return stack.hasAdventureModePlaceTagForBlock(tagContainer, blockInWorld);
	}

	public int getPopTime() {
		return stack.getPopTime();
	}

	public void setPopTime(int i) {
		stack.setPopTime(i);
	}

	public void grow(int i) {
		stack.grow(i);
	}

	public void shrink(int i) {
		stack.shrink(i);
	}

	public void onUseTick(Level level, LivingEntity livingEntity, int i) {
		stack.onUseTick(level, livingEntity, i);
	}

	public boolean isEdible() {
		return stack.isEdible();
	}

	public SoundEvent getDrinkingSound() {
		return stack.getDrinkingSound();
	}

	public SoundEvent getEatingSound() {
		return stack.getEatingSound();
	}
}
