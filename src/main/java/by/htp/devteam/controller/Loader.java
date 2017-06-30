package by.htp.devteam.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;

public class Loader {
	
	public static Connection dbConnector; 
	
	private Loader() {
		
	}
	
	public static Connection LoaderDb() throws ClassNotFoundException, SQLException {
		if ( dbConnector == null ) {
			
			ResourceBundle rb = ResourceBundle.getBundle("config");
			String dbUrl = rb.getString("db.url");
			Properties properties = new Properties();
			properties.setProperty("user", rb.getString("db.login"));
			properties.setProperty("password", rb.getString("db.pass"));

			properties.setProperty("useUnicode", "true");
			properties.setProperty("characterEncoding", "UTF-8");
			String driverName = rb.getString("db.driver.name");
			Class.forName(driverName);
			dbConnector = DriverManager.getConnection(dbUrl, properties);
			dbConnector.createStatement().executeQuery("set character set utf8");
			dbConnector.createStatement().executeQuery("set names utf8");
		}
		
		return dbConnector;
	}
}

