package pie.ilikepiefoo.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;

public class ReflectionUtils {

	public static <T> Pair<Class<?>, T> retrieveEventClass(Class<?> eventProvider, String fieldName, Class<T> eventType) throws IllegalArgumentException {
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
		if (!eventType.isAssignableFrom(field.getType())) {
			throw new IllegalArgumentException("Field must be of type Event!");
		}
		if (!(Modifier.isPublic(field.getModifiers()) || Modifier.isStatic(field.getModifiers()))) {
			throw new IllegalArgumentException("Event Field must be public static!");
		}
		T event = null;
		try {
			event = (T) field.get(null);
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
		return new Pair<>(eventClass, event);
	}

	public static class Pair<A, B> {
		public A a;
		public B b;

		public Pair(A a, B b) {
			this.a = a;
			this.b = b;
		}

		public A getA() {
			return a;
		}

		public B getB() {
			return b;
		}
	}

}

