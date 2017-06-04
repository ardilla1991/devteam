package by.htp.devteam.service.Impl;

import by.htp.devteam.bean.Employee;
import by.htp.devteam.dao.DaoFactory;
import by.htp.devteam.dao.EmployeeDao;
import by.htp.devteam.service.EmployeeService;
import by.htp.devteam.service.ServiceException;

public class EmployeeServiceImpl implements EmployeeService{

	EmployeeDao employeeDao;
	
	public EmployeeServiceImpl() {
		super();
		DaoFactory daoFactory = DaoFactory.getInstance();
		employeeDao = daoFactory.getEmployeeDao();
	}
	
	@Override
	public Employee authorise(String login, String password) throws ServiceException{
		Employee user = employeeDao.fetchByCredentials(login, password);
		System.out.println("user");
		if ( user == null ) {
			throw new ServiceException("Invalid credentials");
		}
		return user;
	}

	@Override
	public boolean logOut(Employee employee) {
		
		return false;
	}
}
