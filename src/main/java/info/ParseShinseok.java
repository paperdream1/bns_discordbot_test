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

import de.btobastian.javacord.entities.Channel;

public class ParseShinseok implements Runnable{

	Document doc;
	int checker;
	Channel channel;
	
	
	public ParseShinseok(Channel channel) {
		this.channel = channel;
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		try {
			// 신석샵 목록사이트 연결
			doc = Jsoup.connect("http://bns.plaync.com/login/displayItemList").get();


			MarkdownBuilder result = new MarkdownBuilder();
			
			Elements itemNames = doc.select("li h3");// 아이템이름 추출
			Elements itemPrices = doc.select("li span.shinseok em");// 아이템 가격 추출

			for (int i = 0; i < itemNames.size(); i++) {
				result.append(itemNames.get(i).text()).append("\t\t ").append((int) Double.parseDouble(itemPrices.get(i).text())).append(" 신석").newLine();
			}

			channel.sendMessage(result.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			channel.sendMessage(new MarkdownBuilder("연결에 실패힜습니다").getMessage());
			new Logger(e);
		}
		
	}
	
	

}
