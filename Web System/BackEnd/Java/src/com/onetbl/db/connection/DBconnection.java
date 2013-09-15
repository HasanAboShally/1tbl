package com.onetbl.db.connection;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class DBconnection {
	public static String DBURL_1= "jdbc:mysql://localhost?"
			+ "user=onetblmanager&password=Tsofen1tbl";// server
	public static String DBURL_2= "jdbc:mysql://localhost?"
			+ "user=root&password=root";
	public static String DBURL_3= "jdbc:mysql://localhost:3306?"+
			 "user=root";// MAC
	
	public static String DBURL = DBURL_1;
	
	static private Connection connect = null;
	static DBconnection dbc = new DBconnection();

	private DBconnection() {
		connectToDatabase();
	}

	public static void connectToDatabase() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = (Connection)
			 DriverManager.getConnection(DBURL);
			
			
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public Connection getConnection() {
		return connect;
	}

	public static DBconnection getInstance() {
		return dbc;
	}

	public static void main(String[] args) {
		DBconnection.connectToDatabase();

	}

}
