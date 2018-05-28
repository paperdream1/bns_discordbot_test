package info;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import de.btobastian.javacord.entities.Channel;

public class Event implements Runnable{
	
	final private String EVENT = "http://bns.plaync.com/event/running/1";
	private ArrayList<EventItem> eventList;
	private Channel channel;
	
	
	public Event(Channel channel) {
		this.channel = channel;
		eventList = new ArrayList();
	}
	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		Document doc;
		try {
			doc = Jsoup.connect(EVENT).get();
		
		Elements e = doc.select("div.wrap_event ul li");
		
		
		for(Element item : e) {
			eventList.add(new EventItem(item.select("strong").text(),
					item.select("img").attr("data-view"),
					item.getElementsByClass("date").text()));
		}
		
		String result = "";
		
		for(EventItem item : eventList) {
			result += item.getTitle() + "\n\t" + item.getDate() + "\n\t\t" +item.getUrl() + "\n";
		}
		
		channel.sendMessage(result);
		
		}catch(IOException e) {
			e.getMessage();
			channel.sendMessage("오류가 발생했습니다");
		}
		
	}

}
