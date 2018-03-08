package at.saith.twasi.rank.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import net.twasi.core.logger.TwasiLogger;

public class JSONURLParser {
	public String parseUrl(String urlString) throws IOException{
	try {
		return this.parseUrl(new URL(urlString));
	} catch (MalformedURLException e) {
		TwasiLogger.log.error(e.getMessage());
	}
	return "{}";
	}
	public String parseUrl(URL url) throws IOException {
		BufferedReader reader = null;

		try {
			URLConnection connection = url.openConnection();
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));
			StringBuilder buffer = new StringBuilder();
			int read;
			char[] chars = new char[1024];
			while ((read = reader.read(chars)) != -1)
				buffer.append(chars, 0, read);

			return buffer.toString();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
	}
}
