package pie.ilikepiefoo2.kubejsadditions.api.recipe;

import net.minecraft.world.item.Item;
import pie.ilikepiefoo2.kubejsadditions.api.graph.TopologicalSort;

public class RecipeDependencySort extends TopologicalSort<Item, RecipeEdge> {

	public RecipeDependencySort(RecipeGraph graph) {
		super(graph);
	}
}
