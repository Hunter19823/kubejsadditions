package dev.kostromdan.mods.netjs.bindings;

import dev.kostromdan.mods.netjs.async.NetJSIAsyncCallback;
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
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedHashMap;

public interface NetJSWrapper {


	static NetJSResult getPasteBinResult(String id) {
		if (!NetJSUtils.isValidId(id, 8)) {
			return new NetJSResultExeption(new RuntimeException("Non valid PasteBin id. It looks like you're doing something that this mod doesn't want to do."));
		}
		Connection.Response response;
		Document doc;
		try {
			response = Jsoup.connect("https://pastebin.com/" + id).execute();
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

		Element elems = doc.getElementsByClass("post-view js-post-view").first();
		if (elems == null) {
			result.put("raw_response_text", doc.text());
			return new NetJSResultExeption(new RuntimeException("Can't parse pastebin page. PasteBin site changed? You using wrong pastebin id? raw_response_text can contain more info."), result);
		}


		Element raw = elems.getElementsByClass("source").first();
		result.put("raw_text", raw != null ? raw.text() : null);

		Element post_name = elems.getElementsByClass("info-top").first();
		result.put("post_name", post_name != null ? post_name.text() : null);
		Element author_username = elems.getElementsByClass("username").first();
		result.put("author_username", author_username != null ? author_username.text() : null);

		Element date = elems.getElementsByClass("date").first();
		if (date != null) {
			result.put("is_edited", date.text().contains(" (edited)"));
			result.put("date_created", date.text().replace(" (edited)", ""));
		}
		Element expire = elems.getElementsByClass("expire").first();
		result.put("date_expire", expire != null ? expire.text() : null);

		Element visits = elems.getElementsByClass("visits").first();
		result.put("visits_count", visits != null ? Integer.parseInt(visits.text()) : null);
		Element stars = elems.getElementsByClass("rating js-post-rating").first();
		result.put("stars_count", stars != null ? Integer.parseInt(stars.text()) : null);

		Element left = elems.getElementsByClass("left").first();
		Elements left_elems;
		String lang = null;
		String category = null;
		String size = null;
		if (left != null && (left_elems = left.children()).size() >= 2) {
			lang = left_elems.get(0).text();
			category = left_elems.get(1).text().replace("| ", "");
			size = left.text().replace(lang, "").split("\\|")[0].strip();
		}
		result.put("lang", lang);
		result.put("category", category);
		result.put("size", size);

		Element likes = elems.getElementsByClass("btn -small -like").first();
		result.put("likes_count", likes != null ? Integer.parseInt(likes.text()) : null);
		Element dislikes = elems.getElementsByClass("btn -small -dislike").first();
		result.put("dislikes_count", dislikes != null ? Integer.parseInt(dislikes.text()) : null);

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

	static void getPasteBin(String id, NetJSIAsyncCallback c) {
		NetJSResult result = getPasteBinResult(id);
		c.onCallback(result);
	}

	static void getGists(String id, NetJSIAsyncCallback c) {
		NetJSResult result = getGistsResult(id);
		c.onCallback(result);
	}

	static void getPasteBinAsync(String id, NetJSIAsyncCallback c) {
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

	static void getGistsAsync(String id, NetJSIAsyncCallback c) {
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
