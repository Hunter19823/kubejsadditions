package pie.ilikepiefoo.compat.jei.events;

import dev.latvian.kubejs.event.EventJS;

public class JEIEventJS extends EventJS {

	@Override
	public boolean canCancel() {
		return false;
	}
}
