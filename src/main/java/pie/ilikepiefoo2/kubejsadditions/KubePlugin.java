package pie.ilikepiefoo2.kubejsadditions;



import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.rhino.util.wrap.TypeWrappers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class KubePlugin extends KubeJSPlugin {
	private static final Logger LOGGER = LogManager.getLogger();
	@Override
	public void addTypeWrappers(ScriptType type, TypeWrappers typeWrappers) {
		LOGGER.info("Adding type wrappers for KubeJSAdditions");
	}
}
