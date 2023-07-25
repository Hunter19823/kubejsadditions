package dev.kostromdan.mods.netjs.tasks;

import dev.kostromdan.mods.netjs.callbacks.NetJSICallback;
import dev.kostromdan.mods.netjs.utils.NetJSUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;

public class GistsTask extends AbstractNetJSTask {

	public GistsTask(String id, NetJSICallback c) {
		super(id);
		this.callback = c;
	}

	@Override
	public void run() {
		if (!NetJSUtils.isValidId(id, 32)) {
			exception(new RuntimeException("Non valid Gists id. It looks like you're doing something that this mod doesn't want to do."));
			return;
		}
		Connection.Response response;
		String raw_text;
		try {
			response = Jsoup.connect("https://api.github.com/gists/" + id).ignoreContentType(true).execute();
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

		result.putAll(NetJSUtils.parseRawJsonToMap(raw_text));
		success();
	}
}
