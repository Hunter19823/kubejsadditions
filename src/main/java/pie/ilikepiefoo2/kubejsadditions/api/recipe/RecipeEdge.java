package pie.ilikepiefoo2.kubejsadditions.api.recipe;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import pie.ilikepiefoo2.kubejsadditions.api.graph.WeightedEdge;

public class RecipeEdge extends WeightedEdge<Item> {
	public ResourceLocation recipe_id;

	public RecipeEdge(@NotNull ItemStack from, @NotNull ItemStack to, ResourceLocation recipe_id) {
		super(from.getItem(), to.getItem(), to.getCount()/(double)from.getCount());
		this.recipe_id = recipe_id;
	}

	public RecipeEdge(@NotNull Item from, @NotNull Item to, double weight, ResourceLocation recipe_id) {
		super(from, to, weight);
		this.recipe_id = recipe_id;
	}

	public RecipeEdge copy() {
		return new RecipeEdge(from, to, weight, recipe_id);
	}
}
