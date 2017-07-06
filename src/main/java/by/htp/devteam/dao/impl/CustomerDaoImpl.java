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

import static by.htp.devteam.dao.util.ConstantValue.*;

public class CustomerDaoImpl implements CustomerDao{

	private final static int ID = 1;
	private final static int NAME = 2;
	private final static int EMAIL = 3;
	private final static int PHONE = 4;

	@Override
	public Customer getCustomerByUser(User user) throws DaoException {
		Customer customer = null;
		try ( Connection dbConnection = ConnectionPool.getConnection();
				PreparedStatement ps = dbConnection.prepareStatement(SQL_CUSTOMER_GET_BY_USER) ) {
			
			ps.setLong(1, user.getId());
			customer = getCustomerFromResultSet(ps);
		} catch (SQLException e) {
			throw new DaoException(MSG_ERROR_CUSTOMER_GET_BY_USER, e);
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
