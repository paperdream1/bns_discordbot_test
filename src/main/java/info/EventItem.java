package info;

public class EventItem {
	private String title;
	private String url;
	private String date;
	
	public EventItem(String title, String url, String date) {
		this.title = title;
		this.url = url;
		this.date = date;
	}
	
	public String getTitle() {
		return title;
	}
	public String getUrl() {
		return url;
	}
	public String getDate() {
		return dateFormating();
	}
	
	/////Wed May 23 10:00:00 KST 2018 ~ Wed Jun 27 00:00:00 KST 2018
	///// 0   1   2   3       4   5   6 7    8   9  10      11   12
	/////2018-05-23 (수) ~ 2018-06-27 (수)
	
	private String dateFormating() {
		if(date.matches("\\d{4}-\\d{2}-\\d{2} \\([가-힣]\\) ~ \\d{4}-\\d{2}-\\d{2} \\([가-힣]\\)")) {
			return date;
		} else {
			String[] elements = date.split(" ");
			return elements[5] + "-" + monthToNum(elements[1]) + "-" + elements[2] + " (" + dateToKorean(elements[0]) + ") ~ " +
			elements[12] + "-" + monthToNum(elements[8]) + "-" + elements[9] + " (" + dateToKorean(elements[7]) + ")";
		}
	}
	
	private String monthToNum(String month) {
		switch(month) {
		case "Jen" : return "01";
		case "Feb" : return "02";
		case "Mar" : return "03";
		case "Apr" : return "04";
		case "May" : return "05";
		case "Jun" : return "06";
		case "Jul" : return "07";
		case "Aug" : return "08";
		case "Sep" : return "09";
		case "Oct" : return "10";
		case "Nov" : return "11";
		case "Dec" : return "12";
		default : return "";
		}
	}
	
	private String dateToKorean(String date) {
		switch(date) {
		case "Mon" : return "월";
		case "Teu" : return "화";
		case "Wed" : return "수";
		case "Thu" : return "목";
		case "Fri" : return "금";
		case "Set" : return "토";
		case "Sun" : return "일";
		default : return "";
		}
	}

}
