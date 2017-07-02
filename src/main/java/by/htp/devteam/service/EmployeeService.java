package by.htp.devteam.service;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import by.htp.devteam.bean.Employee;
import by.htp.devteam.bean.Project;
import by.htp.devteam.bean.Qualification;
import by.htp.devteam.bean.User;

public interface EmployeeService {
	
	Employee getEmployeeByUser(User user);
	boolean logOut(Employee employee);
	List<Employee> getFreeEmployeesForPeriod(Date dateStart, Date dateFinish, Set<Qualification> qualifications);
	Map<Employee, Integer> getEmployeesByProject(Project project);
}
