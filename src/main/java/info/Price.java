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
		if(innerprice.contains("은")) {
			if(innerprice.contains("금")) {
				if(innerprice.indexOf("은") - innerprice.indexOf("금") <= 3) {
					innerprice = innerprice.substring(0, innerprice.indexOf("금")+1) + "0" + 
							innerprice.substring(innerprice.indexOf("금")+1);
				}
			} else {
				if(innerprice.startsWith("0")) {
					if(innerprice.indexOf("은") <= 4) {
						innerprice = innerprice.substring(0, 2) + "0" + innerprice.substring(2);
					}
				}
				
			}
		}
		return Double.parseDouble(innerprice.replaceAll(" 금", ".").replaceAll(" 은", "").replaceAll(" 동", "").replaceAll(",", ""));
	}
	
	// 물품 수수료 계산
	public double calFee() {
		if (doublePrice < 100) {
			return doublePrice * 0.95;
		} else if (doublePrice >= 100 && doublePrice < 1000) {
			return doublePrice * 0.94;
		} else if (doublePrice >= 1000 && doublePrice < 10000) {
			return doublePrice * 0.93;
		} else {
			return doublePrice * 0.92;
		}
	}

	// 일거래 수수료 계산
	public double calDailyFee() {
		if(doublePrice < 1000) {
			return 0;
		} else if (doublePrice >= 1000 && doublePrice < 10000) {
			return doublePrice * 0.01;
		} else if (doublePrice >= 10000 && doublePrice < 50000) {
			return doublePrice * 0.02;
		} else {
			return doublePrice * 0.03;
		}
	}
		
	public double calAllFee() {
		return calFee() - calDailyFee();
	}
	
	public String toString() {
		return price;
	}
	
	public Double toDouble() {
		return doublePrice;
	}

	

	


	
}
