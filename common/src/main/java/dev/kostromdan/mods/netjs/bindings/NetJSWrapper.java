package dev.kostromdan.mods.netjs.bindings;

import com.google.gson.JsonObject;
import dev.kostromdan.mods.netjs.async.NetJSIAsyncCallback;
import dev.kostromdan.mods.netjs.results.NetJSGistsResultSuccess;
import dev.kostromdan.mods.netjs.results.NetJSPasteBinResultSuccess;
import dev.kostromdan.mods.netjs.results.NetJSResult;
import dev.kostromdan.mods.netjs.results.NetJSResultExeption;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import dev.latvian.mods.kubejs.util.JsonIO;
import dev.latvian.mods.rhino.RhinoException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public interface NetJSWrapper {

	private static boolean validIdChar(char c) {
		return c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c >= '0' && c <= '9';
	}

	private static boolean isValidId(String s, int max_len) {
		if (s.length() > max_len) {
			return false;
		}
		for (int i = 0; i < s.length(); ++i) {
			if (!validIdChar(s.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	static NetJSResult getPasteBinResult(String id) {
		if (!isValidId(id, 8)) {
			return new NetJSResultExeption(id, new RuntimeException("Non valid PasteBin id. It looks like you're doing something that this mod doesn't want to do."));
		}
		Connection.Response response;
		Document doc;
		try {
			response = Jsoup.connect("https://pastebin.com/" + id).execute();
			doc = response.parse();
		} catch (IOException ioe) {
			return new NetJSResultExeption(id, ioe);
		}

		JsonObject result = new JsonObject();
		int response_code = response.statusCode();
		result.addProperty("response_code", response_code);
		if (response_code != 200) {
			result.addProperty("raw_response_text", doc.text());
			return new NetJSResultExeption(id, new RuntimeException("Response code " + response_code + " != 200! raw_response_text can contain more info."), result);
		}

		Element elems = doc.getElementsByClass("post-view js-post-view").first();
		if (elems == null) {
			result.addProperty("raw_response_text", doc.text());
			return new NetJSResultExeption(id, new RuntimeException("Can't parse pastebin page. PasteBin site changed? You using wrong pastebin id? raw_response_text can contain more info."), result);
		}


		Element raw = elems.getElementsByClass("source").first();
		result.addProperty("raw_text", raw != null ? raw.text() : null);

		Element post_name = elems.getElementsByClass("info-top").first();
		result.addProperty("post_name", post_name != null ? post_name.text() : null);
		Element author_username = elems.getElementsByClass("username").first();
		result.addProperty("author_username", author_username != null ? author_username.text() : null);

		Element date = elems.getElementsByClass("date").first();
		if (date != null) {
			result.addProperty("is_edited", date.text().contains(" (edited)"));
			result.addProperty("date_created", date.text().replace(" (edited)", ""));
		}
		Element expire = elems.getElementsByClass("expire").first();
		result.addProperty("date_expire", expire != null ? expire.text() : null);

		Element visits = elems.getElementsByClass("visits").first();
		result.addProperty("visits_count", visits != null ? Integer.parseInt(visits.text()) : null);
		Element stars = elems.getElementsByClass("rating js-post-rating").first();
		result.addProperty("stars_count", stars != null ? Integer.parseInt(stars.text()) : null);

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
		result.addProperty("lang", lang);
		result.addProperty("category", category);
		result.addProperty("size", size);

		Element likes = elems.getElementsByClass("btn -small -like").first();
		result.addProperty("likes_count", likes != null ? Integer.parseInt(likes.text()) : null);
		Element dislikes = elems.getElementsByClass("btn -small -dislike").first();
		result.addProperty("dislikes_count", dislikes != null ? Integer.parseInt(dislikes.text()) : null);

		return new NetJSPasteBinResultSuccess(id, result);
	}

	static NetJSResult getGistsResult(String id) {
		if (!isValidId(id, 32)) {
			return new NetJSResultExeption(id, new RuntimeException("Non valid Gists id. It looks like you're doing something that this mod doesn't want to do."));
		}
		Connection.Response response;
		Document doc;
		try {
			response = Jsoup.connect("https://api.github.com/gists/" + id).ignoreContentType(true).execute();
			doc = response.parse();
		} catch (IOException ioe) {
			return new NetJSResultExeption(id, ioe);
		}

		JsonObject result = new JsonObject();
		int response_code = response.statusCode();
		result.addProperty("response_code", response_code);
		if (response_code != 200) {
			result.addProperty("raw_response_text", doc.text());
			return new NetJSResultExeption(id, new RuntimeException("Response code " + response_code + " != 200! raw_response_text can contain more info."), result);
		}
		JsonObject gists_answer = (JsonObject) JsonIO.parseRaw(doc.text());
		result.add("gists_answer", gists_answer);

		return new NetJSGistsResultSuccess(id, result);
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
