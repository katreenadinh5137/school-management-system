package my_util;

import java.sql.*;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database{
	
	//private Connection connect;
	private static final String DBURL = "jdbc:mysql:///school_management_db";	//URL name of the database
	private static final String USERNAME = "root";						//username for the db
	private static final String PASSWORD = "root123";					//password for the db
	
	//connect to the db
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(DBURL, USERNAME, PASSWORD);
	}
}