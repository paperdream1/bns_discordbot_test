package info;

public class ShopItem {
	
	String gold;
	String silver;
	String bronze;
	
	String name;

	public ShopItem() {
		this.gold = "";
		this.silver = "";
		this.bronze = "";
		this.name = "";
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setGold(String gold) {
		this.gold = gold;
	}
	
	public void setSilver(String silver) {
		this.silver = silver;
	}
	
	public void setBronze(String bronze) {
		this.bronze = bronze;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getPrice() {
		return (this.gold.isEmpty()? "" : this.silver + " ") + (this.silver.isEmpty()? "" : this.silver + " ")
				+ (this.bronze.isEmpty()? "" : this.bronze + " ");
	}
	
}
