package dev.kostromdan.mods.netjs.bindings;

import dev.kostromdan.mods.netjs.results.NetJSResult;
import dev.kostromdan.mods.netjs.results.NetJSResultExeption;
import dev.kostromdan.mods.netjs.results.NetJSResultSuccess;
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

	static NetJSResult getPasteBinString(String paste_id) {
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
		Element raw = elems.getElementsByClass("source").first();
		Element post_name = elems.getElementsByClass("info-top").first();
		Element author_username = elems.getElementsByClass("username").first();
		Element date = elems.getElementsByClass("date").first();
		Element visits = elems.getElementsByClass("visits").first();
		Element stars = elems.getElementsByClass("rating js-post-rating").first();
		Element expire = elems.getElementsByClass("expire").first();
		Element left = elems.getElementsByClass("left").first();
		Elements left_elems = left.children();
		Element lang = left_elems.get(0);
		Element category = left_elems.get(1);
		Element size = left;
		Element likes = elems.getElementsByClass("btn -small -like").first();
		Element dislikes = elems.getElementsByClass("btn -small -dislike").first();
		return new NetJSResultSuccess(paste_id, raw, post_name, author_username, date, expire, visits, stars, lang, size, category, likes, dislikes);
	}
}
