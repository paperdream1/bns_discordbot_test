package info;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import de.btobastian.javacord.entities.Channel;
import de.btobastian.javacord.entities.message.Message;

public class FTimer {
	
	Message message;
	Collection<Channel> channels;
	Date time;
	
	final String MESSAGE_10MIN = "화룡 10분전";
	final String MESSAGE_5MIN = "화룡 5분전";
	final String MESSAGE_1MIN = "화룡 1분전";
	final String MESSAGE_0MIN = "화룡탐";
	
	/**************
	 * 화룡 시간 정의
	 * FTIME[0] : 금토일시간표
	 * FTIME[1] : 평일시간
	 */
	final int[][] FTIME = {
			{1, 13, 16, 19, 22},
			{1, 19, 22}
	};
	
	public FTimer() {
		//타이머시작
		startTimer();
		channels = new ArrayList<Channel>();
	}
	
	public void setChannels(Collection<Channel> channels) {
		//this.channels = channels;
		this.channels.addAll(channels);
	}
	
	public void addChannel(Channel channel) {
		this.channels.add(channel);
	}
	
	public void delChannel(Channel channel) {
		this.channels.remove(channel);
	}
	
	public boolean isExistChannel(Channel channel) {
		return this.channels.contains(channel);
	}

	
	
	public void setMessage(Message message) {
		this.message = message;
		
	}
	
	public void startTimer() {
		Timer timer = new Timer(false);
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				try {
					checkFTime();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		};
		
		timer.schedule(task, 1000, 1000);//1 second
	}
	
	
	private void checkFTime() throws InterruptedException {
		time = new Date();
		
		//금토일인지 판단
		int[] HOUR = FTIME[((time.getDay()+1)%6 <= 1 )? 0 : 1];
		
		
		for(int ntime : HOUR) {
			if(time.getHours() == ntime - 1) {
				
				switch(time.getMinutes()){
					case 50 :
						for(Channel channel : channels) {
							channel.sendMessage(MESSAGE_10MIN);
						}
						Thread.sleep(60000);//sleep 1min
						break;
					case 55 :
						for(Channel channel : channels) {
							channel.sendMessage(MESSAGE_5MIN);
						}
						Thread.sleep(60000);//sleep 1min
						break;
					case 59 :
						for(Channel channel : channels) {
							channel.sendMessage(MESSAGE_1MIN);
						}
						Thread.sleep(60000);//sleep 1min
						break;
						
					/*******************
					 * test cell start *
					 *******************
					default:
						for(Channel channel : channels) {
							channel.sendMessage(MESSAGE_1MIN);
						}
						System.out.println("여긴온다" + channels.size());
						Thread.sleep(60000);//sleep 1min
						break;
						
					*******************
					*   test cell end  *
					*******************/
						
					
				}
			} else if(time.getHours() == ntime && time.getMinutes() == 0) {
				for(Channel channel : channels) {
					channel.sendMessage(MESSAGE_0MIN);
				}
				Thread.sleep(60000);//sleep 1min
			}
		}
	}
	
	
	

}
