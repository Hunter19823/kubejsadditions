package dev.kostromdan.mods.netjs.bindings;

import dev.kostromdan.mods.netjs.callbacks.NetJSICallback;
import dev.kostromdan.mods.netjs.tasks.GistsTask;
import dev.kostromdan.mods.netjs.tasks.PasteBinTask;
import dev.kostromdan.mods.netjs.tasks.AbstractNetJSTask;
import dev.latvian.mods.kubejs.util.ConsoleJS;

public interface NetJSWrapper {


	static void getResult(AbstractNetJSTask task, String id, boolean is_async) {
		Thread thread = new Thread(task);
		thread.start();

		if (!is_async) {
			try {
				thread.join();
			} catch (InterruptedException ie) {
				ConsoleJS.SERVER.log("interrupted");
			}
		}
	}

	static void getPasteBin(String id, boolean is_async, NetJSICallback c) {
		PasteBinTask pastebin_task = new PasteBinTask(id, c);
		getResult(pastebin_task, id, is_async);
	}

	static void getGists(String id, boolean is_async, NetJSICallback c) {
		GistsTask gists_task = new GistsTask(id, c);
		getResult(gists_task, id, is_async);
	}

	static void getPasteBin(String id, NetJSICallback c) {
		getPasteBin(id, true, c);
	}

	static void getGists(String id, NetJSICallback c) {
		getGists(id, true, c);
	}
}
