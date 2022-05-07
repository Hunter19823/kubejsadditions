package pie.ilikepiefoo2.kubejsadditions;


import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.EventBus;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pie.ilikepiefoo2.kubejsadditions.api.EventHandlers;

@Mod("kubejsadditions")
public class KubeJSAdditions {
	private static final Logger LOGGER = LogManager.getLogger();

	public KubeJSAdditions() {
		// Register the event handlers
		MinecraftForge.EVENT_BUS.addListener(EventHandlers::onRecipeAdded);
	}
}
