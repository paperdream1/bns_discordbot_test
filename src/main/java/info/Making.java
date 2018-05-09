package info;

import java.util.ArrayList;

public class Making {
	
	
	//영석 월석 영단 선단 금 
	final int KEY = 0;//빛열쇠 695 175 695 175 540
	final int ESTONE = 1;//진화석 230 60 230 60 180
	final int SSTONE = 2;//설혼석 850 370 850 370 900
	final int TALISMAN = 0;//빛봉부 695 175 695 175 540
	final int DIA8 = 2;//팔금주 850 370 850 370 900
	final int DIA3 = 3;//삼금주 1505 375 1505 375 1170
	final int DIA4 = 4;//사금주 1620 405 1620 405 1260
	final int CHIP = 0;//목편묶음 695 175 695 175 540
	
	final static String NAME_KEY = "빛나는 열쇠 꾸러미";
	final static String NAME_ESTONE = "진화석";
	final static String NAME_SSTONE = "설혼석";
	final static String NAME_TALISMAN = "빛나는 백청 봉인부적";
	final static String NAME_DIA8 = "팔각 금강석 주머니";
	final static String NAME_DIA3 = "삼성 금강석 주머니";
	final static String NAME_DIA4 = "사성 금강석 주머니";
	final static String NAME_CHIP = "명인 합성목편 묶음";
	
	static ArrayList<MakingItem> itemList;
	static Shop shop;
	
	double ssC;
	double msC;
	double sbC;
	double wbC;
	
	static{
		shop = new Shop();
		itemList = new ArrayList();
		itemList.add(new MakingItem(695, 175, 695, 175, 540));
		itemList.add(new MakingItem(230, 60, 230, 60, 180));
		itemList.add(new MakingItem(850, 370, 850, 370, 900));
		itemList.add(new MakingItem(1505, 375, 1505, 375, 1170));
		itemList.add(new MakingItem(1620, 405, 1620, 405, 1260));
	}
	
	public Making() {
		
	}
	
	public Making(double ssC, double msC, double sbC, double wbC) {
		this.ssC = ssC;
		this.msC = msC;
		this.sbC = sbC;
		this.wbC = wbC;
		
	}
	
	public void refreshCost() {
		shop.searchItem("영석", false);
		this.ssC = jam.priceToDouble(shop.getMinPrice());
		shop.searchItem("월석", false);
		this.msC = jam.priceToDouble(shop.getMinPrice());
		shop.searchItem("영단", false);
		this.sbC = jam.priceToDouble(shop.getMinPrice());
		shop.searchItem("선단", false);
		this.wbC = jam.priceToDouble(shop.getMinPrice());
	}
	
	
	
	
	
	
	private double getCost(MakingItem item) {
		return (double)item.gold + item.soulstone*ssC + item.moonstone*msC + item.soulbead*sbC+ item.whitebead*wbC;
	}
	
	public double findMakingItemCost(String item) {
		
		int index = -1;
		
		
		switch(item) {
			case NAME_KEY :
			case NAME_TALISMAN:
			case NAME_CHIP:
				index = KEY;
				break;
			case NAME_DIA3:
				index = DIA3;
				break;
			case NAME_DIA4:
				index = DIA4;
				break;
			case NAME_DIA8:
				index = DIA8;
				break;
			case NAME_SSTONE:
				index = SSTONE;
				break;
			case NAME_ESTONE:
				index = ESTONE;
				break;
				
		}
		return getCost(itemList.get(index));
		
	}


}

class MakingItem{
	int moonstone;
	int soulstone;
	int soulbead;
	int whitebead;
	int gold;

	public MakingItem(int soulstone, int moonstone, int soulbead, int whitebead, int gold) {
		this.moonstone = moonstone;
		this.soulstone = soulstone;
		this.soulbead = soulbead;
		this.whitebead = whitebead;
		this.gold = gold;
	
	}

	
}
