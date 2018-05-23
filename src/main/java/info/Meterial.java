package info;

import java.util.ArrayList;

import de.btobastian.javacord.entities.Channel;

public class Meterial implements Runnable{
	
	final String[] meterialName = {
			"영석", "월석", "영단", "선단"
	};
	
	
	ArrayList<MeterialItem> itemList;
	ArrayList<Shop> searchList;
	Channel channel;
	boolean is_making;
	
	final Object lock = new Object();
	
	public Meterial(boolean is_making) {
		this.is_making = is_making;
		itemList = new ArrayList();
		searchList = new ArrayList();
	}
	
	public Meterial(Channel channel, boolean is_making) {
		itemList = new ArrayList();
		searchList = new ArrayList();
		this.channel = channel;
		this.is_making = is_making;
	}
	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		synchronized(lock){
			
			for (String itemName : meterialName) {
				Shop item = new Shop(itemName, false);
				searchList.add(item);
				new Thread(item).run();
			}
			
			for (int i = 0; i < meterialName.length; i++) {
				itemList.add(new MeterialItem(meterialName[i], searchList.get(i).waitForgetMinPrice().toString()));
			}
			lock.notifyAll();
			
			
		}
		
		if(!is_making) {
			String result = "";
			for(MeterialItem item : itemList) {
				result += item.getName() + "\t" + item.getPrice().toString();
			}
			
			channel.sendMessage(result);
		}
		
		
	}
	
	public ArrayList<MeterialItem> waitForMeterialItemPrice() {
		synchronized(lock) {
			try {
				while(itemList.size() < 4) {
					lock.wait();
				}
				return itemList;
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return new ArrayList();
			}
			
		}
	}
	
	
	
	

}
