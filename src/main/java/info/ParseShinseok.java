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
			doc = Jsoup.connect("http://bns.plaync.com/login/displayItemList").get();
			
			/*********
			URL url = new URL("http://a.bns.plaync.com/bnsapi/main/shop/displaygoods");
			URLConnection urlConnection = url.openConnection();
			HttpURLConnection httpurlConnection = (HttpURLConnection) urlConnection;
			
			httpurlConnection.setRequestMethod("GET");
			httpurlConnection.setDoInput(true);
			httpurlConnection.setDoOutput(true);
			httpurlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			InputStream is = httpurlConnection.getInputStream();

			
			StringBuilder builder = new StringBuilder(); //문자열을 담기 위한 객체
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8")); //문자열 셋 세팅
			String line;

			while ((line = reader.readLine()) != null) {
			builder.append(line+ "\n");
			}

			result = builder.toString();
			***************/
			
			
			checker = 1;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			checker = 0;
			e.printStackTrace();
		}
	}
	
	public String getList() {
		
		if(checker == 0) {
			return "";
		}
		result = "";
		
		Elements itemNames = doc.select("li h3");
		Elements itemPrices = doc.select("li span.shinseok em");
		
		for(int i=0; i<itemNames.size(); i++) {
			result += itemNames.get(i).text() + "\t\t " + (int)Double.parseDouble(itemPrices.get(i).text()) + " 신석\n";
		}
		
		
		

		return result;
		/*
		Elements items = doc.select("#mp-itn b a");
		
		for(Element item : items) {
			System.out.println(item.toString());
		}
		*/
		
		//JSONParser parser = new JSONParser();
	}
	
	
	
	

}
