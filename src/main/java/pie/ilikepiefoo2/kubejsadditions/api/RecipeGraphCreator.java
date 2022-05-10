package pie.ilikepiefoo2.kubejsadditions.api;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pie.ilikepiefoo2.kubejsadditions.api.dot.DotFileCreator;
import pie.ilikepiefoo2.kubejsadditions.api.recipe.RecipeDependencySort;
import pie.ilikepiefoo2.kubejsadditions.api.recipe.RecipeGraph;

import java.io.IOException;

public class RecipeGraphCreator extends Thread{
	private static final Logger LOGGER = LogManager.getLogger();
	private final RecipeManager manager;
	private final RecipeGraph graph;
	private RecipeDependencySort dependencySort;

	public RecipeGraphCreator(RecipeManager manager){
		this.manager = manager;
		this.graph = new RecipeGraph();
		this.dependencySort = null;
	}

	@Override
	public void run(){
		LOGGER.info("Starting to create recipe graph...");
		LOGGER.info("There are " + manager.getRecipes().size() + " recipes.");
		manager.getRecipes().forEach(graph::addRecipe);
		LOGGER.info("Recipe graph created.");
		LOGGER.info("Graph contains " + graph.getVertexCount() + " vertices.");
		LOGGER.info("Graph contains " + graph.getEdgeCount() + " edges.");
		LOGGER.info("Graph has " + graph.getSelfCycleCount() + " self cycles.");

		LOGGER.info("Now generating dot file...");
		generateDotFile("recipe_graph.dot");
		LOGGER.info("Dot file generated.");
		LOGGER.info("Now doing topological sort...");
		this.dependencySort = new RecipeDependencySort(graph);
		LOGGER.info("Checking for cycles...");
		if(dependencySort.containsCycle()){
			LOGGER.info("The recipe graph contains cycles.");
		}else{
			LOGGER.info("The recipe graph does not contain cycles.");
		}
		LOGGER.info("Now printing all items that are not in a cycle...");
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		for(Item item : dependencySort.getOrder()){
			builder.append(item.getRegistryName()).append(", ");
		}
		builder.append("]");
		LOGGER.info(builder.toString());
		LOGGER.info("Finished Topological Sort.");
		LOGGER.info("Creating new recipe graph...");
		generateDotFile("recipe_graph_cycles.dot");
		LOGGER.info("Finished creating new recipe graph.");

		LOGGER.info("Finished creating recipe graph.");
	}

	private void generateDotFile(String fileName) {
		DotFileCreator creator = new DotFileCreator("RecipeGraph");
		for(var vertex : graph.getVertices()){
			creator.addNode(
					stripResourceLocation(vertex.getRegistryName()),
					String.format("label=\"ID: %s\nRecipes Using: %d\nRecipes Creating: %d\nSelf Recipes: %d\"", vertex.getRegistryName(), graph.getEdgeCount(vertex), graph.getInDegree(vertex),  graph.getSelfCycleCount(vertex))
				);
			for(var neighbor : graph.getNeighbors(vertex)){
				for(var edge : graph.getEdges(vertex, neighbor)) {
					creator.addEdge(
							stripResourceLocation(edge.from.getRegistryName()),
							stripResourceLocation(edge.to.getRegistryName()),
							"weight=" + edge.weight + " group = \"" + edge.recipe_id +"\""
					);
				}
			}
		}
		try {
			creator.createDotFile(fileName);
		} catch (IOException | IllegalArgumentException e) {
			LOGGER.error("Failed to create dot file.");
			LOGGER.error(e);
			LOGGER.info(creator.toString());
		}
	}

	private String stripResourceLocation(ResourceLocation location) {
		if(location == null) {
			return "null Item";
		}
		return location.toString().replace(":", "_").replace(" ", "");
	}
}
