package by.htp.devteam.service.impl;

import static by.htp.devteam.service.util.ConstantValue.*;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import by.htp.devteam.bean.Employee;
import by.htp.devteam.bean.Project;
import by.htp.devteam.bean.Qualification;
import by.htp.devteam.bean.dto.OrderVo;
import by.htp.devteam.bean.dto.ProjectVo;
import by.htp.devteam.bean.dto.ProjectListVo;
import by.htp.devteam.dao.DaoException;
import by.htp.devteam.dao.DaoFactory;
import by.htp.devteam.dao.ProjectDao;
import by.htp.devteam.service.EmployeeService;
import by.htp.devteam.service.OrderService;
import by.htp.devteam.service.ProjectService;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.ServiceFactory;
import by.htp.devteam.service.util.ErrorCodeEnum;
import by.htp.devteam.service.validation.OrderValidation;
import by.htp.devteam.service.validation.ProjectValidation;
import by.htp.devteam.util.SettingConstantValue;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class ProjectServiceImpl implements ProjectService{

	private ProjectDao projectDao;
	private static final Logger logger = LogManager.getLogger(ProjectServiceImpl.class.getName());
	
	public ProjectServiceImpl() {
		super();
		DaoFactory daoFactory = DaoFactory.getInstance();
		projectDao = daoFactory.getProjectDao();
	}

	@Override
	public ProjectListVo fetchAll(String currPage, Employee employee) throws ServiceException{
		
		if ( currPage == null ) {
			currPage = String.valueOf(SettingConstantValue.START_PAGE);
		}
		
		ProjectValidation projectValidation = new ProjectValidation();
		if ( !projectValidation.validatePage(currPage) ) {
			logger.info(MSG_LOGGER_PAGE_NUMBER_NOT_FOUND, currPage);
			throw new ServiceException(ErrorCodeEnum.PAGE_NUMBER_NOT_FOUND);
		}
		
		int countPerPage = SettingConstantValue.COUNT_PER_PAGE;
		int currPageValue = Integer.valueOf(currPage);		
		int offset = (currPageValue - 1 ) * countPerPage;
			
		ProjectListVo projectListVo = null;
		try {
			projectListVo = projectDao.fetchAll(offset, countPerPage, employee);
			
			int countPages = (int) Math.ceil(projectListVo.getCountRecords() * 1.0 / countPerPage);
			projectListVo.setCountPages(countPages);
			projectListVo.setCurrPage(currPageValue);
		} catch ( DaoException e ) {
			logger.error(e.getMessage(), e);
			throw new ServiceException(ErrorCodeEnum.APPLICATION);
		}

		return projectListVo;
	}

	@Override
	public Project add(OrderVo orderDto, String title, String description, String[] employees, String price) throws ServiceException {
		
		ProjectValidation projectValidation = new ProjectValidation();
		projectValidation.validate(title, description, employees, price);
		
		if ( !projectValidation.isValid() ) {
			logger.info(MSG_LOGGER_PROJECT_ADD_INCORRECT_FIELD);
			throw new ServiceException(ErrorCodeEnum.VALIDATION, projectValidation.getNotValidField());
		}
		
		Long[] employeesIds = comvertFromStringToLongArray(employees);
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		EmployeeService employeeService = serviceFactory.getEmployeeService();
		Map<Long, Integer> qualificationCountByEmployees = employeeService.getQualificationsCountByEmployees(employeesIds);
		Map<Long, Integer> neededQualifications = getNeededQualifications(orderDto.getQualifications());
		projectValidation.validate(qualificationCountByEmployees, neededQualifications);

		if ( !projectValidation.isValid() ) {
			logger.info(MSG_LOGGER_PROJECT_ADD_INCORRECT_FIELD_EMPLOYEE);
			throw new ServiceException(ErrorCodeEnum.VALIDATION, projectValidation.getNotValidField());
		} 
		
		Project project = new Project();
		project.setTitle(title);
		project.setDescription(description);
		project.setOrder(orderDto.getOrder());
		
		Connection connection = null;	
		OrderService orderService = serviceFactory.getOrderService();
		try {
			connection = projectDao.startTransaction();
			boolean neededEmployeeeAreFree = employeeService.isEmployeesFreeFroPeriod(connection, employeesIds,
					orderDto.getOrder().getDateStart(), orderDto.getOrder().getDateFinish());
			if (neededEmployeeeAreFree) {
				project = projectDao.add(connection, project);
				projectDao.addEmployees(connection, project, employeesIds);
				orderService.setPrice(connection, orderDto.getOrder(), price);
				commitTransaction(connection);
			} else {
				rollbackTransaction(connection);
				logger.info(MSG_LOGGER_PROJECT_ADD_NO_ISSET_FREE_EMPLOYEE);
				throw new ServiceException(ErrorCodeEnum.NOT_ISSET_FREE_EMPLOYEE);
			}
		} catch ( DaoException | ServiceException e) {
			rollbackTransaction(connection);
			logger.error(e.getMessage(), e);
			throw new ServiceException(ErrorCodeEnum.APPLICATION);
		}

		return project;
	}
	
	private Map<Long, Integer> getNeededQualifications(Map<Qualification, Integer> qualifications) {
		Map<Long, Integer> neededQualifications = new HashMap<Long, Integer>(qualifications.size());
		for ( Entry<Qualification, Integer> qualification : qualifications.entrySet() ) {
		    Map.Entry<Qualification, Integer> entry = (Map.Entry<Qualification, Integer>) qualification;
		    neededQualifications.put(entry.getKey().getId(), entry.getValue());
		}
		
		return neededQualifications;
	}
	
	private Long[] comvertFromStringToLongArray(String[] arrayOfStringValues) {
		int arrayLength = arrayOfStringValues.length;
		Long[] longTypeArray = new Long[arrayLength];
		for ( int i = 0; i < arrayLength; i++ ) {
			longTypeArray[i] = Long.valueOf(arrayOfStringValues[i]);
		}
		
		return longTypeArray;
	}
	
	private void rollbackTransaction(Connection connection) throws ServiceException {
		try {
			projectDao.rollbackTransaction(connection);
		} catch (DaoException e) {
			logger.error(e.getMessage(), e);
			throw new ServiceException(ErrorCodeEnum.APPLICATION);
		}
	}
	
	private void commitTransaction(Connection connection) throws DaoException {
		projectDao.commitTransaction(connection);
	}

	@Override
	public ProjectVo getById(String id) throws ServiceException {

		ProjectValidation projectValidation = new ProjectValidation();
		if ( !projectValidation.validateId(id)) {
			logger.info(MSG_LOGGER_PROJECT_VIEW_INCORRECT_ID, id);
			throw new ServiceException(ErrorCodeEnum.VALIDATION_ID);
		} 
		
		ProjectVo projectDto = new ProjectVo();
		Project project;
		try {
			project = projectDao.getById(Long.valueOf(id));
			projectDto.setProject(project);
			ServiceFactory serviceFactory = ServiceFactory.getInstance();
			EmployeeService employeeService = serviceFactory.getEmployeeService();
			Map<Employee, Integer> employees = employeeService.getByProject(project);
			projectDto.setEmployee(employees);
		} catch (DaoException e) {
			logger.error(e.getMessage(), e);
			throw new ServiceException(ErrorCodeEnum.APPLICATION);
		} catch (NullPointerException e) {
			logger.info(MSG_LOGGER_PROJECT_VIEW_NOT_EXIST_ID, id);
			throw new ServiceException(ErrorCodeEnum.VALIDATION_ID);
		}
		
		return projectDto;
	}

	@Override
	public void updateHours(String id, Employee employee, String hours) throws ServiceException {
		
		ProjectValidation projectValidation = new ProjectValidation();
		
		if ( !projectValidation.validateId(id)) {
			logger.info(MSG_LOGGER_PROJECT_UPDATE_HOURS_INCORRECT_PROJECT_ID, id);
			throw new ServiceException(ErrorCodeEnum.VALIDATION_ID);
		} 
		
		projectValidation.validate(hours);
		
		if ( !projectValidation.isValid() ) {
			logger.info(MSG_LOGGER_PROJECT_UPDATE_HOURS_INCORRECT_FIELD, id);
			throw new ServiceException(ErrorCodeEnum.VALIDATION, projectValidation.getNotValidField());
		}
		
		Project project = new Project();
		project.setId(Long.valueOf(id));
		try {
			projectDao.updateHours(project, employee, Integer.valueOf(hours));
		} catch ( DaoException e ) {
			logger.error(e.getMessage(), e);
			throw new ServiceException(ErrorCodeEnum.APPLICATION);
		}
		
	}

}
