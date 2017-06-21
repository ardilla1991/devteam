package by.htp.devteam.service.Impl;

import by.htp.devteam.bean.Customer;
import by.htp.devteam.bean.Employee;
import by.htp.devteam.dao.CustomerDao;
import by.htp.devteam.dao.DaoFactory;
import by.htp.devteam.dao.EmployeeDao;
import by.htp.devteam.service.CustomerService;
import by.htp.devteam.service.ServiceException;

public class CustomerServiceImpl implements CustomerService{
	CustomerDao customerDao;
	
	public CustomerServiceImpl() {
		super();
		DaoFactory daoFactory = DaoFactory.getInstance();
		customerDao = daoFactory.getCustomerDao();
	}
	
	@Override
	public Customer authorise(String login, String password) throws ServiceException{
		Customer user = customerDao.fetchByCredentials(login, password);
		System.out.println("user");
		if ( user == null ) {
			throw new ServiceException("Invalid credentials");
		}
		return user;
	}

	@Override
	public boolean logOut(Customer customer) {
		
		return false;
	}
}
