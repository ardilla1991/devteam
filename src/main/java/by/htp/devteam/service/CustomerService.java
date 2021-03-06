package by.htp.devteam.service;

import by.htp.devteam.bean.Customer;
import by.htp.devteam.bean.User;

/**
 * Interface for customer's Service layer.
 * Do business logic including validation and dao exceptions.
 * Select data from storage using DAO object
 * @author julia
 *
 */
public interface CustomerService {

	/**
	 * Get customer record for user logined in system
	 * @param user User that logined in system
	 * @return Customer
	 * @throws ServiceException  after catching DAOException
	 */
	Customer getByUser(User user) throws ServiceException;

}
