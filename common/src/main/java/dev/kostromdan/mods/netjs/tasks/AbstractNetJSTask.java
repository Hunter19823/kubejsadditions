package dev.kostromdan.mods.netjs.tasks;

import dev.kostromdan.mods.netjs.callbacks.NetJSICallback;
import dev.kostromdan.mods.netjs.results.NetJSResultMap;
import dev.latvian.mods.kubejs.util.ConsoleJS;

public abstract class AbstractNetJSTask implements Runnable {
	public String id = null;
	public NetJSResultMap<String, Object> result;
	protected NetJSICallback callback = null;

	private boolean is_subtask() {
		return this.getClass().getPackage().toString().endsWith(".subtasks");
	}

	public AbstractNetJSTask(String id) {
		result = new NetJSResultMap<String, Object>(this.getClass());
		this.id = id;
	}

	public void callback() {
		if (this.is_subtask()) {
			return;
		}
		try {
			callback.onCallback(result);
		} catch (Throwable ex) {
			ConsoleJS.SERVER.error("Error occurred while handling NetJS callback: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public void success() {
		result.put("success", true);
		callback();
	}

	public void exception(Exception err) {
		result.put("success", false);
		result.put("exception", err);
		callback();
	}

	public boolean isSuccess() {
		return result.isSuccess();
	}

	public Exception getException() {
		return result.getException();
	}
}
