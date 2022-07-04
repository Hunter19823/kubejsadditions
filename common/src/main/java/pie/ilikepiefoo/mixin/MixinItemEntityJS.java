package pie.ilikepiefoo.mixin;

import dev.latvian.kubejs.entity.EntityJS;
import dev.latvian.kubejs.entity.ItemEntityJS;
import dev.latvian.kubejs.item.ItemStackJS;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.UUID;

@Mixin(value = ItemEntityJS.class, remap = false)
public class MixinItemEntityJS {

	public ItemEntityJS getInstance() {
		return ((ItemEntityJS) (Object) this);
	}

	@Shadow
	@Final
	private ItemEntity itemEntity;

	public void tick() {
		itemEntity.tick();
	}

	public boolean fireImmune() {
		return itemEntity.fireImmune();
	}

	public boolean hurt(DamageSource damageSource, float f) {
		return itemEntity.hurt(damageSource, f);
	}

	public void addAdditionalSaveData(CompoundTag compoundTag) {
		itemEntity.addAdditionalSaveData(compoundTag);
	}

	public void readAdditionalSaveData(CompoundTag compoundTag) {
		itemEntity.readAdditionalSaveData(compoundTag);
	}

	public void playerTouch(Player player) {
		itemEntity.playerTouch(player);
	}

	public Component getName() {
		return itemEntity.getName();
	}

	public boolean isAttackable() {
		return itemEntity.isAttackable();
	}

	@Nullable
	public EntityJS changeDimension(ServerLevel serverLevel) {
		Entity entity = itemEntity.changeDimension(serverLevel);

		return (entity == null) ? null : new EntityJS(getInstance().getWorld(), entity);
	}

	public ItemStackJS getItem() {
		return new ItemStackJS(itemEntity.getItem());
	}

	public void setItem(ItemStack itemStack) {
		itemEntity.setItem(itemStack);
	}

	public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
		itemEntity.onSyncedDataUpdated(entityDataAccessor);
	}

	@Nullable
	public UUID getOwner() {
		return itemEntity.getOwner();
	}

	public void setOwner(@Nullable UUID uUID) {
		itemEntity.setOwner(uUID);
	}

	@Nullable
	public UUID getThrower() {
		return itemEntity.getThrower();
	}

	public void setThrower(@Nullable UUID uUID) {
		itemEntity.setThrower(uUID);
	}

	@Environment(EnvType.CLIENT)
	public int getAge() {
		return itemEntity.getAge();
	}

	public void setDefaultPickUpDelay() {
		itemEntity.setDefaultPickUpDelay();
	}

	public void setNoPickUpDelay() {
		itemEntity.setNoPickUpDelay();
	}

	public void setNeverPickUp() {
		itemEntity.setNeverPickUp();
	}

	public void setPickUpDelay(int i) {
		itemEntity.setPickUpDelay(i);
	}

	public boolean hasPickUpDelay() {
		return itemEntity.hasPickUpDelay();
	}

	public void setExtendedLifetime() {
		itemEntity.setExtendedLifetime();
	}

	public void makeFakeItem() {
		itemEntity.makeFakeItem();
	}

	@Environment(EnvType.CLIENT)
	public float getSpin(float f) {
		return itemEntity.getSpin(f);
	}

	public Packet<?> getAddEntityPacket() {
		return itemEntity.getAddEntityPacket();
	}

	@Environment(EnvType.CLIENT)
	public ItemEntityJS copy() {
		return new ItemEntityJS(getInstance().getWorld(), itemEntity.copy());
	}
}
