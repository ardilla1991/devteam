package by.htp.devteam.dao.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import by.htp.devteam.bean.Customer;
import by.htp.devteam.controller.ConnectionPool;
import by.htp.devteam.dao.CustomerDao;

public class CustomerDaoImpl implements CustomerDao{

	private final int ID = 1;
	private final int NAME = 2;
	private final int LOGIN = 3;
	private final int PASSWORD = 4;
	private final int EMAIL = 5;
	private final int PHONE = 6;
	
	private final String FETCH_BY_CREDENTIALS = "SELECT e.* FROM customer as e "
			+ "WHERE e.login=? AND e.password=?";

	
	@Override
	public Customer fetchByCredentials(String login, String password) {
		
		Customer customer = null;
		Connection dbConnection = null;
		PreparedStatement ps = null;
		
		try {
			dbConnection = ConnectionPool.getConnection();
		
			ps = dbConnection.prepareStatement(FETCH_BY_CREDENTIALS);
			ps.setString(1, login);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();

			if ( rs.next() ) {
				customer = new Customer();
				customer.setId(rs.getLong(ID));
				customer.setName(rs.getString(NAME));
				customer.setLogin(rs.getString(LOGIN));
				customer.setPassword(rs.getString(PASSWORD));
				customer.setEmail(rs.getString(EMAIL));
				customer.setPhone(rs.getString(PHONE));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps);
			ConnectionPool.close(dbConnection);
		}
		return customer;
	}
	
	private void close(PreparedStatement ps) {
		if ( ps != null ) {
			try {
				ps.close();
			} catch ( SQLException e ) {
				e.printStackTrace();
			}
		}
	}
	
	private void close(Statement st) {
		if ( st != null ) {
			try {
				st.close();
			} catch ( SQLException e ) {
				e.printStackTrace();
			}
		}
	}
}
