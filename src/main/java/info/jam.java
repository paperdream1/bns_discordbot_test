package info;

import java.util.StringTokenizer;

import com.google.common.util.concurrent.FutureCallback;

import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.Javacord;
import de.btobastian.javacord.entities.message.Message;
import de.btobastian.javacord.listener.message.MessageCreateListener;
 
public class jam {
    static public void main(String args[]){
    	
    	
    	final String[] makingItemNames = {Making.NAME_KEY, Making.NAME_ESTONE, Making.NAME_SSTONE, Making.NAME_DIA8, 
    			Making.NAME_DIA3, Making.NAME_DIA4, Making.NAME_TALISMAN, Making.NAME_CHIP
    	};
    	final int[] COUNT = {
    			11, 35, 20, 3, 3, 2, 6, 6
    	};
    	
    	final FTimer ftimer = new FTimer();
    	
    	
    	
    	
        DiscordAPI api = Javacord.getApi("NDM3ODc5OTgxNzYzNDYxMTIx.Db8fFQ.uvWGUGwLMqT6kvdry0bCJ18u8x0", true);
        
        
        api.connect(new FutureCallback<DiscordAPI>() {
              public void onSuccess(final DiscordAPI api) {
                  api.registerListener(new MessageCreateListener() {
                      public void onMessageCreate(DiscordAPI api, Message message) {
                    	  
                    	  if(!ftimer.getWorkState()) {
                    		  ftimer.setMessage(message);
                    	  }
                    	  
                    	  
                    	  if(message.getAuthor().isBot()) return;
                    	  String innermessage = message.getContent();
                    	  
                    	  
                          
                    	  if(innermessage.equals("!help")){
                              message.reply(
                            		  "\n블소 도우미 봇\n!시장 물품이름\n!신석샵\n!제작\n!분배 파티인원 가격\n!재료\n");
                          } else if(innermessage.contains("린녀쟝")){
                        	  message.reply("기여어");
                          } else if(innermessage.equals("하이임니다") || innermessage.equals("하이입니다") ||innermessage.equals("하임다") ||
                        		  innermessage.equals("하이") ||innermessage.equals("하이하이") || innermessage.equals("안녕하세요")) {
                        	  
                        	  message.reply(message.getAuthor().getMentionTag() + " 하이임니다.");
                          
                      	  } else if(innermessage.equals("!신석샵")) {
                        	  parseSin parse = new parseSin();
                        	  //parse.getSin();
                        	  //message.reply(parse.getSin());
                        	 message.reply("준비중");
                          } else if(innermessage.startsWith("!시장")) {
                        	  Shop shop = new Shop(innermessage.substring(4));
                        	  message.reply(shop.getPrice());
                          } else if(innermessage.equals("!제작")) {
                        	  String resultMessage = "";
                        	  Making making = new Making(priceToDouble(new Shop("영석").getMinPrice()), priceToDouble(new Shop("월석").getMinPrice()),
                        			 priceToDouble(new Shop("영단").getMinPrice()), priceToDouble(new Shop("선단").getMinPrice()));
                        	  for(int i=0 ; i<COUNT.length; i++) {
                        		 resultMessage += calMakingProfit(makingItemNames[i], making.findMakingItemCost(makingItemNames[i]), COUNT[i]) + "\n";
                        	  }
                        	  message.reply(resultMessage);
                        	 
                          } else if(innermessage.startsWith("!분배")) {
                        	  String messageToc = message.getContent().substring(4);
                        	 
                        	  
                        	  StringTokenizer toc = new StringTokenizer(messageToc," ");
                        	  int num = Integer.parseInt(toc.nextToken());
                        	  int price = Integer.parseInt(toc.nextToken());
                        	  
                        	  message.reply(((int)(double)price/num*(num-1)) + " 금");
                          } else if(innermessage.equals("!재료")) {
                        	  String resultMessage = "영석\t " + new Shop("영석").getMinPrice() +
                        			  "월석\t" + new Shop("월석").getMinPrice() +
                        			  "영단\t" + new Shop("영단").getMinPrice() + 
                        			  "선단\t" + new Shop("선단").getMinPrice() ;
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
    	double itemPrice = priceToDouble(new Shop(item).getMinPrice()) * count;
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

