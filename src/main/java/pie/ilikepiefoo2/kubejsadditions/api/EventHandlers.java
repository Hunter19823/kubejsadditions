package pie.ilikepiefoo2.kubejsadditions.api;

import net.minecraftforge.client.event.RecipesUpdatedEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EventHandlers {
	public static RecipeGraphCreator creator = null;
	public static final Logger LOGGER = LogManager.getLogger();

	public static void onRecipeAdded(RecipesUpdatedEvent event) {
		if(creator == null) {
			RecipeGraphCreator creator = new RecipeGraphCreator(event.getRecipeManager());
			creator.start();
			LOGGER.info("RecipeGraphCreator started");
		}else{
			LOGGER.info("RecipeGraphCreator already running!");
		}
	}
}
