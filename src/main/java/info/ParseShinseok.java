package info;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParseShinseok {

	Document doc;
	int checker;
	String result;

	public ParseShinseok() {
		try {
			// 신석샵 목록사이트 연결
			doc = Jsoup.connect("http://bns.plaync.com/login/displayItemList").get();

			checker = 1;// 연결성공시 체커 1
		} catch (IOException e) {
			// TODO Auto-generated catch block
			checker = 0;// 연결실패시 체커 0
			e.printStackTrace();
		}
	}

	public String getList() {

		if (checker == 0) {
			// 연결실패시
			return "";
		}
		result = "";

		Elements itemNames = doc.select("li h3");// 아이템이름 추출
		Elements itemPrices = doc.select("li span.shinseok em");// 아이템 가격 추출

		for (int i = 0; i < itemNames.size(); i++) {
			result += itemNames.get(i).text() + "\t\t " + (int) Double.parseDouble(itemPrices.get(i).text()) + " 신석\n";
		}

		return result;

	}

}
