package pie.ilikepiefoo.events.custom;

import dev.latvian.mods.kubejs.event.EventJS;
import dev.latvian.mods.kubejs.script.ScriptType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pie.ilikepiefoo.util.EventAdapter;
import pie.ilikepiefoo.util.ReflectionUtils;

import java.util.function.BiConsumer;

public class RegisterProxyEventJS extends EventJS {
    public static final Logger LOG = LogManager.getLogger();

    /**
     * Registers a new proxy event.
     * For the JS side, an example would be:
     * <pre>
     * {@code
     * console.log("Now loading reflection classes…");
     * let $SERVER_SIDE_ROLL_EVENTS = java('net.combatroll.api.event.ServerSideRollEvents');
     * let FIELD_NAME = 'PLAYER_START_ROLLING';
     * onEvent('proxy.event.register', event => {
     *     console.log("Now registering custom proxy events…");
     *     event.register('player.roll', $SERVER_SIDE_ROLL_EVENTS, FIELD_NAME, (eventClass, eventHandler) => eventClass.register(eventHandler));
     * });
     * //---------------------------------------------------------------------------------------------------------------------//
     * onEvent('player.roll', event => {
     *     console.info('Player rolled!');
     * })
     * }</pre>
     *
     * @param name          The name of the event that will be registered and used to listen to the event.
     * @param eventProvider The class that contains the event field.
     *                      This class must be a public class and must be accessible from the class loader.
     *                      This class must also contain a public static field.
     * @param fieldName     The name of the field that contains the event.
     *                      This field must be public and static.
     *                      This field must also contain a singular parameterized type.
     *                      This parameterized type must be an interface.
     * @param eventConsumer The consumer that will be called to allow the JS side to register the event handler.
     *                      The first parameter is the event object retrieved from the public static field.
     *                      The second parameter is the event handler that was created for the event.
     * @param <T>           The type of interface that the event handler is expected to be.
     * @return The name of the event that was registered.
     */
    public <T> String register(String name, Class<?> eventProvider, String fieldName, BiConsumer<Object, Object> eventConsumer) {
        ReflectionUtils.Pair<Class<?>, T> pair = ReflectionUtils.retrieveEventClass(eventProvider, fieldName, null);
        EventAdapter<?> adapter = new EventAdapter<>(pair.getA(), name,
                ScriptType.STARTUP,
                ScriptType.SERVER,
                ScriptType.CLIENT
        );
        LOG.info("Passing newly created event adapter for the provided event class '{}'. Be sure to properly register the event!", pair.getA().getName());
        eventConsumer.accept(pair.getB(), adapter.handler);
        LOG.info("Created ArchEventAdapter with the name '{}'", name);
        return name;
    }

}

