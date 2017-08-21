package by.htp.devteam.service.impl;

import by.htp.devteam.bean.Customer;
import by.htp.devteam.bean.User;
import by.htp.devteam.dao.CustomerDao;
import by.htp.devteam.dao.DaoException;
import by.htp.devteam.dao.DaoFactory;
import by.htp.devteam.service.CustomerService;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.util.ErrorCode;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public final class CustomerServiceImpl implements CustomerService {
	
	/** Logger */
	private static final Logger logger = LogManager.getLogger(CustomerServiceImpl.class.getName());
	
	/** DAO object */
	private CustomerDao customerDao;
	
	public CustomerServiceImpl() {
		super();
		DaoFactory daoFactory = DaoFactory.getInstance();
		customerDao = daoFactory.getCustomerDao();
	}

	@Override
	public Customer getByUser(User user) throws ServiceException{
		Customer customer = null;
		try {
			customer = customerDao.getCustomerByUser(user);
		} catch ( DaoException e ) {
			logger.error(e.getMessage(), e);
			throw new ServiceException(ErrorCode.APPLICATION);
		}
		
		return customer;
	}
}
