package info;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Shop {
	
	String URL_SHOP = "http://bns.plaync.com/bs/market/search?ct=0-0&level=0-0&stepper=&exact=&sort=default-asc&type=INVALID&grade=0&prevq=&q=";
	Document doc;
	
	int checker;
	String result;
	ArrayList<ShopItem> itemList;
	
	
	public Shop(String item) {
		try {
			doc = Jsoup.connect(URL_SHOP + item).get();
			checker = 1;
			itemList = new ArrayList();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			checker = 0;
			e.printStackTrace();
		}
		
		
	}
	
	public String getPrice() {
		
		if(checker == 0) {
			result = "검색 결과가 없습니다";
		} else {

			Elements items = doc.select("td.price");
			if(items.isEmpty()) {
				result = "검색 결과가 없습니다";
			} else {
				result = "";
			}
			Elements itemNames = doc.select("tbody tr th img");
		

			Element item;
			for(int i=0; i<items.size(); i++){
				result += itemNames.get(i).attr("title") + "\t\t";
				item = items.get(i);
				if(!item.getElementsByClass("unit").isEmpty()) {
					result += item.getElementsByClass("unit").text().replace("개당", "") + "\n";
				} else {
					result += item.getElementsByClass("total").text().replace("전체", "") + "\n";
				}
			}
		}
		
		return result;
		
		
	}
	
	public String getMinPrice() {
		
		if(checker == 0) {
			result = "검색 결과가 없습니다";
		} else {

			Elements items = doc.select("td.price");
			if(items.isEmpty()) {
				result = "검색 결과가 없습니다";
			} else {
				result = "";
			}
		

			Element item;
			int i=0;
			item = items.get(i);
			if(!item.getElementsByClass("unit").isEmpty()) {
				result += item.getElementsByClass("unit").text().replace("개당", "") + "\n";
			} else {
				result += item.getElementsByClass("total").text().replace("전체", "") + "\n";
			}
			
		}
		
		return result;
		
		
	}

}
