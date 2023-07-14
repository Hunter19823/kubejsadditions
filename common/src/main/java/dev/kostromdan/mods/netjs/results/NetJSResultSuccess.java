package dev.kostromdan.mods.netjs.results;

import org.jsoup.nodes.Element;

public class NetJSResultSuccess extends NetJSResult {
	public String post_name = null;
	public String author_username = null;
	public String date = null;
	public String expire = null;
	public String lang = null;
	public String size = null;
	public String category = null;
	public int visits = 0;
	public int stars = 0;
	public int likes = 0;
	public int dislikes = 0;
	public boolean edited;

	public String toString() {
		return raw;
	}

	public NetJSResultSuccess(String id, Element raw, Element post_name, Element author_username, Element date, Element expire, Element visits, Element stars, Element lang, Element size, Element category, Element likes, Element dislikes) {
		this.success = true;
		this.id = id;
		this.raw = raw.text();
		this.post_name = post_name.text();
		this.author_username = author_username.text();
		this.edited = date.text().contains(" (edited)");
		this.date = date.text().replace(" (edited)", "");
		this.expire = expire.text();
		this.visits = Integer.parseInt(visits.text());
		this.stars = Integer.parseInt(stars.text());
		this.lang = lang.text();
		this.category = category.text().replace("| ", "");
		this.size = size.text().replace(this.lang+" ","").split(this.category)[0].replace("|", "").strip();
		this.likes = Integer.parseInt(likes.text());
		this.dislikes = Integer.parseInt(dislikes.text());
	}
}
