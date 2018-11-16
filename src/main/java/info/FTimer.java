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
	BotDB botdb = null;

	final String MESSAGE_10MIN = "화룡 10분전";
	final String MESSAGE_5MIN = "화룡 5분전";
	final String MESSAGE_1MIN = "화룡 1분전";
	final String MESSAGE_0MIN = "화룡탐";

	/*****************************************************
	 * 화룡 시간 정의 FTIME[0] : 금토일시간표 FTIME[1] : 평일시간 *
	 *****************************************************/
	final int[][] FTIME = { { 1, 13, 16, 19, 22 }, { 1, 19, 22 } };

	public FTimer() {
		// 타이머시작
		botdb = new BotDB();
		channels = new ArrayList<Channel>();
		startTimer();
	}

	public void getChannelsFromDB() {
		ArrayList<String> db_channels = botdb.getFTimerIdfromDB();

		for (String ch_id : db_channels) {
			channels.add(jam.getChannelById(ch_id));
		}

	}

	public void setChannels(Collection<Channel> channels) {
		// this.channels = channels;
		this.channels.addAll(channels);
	}

	public boolean addChannel(Channel channel) {
		if(botdb.addFTimerId(channel.getId())) {
			this.channels.add(channel);
			return true;
		}
		return false;
	}

	public boolean delChannel(Channel channel) {
		if(botdb.delFTimerId(channel.getId())) {
			this.channels.remove(channel);
			return true;
		}
		return false;
	}

	public boolean isExistChannel(Channel channel) {
		//return this.channels.contains(channel);
		return botdb.isExistFTimerId(channel.getId());
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
					new Logger(e);
				}

			}

		};

		timer.schedule(task, 1000, 2000);//repeat every 2sec
	}

	private void checkFTime() throws InterruptedException {
		time = new Date();
		
		//2시이후 12시이전일경우
		if(time.getHours()>1 && time.getHours() < 12) {
			Thread.sleep(60*60*1000);//sleep 1hour
			return;
		}

		// 금토일인지 판단
		int[] HOUR = FTIME[((time.getDay() + 1) % 6 <= 1) ? 0 : 1];

		for (int ntime : HOUR) {
			if (time.getHours() == ntime - 1) {

				switch (time.getMinutes()) {
				case 50:
					for (Channel channel : channels) {
						channel.sendMessage(MESSAGE_10MIN);
					}
					Thread.sleep(5*60*1000);// sleep 5min
					return;
				case 55:
					for (Channel channel : channels) {
						channel.sendMessage(MESSAGE_5MIN);
					}
					Thread.sleep(4*60*1000);// sleep 4min
					return;
				case 59:
					for (Channel channel : channels) {
						channel.sendMessage(MESSAGE_1MIN);
					}
					Thread.sleep(60*1000);// sleep 1min
					return;
				}
			} else if (time.getHours() == ntime && time.getMinutes() == 0) {
				for (Channel channel : channels) {
					channel.sendMessage(MESSAGE_0MIN);
				}
				Thread.sleep(3*60*60*1000 - 10*60*1000);// sleep 2hour 50min
				return;
			}
		}
	}

}
