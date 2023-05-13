package pie.ilikepiefoo.events;

import dev.architectury.event.Event;
import dev.latvian.mods.kubejs.event.EventJS;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pie.ilikepiefoo.compat.arch.ArchEventAdapter;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;

public class ArchEventRegisterEventJS extends EventJS {
	public static final Logger LOG = LogManager.getLogger();

	public ArchEventRegisterEventJS() {

	}

	public String register(String name, Class<?> eventProvider, String fieldName) {
		if (eventProvider == null) {
			throw new IllegalArgumentException("Event Provider cannot be null!");
		}
		if (fieldName == null) {
			throw new IllegalArgumentException("Field Name cannot be null!");
		}
		Field field = null;
		try {
			field = eventProvider.getField(fieldName);
		} catch (NoSuchFieldException e) {
			throw new IllegalArgumentException("Field Name must be a valid field!");
		}
		if (!Event.class.isAssignableFrom(field.getType())) {
			throw new IllegalArgumentException("Field must be of type Event!");
		}
		if (!(Modifier.isPublic(field.getModifiers()) || Modifier.isStatic(field.getModifiers()))) {
			throw new IllegalArgumentException("Event Field must be public static!");
		}
		Event event = null;
		try {
			event = (Event) field.get(null);
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException("Event Field must be public static!");
		}
		if (!(field.getGenericType() instanceof ParameterizedType parameterizedType)) {
			throw new IllegalArgumentException("Event Field must contain a be parameterized type!");
		}
		if (!(parameterizedType.getActualTypeArguments()[0] instanceof Class<?> eventClass)) {
			throw new IllegalArgumentException("Event Field must contain a be parameterized type!");
		}
		if (!eventClass.isInterface()) {
			throw new IllegalArgumentException("Event must be an interface!");
		}
		ArchEventAdapter<?> adapter = new ArchEventAdapter<>(event, eventClass, name);
		LOG.info("Created ArchEventAdapter for event '{}' with handler name '{}'", adapter.event.getClass().getName(), name);
		return name;
	}
}

