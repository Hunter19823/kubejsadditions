package pie.ilikepiefoo.events;

import dev.latvian.mods.kubejs.event.EventJS;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;
import java.util.Optional;

public class ProxyEventJS extends EventJS {
	public static final Logger LOG = LogManager.getLogger();
	private final Method method;
	private final Object[] args;
	private Optional<Object> result;
	private boolean hasResult = false;

	public ProxyEventJS(final Method method, final Object[] args) {
		this.method = method;
		this.args = args;
		this.result = Optional.empty();
	}

	public String getMethodName() {
		return method.getName();
	}

	public Object[] getArgs() {
		return args;
	}

	public Optional<Object> getResultOptional() {
		return result;
	}

	public Object getResult() {
		return result.orElse(null);
	}

	public void setResult(final Object result) {
		this.hasResult = true;
		this.result = Optional.ofNullable(result);
	}

	public boolean hasResult() {
		return hasResult;
	}

	public boolean requiresResult() {
		return !method.getReturnType().equals(Void.TYPE);
	}

	public Object getArg(final int index) {
		return args[index];
	}


}

