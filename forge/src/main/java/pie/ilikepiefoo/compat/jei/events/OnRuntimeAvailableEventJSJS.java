package pie.ilikepiefoo.compat.jei.events;

import mezz.jei.api.runtime.IJeiRuntime;

public class OnRuntimeAvailableEventJSJS extends JEIEventJS {
	public final IJeiRuntime data;

	public OnRuntimeAvailableEventJSJS(IJeiRuntime data) {
		this.data = data;
	}
}
