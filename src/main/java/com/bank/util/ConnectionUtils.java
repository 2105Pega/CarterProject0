package com.bank.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtils {
	
	public static Connection getConnection() throws SQLException {
		try {
			Class.forName("org.postgresql.Driver"); // Gives app drivers we need to connect to the database						
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// url = jdbc:postgresql://USER_databse_endpoint:USER_port_number/USER_db_name
		String url = "";
		String username = "";
		String password = "";	
				
		try {
			FileInputStream fis = new FileInputStream("src/main/resources/banking_db_properties.properties");
			Properties prop = new Properties();
			prop.load(fis);
			url = prop.getProperty("DB_URL");
			username = prop.getProperty("DB_Username");
			password = prop.getProperty("DB_Password");
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		} catch (Exception e3) {
			e3.printStackTrace();
		} 
		
		return DriverManager.getConnection(url, username, password);
	}
}
