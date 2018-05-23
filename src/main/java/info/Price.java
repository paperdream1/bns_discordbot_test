package info;

public class Price{
	
	String price;
	boolean isCalFee = false;
	
	public Price(String price) {
		this.price = price;
	}
	
	public Price(String price, boolean isCalFee) {
		this.price = price;
		this.isCalFee = isCalFee;
	}

	// 시장에서 파싱해온 가격을 double로 변환
	public double priceToDouble() {
		if (!isCalFee && !price.contains("금") && !price.startsWith("0.")) {
			price = "0." + price;
		}
		return Double.parseDouble(price.replaceAll(" 금", ".").replaceAll(" 은", "").replaceAll(" 동", "").replaceAll(",", ""));
	}
	
	// 물품 수수료 계산
		public int calFee() {
			double price = priceToDouble();
			if (price < 100) {
				return (int) (price * 0.95);
			} else if (price >= 100 && price < 1000) {
				return (int) (price * 0.94);
			} else if (price >= 1000 && price < 10000) {
				return (int) (price * 0.93);
			} else {
				return (int) (price * 0.92);
			}
		}

		// 일거래 수수료 계산
		public int calDailyFee() {
			double price = priceToDouble();
			if(price < 1000) {
				return 0;
			} else if (price >= 1000 && price < 10000) {
				return (int) (price * 0.01);
			} else if (price >= 10000 && price < 50000) {
				return (int) (price * 0.02);
			} else {
				return (int) (price * 0.03);
			}
		}
		
		public int calAllFee() {
			return calFee() - calDailyFee();
		}
	
	public String toString() {
		return price;
	}
}
