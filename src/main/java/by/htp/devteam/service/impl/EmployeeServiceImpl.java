package by.htp.devteam.service.impl;

import static by.htp.devteam.service.util.ConstantValue.*;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import by.htp.devteam.bean.Employee;
import by.htp.devteam.bean.Project;
import by.htp.devteam.bean.Qualification;
import by.htp.devteam.bean.User;
import by.htp.devteam.bean.vo.PagingVo;
import by.htp.devteam.dao.DaoException;
import by.htp.devteam.dao.DaoFactory;
import by.htp.devteam.dao.EmployeeDao;
import by.htp.devteam.service.EmployeeService;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.util.ErrorCode;
import by.htp.devteam.service.validation.EmployeeValidation;
import by.htp.devteam.util.ConfigProperty;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public final class EmployeeServiceImpl implements EmployeeService{

	/** Logger */
	private static final Logger logger = LogManager.getLogger(EmployeeServiceImpl.class.getName());
	
	/** DAO object */
	private EmployeeDao employeeDao;
	
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
			throw new ServiceException(ErrorCode.APPLICATION);
		}
		
		return employee;
	}
	
	@Override
	public List<Employee> getNotBusyEmployeesForPeriodByQualifications(Date dateStart, Date dateFinish, 
			Set<Qualification> qualifications) throws ServiceException{
		List<Employee> employees = null;
		try {
			employees = employeeDao.getFreeEmployeesForPeriod(dateStart, dateFinish, qualifications);
		} catch ( DaoException  e ) {
			logger.error(e.getMessage(), e);
			throw new ServiceException(ErrorCode.APPLICATION);
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
			throw new ServiceException(ErrorCode.APPLICATION);
		}
		
		return employees;
	}

	@Override
	public Map<Long, Integer> getQualificationsIdsAndCountByEmployees(Long[] ids) throws ServiceException {
		Map<Long, Integer> qualificationCountByEmployees = null;
		try {
			qualificationCountByEmployees = employeeDao.getQualificationsIdsAndCountByEmployees(ids);
		} catch ( DaoException e ) {
			logger.error(e.getMessage(), e);
			throw new ServiceException(ErrorCode.APPLICATION);
		}
		return qualificationCountByEmployees;
	}

	@Override
	public boolean isEmployeesNotBusyForPeriod(Connection connection, Long[] employeesIds, Date dateStart, Date dateFinish)
			throws DaoException {
	
		return employeeDao.isEmployeesNotBusyForPeriod(connection, employeesIds, dateStart, dateFinish);
	}

	@Override
	public Employee add(String name, String startWork, String qualificationId) throws ServiceException {
		EmployeeValidation employeeValidation = new EmployeeValidation();
		employeeValidation.validate(name, startWork, qualificationId);
		
		if ( !employeeValidation.isValid() ) {
			logger.info(MSG_LOGGER_EMPLOYEE_ADD_INCORRECT_FIELD);
			throw new ServiceException(ErrorCode.VALIDATION, employeeValidation.getNotValidField());
		} 
		
		Employee employee = new Employee();
		employee.setName(name);
		employee.setStartWork(Date.valueOf(startWork));
		
		Qualification qualification = new Qualification();
		qualification.setId(Long.valueOf(qualificationId));
		
		employee.setQualification(qualification);
		
		try {
			employee = employeeDao.add(employee);
		} catch ( DaoException e ) {
			logger.error(e.getMessage(), e);
			throw new ServiceException(ErrorCode.APPLICATION);
		}
		
		return employee;
	}

	@Override
	public Employee getById(String id) throws ServiceException {
		EmployeeValidation employeeValidation = new EmployeeValidation();
		if ( !employeeValidation.validateId(id)) {
			logger.info(MSG_LOGGER_EMPLOYEE_VIEW_INCORRECT_ID, id);
			throw new ServiceException(ErrorCode.VALIDATION_ID);
		} 
		
		Employee employee = null;
		try {
			employee = employeeDao.getById(Long.valueOf(id));
		} catch ( DaoException e ) {
			logger.error(e.getMessage(), e);
			throw new ServiceException(ErrorCode.APPLICATION);
		} catch ( NullPointerException e ) {
			logger.info(MSG_LOGGER_EMPLOYEE_VIEW_NOT_EXIST_ID, id);
			throw new ServiceException(ErrorCode.VALIDATION_ID);
		}
		
		return employee;
	}

	@Override
	public void setUserForEmployee(Connection connection, Employee employee, User user) throws ServiceException {
		try {
			employeeDao.setUserForEmployee(connection, employee, user);
		} catch ( DaoException e ) {
			logger.error(e.getMessage(), e);
			throw new ServiceException(ErrorCode.APPLICATION);
		}		
	}

	@Override
	public boolean isExistUserForEmployee(Connection connection, Employee employee) throws DaoException {
		return employeeDao.isExistUserForEmployee(connection, employee);
	}

	@Override
	public PagingVo<Employee> fetchAll(String currPage) throws ServiceException {
		if ( currPage == null ) {
			currPage = ConfigProperty.INSTANCE.getStringValue(CONFIG_PAGE_START_PAGE);
		}
		
		if ( !EmployeeValidation.validatePage(currPage) ) {
			logger.info(MSG_LOGGER_PAGE_NUMBER_NOT_FOUND, currPage);
			throw new ServiceException(ErrorCode.PAGE_NUMBER_NOT_FOUND);
		}
		
		int countPerPage = ConfigProperty.INSTANCE.getIntValue(CONFIG_PAGE_COUNT_PER_PAGE);
		int currPageValue = Integer.valueOf(currPage);		
		int offset = (currPageValue - 1 ) * countPerPage;
			
		PagingVo<Employee> pagingVo = null;
		try {
			pagingVo = employeeDao.fetchAll(offset, countPerPage);
			
			int countPages = (int) Math.ceil(pagingVo.getCountAllRecords() * 1.0 / countPerPage);
			pagingVo.setCountPages(countPages);
			pagingVo.setCurrPage(currPageValue);
		} catch ( DaoException e ) {
			logger.error(e.getMessage(), e);
			throw new ServiceException(ErrorCode.APPLICATION);
		}

		return pagingVo;
	}

	@Override
	public List<Employee> getListWithNotSetUser() throws ServiceException {
		List<Employee> employeeList = null;
		try {
			employeeList = employeeDao.getListWithNotSetUser();
		} catch ( DaoException e ) {
			logger.error(e.getMessage(), e);
			throw new ServiceException(ErrorCode.APPLICATION);
		}

		return employeeList;
	}
}
