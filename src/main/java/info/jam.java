package info;

import java.util.Collection;
import java.util.StringTokenizer;

import com.google.common.util.concurrent.FutureCallback;

import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.Javacord;
import de.btobastian.javacord.entities.Channel;
import de.btobastian.javacord.entities.Server;
import de.btobastian.javacord.entities.message.Message;
import de.btobastian.javacord.listener.Listener;
import de.btobastian.javacord.listener.channel.*;
import de.btobastian.javacord.listener.message.MessageCreateListener;
import de.btobastian.javacord.listener.server.ServerJoinListener;
 
public class jam {
    static public void main(String args[]){
    	
    	
    	final String[] makingItemNames = {Making.NAME_KEY, Making.NAME_ESTONE, Making.NAME_SSTONE, Making.NAME_DIA8, 
    			Making.NAME_DIA3, Making.NAME_DIA4, Making.NAME_TALISMAN, Making.NAME_CHIP
    	};
    	final int[] COUNT = {
    			11, 35, 20, 3, 3, 2, 6, 6
    	};
    	
    	final String TOKEN_BOT = "NDM3ODc5OTgxNzYzNDYxMTIx.Db8fFQ.uvWGUGwLMqT6kvdry0bCJ18u8x0";
    	
    	final String HELP_MESSAGE = "\n블소 한국서버 도우미 봇"
      		  + "\n!시장 물품이름 : 물품이름으로 시장검색"
      		  + "\n!시장! 물품이름 : 물품이름으로 시장검색(검색어 일치)"
      		  + "\n!신석샵 : 신석샵 오늘의 상품"
      		  + "\n!제작 : 주요 제작 물품 제작비, 수익 비교"
      		  + "\n!분배 파티인원 가격 : 분배금 계산"
      		  + "\n!재료 : 주요 제작재료 가격 검색"
      		  + "\n!help를 입력하면 다시 볼 수 있습니다";
    	
    	
    	
    	final FTimer ftimer = new FTimer();
    	
    	
    	
    	
        DiscordAPI api = Javacord.getApi(TOKEN_BOT, true);
        
        
        api.registerListener(new ServerJoinListener(){

			@Override
			public void onServerJoin(DiscordAPI arg0, Server arg1) {
				// TODO Auto-generated method stub
				
				Collection<Channel> channels = arg1.getChannels();
				
				ftimer.setChannels(channels);
				
				
				for(Channel in : channels) {
					in.sendMessage(HELP_MESSAGE);
				}
			
				
			}
        	
        	
        });
       
        
        api.connect(new FutureCallback<DiscordAPI>() {
              public void onSuccess(final DiscordAPI api) {
            	  api.registerListener(new MessageCreateListener() {
                      public void onMessageCreate(DiscordAPI api, Message message) {

                    	  
                    	  
                    	  
                    	  if(message.getAuthor().isBot()) return;
                    	  String innermessage = message.getContent();
                    	  
                    	  
                          
                    	  if(innermessage.equals("!help")){
                              message.reply(HELP_MESSAGE);
                          } else if(innermessage.contains("린녀쟝")){
                        	  message.reply("기여어");
                          } else if(innermessage.equals("하이임니다") || innermessage.equals("하이입니다") ||innermessage.equals("하임다") ||
                        		  innermessage.equals("하이") ||innermessage.equals("하이하이") || innermessage.equals("안녕하세요")) {
                        	  
                        	  message.reply(message.getAuthor().getMentionTag() + " 하이임니다.");
                          
                      	  } else if(innermessage.equals("!신석샵")) {
                        	  ParseShinseok shinseok = new ParseShinseok();
                        	  message.reply(shinseok.getList());
                          } else if(innermessage.startsWith("!시장!")) {
                        	  Shop shop = new Shop(innermessage.substring(5), true);
                        	  message.reply(shop.getPrice());
                          } else if(innermessage.startsWith("!시장")) {
                        	  Shop shop = new Shop(innermessage.substring(4), true);
                        	  message.reply(shop.getPrice());
                          } else if(innermessage.equals("!제작")) {
                        	  String resultMessage = "";
                        	  Making making = new Making(priceToDouble(new Shop("영석", false).getMinPrice()), priceToDouble(new Shop("월석", false).getMinPrice()),
                        			 priceToDouble(new Shop("영단", false).getMinPrice()), priceToDouble(new Shop("선단", false).getMinPrice()));
                        	  for(int i=0 ; i<COUNT.length; i++) {
                        		 resultMessage += calMakingProfit(makingItemNames[i], making.findMakingItemCost(makingItemNames[i]), COUNT[i]) + "\n";
                        	  }
                        	  message.reply(resultMessage);
                        	 
                          } else if(innermessage.startsWith("!분배")) {
                        	  String messageToc = message.getContent().substring(4);
                        	 
                        	  
                        	  StringTokenizer toc = new StringTokenizer(messageToc," ");
                        	  int num = Integer.parseInt(toc.nextToken());
                        	  int price = Integer.parseInt(toc.nextToken());
                        	  
                        	  message.reply("최대입찰가 : "+ ((int)(double)price/num*(num-1)) + " 금 " + "분배금 : 1인당 " + (int)(double)price/num + " 금");
                          } else if(innermessage.equals("!재료")) {
                        	  String resultMessage = "영석\t " + new Shop("영석", false).getMinPrice() +
                        			  "월석\t" + new Shop("월석", false).getMinPrice() +
                        			  "영단\t" + new Shop("영단", false).getMinPrice() + 
                        			  "선단\t" + new Shop("선단", false).getMinPrice() ;
                        	  message.reply(resultMessage);
                          }
                    	  
                          
                      }
                  });
              }
 
              public void onFailure(Throwable t) {
                t.printStackTrace();
              }
            });
    }
    
    static private double priceToDouble(String price) {
    	return Double.parseDouble(price.replaceAll(" 금",".").replaceAll(" 은","").replaceAll(" 동","").replaceAll(",", ""));
    }
    
    static private String calMakingProfit(String item, double price, int count) {
    	double itemPrice = priceToDouble(new Shop(item, false).getMinPrice()) * count;
    	String result = item + "\t\t시장가 : " + (int)itemPrice + " 금\t제작비 : " + (int)price + " 금\t수익 : " + (calFee(itemPrice) - (int)price) + " 금";
    	return result;
    }
    
    private static int calFee(double price) {
    	if(price < 1000) {
    		return (int)(price*0.94);
    	} else if(price >=1000 && price <10000) {
    		return (int)(price*0.92);
    	} else if(price >= 10000 && price < 50000) {
    		return (int)(price*0.90);
    	} else {
    		return (int)(price*0.89);
    	}
    }
}

