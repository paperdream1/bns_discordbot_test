package info;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import de.btobastian.javacord.entities.message.Message;

public class FTimer {
	
	Message message;
	Date time;
	boolean workState = false;
	
	final String MESSAGE_10MIN = "화룡 10분전";
	final String MESSAGE_5MIN = "화룡 5분전";
	final String MESSAGE_1MIN = "화룡 1분전";
	final String MESSAGE_0MIN = "화룡탐";
	
	final int[][] FTIME = {
			{1, 13, 16, 19, 22},
			{1, 19, 22}
	};
	
	public FTimer() {
		
	}
	
	
	public void setMessage(Message message) {
		this.message = message;
		
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
		workState = true;
		
		
	}
	
	
	private void checkFTime() throws InterruptedException {
		time = new Date();
		int[] HOUR = FTIME[((time.getDay()+1)%6 <= 1 )? 0 : 1];
		
		
		for(int ntime : HOUR) {
			if(time.getHours() == ntime - 1) {
				
				switch(time.getMinutes()){
					case 50 :
						message.reply(MESSAGE_10MIN);
						Thread.sleep(60000);//sleep 1min
						break;
					case 55 :
						message.reply(MESSAGE_5MIN);
						Thread.sleep(60000);//sleep 1min
						break;
					case 59 :
						message.reply(MESSAGE_1MIN);
						Thread.sleep(60000);//sleep 1min
						break;
						
					
				}
			} else if(time.getTime() == ntime && time.getMinutes() == 0) {
				message.reply(MESSAGE_0MIN);
				Thread.sleep(60000);//sleep 1min
			}
		}
	}
	
	public boolean getWorkState() {
		return workState;
	}
	
	

}
