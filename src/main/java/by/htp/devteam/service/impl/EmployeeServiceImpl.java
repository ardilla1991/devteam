package by.htp.devteam.service.impl;

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
import by.htp.devteam.dao.DaoFactory;
import by.htp.devteam.dao.EmployeeDao;
import by.htp.devteam.service.EmployeeService;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.util.ErrorCodeEnum;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import static by.htp.devteam.service.util.ConstantValue.*;

public class EmployeeServiceImpl implements EmployeeService{

	EmployeeDao employeeDao;
	private static final Logger logger = LogManager.getLogger(EmployeeServiceImpl.class.getName());
	
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
		} catch ( DaoException e ) {
			logger.error(e.getMessage(), e);
			throw new ServiceException(ErrorCodeEnum.APPLICATION);
		}
		
		return employee;
	}
	
	public List<Employee> getFreeEmployeesForPeriod(Date dateStart, Date dateFinish, Set<Qualification> qualifications) throws ServiceException{
		List<Employee> employees = null;
		try {
			employees = employeeDao.getFreeEmployeesForPeriod(dateStart, dateFinish, qualifications);
		} catch ( DaoException  e ) {
			logger.error(e.getMessage(), e);
			throw new ServiceException(ErrorCodeEnum.APPLICATION);
		}
		
		return employees;
	}

	@Override
	public Map<Employee, Integer> getByProject(Project project) throws ServiceException{
		Map<Employee, Integer>  employees = null;
		try {
			employees = employeeDao.getEmployeesAndSpendingHoursByProject(project);
		} catch ( DaoException e ) {
			logger.error(e.getMessage(), e);
			throw new ServiceException(ErrorCodeEnum.APPLICATION);
		}
		
		return employees;
	}

	@Override
	public Map<Long, Integer> getQualificationsCountByEmployees(Long[] ids) throws ServiceException {
		Map<Long, Integer> qualificationCountByEmployees = null;
		try {
			qualificationCountByEmployees = employeeDao.getQualificationsIdsAndCountByEmployees(ids);
		} catch ( DaoException e ) {
			logger.error(e.getMessage(), e);
			throw new ServiceException(ErrorCodeEnum.APPLICATION);
		}
		return qualificationCountByEmployees;
	}

	@Override
	public boolean isEmployeesFreeFroPeriod(Connection connection, Long[] employeesIds, Date dateStart, Date dateFinish)
			throws DaoException {
	
		return employeeDao.isEmployeesFreeFroPeriod(connection, employeesIds, dateStart, dateFinish);
	}
}
