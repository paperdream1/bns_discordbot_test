package info;

public class MeterialItem {
	String name;
	String price;
	
	public MeterialItem() {
		
	}
	
	public MeterialItem(String name, String price) {
		this.name = name;
		this.price = price;
	}
	
	public String getName() {
		return name;
	}
	
	public Price getPrice() {
		return new Price(price);
	}
}
