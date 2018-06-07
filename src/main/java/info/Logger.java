package info;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Scanner;

public class Logger {
	
	final String LOG_PATH = "./Log/";
	
	Scanner in;
	PrintWriter out;
	Date date;
	
	public Logger(Exception e) {
		date = new Date();
		try {
			out = new PrintWriter(LOG_PATH + getDateFormat() + ".log");
			out.println(e.getMessage());
			out.close();
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	private String getDateFormat() {
		return String.format("%tY-%<tm-%<td %<tH:%<tM:%<tS", date);
	}
	

}
