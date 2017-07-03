package by.htp.devteam.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import by.htp.devteam.bean.Customer;
import by.htp.devteam.bean.User;
import by.htp.devteam.controller.ConnectionPool;
import by.htp.devteam.dao.CustomerDao;
import by.htp.devteam.dao.DaoException;

public class CustomerDaoImpl extends CommonDao implements CustomerDao{

	private final static int ID = 1;
	private final static int NAME = 2;
	private final static int EMAIL = 3;
	private final static int PHONE = 4;
	
	private final static String GET_BY_USER = "SELECT e.* FROM customer as e "
			+ "WHERE e.user_id=?";

	
	@Override
	public Customer getCustomerByUser(User user) throws DaoException {
		Customer customer = null;
		try ( Connection dbConnection = ConnectionPool.getConnection();
				PreparedStatement ps = dbConnection.prepareStatement(GET_BY_USER) ) {
			
			ps.setLong(1, user.getId());
			customer = getCustomerFromResultSet(ps);
		} catch (SQLException e) {
			throw new DaoException("sql error", e);
		}
		return customer;
	}
	
	private Customer getCustomerFromResultSet(PreparedStatement ps) throws SQLException {
		Customer customer = null;
		try ( ResultSet rs = ps.executeQuery() ) {
			if ( rs.next() ) {
				customer = new Customer();
				customer.setId(rs.getLong(ID));
				customer.setName(rs.getString(NAME));
				customer.setEmail(rs.getString(EMAIL));
				customer.setPhone(rs.getString(PHONE));
			}
		}
		
		return customer;
	}

}
