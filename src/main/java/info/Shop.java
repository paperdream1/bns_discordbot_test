package info;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Shop{
	
	String URL_SHOP_HEAD = "http://bns.plaync.com/bs/market/search?ct=0-0&level=0-0&stepper=&exact=";
	String URL_SHOP_TAIL = "&sort=default-asc&type=INVALID&grade=0&prevq=&q=";
	Document doc;
	
	int checker;
	String result;
	
	
	public Shop() {
		
	}
	
	public Shop(String item, boolean exact) {
		try {
			//블소 시장페이지에 연결
			doc = Jsoup.connect(URL_SHOP_HEAD + (exact ? "1" : "") + URL_SHOP_TAIL + item).get();
			checker = 1;//연결성공하면 체커 1
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			checker = 0;//연결실패하면 체커 0
			e.printStackTrace();
		}
	}
	
	public void searchItem(String item, boolean exact) {
		try {
			//블소 시장페이지에 연결
			doc = Jsoup.connect(URL_SHOP_HEAD + (exact ? "1" : "") + URL_SHOP_TAIL + item).get();
			checker = 1;//연결성공하면 체커 1
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			checker = 0;//연결실패하면 체커 0
			e.printStackTrace();
		}
	}
	
	//시장물품 반환
	public String getPrice() {
		
		if(checker == 0) {
			//연결 실패시
			result = "검색 결과가 없습니다";
		} else {
			//연결 성공시
			Elements items = doc.select("td.price");//물품 관련 요소 추출
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
	
	//최저가 반환
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
	
	public int getChecker() {
		return checker;
	}

	

}
