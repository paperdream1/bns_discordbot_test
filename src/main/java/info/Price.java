package info;

public class Price{
	
	String price;

	public Price(String price) {
		this.price = price;
	}

	// 시장에서 파싱해온 가격을 double로 변환
	public double priceToDouble(String price) {
		if (!price.contains("금")) {
			price = "0." + price;
		}
		return Double.parseDouble(price.replaceAll(" 금", ".").replaceAll(" 은", "").replaceAll(" 동", "").replaceAll(",", ""));
	}
	
	public String toString() {
		return price;
	}
}
