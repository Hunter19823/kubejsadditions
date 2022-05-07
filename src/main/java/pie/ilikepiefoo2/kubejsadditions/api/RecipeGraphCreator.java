package pie.ilikepiefoo2.kubejsadditions.api;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pie.ilikepiefoo2.kubejsadditions.api.dot.DotFileCreator;
import pie.ilikepiefoo2.kubejsadditions.api.recipe.RecipeGraph;

import java.io.IOException;

public class RecipeGraphCreator extends Thread{
	private static final Logger LOGGER = LogManager.getLogger();
	private final RecipeManager manager;
	private final RecipeGraph graph;

	public RecipeGraphCreator(RecipeManager manager){
		this.manager = manager;
		this.graph = new RecipeGraph();
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
		generateDotFile();
		LOGGER.info("Dot file generated.");
	}

	private void generateDotFile(){
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
			creator.createDotFile();
		} catch (IOException e) {
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
