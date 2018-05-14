package info;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Shop implements Runnable {
	
	String URL_SHOP_HEAD = "http://bns.plaync.com/bs/market/search?ct=0-0&level=0-0&stepper=&exact=";
	String URL_SHOP_TAIL = "&sort=default-asc&type=INVALID&grade=0&prevq=&q=";
	Document doc;
	
	boolean exact;
	String item;
	private final Object lock = new Object();
	
	
	public Shop() {
		
	}
	
	public Shop(String item, boolean exact) {
		this.item = item;
		this.exact = exact;
	}
	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		try {
			Document doc = Jsoup.connect(URL_SHOP_HEAD + (exact ? "1" : "") + URL_SHOP_TAIL + item).get();
			synchronized (lock) {
				this.doc = doc;
				lock.notifyAll();
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}
	
	public String waitForgetPrice() {
        synchronized (lock) {
            try {
                while (this.doc == null) {
                    lock.wait();
                }
                
                String result;
                Elements items = this.doc.select("td.price");//물품 관련 요소 추출
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
    			
    			return result;
    			
            } catch (InterruptedException e) {
                // Again better error handling
                e.printStackTrace();
            }

            return null;
        }
    }
	
	public String waitForgetMinPrice() {
		synchronized(lock) {
			try {
                while (this.doc == null) {
                    lock.wait();
                }
                
                String result;
                Elements items = this.doc.select("td.price");
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

                return result;
                
                
            } catch (InterruptedException e) {
                // Again better error handling
                e.printStackTrace();
            }

            return "검색 결과가 없습니다";
		}
	}
	

	

}
