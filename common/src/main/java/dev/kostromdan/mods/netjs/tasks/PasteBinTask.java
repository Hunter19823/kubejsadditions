package dev.kostromdan.mods.netjs.tasks;

import dev.kostromdan.mods.netjs.callbacks.NetJSICallback;
import dev.kostromdan.mods.netjs.tasks.subtasks.PasteBinMetaDataTask;
import dev.kostromdan.mods.netjs.tasks.subtasks.PasteBinRawTask;

public class PasteBinTask extends AbstractNetJSTask {

	public PasteBinTask(String id, NetJSICallback c) {
		super(id);
		this.callback = c;
	}

	@Override
	public void run() {
		PasteBinRawTask raw_task = new PasteBinRawTask(id);
		Thread raw_thread = new Thread(raw_task);
		raw_thread.start();

		PasteBinMetaDataTask metadata_task = new PasteBinMetaDataTask(id);
		Thread metadata_thread = new Thread(metadata_task);
		metadata_thread.start();

		try {
			raw_thread.join();
			metadata_thread.join();
		} catch (InterruptedException ie) {
			exception = ie;
			success = false;
			callback();
			return;
		}
		if (!metadata_task.success) {
			exception = metadata_task.exception;
			result = metadata_task.result;
			success = false;
			callback();
			return;
		}
		if (!raw_task.success) {
			exception = raw_task.exception;
			result = raw_task.result;
			success = false;
			callback();
			return;
		}

		result.putAll(metadata_task.result);
		result.putAll(raw_task.result);
		success = true;
		callback();
	}
}
