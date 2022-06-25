package pie.ilikepiefoo.mixin;


import dev.latvian.mods.kubejs.item.InventoryJS;
import dev.latvian.mods.kubejs.item.ItemStackJS;
import dev.latvian.mods.kubejs.level.BlockContainerJS;
import dev.latvian.mods.kubejs.player.PlayerJS;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = PlayerJS.class)
public class MixinPlayerJS {

	@Shadow
	@Final
	public Player minecraftPlayer;

	public void dropItem(ItemStack item, boolean dropAll) {
		minecraftPlayer.drop(item, dropAll);
	}

	public float getMiningSpeed(BlockState state) {
		return minecraftPlayer.getDestroySpeed(state);
	}

	public boolean canHarvestBlock(BlockState state) {
		return minecraftPlayer.hasCorrectToolForDrops(state);
	}

	public boolean canHarm(Player player) {
		return minecraftPlayer.canHarmPlayer(player);
	}

	public InteractionResult interactWith(Entity entity, InteractionHand hand) {
		return minecraftPlayer.interactOn(entity, hand);
	}

	public void attack(Entity entity) {
		minecraftPlayer.attack(entity);
	}

	public void disableShield(boolean forced){
		(minecraftPlayer).disableShield(forced);
	}

	public void remove(Entity.RemovalReason reason) { // I wonder if this will cause any issues...
		minecraftPlayer.remove(reason);
	}

	public void stopSleeping() {
		minecraftPlayer.stopSleeping();
	}

	public void awardStat(ResourceLocation stat) {
		minecraftPlayer.awardStat(stat);
	}

	public void awardStat(ResourceLocation stat, int amount) {
		minecraftPlayer.awardStat(stat, amount);
	}

	public void travel(Vec3 pos) {
		minecraftPlayer.travel(pos);
	}

	public void toggleElytra(boolean enabled) {
		if(enabled) {
			minecraftPlayer.startFallFlying();
		} else {
			minecraftPlayer.stopFallFlying();
		}
	}

	public void forceSleep(BlockPos pos) {
		minecraftPlayer.startSleeping(pos);
	}

	public int getFoodLevel() {
		return minecraftPlayer.getFoodData().getFoodLevel();
	}

	public float getFoodSaturationLevel() {
		return minecraftPlayer.getFoodData().getSaturationLevel();
	}

	public void setFoodLevel(int level) {
		minecraftPlayer.getFoodData().setFoodLevel(level);
	}

	public void setFoodSaturationLevel(float level) {
		minecraftPlayer.getFoodData().setSaturation(level);
	}

	public boolean needsFood() {
		return minecraftPlayer.getFoodData().needsFood();
	}

	public void magicEat(int foodLevel, int saturation) {
		minecraftPlayer.getFoodData().eat(foodLevel, saturation);
	}

	public void magicEat(Item food, ItemStack foodStack) {
		minecraftPlayer.getFoodData().eat(food, foodStack);
	}

	public void eat(ItemStack foodStack) {
		minecraftPlayer.eat(minecraftPlayer.level, foodStack);
	}

	public void foodExhaust(float amount) {
		minecraftPlayer.getFoodData().addExhaustion(amount);
	}

	public InventoryJS getEnderChest() {
		return new InventoryJS(minecraftPlayer.getEnderChestInventory());
	}

	public boolean hasCooldown(Item item) {
		return minecraftPlayer.getCooldowns().isOnCooldown(item);
	}

	public void removeCooldown(Item item) {
		minecraftPlayer.getCooldowns().removeCooldown(item);
	}

	public float getCooldownPercent(Item item) {
		return getCooldownPercent(item, 0);
	}

	public float getCooldownPercent(Item item, int additionalTime) {
		return minecraftPlayer.getCooldowns().getCooldownPercent(item, additionalTime);
	}

	public float getLuck() {
		return minecraftPlayer.getLuck();
	}

	public ItemStackJS getAmmo(ItemStack stack) {
		return ItemStackJS.of(minecraftPlayer.getProjectile(stack));
	}

	public int getArmorValue() {
		return minecraftPlayer.getArmorValue();
	}

	public MobType getMobType() {
		return (minecraftPlayer.getMobType());
	}

	public void push(Entity entity){
		minecraftPlayer.push(entity);
	}

	public void simulateItemPickup(ItemEntity stack) {
		minecraftPlayer.onItemPickup(stack);
	}

	public BlockContainerJS getBed() {
		if(minecraftPlayer.getSleepingPos().isPresent()) {
			return new BlockContainerJS(minecraftPlayer.level,minecraftPlayer.getSleepingPos().get());
		}
		return null;
	}



}
