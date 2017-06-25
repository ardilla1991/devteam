package by.htp.devteam.service.impl;

import by.htp.devteam.bean.Employee;
import by.htp.devteam.bean.User;
import by.htp.devteam.dao.DaoFactory;
import by.htp.devteam.dao.EmployeeDao;
import by.htp.devteam.service.EmployeeService;

public class EmployeeServiceImpl implements EmployeeService{

	EmployeeDao employeeDao;
	
	public EmployeeServiceImpl() {
		super();
		DaoFactory daoFactory = DaoFactory.getInstance();
		employeeDao = daoFactory.getEmployeeDao();
	}
	
	@Override
	public Employee getEmployeeByUser(User user){
		Employee employee = employeeDao.getEmployeeByUser(user);
		System.out.println("user");
		
		return employee;
	}

	@Override
	public boolean logOut(Employee employee) {
		
		return false;
	}
}
