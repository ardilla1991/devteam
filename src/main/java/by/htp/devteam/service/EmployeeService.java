package by.htp.devteam.service;

import by.htp.devteam.bean.Employee;
import by.htp.devteam.bean.User;

public interface EmployeeService {
	
	Employee getEmployeeByUser(User user);
	boolean logOut(Employee employee);
}
