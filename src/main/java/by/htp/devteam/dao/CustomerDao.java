package by.htp.devteam.dao;

import by.htp.devteam.bean.Customer;
import by.htp.devteam.bean.User;

/**
 * Interface for customer's DAO layer
 * @author julia
 *
 */
public interface CustomerDao {

	/**
	 * Get customer records for user loggined in system
	 * @param user User that loggined in system
	 * @return Customer
	 * @throws DaoException
	 */
	Customer getCustomerByUser(User user) throws DaoException;
}
