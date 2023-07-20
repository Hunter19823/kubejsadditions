package dev.kostromdan.mods.netjs.bindings;

import com.google.gson.JsonObject;
import dev.kostromdan.mods.netjs.async.NetJSIAsyncCallback;
import dev.kostromdan.mods.netjs.results.NetJSResult;
import dev.kostromdan.mods.netjs.results.NetJSResultExeption;
import dev.kostromdan.mods.netjs.results.NetJSResultSuccess;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import dev.latvian.mods.rhino.RhinoException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public interface NetJSWrapper {

	private static boolean validPasteBinIdChar(char c) {
		return c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c >= '0' && c <= '9';
	}

	private static boolean isValidPasteBinId(String s) {
		if (s.length() >= 10) {
			return false;
		}
		for (int i = 0; i < s.length(); ++i) {
			if (!validPasteBinIdChar(s.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	static NetJSResult getPasteBinResult(String paste_id) {
		if (!isValidPasteBinId(paste_id)) {
			return new NetJSResultExeption(paste_id, new RuntimeException("Non valid PasteBin id. It looks like you're doing something that this mod doesn't want to do."));
		}
		Document doc;
		try {
			doc = Jsoup.connect("https://pastebin.com/" + paste_id).get();
		} catch (IOException ioe) {
			return new NetJSResultExeption(paste_id, ioe);
		}
		Element elems = doc.getElementsByClass("post-view js-post-view").first();
		if (elems == null) {
			return new NetJSResultExeption(paste_id, new RuntimeException("Can't parse pastebin page. PasteBin site changed? You using wrong pastebin id?"));
		}

		JsonObject result = new JsonObject();
//		result.addProperty("raw_html_text", doc.text());

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

		return new NetJSResultSuccess(paste_id, result);
	}

	static void getPasteBin(String paste_id, NetJSIAsyncCallback c) {
		NetJSResult result = getPasteBinResult(paste_id);
		c.onCallback(result);
	}

	static void getPasteBinAsync(String paste_id, NetJSIAsyncCallback c) {
		Thread thread = new Thread(() -> {
			try {
				getPasteBin(paste_id, c);
			} catch (RhinoException ex) {
				ConsoleJS.SERVER.error("Error occurred while handling async NetJS callback: " + ex.getMessage());
			} catch (Throwable ex) {
				ex.printStackTrace();
			}
		});
		thread.start();
	}
}
