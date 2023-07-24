package dev.kostromdan.mods.netjs.tasks;

import dev.kostromdan.mods.netjs.callbacks.NetJSICallback;
import dev.kostromdan.mods.netjs.utils.NetJSUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class GistsTask extends AbstractNetJSTask implements Runnable {

	public GistsTask(String id, NetJSICallback c) {
		super(id);
		this.callback = c;
	}

	@Override
	public void run() {
		if (!NetJSUtils.isValidId(id, 32)) {
			this.exception = new RuntimeException("Non valid Gists id. It looks like you're doing something that this mod doesn't want to do.");
			this.success = false;
			callback();
			return;
		}
		Connection.Response response;
		String doc;
		try {
			response = Jsoup.connect("https://api.github.com/gists/" + id).ignoreContentType(true).execute();
			doc = response.body();
		} catch (IOException ioe) {
			this.exception = ioe;
			this.success = false;
			callback();
			return;
		}


		int response_code = response.statusCode();
		result.put("response_code", response_code);
		if (response_code != 200) {
			result.put("raw_response_text", doc);
			this.exception = new RuntimeException("Response code " + response_code + " != 200! raw_response_text can contain more info.");
			this.success = false;
			callback();
			return;
		}

		result.putAll(NetJSUtils.parseRawJsonToMap(doc));
		this.success = true;
		callback();
	}
}
