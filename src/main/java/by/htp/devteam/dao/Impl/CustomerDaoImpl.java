package by.htp.devteam.dao.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import by.htp.devteam.bean.Customer;
import by.htp.devteam.controller.ConnectionPool;
import by.htp.devteam.dao.CustomerDao;

public class CustomerDaoImpl extends CommonDao implements CustomerDao{

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
		try ( Connection dbConnection = ConnectionPool.getConnection();
				PreparedStatement ps = dbConnection.prepareStatement(FETCH_BY_CREDENTIALS) ) {

			ps.setString(1, login);
			ps.setString(2, password);
			try ( ResultSet rs = ps.executeQuery() ) {
				customer = getCustomerFromResultSet(rs);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customer;
	}
	
	private Customer getCustomerFromResultSet(ResultSet rs) throws SQLException {
		Customer customer = null;
		if ( rs.next() ) {
			customer = new Customer();
			customer.setId(rs.getLong(ID));
			customer.setName(rs.getString(NAME));
			customer.setLogin(rs.getString(LOGIN));
			customer.setPassword(rs.getString(PASSWORD));
			customer.setEmail(rs.getString(EMAIL));
			customer.setPhone(rs.getString(PHONE));
		}
		
		return customer;
	}
}
