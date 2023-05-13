package pie.ilikepiefoo.events.custom;

import dev.architectury.event.Event;
import dev.latvian.mods.kubejs.event.EventJS;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pie.ilikepiefoo.events.AdditionalEvents;
import pie.ilikepiefoo.util.EventAdapter;
import pie.ilikepiefoo.util.ReflectionUtils;

public class ArchEventRegisterEventJS extends EventJS {
	public static final Logger LOG = LogManager.getLogger();

	public ArchEventRegisterEventJS() {

	}

	public String register(String name, Class<?> eventProvider, String fieldName) {
		ReflectionUtils.Pair<Class<?>, Event> pair = ReflectionUtils.retrieveEventClass(eventProvider, fieldName, Event.class);
		EventAdapter<?> adapter = new EventAdapter<>(pair.getA(), name,
				AdditionalEvents.ARCH_STARTUP_EVENT_HANDLER,
				AdditionalEvents.ARCH_SERVER_EVENT_HANDLER,
				AdditionalEvents.ARCH_CLIENT_EVENT_HANDLER
		);
		pair.getB().register(adapter.handler);
		LOG.info("Created ArchEventAdapter with the name '{}'", name);
		return name;
	}
}

