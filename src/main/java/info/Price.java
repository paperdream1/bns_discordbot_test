package info;

public class Price{
	
	String price;
	boolean isCalFee = false;
	double doublePrice;
	
	public Price(String price) {
		this.price = price;
		this.doublePrice = priceToDouble();
	}
	
	public Price(String price, boolean isCalFee) {
		this.price = price;
		this.isCalFee = isCalFee;
		this.doublePrice = priceToDouble();
	}

	// 시장에서 파싱해온 가격을 double로 변환
	private double priceToDouble() {
		String innerprice = price;
		if (!isCalFee && !price.contains("금") && !price.startsWith("0.")) {
			innerprice = "0." + price;
		}
		return Double.parseDouble(innerprice.replaceAll(" 금", ".").replaceAll(" 은", "").replaceAll(" 동", "").replaceAll(",", ""));
	}
	
	// 물품 수수료 계산
	public int calFee() {
		if (doublePrice < 100) {
			return (int) (doublePrice * 0.95);
		} else if (doublePrice >= 100 && doublePrice < 1000) {
			return (int) (doublePrice * 0.94);
		} else if (doublePrice >= 1000 && doublePrice < 10000) {
			return (int) (doublePrice * 0.93);
		} else {
			return (int) (doublePrice * 0.92);
		}
	}

	// 일거래 수수료 계산
	public int calDailyFee() {
		if(doublePrice < 1000) {
			return 0;
		} else if (doublePrice >= 1000 && doublePrice < 10000) {
			return (int) (doublePrice * 0.01);
		} else if (doublePrice >= 10000 && doublePrice < 50000) {
			return (int) (doublePrice * 0.02);
		} else {
			return (int) (doublePrice * 0.03);
		}
	}
		
	public int calAllFee() {
		return calFee() - calDailyFee();
	}
	
	public String toString() {
		return price;
	}
	
	public Double toDouble() {
		return doublePrice;
	}

	

	


	
}
