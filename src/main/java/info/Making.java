package info;

import java.util.ArrayList;

import de.btobastian.javacord.entities.Channel;

public class Making implements Runnable{

	// 영석 월석 영단 선단 금
	final int KEY = 0;// 빛열쇠 695 175 695 175 540
	final int ESTONE = 1;// 진화석 230 60 230 60 180
	final int SSTONE = 2;// 설혼석 850 370 850 370 900
	final int TALISMAN = 0;// 빛봉부 695 175 695 175 540
	final int DIA8 = 2;// 팔금주 850 370 850 370 900
	final int DIA3 = 3;// 삼금주 1505 375 1505 375 1170
	final int DIA4 = 4;// 사금주 1620 405 1620 405 1260
	final int CHIP = 0;// 목편묶음 695 175 695 175 540

	final static String NAME_KEY = "빛나는 열쇠 꾸러미";
	final static String NAME_ESTONE = "진화석";
	final static String NAME_SSTONE = "설혼석";
	final static String NAME_TALISMAN = "빛나는 백청 봉인부적";
	final static String NAME_DIA8 = "팔각 금강석 주머니";
	final static String NAME_DIA3 = "삼성 금강석 주머니";
	final static String NAME_DIA4 = "사성 금강석 주머니";
	final static String NAME_CHIP = "명인 합성목편 묶음";

	static ArrayList<MakingItem> itemList;

	double ssC;
	double msC;
	double sbC;
	double wbC;

	ArrayList<Double> meterialCosts;
	
	Channel channel;
	
	final int[] COUNT = { 11, 35, 20, 3, 3, 2, 6, 6 };


	static {
		itemList = new ArrayList();
		itemList.add(new MakingItem(NAME_KEY, 695, 175, 695, 175, 540, 11));
		itemList.add(new MakingItem(NAME_ESTONE, 230, 60, 230, 60, 180, 35));
		itemList.add(new MakingItem(NAME_SSTONE, 850, 370, 850, 370, 900, 20));
		itemList.add(new MakingItem(NAME_DIA8, 850, 370, 850, 370, 900, 3));
		itemList.add(new MakingItem(NAME_DIA3, 1505, 375, 1505, 375, 1170, 3));
		itemList.add(new MakingItem(NAME_DIA4, 1620, 405, 1620, 405, 1260, 2));
		itemList.add(new MakingItem(NAME_TALISMAN, 695, 175, 695, 175, 540, 6));
		itemList.add(new MakingItem(NAME_CHIP, 695, 175, 695, 175, 540, 6));

	}

	public Making() {

	}
	
	public Making(Channel channel) {
		this.channel = channel;
		refreshCost();
	}

	public Making(double ssC, double msC, double sbC, double wbC) {
		this.ssC = ssC;
		this.msC = msC;
		this.sbC = sbC;
		this.wbC = wbC;

	}

	public void refreshCost() {
		Meterial meterial = new Meterial(true);
		new Thread(meterial).run();

		ArrayList<MeterialItem> meterials = meterial.waitForMeterialItemPrice();
		meterialCosts = new ArrayList();
		for (MeterialItem item : meterials) {
			meterialCosts.add(item.getPrice().toDouble());
		}

	}

	private double getCost(MakingItem item) {
		return (double) item.getGold() + item.getSoulstone() * meterialCosts.get(0) + item.getMoonstone() * meterialCosts.get(1)
				+ item.getSoulbead() * meterialCosts.get(2) + item.getWhitebead() * meterialCosts.get(3);
	}
	
	private int calProfit(MakingItem item, Price itemPrice, int count) {
		return (int)(itemPrice.calFee()*count - getCost(item));
	}

	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		String result = "";
		
		ArrayList<Shop> priceList = new ArrayList();
		for(MakingItem item : itemList) {
			Shop shop = new Shop(item.getName(), false);
			priceList.add(shop);
			new Thread(shop).run();
		}
		
		for(int i=0; i<itemList.size(); i++) {
			Price thisItemPrice = priceList.get(i).waitForgetMinPrice();
			result += itemList.get(i).getName() + "   시장가 : " + (int)(thisItemPrice.toDouble() * itemList.get(i).getCount())
					+ "금  제작비 : " + (int)getCost(itemList.get(i)) + "  수익 : " + calProfit(itemList.get(i), thisItemPrice, itemList.get(i).getCount()) + "\n";
		}
		
		channel.sendMessage("```" + result + "```");
		
	}

}

