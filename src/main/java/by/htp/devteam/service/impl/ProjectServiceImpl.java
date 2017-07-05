package by.htp.devteam.service.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import by.htp.devteam.bean.Employee;
import by.htp.devteam.bean.Order;
import by.htp.devteam.bean.Project;
import by.htp.devteam.bean.Qualification;
import by.htp.devteam.bean.dto.OrderVo;
import by.htp.devteam.bean.dto.ProjectVo;
import by.htp.devteam.bean.dto.ProjectListVo;
import by.htp.devteam.dao.DaoException;
import by.htp.devteam.dao.DaoFactory;
import by.htp.devteam.dao.EmployeeDao;
import by.htp.devteam.dao.OrderDao;
import by.htp.devteam.dao.ProjectDao;
import by.htp.devteam.service.EmployeeService;
import by.htp.devteam.service.ProjectService;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.ServiceFactory;
import by.htp.devteam.service.util.ErrorCodeEnum;
import by.htp.devteam.service.validation.ProjectValidation;
import by.htp.devteam.util.SettingConstantValue;
import by.htp.devteam.util.Validator;

public class ProjectServiceImpl implements ProjectService{

	private ProjectDao projectDao;
	private EmployeeDao employeeDao;
	private OrderDao orderDao;
	
	public ProjectServiceImpl() {
		super();
		DaoFactory daoFactory = DaoFactory.getInstance();
		projectDao = daoFactory.getProjectDao();
		employeeDao = daoFactory.getEmployeeDao();
		orderDao = daoFactory.getOrderDao();
	}

	@Override
	public ProjectListVo fetchAll(String currPage, Employee employee) throws ServiceException{
		int countPerPage = SettingConstantValue.COUNT_PER_PAGE;
		int currPageValue = 0;
		
		currPageValue = ( currPage == null 
					  ? SettingConstantValue.START_PAGE 
					  : Integer.valueOf(currPage) );
		
		if ( currPageValue == 0 )
			throw new ServiceException("page not found");
		
		int offset = (currPageValue - 1 ) * countPerPage;
			
		ProjectListVo projectListVo = null;
		try {
			projectListVo = projectDao.fetchAll(offset, countPerPage, employee);
			
			int countPages = (int) Math.ceil(projectListVo.getCountRecords() * 1.0 / countPerPage);
			projectListVo.setCountPages(countPages);
			projectListVo.setCurrPage(currPageValue);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException("service error", e);
		}

		return projectListVo;
	}

	@Override
	public Project add(OrderVo orderDto, String title, String description, String[] employees, String price) throws ServiceException {
		
		ProjectValidation projectValidation = new ProjectValidation();
		projectValidation.validate(title, description, employees, price);
		
		if ( !projectValidation.isValid() ) {
			throw new ServiceException(ErrorCodeEnum.VALIDATION_ERROR, projectValidation.getNotValidField());
		}
		
		Long[] employeesIds = comvertFromStringToLongArray(employees);
		Map<Long, Integer> qualificationCountByEmployees = null;
		try {
			qualificationCountByEmployees = employeeDao.getQualificationsCountByEmployees(employeesIds);
		} catch ( DaoException e ) {
			//logger
			e.printStackTrace();
			throw new ServiceException(ErrorCodeEnum.APPLICATION_ERROR);
		}
		
		Map<Long, Integer> neededQualifications = new HashMap<Long, Integer>(orderDto.getQualifications().size());
		for ( Entry<Qualification, Integer> qualification : orderDto.getQualifications().entrySet() ) {
		    Map.Entry<Qualification, Integer> entry = (Map.Entry<Qualification, Integer>) qualification;
		    neededQualifications.put(entry.getKey().getId(), entry.getValue());
		}
		
		projectValidation.validate(qualificationCountByEmployees, neededQualifications);

		if ( !projectValidation.isValid() ) {
			throw new ServiceException(ErrorCodeEnum.VALIDATION_ERROR, projectValidation.getNotValidField());
		} 
		
		Project project = new Project();
		project.setTitle(title);
		project.setDescription(description);
		project.setOrder(orderDto.getOrder());
		//Long[] employeesIds = comvertFromStringToLongArray(employees);
		Connection connection = null;
		try {
			connection = projectDao.startTransaction();
			boolean neededEmployeeeAreFree = employeeDao.isEmployeesFreeFroPeriod(connection, employeesIds,
					orderDto.getOrder().getDateStart(), orderDto.getOrder().getDateFinish());
			if (neededEmployeeeAreFree) {
				project = projectDao.add(connection, project);
				projectDao.addEmployees(connection, project, employeesIds);
				Order order = orderDto.getOrder();
				order.setPrice(new BigDecimal(price).setScale(2, BigDecimal.ROUND_CEILING));
				orderDao.setPrice(connection, order);
				commitTransaction(connection);
			} else {
				rollbackTransaction(connection);
				//logger
				throw new ServiceException("not isset all free employee");
			}
		} catch (DaoException e) {
			rollbackTransaction(connection);
			e.printStackTrace();
			//logger
			throw new ServiceException(ErrorCodeEnum.APPLICATION_ERROR);
		}

		return project;
	}
	
	private Long[] comvertFromStringToLongArray(String[] arrayOfStringValues) {
		int arrayLength = arrayOfStringValues.length;
		Long[] longTypeArray = new Long[arrayLength];
		for ( int i = 0; i < arrayLength; i++ ) {
			longTypeArray[i] = Long.valueOf(arrayOfStringValues[i]);
		}
		
		return longTypeArray;
	}
	
	private void rollbackTransaction(Connection connection) {
		try {
			projectDao.rollbackTransaction(connection);
		} catch (DaoException e1) {
			e1.printStackTrace();
			System.out.println("rollback error");
		}
	}
	
	private void commitTransaction(Connection connection) {
		try {
			projectDao.commitTransaction(connection);
		} catch (DaoException e) {
			System.out.println("commit error");
		}
	}

	@Override
	public ProjectVo getById(String id) throws ServiceException {

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
			e.printStackTrace();
			throw new ServiceException("service error", e);
		}
		
		return projectDto;
	}

	@Override
	public void updateHours(String id, Employee employee, String hours) throws ServiceException {
		if ( !Validator.isLong(hours) )
			throw new ServiceException("invalid value for hours");
		
		Project project = new Project();
		project.setId(Long.valueOf(id));
		try {
			projectDao.updateHours(project, employee, Integer.valueOf(hours));
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
	}


	

}
