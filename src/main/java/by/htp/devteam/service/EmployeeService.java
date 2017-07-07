package by.htp.devteam.service;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import by.htp.devteam.bean.Employee;
import by.htp.devteam.bean.Project;
import by.htp.devteam.bean.Qualification;
import by.htp.devteam.bean.User;
import by.htp.devteam.dao.DaoException;

public interface EmployeeService {
	
	Employee getByUser(User user) throws ServiceException;
	
	List<Employee> getFreeEmployeesForPeriod(Date dateStart, Date dateFinish, Set<Qualification> qualifications) 
			throws ServiceException;
	
	Map<Employee, Integer> getByProject(Project project) throws ServiceException;
	
	Map<Long, Integer> getQualificationsCountByEmployees(Long[] ids) throws ServiceException;
	
	boolean isEmployeesFreeFroPeriod(Connection connection, Long[] employeesIds, Date dateStart, Date dateFinish) throws DaoException;
}
