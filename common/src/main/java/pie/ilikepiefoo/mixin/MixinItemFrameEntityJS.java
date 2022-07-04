package pie.ilikepiefoo.mixin;

import dev.latvian.kubejs.entity.ItemEntityJS;
import dev.latvian.kubejs.entity.ItemFrameEntityJS;
import dev.latvian.kubejs.item.ItemStackJS;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = ItemFrameEntityJS.class, remap = false)
public class MixinItemFrameEntityJS {

	@Shadow
	@Final
	private ItemFrame itemFrameEntity;

	public ItemFrameEntityJS getInstance() {
		return ((ItemFrameEntityJS) (Object) this);
	}

	public boolean survives() {
		return itemFrameEntity.survives();
	}

	public void move(MoverType moverType, Vec3 vec3) {
		itemFrameEntity.move(moverType, vec3);
	}

	public void push(double d, double e, double f) {
		itemFrameEntity.push(d, e, f);
	}

	public float getPickRadius() {
		return itemFrameEntity.getPickRadius();
	}

	public void kill() {
		itemFrameEntity.kill();
	}

	public boolean hurt(DamageSource damageSource, float f) {
		return itemFrameEntity.hurt(damageSource, f);
	}

	public int getWidth() {
		return itemFrameEntity.getWidth();
	}

	public int getHeight() {
		return itemFrameEntity.getHeight();
	}

	@Environment(EnvType.CLIENT)
	public boolean shouldRenderAtSqrDistance(double d) {
		return itemFrameEntity.shouldRenderAtSqrDistance(d);
	}

	public void dropItem(@Nullable Entity entity) {
		itemFrameEntity.dropItem(entity);
	}

	public void playPlacementSound() {
		itemFrameEntity.playPlacementSound();
	}

	public ItemStackJS getItem() {
		return new ItemStackJS(itemFrameEntity.getItem());
	}

	public void setItem(ItemStack itemStack) {
		itemFrameEntity.setItem(itemStack);
	}

	public void setItem(ItemStack itemStack, boolean bl) {
		itemFrameEntity.setItem(itemStack, bl);
	}

	public boolean setSlot(int i, ItemStack itemStack) {
		return itemFrameEntity.setSlot(i, itemStack);
	}

	public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
		itemFrameEntity.onSyncedDataUpdated(entityDataAccessor);
	}

	public int getRotation() {
		return itemFrameEntity.getRotation();
	}

	public void setRotation(int i) {
		itemFrameEntity.setRotation(i);
	}

	public void addAdditionalSaveData(CompoundTag compoundTag) {
		itemFrameEntity.addAdditionalSaveData(compoundTag);
	}

	public void readAdditionalSaveData(CompoundTag compoundTag) {
		itemFrameEntity.readAdditionalSaveData(compoundTag);
	}

	public InteractionResult interact(Player player,
									  InteractionHand interactionHand) {
		return itemFrameEntity.interact(player, interactionHand);
	}

	public int getAnalogOutput() {
		return itemFrameEntity.getAnalogOutput();
	}

	public Packet<?> getAddEntityPacket() {
		return itemFrameEntity.getAddEntityPacket();
	}

	public void tick() {
		itemFrameEntity.tick();
	}

	public boolean isPickable() {
		return itemFrameEntity.isPickable();
	}

	public boolean skipAttackInteraction(Entity entity) {
		return itemFrameEntity.skipAttackInteraction(entity);
	}

	public Direction getDirection() {
		return itemFrameEntity.getDirection();
	}

	public ItemEntityJS spawnAtLocation(ItemStack itemStack, float f) {
		ItemEntity itemEntity = itemFrameEntity.spawnAtLocation(itemStack, f);
		return (itemEntity == null) ? null : new ItemEntityJS(getInstance().getWorld(), itemEntity);
	}

	public void setPos(double d, double e, double f) {
		itemFrameEntity.setPos(d, e, f);
	}

	public BlockPos getPos() {
		return itemFrameEntity.getPos();
	}

	public float rotate(Rotation rotation) {
		return itemFrameEntity.rotate(rotation);
	}

	public float mirror(Mirror mirror) {
		return itemFrameEntity.mirror(mirror);
	}

	public void thunderHit(ServerLevel serverLevel, LightningBolt lightningBolt) {
		itemFrameEntity.thunderHit(serverLevel, lightningBolt);
	}

	public void refreshDimensions() {
		itemFrameEntity.refreshDimensions();
	}
}
