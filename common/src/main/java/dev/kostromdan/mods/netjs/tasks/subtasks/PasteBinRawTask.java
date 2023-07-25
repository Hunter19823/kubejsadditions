package dev.kostromdan.mods.netjs.tasks.subtasks;

import dev.kostromdan.mods.netjs.tasks.AbstractNetJSTask;
import dev.kostromdan.mods.netjs.utils.NetJSUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;

public class PasteBinRawTask extends AbstractNetJSTask {

	public PasteBinRawTask(String id) {
		super(id);
	}

	@Override
	public void run() {
		if (!NetJSUtils.isValidId(id, 8)) {
			exception(new RuntimeException("Non valid PasteBin id. It looks like you're doing something that this mod doesn't want to do."));
			return;
		}
		Connection.Response response;
		String raw_text;
		try {
			response = Jsoup.connect("https://pastebin.com/raw/" + id).ignoreContentType(true).execute();
			raw_text = response.body();
		} catch (IOException ioe) {
			exception(ioe);
			return;
		}

		int response_code = response.statusCode();
		result.put("response_code", response_code);
		if (response_code != 200) {
			result.put("raw_response_text", raw_text);
			exception(new RuntimeException("Response code " + response_code + " != 200! raw_response_text can contain more info."));
			return;
		}

		result.put("raw_text", raw_text);
		success();
	}
}
