package by.htp.devteam.service.impl;

import by.htp.devteam.bean.Customer;
import by.htp.devteam.bean.User;
import by.htp.devteam.dao.CustomerDao;
import by.htp.devteam.dao.DaoFactory;
import by.htp.devteam.service.CustomerService;

public class CustomerServiceImpl implements CustomerService{
	CustomerDao customerDao;
	
	public CustomerServiceImpl() {
		super();
		DaoFactory daoFactory = DaoFactory.getInstance();
		customerDao = daoFactory.getCustomerDao();
	}

	@Override
	public boolean logOut(Customer customer) {
		
		return false;
	}


	@Override
	public Customer getCustomerByUser(User user) {
		return customerDao.getCustomerByUser(user);
	}
}
