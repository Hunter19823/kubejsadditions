package pie.ilikepiefoo.util;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.stream.Stream;

public class ReflectionUtils {

    public static <T> Pair<Class<?>,T> retrieveEventClass( Class<?> eventProvider,
                                                           String fieldName,
                                                           Class<T> eventType ) throws IllegalArgumentException {
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
			String field_list = getFieldList(eventProvider.getFields());
			throw new IllegalArgumentException("Field Name must be a valid field! Valid fields are: " + field_list + "!");
		}
        if (!eventType.isAssignableFrom(field.getType())) {
            throw new IllegalArgumentException("Field must be of type Event!");
        }
        if (!( Modifier.isPublic(field.getModifiers()) || Modifier.isStatic(field.getModifiers()) )) {
			String field_list = getFieldList(eventProvider.getFields());
			throw new IllegalArgumentException("Event Field must be public static! Valid fields are: " + field_list + "!");
		}
        T event = null;
        try {
            event = (T) field.get(null);
        } catch (IllegalAccessException e) {
			String field_list = getFieldList(eventProvider.getFields());
			throw new IllegalArgumentException("Event Field must be public static! Valid fields are: " + field_list + "!");
		}
        if (!( field.getGenericType() instanceof ParameterizedType parameterizedType )) {
            throw new IllegalArgumentException("Event Field must contain a be parameterized type!");
        }
        Type type = parameterizedType.getActualTypeArguments()[ 0 ];
        Class<?> eventClass = null;
        if (type instanceof Class<?> clazz) {
            eventClass = clazz;
        }
        if (eventClass == null && type instanceof ParameterizedType parameterizedType2) {
            eventClass = (Class<?>) parameterizedType2.getRawType();
		}
		if (eventClass == null) {
			throw new IllegalArgumentException("Event Field must contain a either a parameterized type or a class!");
		}
		if (!eventClass.isInterface()) {
			throw new IllegalArgumentException("Event Type must be an interface!");
		}
		return new Pair<>(eventClass, event);
	}

	@NotNull
	private static String getFieldList(Field[] eventProvider) {
		String field_list = Arrays.toString(Stream.of(eventProvider)
				.filter(f -> Modifier.isPublic(f.getModifiers()) && Modifier.isStatic(f.getModifiers()))
				.map(Field::getName)
				.toArray());
		return field_list;
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

