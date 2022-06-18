package pie.ilikepiefoo.mixin;

import dev.latvian.kubejs.item.InventoryJS;
import dev.latvian.kubejs.item.ItemStackJS;
import dev.latvian.kubejs.player.PlayerJS;
import dev.latvian.kubejs.world.BlockContainerJS;
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

	public void dropItem(boolean dropAll) {
		if(minecraftPlayer != null) {
			minecraftPlayer.drop(dropAll);
		}

		throw new NullPointerException("Minecraft player is null!");
	}

	public void dropItem(ItemStack item, boolean dropAll) {
		if(minecraftPlayer != null) {
			minecraftPlayer.drop(item, dropAll);
		}

		throw new NullPointerException("Minecraft player is null!");
	}

	public float getMiningSpeed(BlockState state) {
		if(minecraftPlayer != null) {
			return minecraftPlayer.getDestroySpeed(state);
		}

		throw new NullPointerException("Minecraft player is null!");
	}

	public boolean canHarvestBlock(BlockState state) {
		if(minecraftPlayer != null) {
			return minecraftPlayer.hasCorrectToolForDrops(state);
		}
		throw new NullPointerException("Minecraft player is null!");
	}

	public boolean canHarm(Player player) {
		if(minecraftPlayer != null) {
			return minecraftPlayer.canHarmPlayer(player);
		}
		throw new NullPointerException("Minecraft player is null!");
	}

	public InteractionResult interactWith(Entity entity, InteractionHand hand) {
		if(minecraftPlayer != null) {
			return minecraftPlayer.interactOn(entity, hand);
		}
		throw new NullPointerException("Minecraft player is null!");
	}

	public void attack(Entity entity) {
		if(minecraftPlayer != null) {
			minecraftPlayer.attack(entity);
		}
		throw new NullPointerException("Minecraft player is null!");
	}

	public void disableShield(boolean forced){
		if(minecraftPlayer != null) {
			(minecraftPlayer).disableShield(forced);
		}
		throw new NullPointerException("Minecraft player is null!");
	}

	public void remove() { // I wonder if this will cause any issues...
		if(minecraftPlayer != null) {
			minecraftPlayer.remove();
		}
		throw new NullPointerException("Minecraft player is null!");
	}

	public void stopSleeping() {
		if(minecraftPlayer != null) {
			minecraftPlayer.stopSleeping();
		}
		throw new NullPointerException("Minecraft player is null!");
	}

	public void awardStat(ResourceLocation stat) {
		if(minecraftPlayer != null) {
			minecraftPlayer.awardStat(stat);
		}
		throw new NullPointerException("Minecraft player is null!");
	}

	public void awardStat(ResourceLocation stat, int amount) {
		if(minecraftPlayer != null) {
			minecraftPlayer.awardStat(stat, amount);
		}
		throw new NullPointerException("Minecraft player is null!");
	}

	public void travel(Vec3 pos) {
		if(minecraftPlayer != null) {
			minecraftPlayer.travel(pos);
		}
		throw new NullPointerException("Minecraft player is null!");
	}

	public void toggleElytra(boolean enabled) {
		if(minecraftPlayer != null) {
			if(enabled) {
				minecraftPlayer.startFallFlying();
			} else {
				minecraftPlayer.stopFallFlying();
			}
		}
		throw new NullPointerException("Minecraft player is null!");
	}

	public void forceSleep(BlockPos pos) {
		if(minecraftPlayer != null) {
			minecraftPlayer.startSleeping(pos);
		}
		throw new NullPointerException("Minecraft player is null!");
	}

	public int getFoodLevel() {
		if(minecraftPlayer != null) {
			return minecraftPlayer.getFoodData().getFoodLevel();
		}
		throw new NullPointerException("Minecraft player is null!");
	}

	public float getFoodSaturationLevel() {
		if(minecraftPlayer != null) {
			return minecraftPlayer.getFoodData().getSaturationLevel();
		}
		throw new NullPointerException("Minecraft player is null!");
	}

	public void setFoodLevel(int level) {
		if(minecraftPlayer != null) {
			minecraftPlayer.getFoodData().setFoodLevel(level);
		}
		throw new NullPointerException("Minecraft player is null!");
	}

	public void setFoodSaturationLevel(float level) {
		if(minecraftPlayer != null) {
			minecraftPlayer.getFoodData().setSaturation(level);
		}
		throw new NullPointerException("Minecraft player is null!");
	}

	public boolean needsFood() {
		if(minecraftPlayer != null) {
			return minecraftPlayer.getFoodData().needsFood();
		}
		throw new NullPointerException("Minecraft player is null!");
	}

	public void magicEat(int foodLevel, int saturation) {
		if(minecraftPlayer != null) {
			minecraftPlayer.getFoodData().eat(foodLevel, saturation);
		}
		throw new NullPointerException("Minecraft player is null!");
	}

	public void magicEat(Item food, ItemStack foodStack) {
		if(minecraftPlayer != null) {
			minecraftPlayer.getFoodData().eat(food, foodStack);
		}
		throw new NullPointerException("Minecraft player is null!");
	}

	public void eat(ItemStack foodStack) {
		if(minecraftPlayer != null) {
			minecraftPlayer.eat(minecraftPlayer.level, foodStack);
		}
		throw new NullPointerException("Minecraft player is null!");
	}

	public void foodExhaust(float amount) {
		if(minecraftPlayer != null) {
			minecraftPlayer.getFoodData().addExhaustion(amount);
		}
		throw new NullPointerException("Minecraft player is null!");
	}

	public InventoryJS getEnderChest() {
		if(minecraftPlayer != null) {
			return new InventoryJS(minecraftPlayer.getEnderChestInventory());
		}
		throw new NullPointerException("Minecraft player is null!");
	}

	public boolean hasCooldown(Item item) {
		if(minecraftPlayer != null) {
			return minecraftPlayer.getCooldowns().isOnCooldown(item);
		}
		throw new NullPointerException("Minecraft player is null!");
	}

	public void removeCooldown(Item item) {
		if(minecraftPlayer != null) {
			minecraftPlayer.getCooldowns().removeCooldown(item);
		}
		throw new NullPointerException("Minecraft player is null!");
	}

	public float getCooldownPercent(Item item) {
		return getCooldownPercent(item, 0);
	}

	public float getCooldownPercent(Item item, int additionalTime) {
		if(minecraftPlayer != null) {
			return minecraftPlayer.getCooldowns().getCooldownPercent(item, additionalTime);
		}
		throw new NullPointerException("Minecraft player is null!");
	}

	public float getLuck() {
		if(minecraftPlayer != null) {
			return minecraftPlayer.getLuck();
		}
		throw new NullPointerException("Minecraft player is null!");
	}

	public ItemStackJS getAmmo(ItemStack stack) {
		if(minecraftPlayer != null) {
			return ItemStackJS.of(minecraftPlayer.getProjectile(stack));
		}
		throw new NullPointerException("Minecraft player is null!");
	}

	public int getArmorValue() {
		if(minecraftPlayer != null) {
			return minecraftPlayer.getArmorValue();
		}
		throw new NullPointerException("Minecraft player is null!");
	}

	public MobType getMobType() {
		if(minecraftPlayer != null) {
			return (minecraftPlayer.getMobType());
		}
		throw new NullPointerException("Minecraft player is null!");
	}

	public void push(Entity entity){
		if(minecraftPlayer != null) {
			minecraftPlayer.push(entity);
		}
		throw new NullPointerException("Minecraft player is null!");
	}

	public void simulateItemPickup(ItemEntity stack) {
		if(minecraftPlayer != null) {
			minecraftPlayer.onItemPickup(stack);
		}
		throw new NullPointerException("Minecraft player is null!");
	}

	public BlockContainerJS getBed() {
		if(minecraftPlayer == null)
			throw new NullPointerException("Minecraft player is null!");
		if(minecraftPlayer.getSleepingPos().isPresent()) {
			return new BlockContainerJS(minecraftPlayer.level,minecraftPlayer.getSleepingPos().get());
		}
		return null;
	}



}
