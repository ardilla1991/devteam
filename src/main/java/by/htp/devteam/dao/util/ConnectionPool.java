package by.htp.devteam.dao.util;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import by.htp.devteam.dao.DaoException;
import static by.htp.devteam.dao.util.ConstantValue.*;

/**
 * Tomcat's Connection pool. Singleton pattern
 * @author julia
 *
 */
public class ConnectionPool {
	
	private static final String DATASOURCE_NAME = "jdbc/devteam";
	private static DataSource dataSource;
	
	private static final Logger logger = LogManager.getLogger(ConnectionPool.class.getName());
	
	/* Initialization of connection */
	static {
		try {
			Context initContext = new InitialContext();
	        Context envContext  = (Context)initContext.lookup("java:comp/env");
	        dataSource = (DataSource)envContext.lookup(DATASOURCE_NAME);
		} catch ( NamingException e ) {
			logger.error(MSG_ERROR_GET_CONNECTION_TO_DB, e);
		}
	}
	
	private ConnectionPool() {
		super();
	}
	
	/**
	 * Get connection from pool
	 * @return Connection
	 * @throws DaoException
	 */
	public static Connection getConnection() throws DaoException {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
		} catch (SQLException e) {
			throw new DaoException(MSG_ERROR_GET_CONNECTION_FROM_POOL, e);
		}
		
		return connection;
	}
	
	/**
	 * Return connection into pool
	 * @param connection
	 * @throws DaoException
	 */
	public static void returnConnection(Connection connection) throws DaoException{
		try {
			connection.close();
		} catch (SQLException e) {
			throw new DaoException(MSG_ERROR_CLOSE_CONNECTION, e);
		}
	}
}
