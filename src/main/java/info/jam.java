package info;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.google.common.util.concurrent.FutureCallback;

import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.Javacord;
import de.btobastian.javacord.entities.Channel;
import de.btobastian.javacord.entities.Server;
import de.btobastian.javacord.entities.message.Message;
import de.btobastian.javacord.listener.message.MessageCreateListener;
import de.btobastian.javacord.listener.server.ServerJoinListener;

public class jam {

	static DiscordAPI api = null;
	
	
	final static String TOKEN_PATH = "./token.text";
	
	

	final static String HELP_MESSAGE = 
			  "```\n블소 한국서버 도우미 봇"
			+ "\n!시장 (물품이름) : 물품이름으로 시장검색"
			+ "\n!시장! (물품이름) : 물품이름으로 시장검색(검색어 일치)"
			+ "\n!신석샵 : 신석샵 오늘의 상품"
			+ "\n!제작 : 주요 제작 물품 제작비, 수익 비교"
			+ "\n!분배 (파티인원) (가격) : 분배금 계산"
			+ "\n!재료 : 주요 제작재료 가격 검색"
			+ "\n!화룡타이머 [on/off] : 화룡타이머 활성화/비활성화"
			+ "\n!수수료 (시장가격): 시장수수료 계산 (일거래수수료 포함)"
			+ "\n!이벤트 : 현재 진행중인 이벤트 확인"
			+ "\n!help를 입력하면 다시 볼 수 있습니다```";

	static public void main(String args[]) {
		
		try {
			Scanner in = new Scanner(new File(TOKEN_PATH));
			while(in.hasNext()) {
				api = Javacord.getApi(in.nextLine(), true);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			new Logger(e);
		}
		
		

		//api = Javacord.getApi(TOKEN_BOT, true);

		// 화룡타이머 선언
		final FTimer ftimer = new FTimer();

		// 봇이 초되되엇을 때 동작 정의
		api.registerListener(new ServerJoinListener() {

			@Override
			public void onServerJoin(DiscordAPI arg0, Server arg1) {
				// TODO Auto-generated method stub

				Collection<Channel> channels = arg1.getChannels();

				for (Channel in : channels) {
					in.sendMessage(HELP_MESSAGE);
				}

			}

		});

		api.connect(new FutureCallback<DiscordAPI>() {

			public void onSuccess(final DiscordAPI api) {

				ftimer.getChannelsFromDB();

				api.registerListener(new MessageCreateListener() {
					public void onMessageCreate(DiscordAPI api, Message message) {
						// 메세지가 들어왓을 때 동작 설정

						if (message.getAuthor().isBot())
							return;
						String innermessage = message.getContent();

						if (innermessage.startsWith("!")) {
							if (innermessage.equals("!help")) {
								message.reply(HELP_MESSAGE);
							} else if (innermessage.equals("!신석샵")) {
								new Thread(new ParseShinseok(message.getChannelReceiver())).run();
							} else if (innermessage.startsWith("!시장!")) {
								if (innermessage.length() >= 6) {
									new Thread(new Shop(innermessage.substring(5), message.getChannelReceiver(), true)).run();
								}

							} else if (innermessage.startsWith("!시장")) {
								if (innermessage.length() >= 5) {
									new Thread(new Shop(innermessage.substring(4), message.getChannelReceiver(), false)).run();
								}

							} else if (innermessage.equals("!제작")) {
								new Thread(new Making(message.getChannelReceiver())).run();
							} else if (innermessage.startsWith("!분배")) {
								String messageToc = message.getContent().substring(4);

								StringTokenizer toc = new StringTokenizer(messageToc, " ");
								int num = Integer.parseInt(toc.nextToken());
								int price = Integer.parseInt(toc.nextToken());

								message.reply(new MarkdownBuilder("최대입찰가 : ").append(((int) (double) price / num * (num - 1))).append(" 금 ")
										.append("분배금 : 1인당 ").append((int) (double) price / num).append(" 금").getMessage());
							} else if (innermessage.equals("!재료")) {
								new Thread(new Meterial(message.getChannelReceiver(), false)).run();
							} else if (innermessage.startsWith("!화룡타이머")) {
								if (!ftimer.isExistChannel(message.getChannelReceiver())
										&& innermessage.substring(7).contains("on")) {
									ftimer.addChannel(message.getChannelReceiver());
									message.reply(new MarkdownBuilder("화룡타이머 활성화").getMessage());
								} else if (ftimer.isExistChannel(message.getChannelReceiver())
										&& innermessage.substring(7).contains("off")) {
									ftimer.delChannel(message.getChannelReceiver());
									message.reply(new MarkdownBuilder("화룡타이머 비활성화").getMessage());
								}
							} else if(innermessage.startsWith("!수수료")) {
								Price price = new Price(innermessage.substring(5), true);
								message.reply(new MarkdownBuilder(price.calAllFee()).append("금").getMessage());
							} else if(innermessage.equals("!이벤트")) {
								new Thread(new Event(message.getChannelReceiver())).run();
							}

						} else if (innermessage.equals("린녀쟝")) {
							message.reply("기여어");
						} else if (innermessage.equals("하이임니다") || innermessage.equals("하이입니다")
								|| innermessage.equals("하임다") || innermessage.equals("하이")
								|| innermessage.equals("하이하이") || innermessage.equals("안녕하세요")) {

							message.reply(new MarkdownBuilder(message.getAuthor().getName()).append(" 하이임니다.").getMessage());

						}

					}
				});
			}

			public void onFailure(Throwable t) {
				t.printStackTrace();
			}
		});

	}

	

	public static Channel getChannelById(String id) {
		return api.getChannelById(id);

	}
}
