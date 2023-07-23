package dev.kostromdan.mods.netjs.pastebin;

import dev.kostromdan.mods.netjs.utils.NetJSUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.LinkedHashMap;

public class PasteBinRawTask extends PasteBinTask implements Runnable {

	public PasteBinRawTask(String id) {
		super();
		this.id = id;
	}

	@Override
	public void run() {
		if (!NetJSUtils.isValidId(id, 8)) {
			this.exception = new RuntimeException("Non valid PasteBin id. It looks like you're doing something that this mod doesn't want to do.");
			this.success = false;
			return;
		}
		Connection.Response response;
		Document doc;
		try {
			response = Jsoup.connect("https://pastebin.com/raw/" + id).ignoreContentType(true).execute();
			doc = response.parse();
		} catch (IOException ioe) {
			this.exception = ioe;
			this.success = false;
			return;
		}

		int response_code = response.statusCode();
		result.put("response_code", response_code);
		if (response_code != 200) {
			result.put("raw_response_text", doc.text());
			this.exception = new RuntimeException("Response code " + response_code + " != 200! raw_response_text can contain more info.");
			this.success = false;
			return;
		}

		result.put("raw_text", doc.text());
		this.success = true;
	}
}
