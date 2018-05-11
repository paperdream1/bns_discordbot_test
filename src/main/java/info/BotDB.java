package info;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BotDB {
	
	Connection connection = null;
	static Statement statement = null;
	
	
	static {
		makeDB();
	}
	
	public BotDB() {
		
	}
	
	
	
	public ArrayList<String> getFTimerIdfromDB() {
		ArrayList<String> results = new ArrayList();
		try {
			ResultSet rs = statement.executeQuery("select * from ftimer");
			while(rs.next()) {
				results.add(rs.getString("id"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return results;
	}
	
	public boolean addFTimerId(String id) {
		boolean result = false;
		try {
			ResultSet rs = statement.executeQuery("select id = '" + id + "' from ftimer");
			if(!rs.next()) {
				statement.execute("insert into ftimer (id) values ('"+ id + "');");
			}
			result = true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
		
	}
	
	public boolean delFTimerId(String id) {
		boolean result = false;
		try {
			statement.execute("delete from ftimer where id = '"+ id + "';");
			result = true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	

	private static void makeDB() {
		// TODO Auto-generated method stub
		Connection connection = null;
		
		// create a database connection
          try {
			connection = DriverManager.getConnection("jdbc:sqlite:bns_discordbot_test.db");
			statement = connection.createStatement();
	        statement.setQueryTimeout(30);  // set timeout to 30 sec.
	        
	        
	        
	        statement.executeUpdate("create table if not exists ftimer (id char(255))");
	        
	        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          
		
	}

}
