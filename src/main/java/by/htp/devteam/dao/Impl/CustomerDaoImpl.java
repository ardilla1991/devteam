package by.htp.devteam.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import by.htp.devteam.bean.Customer;
import by.htp.devteam.bean.User;
import by.htp.devteam.controller.ConnectionPool;
import by.htp.devteam.dao.CustomerDao;

public class CustomerDaoImpl extends CommonDao implements CustomerDao{

	private final int ID = 1;
	private final int NAME = 2;
	private final int EMAIL = 3;
	private final int PHONE = 4;
	
	private final String GET_BY_USER = "SELECT e.* FROM customer as e "
			+ "WHERE e.user_id=?";

	
	@Override
	public Customer getCustomerByUser(User user) {
		Customer customer = null;
		try ( Connection dbConnection = ConnectionPool.getConnection();
				PreparedStatement ps = dbConnection.prepareStatement(GET_BY_USER) ) {
			System.out.println("user_id=");
			System.out.println(user.getId());
			ps.setLong(1, user.getId());
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
			customer.setEmail(rs.getString(EMAIL));
			customer.setPhone(rs.getString(PHONE));
		}
		
		return customer;
	}

}
