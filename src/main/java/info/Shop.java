package info;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import de.btobastian.javacord.entities.Channel;

public class Shop implements Runnable {

	String URL_SHOP_HEAD = "http://bns.plaync.com/bs/market/search?ct=0-0&level=0-0&stepper=&exact=";
	String URL_SHOP_TAIL = "&sort=default-asc&type=INVALID&grade=0&prevq=&q=";
	Document doc;

	boolean exact;
	String item;
	private final Object lock = new Object();
	Channel channel = null;

	public Shop() {

	}

	public Shop(String item, boolean exact) {
		this.item = item;
		this.exact = exact;
	}
	
	public Shop(String item, Channel channel, boolean exact) {
		this.item = item;
		this.exact = exact;
		this.channel = channel;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		try {
			Document doc = Jsoup.connect(URL_SHOP_HEAD + (exact ? "1" : "") + URL_SHOP_TAIL + item).get();
		if(channel == null) {
			
			synchronized (lock) {
				this.doc = doc;
				lock.notifyAll();
			}

		
		} else {
			MarkdownBuilder result = new MarkdownBuilder();
			Elements items = doc.select("td.price");// 물품 관련 요소 추출
			if (items.isEmpty()) {
				result.append("검색 결과가 없습니다");
				channel.sendMessage(result.getMessage());
				return;
			}
			Elements itemNames = doc.select("tbody tr th img");

			Element item;
			for (int i = 0; i < items.size(); i++) {
				result.append(itemNames.get(i).attr("title")).append("\t\t");
				item = items.get(i);
				if (!item.getElementsByClass("unit").isEmpty()) {
					result.append(item.getElementsByClass("unit").text().replace("개당", "")).newLine();
				} else {
					result.append(item.getElementsByClass("total").text().replace("전체", "")).newLine();
				}
			}

			channel.sendMessage(result.getMessage());
			
		}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			new Logger(e);
		}
		

	}

	public String waitForgetPrice() {
		synchronized (lock) {
			try {
				while (this.doc == null) {
					lock.wait();
				}

				String result;
				Elements items = this.doc.select("td.price");// 물품 관련 요소 추출
				if (items.isEmpty()) {
					result = "검색 결과가 없습니다";
					return result;
				} else {
					result = "";
				}
				Elements itemNames = doc.select("tbody tr th img");

				Element item;
				for (int i = 0; i < items.size(); i++) {
					result += itemNames.get(i).attr("title") + "\t\t";
					item = items.get(i);
					if (!item.getElementsByClass("unit").isEmpty()) {
						result += item.getElementsByClass("unit").text().replace("개당", "") + "\n";
					} else {
						result += item.getElementsByClass("total").text().replace("전체", "") + "\n";
					}
				}

				return result;

			} catch (InterruptedException e) {
				// Again better error handling
				new Logger(e);
			}

			return null;
		}
	}

	public Price waitForgetMinPrice() {
		synchronized (lock) {
			try {
				while (this.doc == null) {
					lock.wait();
				}

				String result;
				Elements items = this.doc.select("td.price");
				if (items.isEmpty()) {
					result = "0";
					return new Price(result);
				} else {
					result = "";
				}

				Element item;
				/*
				int i = 0;
				item = items.get(i);
				*/
				item = items.last();
				if (!item.getElementsByClass("unit").isEmpty()) {
					result += item.getElementsByClass("unit").text().replace("개당", "") + "\n";
				} else {
					result += item.getElementsByClass("total").text().replace("전체", "") + "\n";
				}

				return new Price(result);

			} catch (InterruptedException e) {
				// Again better error handling
				new Logger(e);
			}

			return new Price("0");
		}
	}

}
