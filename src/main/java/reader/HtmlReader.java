package reader;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HtmlReader {
	private static final String ENCODING = "UTF-8";
	private static final String HEDER_FILE_NAME = "heder.html";
	private static final String FOOTER_FILE_NAME = "footer.html";
	private static String path = HtmlReader.class.getResource("").toString().substring(5);

	public static String heder;
	public static String footer;

	public static String getHeder() {
		if (heder == null) {
			File file = new File(path.replace("reader/", HEDER_FILE_NAME));
			try {
				Document document = Jsoup.parse(file, ENCODING);
				heder = document.html();
			} catch (IOException e) {
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
			}
			return heder;
		} else {
			return heder;
		}
	}

	public static void setHeder(String newHeder) {
		heder = newHeder;
	}

	public static String getFooter() {
		File file = new File(path.replace("reader/", FOOTER_FILE_NAME));
		try {
			Document document = Jsoup.parse(file, ENCODING);
			footer = document.html();
		} catch (IOException e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());

		}
		return footer;
	}
}
