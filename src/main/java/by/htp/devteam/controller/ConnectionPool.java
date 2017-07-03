package by.htp.devteam.controller;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import by.htp.devteam.dao.DaoException;

public class ConnectionPool {
	
	private static final String DATASOURCE_NAME = "jdbc/devteam";
	private static DataSource dataSource;
	
	static {
		try {
			Context initContext = new InitialContext();
	        Context envContext  = (Context)initContext.lookup("java:comp/env");
	        dataSource = (DataSource)envContext.lookup(DATASOURCE_NAME);
		} catch ( NamingException e ) {
			e.printStackTrace();
		}
	}
	
	private ConnectionPool() {
		
	}
	
	public static Connection getConnection() throws SQLException {
		Connection connection = dataSource.getConnection();
		return connection;
	}
	
	public static void returnConnection(Connection connection) throws DaoException{
		try {
			connection.close();
		} catch (SQLException e) {
			throw new DaoException("Couldn't close connection", e);
		}
	}
}
