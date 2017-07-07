package by.htp.devteam.service.impl;

import by.htp.devteam.bean.Customer;
import by.htp.devteam.bean.User;
import by.htp.devteam.dao.CustomerDao;
import by.htp.devteam.dao.DaoException;
import by.htp.devteam.dao.DaoFactory;
import by.htp.devteam.service.CustomerService;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.util.ErrorCodeEnum;

public class CustomerServiceImpl implements CustomerService{
	CustomerDao customerDao;
	
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
			/// Logger
			throw new ServiceException(ErrorCodeEnum.APPLICATION);
		}
		
		return customer;
	}
}
