package dev.kostromdan.mods.netjs.tasks.subtasks;

import dev.kostromdan.mods.netjs.tasks.AbstractNetJSTask;
import dev.kostromdan.mods.netjs.utils.NetJSUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class PasteBinMetaDataTask extends AbstractNetJSTask {


	public PasteBinMetaDataTask(String id) {
		super(id);
	}

	@Override
	public void run() {
		if (!NetJSUtils.isValidId(id, 8)) {
			exception(new RuntimeException("Non valid PasteBin id. It looks like you're doing something that this mod doesn't want to do."));
			return;
		}
		Connection.Response response;
		Document doc;
		try {
			response = Jsoup.connect("https://pastebin.com/" + id).execute();
			doc = response.parse();
		} catch (IOException ioe) {
			exception(ioe);
			return;
		}

		int response_code = response.statusCode();
		result.put("response_code", response_code);
		if (response_code != 200) {
			result.put("raw_response_text", doc.body());
			exception(new RuntimeException("Response code " + response_code + " != 200! raw_response_text can contain more info."));
			return;
		}

		Element elems = doc.getElementsByClass("post-view js-post-view").first();
		if (elems == null) {
			result.put("raw_response_text", doc.body());
			exception(new RuntimeException("Can't parse pastebin page. PasteBin site changed? You using wrong pastebin id? raw_response_text can contain more info."));
			return;
		}


		Element post_name = elems.getElementsByClass("info-top").first();
		result.put("post_name", post_name != null ? post_name.text() : null);
		Element author_username = elems.getElementsByClass("username").first();
		result.put("author_username", author_username != null ? author_username.text() : null);

		Element date = elems.getElementsByClass("date").first();
		boolean is_edited = false;
		if (date != null) {
			Elements date_elems = date.select("span[title]");
			if (date_elems.size() >= 1) {
				Element date_created = date_elems.first();
				result.put("date_created", date_created != null ? date_created.attr("title") : null);
			}
			if (date_elems.size() >= 2) {
				is_edited = true;
				Element date_edited = date.select("span[title]").get(1);
				result.put("date_edited", date_edited != null ? date_edited.attr("title").replace("Last edit on: ", "") : null);
			}
		}
		result.put("is_edited", is_edited);
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

		success();
	}
}
