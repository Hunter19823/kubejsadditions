package dev.kostromdan.mods.netjs.bindings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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

	static String getPasteBinString(String paste_id) {
		if (!isValidPasteBinId(paste_id)) {
			throw new RuntimeException(
					"Non valid PasteBin id. It looks like you're doing something that this mod doesn't want to do.");
		}
		try {
			HttpURLConnection connection = (HttpURLConnection) new URL(
					"https://pastebin.com/raw/" + paste_id).openConnection();
			int responseCode = connection.getResponseCode();
			InputStream inputStream;
			if (200 <= responseCode && responseCode <= 299) {
				inputStream = connection.getInputStream();
			} else {
				inputStream = connection.getErrorStream();
			}

			BufferedReader in = new BufferedReader(
					new InputStreamReader(
							inputStream));

			StringBuilder response = new StringBuilder();
			String currentLine;

			while ((currentLine = in.readLine()) != null) {
				response.append(currentLine);
			}

			in.close();

			return response.toString();
		} catch (IOException ioe) {
			return null;
		}
	}
}
