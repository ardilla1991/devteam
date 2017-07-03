package by.htp.devteam.service.impl;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import by.htp.devteam.bean.Employee;
import by.htp.devteam.bean.Project;
import by.htp.devteam.bean.Qualification;
import by.htp.devteam.bean.User;
import by.htp.devteam.dao.DaoException;
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
	public Employee getByUser(User user) throws ServiceException{
		Employee employee = null;
		try {
			employee = employeeDao.getByUser(user);
		} catch (DaoException e) {
			throw new ServiceException("service error", e);
		}
		
		return employee;
	}
	
	public List<Employee> getFreeEmployeesForPeriod(Date dateStart, Date dateFinish, Set<Qualification> qualifications) throws ServiceException{
		List<Employee> employees = null;
		try {
			employees = employeeDao.getFreeEmployeesForPeriod(dateStart, dateFinish, qualifications);
		} catch (DaoException e) {
			throw new ServiceException("service error", e);
		}
		
		return employees;
	}

	@Override
	public Map<Employee, Integer> getByProject(Project project) throws ServiceException{
		Map<Employee, Integer>  employees = null;
		try {
			employees = employeeDao.getByProject(project);
		} catch (DaoException e) {
			throw new ServiceException("service error", e);
		}
		
		return employees;
	}
}
