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
			while (rs.next()) {
				results.add(rs.getString("id"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Logger(e);
		}

		return results;
	}

	public boolean addFTimerId(String id) {
		boolean result = false;
		try {
			ResultSet rs = statement.executeQuery("select id = '" + id + "' from ftimer");
			if (!rs.next()) {
				statement.execute("insert into ftimer (id) values ('" + id + "');");
			}
			result = true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Logger(e);
		}

		return result;

	}
	
	public boolean isExisFTimerId(String id) {
		ResultSet rs;
		try {
			rs = statement.executeQuery("select id = '" + id + "' from ftimer");
			return rs.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public boolean delFTimerId(String id) {
		ResultSet rs;
		boolean result = false;
		try {
			do {
			statement.execute("delete from ftimer where id = '" + id + "';");
			rs = statement.executeQuery("select id = '" + id + "' from ftimer");
			} while (rs.next());
			result = true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Logger(e);
		}

		return result;
	}
	
	public ArrayList<String> getChainTimerIdfromDB() {
		ArrayList<String> results = new ArrayList();
		try {
			ResultSet rs = statement.executeQuery("select * from chaintimer");
			while (rs.next()) {
				results.add(rs.getString("id"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Logger(e);
		}

		return results;
	}

	public boolean addChainTimerId(String id) {
		boolean result = false;
		try {
			ResultSet rs = statement.executeQuery("select id = '" + id + "' from chaintimer");
			if (!rs.next()) {
				statement.execute("insert into chaintimer (id) values ('" + id + "');");
			}
			result = true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Logger(e);
		}

		return result;

	}
	
	public boolean isExistChainTimerId(String id) {
		ResultSet rs;
		try {
			rs = statement.executeQuery("select id = '" + id + "' from chaintimer");
			return rs.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public boolean delChainTimerId(String id) {
		ResultSet rs;
		boolean result = false;
		try {
			do {
			statement.execute("delete from chaintimer where id = '" + id + "';");
			rs = statement.executeQuery("select id = '" + id + "' from chaintimer");
			} while (rs.next());
			result = true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Logger(e);
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
			statement.setQueryTimeout(30); // set timeout to 30 sec.

			statement.executeUpdate("create table if not exists ftimer (id char(255))");
			statement.executeUpdate("create table if not exists chaintimer (id char(255))");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Logger(e);
		}

	}

}
