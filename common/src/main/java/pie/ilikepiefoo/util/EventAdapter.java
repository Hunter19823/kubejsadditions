package pie.ilikepiefoo.util;

import dev.latvian.mods.kubejs.event.EventHandler;
import dev.latvian.mods.kubejs.event.EventResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pie.ilikepiefoo.events.ProxyEventJS;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class EventAdapter<T> implements InvocationHandler {
    public static final Logger LOG = LogManager.getLogger();
    public final String name;
    public final T handler;
    public final Class<T> eventClass;
    public final Set<Method> customMethods;
    public final EventHandler[] handlers;

    public EventAdapter(Class<T> eventClass, String eventName, EventHandler... handlers) {
        this.name = eventName;
        this.eventClass = eventClass;
        this.handlers = handlers;
        if (!this.eventClass.isInterface()) {
            throw new IllegalArgumentException("Event must be an interface!");
        }
        this.handler = eventClass.cast(Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[]{eventClass}, this));
        this.customMethods = Arrays.stream(this.eventClass.getMethods())
                .filter(method -> !Modifier.isStatic(method.getModifiers()))
                .filter(method -> !method.getDeclaringClass().equals(Object.class))
                .collect(Collectors.toSet());
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
            EventResult result = EventResult.PASS;
            for (EventHandler handler : this.handlers) {
                if (result == EventResult.PASS) {
                    result = handler.post(event, this.name);
                } else {
                    break;
                }
            }
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

}

