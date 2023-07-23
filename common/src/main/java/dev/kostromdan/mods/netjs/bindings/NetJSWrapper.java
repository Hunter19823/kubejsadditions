package dev.kostromdan.mods.netjs.bindings;

import dev.kostromdan.mods.netjs.callbacks.NetJSICallback;
import dev.kostromdan.mods.netjs.pastebin.PasteBinMetaDataTask;
import dev.kostromdan.mods.netjs.pastebin.PasteBinRawTask;
import dev.kostromdan.mods.netjs.results.NetJSGistsResultSuccess;
import dev.kostromdan.mods.netjs.results.NetJSPasteBinResultSuccess;
import dev.kostromdan.mods.netjs.results.NetJSResult;
import dev.kostromdan.mods.netjs.results.NetJSResultExeption;
import dev.kostromdan.mods.netjs.utils.NetJSUtils;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import dev.latvian.mods.rhino.RhinoException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.LinkedHashMap;

public interface NetJSWrapper {


	static NetJSResult getPasteBinResult(String id) {

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
			return new NetJSResultExeption(ie);
		}
		if (!metadata_task.success) {
			return new NetJSResultExeption(metadata_task.exception, metadata_task.result);
		}
		if (!raw_task.success) {
			return new NetJSResultExeption(raw_task.exception, raw_task.result);
		}

		ConsoleJS.SERVER.log(metadata_task.result);
		ConsoleJS.SERVER.log(raw_task.result);
		LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
		result.putAll(metadata_task.result);
		result.putAll(raw_task.result);
		ConsoleJS.SERVER.log(result);
		return new NetJSPasteBinResultSuccess(result);
	}

	static NetJSResult getGistsResult(String id) {
		if (!NetJSUtils.isValidId(id, 32)) {
			return new NetJSResultExeption(new RuntimeException("Non valid Gists id. It looks like you're doing something that this mod doesn't want to do."));
		}
		Connection.Response response;
		Document doc;
		try {
			response = Jsoup.connect("https://api.github.com/gists/" + id).ignoreContentType(true).execute();
			doc = response.parse();
		} catch (IOException ioe) {
			return new NetJSResultExeption(ioe);
		}
		LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();

		int response_code = response.statusCode();
		result.put("response_code", response_code);
		if (response_code != 200) {
			result.put("raw_response_text", doc.text());
			return new NetJSResultExeption(new RuntimeException("Response code " + response_code + " != 200! raw_response_text can contain more info."), result);
		}

		result.putAll(NetJSUtils.parseRawJsonToMap(doc.text()));

		return new NetJSGistsResultSuccess(result);
	}

	static void getPasteBin(String id, NetJSICallback c) {
		NetJSResult result = getPasteBinResult(id);
		c.onCallback(result);
	}

	static void getGists(String id, NetJSICallback c) {
		NetJSResult result = getGistsResult(id);
		c.onCallback(result);
	}

	static void getPasteBinAsync(String id, NetJSICallback c) {
		Thread thread = new Thread(() -> {
			try {
				getPasteBin(id, c);
			} catch (RhinoException ex) {
				ConsoleJS.SERVER.error("Error occurred while handling async NetJS callback: " + ex.getMessage());
			} catch (Throwable ex) {
				ex.printStackTrace();
			}
		});
		thread.start();
	}

	static void getGistsAsync(String id, NetJSICallback c) {
		Thread thread = new Thread(() -> {
			try {
				getGists(id, c);
			} catch (RhinoException ex) {
				ConsoleJS.SERVER.error("Error occurred while handling async NetJS callback: " + ex.getMessage());
			} catch (Throwable ex) {
				ex.printStackTrace();
			}
		});
		thread.start();
	}
}
