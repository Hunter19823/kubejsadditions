package pie.ilikepiefoo.fabric.events.custom;

import dev.latvian.mods.kubejs.event.EventJS;
import net.fabricmc.fabric.api.event.Event;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pie.ilikepiefoo.fabric.FabricEventsJS;
import pie.ilikepiefoo.util.EventAdapter;
import pie.ilikepiefoo.util.ReflectionUtils;

public class FabricEventRegisterEventJS extends EventJS {
	public static final Logger LOG = LogManager.getLogger();

	public FabricEventRegisterEventJS() {

	}

	public String register(String name, Class<?> eventProvider, String fieldName) {
		ReflectionUtils.Pair<Class<?>, Event> pair = ReflectionUtils.retrieveEventClass(eventProvider, fieldName, Event.class);
		EventAdapter<?> adapter = new EventAdapter<>(pair.getA(), name,
				FabricEventsJS.FABRIC_STARTUP_EVENT_HANDLER,
				FabricEventsJS.FABRIC_SERVER_EVENT_HANDLER,
				FabricEventsJS.FABRIC_CLIENT_EVENT_HANDLER
		);
		pair.getB().register(adapter.handler);
		LOG.info("Created EventAdapter with the name '{}'", name);
		return name;
	}

}

