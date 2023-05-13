package pie.ilikepiefoo.compat.arch;

import dev.architectury.event.Event;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pie.ilikepiefoo.events.AdditionalEvents;
import pie.ilikepiefoo.events.ProxyEventJS;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class ArchEventAdapter<T> implements InvocationHandler {
	public static final Logger LOG = LogManager.getLogger();
	public final Event<T> event;
	private final String name;
	private final T handler;
	private final Class<T> eventClass;
	private final Set<Method> customMethods;
	private Object[] args;
	private Object result;

	public ArchEventAdapter(Event<T> event, Class<T> eventClass, String eventName) {
		this.event = event;
		this.name = eventName;
		this.eventClass = eventClass;
		LOG.info("ArchEventAdapter type: " + eventClass.getName());
		if (!this.eventClass.isInterface()) {
			throw new IllegalArgumentException("Event must be an interface!");
		}
		this.handler = eventClass.cast(Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[]{eventClass}, this));
		this.customMethods = Arrays.stream(this.eventClass.getMethods()).filter(method -> !method.getDeclaringClass().equals(Object.class)).collect(Collectors.toSet());
		this.event.register(this.handler);
	}

	/**
	 * Processes a method invocation on a proxy instance and returns
	 * the result.  This method will be invoked on an invocation handler
	 * when a method is invoked on a proxy instance that it is
	 * associated with.
	 *
	 * @param proxy  the proxy instance that the method was invoked on
	 * @param method the {@code Method} instance corresponding to
	 *               the interface method invoked on the proxy instance.  The declaring
	 *               class of the {@code Method} object will be the interface that
	 *               the method was declared in, which may be a superinterface of the
	 *               proxy interface that the proxy class inherits the method through.
	 * @param args   an array of objects containing the values of the
	 *               arguments passed in the method invocation on the proxy instance,
	 *               or {@code null} if interface method takes no arguments.
	 *               Arguments of primitive types are wrapped in instances of the
	 *               appropriate primitive wrapper class, such as
	 *               {@code java.lang.Integer} or {@code java.lang.Boolean}.
	 * @return the value to return from the method invocation on the
	 * proxy instance.  If the declared return type of the interface
	 * method is a primitive type, then the value returned by
	 * this method must be an instance of the corresponding primitive
	 * wrapper class; otherwise, it must be a type assignable to the
	 * declared return type.  If the value returned by this method is
	 * {@code null} and the interface method's return type is
	 * primitive, then a {@code NullPointerException} will be
	 * thrown by the method invocation on the proxy instance.  If the
	 * value returned by this method is otherwise not compatible with
	 * the interface method's declared return type as described above,
	 * a {@code ClassCastException} will be thrown by the method
	 * invocation on the proxy instance.
	 * @throws Throwable the exception to throw from the method
	 *                   invocation on the proxy instance.  The exception's type must be
	 *                   assignable either to any of the exception types declared in the
	 *                   {@code throws} clause of the interface method or to the
	 *                   unchecked exception types {@code java.lang.RuntimeException}
	 *                   or {@code java.lang.Error}.  If a checked exception is
	 *                   thrown by this method that is not assignable to any of the
	 *                   exception types declared in the {@code throws} clause of
	 *                   the interface method, then an
	 *                   {@link UndeclaredThrowableException} containing the
	 *                   exception that was thrown by this method will be thrown by the
	 *                   method invocation on the proxy instance.
	 * @see UndeclaredThrowableException
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if (this.customMethods.contains(method)) {
			ProxyEventJS event = new ProxyEventJS(method, args);
			AdditionalEvents.ARCH_EVENT_HANDLER.post(this.name, event, true);
			if (event.requiresResult()) {
				if (!event.hasResult()) {
					throw new IllegalArgumentException("Arch Event requires a result but was provided none!");
				} else {
					return event.getResult();
				}
			}
			return null;
		} else {
			return InvocationHandler.invokeDefault(proxy, method, args);
		}
	}

	public Object[] getArgs() {
		return args;
	}

	public void setResult(Object result) {
		this.result = result;
	}


}

