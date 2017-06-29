package by.htp.devteam.service.impl;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import by.htp.devteam.bean.Employee;
import by.htp.devteam.bean.Qualification;
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
	
	public List<Employee> getFreeEmployeesForPeriod(Date dateStart, Date dateFinish, Set<Qualification> qualifications) {
		return employeeDao.getFreeEmployeesForPeriod(dateStart, dateFinish, qualifications);
	}
}
