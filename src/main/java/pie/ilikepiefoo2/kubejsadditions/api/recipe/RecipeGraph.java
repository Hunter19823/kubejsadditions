package pie.ilikepiefoo2.kubejsadditions.api.recipe;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pie.ilikepiefoo2.kubejsadditions.api.WeightedDiGraphImpl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RecipeGraph extends WeightedDiGraphImpl<Item, RecipeEdge> {
	private static final Logger LOGGER = LogManager.getLogger();
	public void addRecipe(Recipe<?> recipe) {
		LOGGER.debug("Adding recipe {}", recipe);
		HashMap<Item, Integer> usableItems = new HashMap<>();
		recipe.getIngredients().forEach(ingredient -> Arrays.stream(ingredient.getItems()).parallel().forEach(item -> {
			if (usableItems.containsKey(item.getItem())) {
				usableItems.put(item.getItem(), usableItems.get(item.getItem()) + item.getCount());
			}else{
				usableItems.put(item.getItem(), item.getCount());
			}
		}));
		for (Map.Entry<Item, Integer> entry : usableItems.entrySet()) {
			Item item = entry.getKey();
			Integer count = entry.getValue();
			addEdge(new RecipeEdge(new ItemStack(item, count), recipe.getResultItem(), recipe.getId()));
		}
	}

	public RecipeGraph deepCopy() {
		RecipeGraph copy = new RecipeGraph();
		for(Item from : getVertices()) {
			for(Item to : getNeighbors(from)) {
				for(RecipeEdge edge : getEdges(from, to)) {
					copy.addEdge(edge.copy());
				}
			}
		}
		return copy;
	}
}
