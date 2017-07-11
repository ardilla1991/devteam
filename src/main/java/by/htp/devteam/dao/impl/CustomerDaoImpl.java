package by.htp.devteam.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import by.htp.devteam.bean.Customer;
import by.htp.devteam.bean.User;
import by.htp.devteam.dao.CustomerDao;
import by.htp.devteam.dao.DaoException;
import by.htp.devteam.dao.util.ConnectionPool;

import static by.htp.devteam.dao.util.ConstantValue.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class CustomerDaoImpl implements CustomerDao {

	private final static int ID = 1;
	private final static int NAME = 2;
	private final static int EMAIL = 3;
	private final static int PHONE = 4;
	
	private static final Logger logger = LogManager.getLogger(CustomerDaoImpl.class.getName());

	@Override
	public Customer getCustomerByUser(User user) throws DaoException {
		Customer customer = null;
		try ( Connection dbConnection = ConnectionPool.getConnection();
				PreparedStatement ps = dbConnection.prepareStatement(SQL_CUSTOMER_GET_BY_USER) ) {
			
			ps.setLong(1, user.getId());
			customer = executeQueryAndGetCustomerFromResultSet(ps, user);
		} catch (SQLException e) {
			throw new DaoException(MSG_ERROR_CUSTOMER_GET_BY_USER, e);
		}
		return customer;
	}
	
	private Customer executeQueryAndGetCustomerFromResultSet(PreparedStatement ps, User user) throws SQLException {
		Customer customer = new Customer();
		try ( ResultSet rs = ps.executeQuery() ) {
			if ( rs.next() ) {
				customer = new Customer();
				customer.setId(rs.getLong(ID));
				customer.setName(rs.getString(NAME));
				customer.setEmail(rs.getString(EMAIL));
				customer.setPhone(rs.getString(PHONE));
			} else {
				logger.info(MSG_CUSTOMER_NOT_FOUND, user.getId());
			}
		}
		
		return customer;
	}

}
