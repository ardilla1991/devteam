package by.htp.devteam.service.impl;

import static by.htp.devteam.service.util.ConstantValue.*;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Set;

import javax.mail.MessagingException;

import by.htp.devteam.bean.Employee;
import by.htp.devteam.bean.OrderQualification;
import by.htp.devteam.bean.OrderWork;
import by.htp.devteam.bean.Project;
import by.htp.devteam.bean.ProjectEmployee;
import by.htp.devteam.bean.Qualification;
import by.htp.devteam.bean.Work;
import by.htp.devteam.bean.vo.OrderVo;
import by.htp.devteam.bean.vo.PagingVo;
import by.htp.devteam.bean.vo.ProjectVo;
import by.htp.devteam.controller.ObjectNotFoundException;
import by.htp.devteam.dao.DaoException;
import by.htp.devteam.dao.ProjectDao;
import by.htp.devteam.service.EmployeeService;
import by.htp.devteam.service.OrderService;
import by.htp.devteam.service.ProjectService;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.util.ErrorCode;
import by.htp.devteam.service.util.email.TLSEmail;
import by.htp.devteam.service.validation.PagingValidation;
import by.htp.devteam.service.validation.ProjectValidation;
import by.htp.devteam.util.ConfigProperty;

import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.apache.logging.log4j.LogManager;

public final class ProjectServiceImpl implements ProjectService{

	/** Logger */
	private static final Logger logger = LogManager.getLogger(ProjectServiceImpl.class.getName());
	
	private ProjectDao projectDao;

	private EmployeeService employeeService;
	
	private OrderService orderService;

	public ProjectServiceImpl() {
		super();
	}
	
	public ProjectDao getProjectDao() {
		return projectDao;
	}

	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

	public EmployeeService getEmployeeService() {
		return employeeService;
	}

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	@Override
	public PagingVo<Project> fetchAll(String currPage, Employee employee) throws ServiceException{
		
		if ( currPage == null ) {
			currPage = ConfigProperty.INSTANCE.getStringValue(CONFIG_PAGE_START_PAGE);
		}
		
		if ( !PagingValidation.getInstance().validatePage(currPage) ) {
			logger.info(MSG_LOGGER_PAGE_NUMBER_NOT_FOUND, currPage);
			throw new ServiceException(ErrorCode.PAGE_NUMBER_NOT_FOUND);
		}
		
		int countPerPage = ConfigProperty.INSTANCE.getIntValue(CONFIG_PAGE_COUNT_PER_PAGE);
		int currPageValue = Integer.valueOf(currPage);		
		int offset = (currPageValue - 1 ) * countPerPage;
			
		PagingVo<Project> pagingVo = null;
		try {
			pagingVo = projectDao.fetchAll(offset, countPerPage, employee);
			
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
	public Project add(OrderVo orderVo, String title, String description, String[] employees, String price) throws ServiceException {
		
		ProjectValidation projectValidation = new ProjectValidation();
		projectValidation.validate(title, description, employees, price);
		
		if ( !projectValidation.isValid() ) {
			logger.info(MSG_LOGGER_PROJECT_ADD_INCORRECT_FIELD);
			throw new ServiceException(ErrorCode.VALIDATION, projectValidation.getNotValidField());
		}
		
		Long[] employeesIds = comvertFromStringToLongArray(employees);
		
		// get map of selected employees qualifications and their count 
		Map<Long, Integer> qualificationCountByEmployees = employeeService.getQualificationsIdsAndCountByEmployees(employeesIds);
		// create a map for compare with selected values of qualifications
		Map<Long, Integer> neededQualifications = getNeededQualificationsAsIdAndCount(orderVo.getQualifications());
		// compare selected qualificationa and their count with qualifications from order
		projectValidation.validate(qualificationCountByEmployees, neededQualifications);

		if ( !projectValidation.isValid() ) {
			logger.info(MSG_LOGGER_PROJECT_ADD_INCORRECT_FIELD_EMPLOYEE);
			throw new ServiceException(ErrorCode.VALIDATION, projectValidation.getNotValidField());
		} 
		
		Project project = new Project();
		project.setTitle(title);
		project.setDescription(description);
	    project.setDateCreated(new Date());
		project.setOrder(orderVo.getOrder());
		project.setEmployees(createProjectEmployees(employees));
		
		Session session = null;	
		
		orderVo.getOrder().setPrice(new BigDecimal(price).setScale(2, BigDecimal.ROUND_CEILING));
		orderVo.getOrder().setDateProcessing(new Date());
		try {
			session = projectDao.startTransaction();
			boolean neededEmployeeeAreFree = employeeService.isEmployeesNotBusyForPeriod(session, project.getEmployees(),
					orderVo.getOrder().getDateStart(), orderVo.getOrder().getDateFinish());
			if (neededEmployeeeAreFree) {
				project = projectDao.add(session, project);
				//projectDao.setEmployees(connection, project, employeesIds);
				orderService.setPriceAndDateProcessing(session, orderVo.getOrder());
				commitTransaction(session);
				createAndSendBill(project, orderVo);
			} else {
				rollbackTransaction(session);
				logger.info(MSG_LOGGER_PROJECT_ADD_NO_ISSET_FREE_EMPLOYEE);
				throw new ServiceException(ErrorCode.NOT_ISSET_FREE_EMPLOYEE);
			}
		} catch ( DaoException | ServiceException e) {
			rollbackTransaction(session);
			logger.error(e.getMessage(), e);
			throw new ServiceException(ErrorCode.APPLICATION);
		}

		return project;
	}
	
	/*
	 * Form Map with id qualification as Key and count qualifications as value
	 */
	private Map<Long, Integer> getNeededQualificationsAsIdAndCount(Map<Qualification, Integer> qualifications) {
		Map<Long, Integer> neededQualifications = new HashMap<>(qualifications.size());
		for ( Entry<Qualification, Integer> qualification : qualifications.entrySet() ) {
		    Map.Entry<Qualification, Integer> entry = (Map.Entry<Qualification, Integer>) qualification;
		    neededQualifications.put(entry.getKey().getId(), entry.getValue());
		}
		
		return neededQualifications;
	}
	
	private Set<ProjectEmployee> createProjectEmployees(String[] employeesIds) {
		Set<ProjectEmployee> employees = new HashSet<>();
		for (int i = 0; i < employeesIds.length; i++ ) {
			ProjectEmployee projectEmployee = new ProjectEmployee();
			Employee employee = new Employee();
			employee.setId(Long.valueOf(employeesIds[i]));
			projectEmployee.setEmployee(employee);
			
			employees.add(projectEmployee);
		}
		
		return employees;
	}
	
	/*
	 * Convert string value to long for array of values
	 */
	private Long[] comvertFromStringToLongArray(String[] arrayOfStringValues) {
		int arrayLength = arrayOfStringValues.length;
		Long[] longTypeArray = new Long[arrayLength];
		for ( int i = 0; i < arrayLength; i++ ) {
			longTypeArray[i] = Long.valueOf(arrayOfStringValues[i]);
		}
		
		return longTypeArray;
	}
	
	/*
	 * rollback transaction
	 */
	private void rollbackTransaction(Session session) throws ServiceException {
		try {
			projectDao.rollbackTransaction(session);
		} catch (DaoException e) {
			logger.error(e.getMessage(), e);
			throw new ServiceException(ErrorCode.APPLICATION);
		}
	}
	
	/*
	 * Commit transaction
	 */
	private void commitTransaction(Session session) throws DaoException {
		projectDao.commitTransaction(session);
	}

	@Override
	public ProjectVo getById(String id) throws ServiceException, ObjectNotFoundException {

		ProjectValidation projectValidation = new ProjectValidation();
		if ( !projectValidation.validateId(id)) {
			logger.info(MSG_LOGGER_PROJECT_VIEW_INCORRECT_ID, id);
			throw new ServiceException(ErrorCode.VALIDATION_ID);
		} 
		
		ProjectVo projectDto = new ProjectVo();
		Project project;
		try {
			project = projectDao.getById(Long.valueOf(id));
			projectDto.setProject(project);
			projectDto.setEmployee(getEmployee(project.getEmployees()));
		} catch (DaoException e) {
			logger.error(e.getMessage(), e);
			throw new ServiceException(ErrorCode.APPLICATION);
		} catch (ObjectNotFoundException e) {
			logger.info(e.getMessage());
			throw new ObjectNotFoundException(e.getMessage());
		}
		
		return projectDto;
	}
	
	private Map<Employee, Integer> getEmployee(Set<ProjectEmployee> employeesProxy) {
		Map<Employee, Integer> employees = new HashMap<>();
    	for (ProjectEmployee employee : employeesProxy) {
    		employees.put(employee.getEmployee(), employee.getHours());
    	}
		return employees;
	}

	@Override
	public void updateHours(String id, Employee employee, String hours) throws ServiceException {
		
		ProjectValidation projectValidation = new ProjectValidation();
		
		if ( !projectValidation.validateId(id)) {
			logger.info(MSG_LOGGER_PROJECT_UPDATE_HOURS_INCORRECT_PROJECT_ID, id);
			throw new ServiceException(ErrorCode.VALIDATION_ID);
		} 
		
		projectValidation.validate(hours);
		
		if ( !projectValidation.isValid() ) {
			logger.info(MSG_LOGGER_PROJECT_UPDATE_HOURS_INCORRECT_FIELD, id);
			throw new ServiceException(ErrorCode.VALIDATION, projectValidation.getNotValidField());
		}
		
		Project project = new Project();
		project.setId(Long.valueOf(id));
		try {
			projectDao.updateHours(project, employee, Integer.valueOf(hours));
		} catch ( DaoException e ) {
			logger.error(e.getMessage(), e);
			throw new ServiceException(ErrorCode.APPLICATION);
		}
		
	}

	@Override
	public List<Project> findByTitle(String title) throws ServiceException {
		if ( !ProjectValidation.validateFindedTitle(title) ) {
			logger.info(MSG_LOGGER_PROJECT_FIND_TITLE_NOT_CORRECT_LENGTH, title);
			throw new ServiceException(ErrorCode.TITLE_NOT_CORRECT_LENGTH);
		}
		
		List<Project> project = null;
		try {
			project = projectDao.findByTitle(title);
		} catch (DaoException e) {
			logger.error(e.getMessage(), e);
			throw new ServiceException(ErrorCode.APPLICATION);
		}
		return project;
	}
	
	/**
	 * Create bill's body and send email to customer 
	 * @param project project information
	 * @param orderVo order information
	 */
	private void createAndSendBill(Project project, OrderVo orderVo) {
		StringBuilder body = new StringBuilder();
		ResourceBundle textBundle = ResourceBundle.getBundle(RESOURCE_TEXT_BUNDLE);
		body.append(textBundle.getString(RESOURCE_MAIL_BODY_FIELD_ORDER_TITLE));
		body.append(textBundle.getString(RESOURCE_MAIL_BODY_FIELD_ORDER_NAME) 
										 + orderVo.getOrder().getTitle() + MAIL_BODY_NEWLINE);
		body.append(textBundle.getString(RESOURCE_MAIL_BODY_FIELD_ORDER_DATESTART) 
										 + orderVo.getOrder().getDateStart()+ MAIL_BODY_NEWLINE);
		body.append(textBundle.getString(RESOURCE_MAIL_BODY_FIELD_ORDER_DATEFINISH)
										 + orderVo.getOrder().getDateFinish()+ MAIL_BODY_NEWLINE);
		body.append(textBundle.getString(RESOURCE_MAIL_BODY_FIELD_ORDER_PRICE)
										 + orderVo.getOrder().getPrice()+ MAIL_BODY_NEWLINE);
		try {
			TLSEmail.sendEmail(orderVo.getOrder().getCustomer().getEmail(), textBundle.getString(RESOURCE_EMAIL_SUBJECT), body.toString());
		} catch (UnsupportedEncodingException | MessagingException e) {
			logger.error(MSG_LOGGER_PROJECT_SEND_MAIL, e);
		}
	}
	
}
