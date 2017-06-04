package by.htp.devteam.service;

import by.htp.devteam.bean.Employee;

public interface EmployeeService {
	
	Employee authorise(String login, String password) throws ServiceException;
	boolean logOut(Employee employee);
}
