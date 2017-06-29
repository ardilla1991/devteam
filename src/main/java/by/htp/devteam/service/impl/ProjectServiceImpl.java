package by.htp.devteam.service.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import by.htp.devteam.bean.Order;
import by.htp.devteam.bean.Project;
import by.htp.devteam.bean.Qualification;
import by.htp.devteam.bean.dto.OrderDto;
import by.htp.devteam.bean.dto.ProjectListDto;
import by.htp.devteam.dao.DaoException;
import by.htp.devteam.dao.DaoFactory;
import by.htp.devteam.dao.EmployeeDao;
import by.htp.devteam.dao.OrderDao;
import by.htp.devteam.dao.ProjectDao;
import by.htp.devteam.service.ProjectService;
import by.htp.devteam.service.ServiceException;
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
	public ProjectListDto fetchAll(String currPage) throws ServiceException{
		int countPerPage = SettingConstantValue.COUNT_PER_PAGE;
		int currPageValue = 0;
		
		currPageValue = ( currPage == null 
					  ? SettingConstantValue.START_PAGE 
					  : Integer.valueOf(currPage) );
		
		if ( currPageValue == 0 )
			throw new ServiceException("page not found");
		
		int offset = (currPageValue - 1 ) * countPerPage;
			
		ProjectListDto projectDto = projectDao.fetchAll(offset, countPerPage);

		int countPages = (int) Math.ceil(projectDto.getCountRecords() * 1.0 / countPerPage);
		projectDto.setCountPages(countPages);
		projectDto.setCurrPage(currPageValue);
		
		return projectDto;
	}

	@Override
	public Project add(OrderDto orderDto, String title, String description, String[] employees, String price) throws ServiceException {
		Long[] employeesIds = comvertFromStringToLongArray(employees);
		System.out.println("title=" +Validator.isEmpty(title));
		System.out.println("dwescr=" + Validator.isEmpty(description));
		Project project = null;
		if ( Validator.isEmpty(title) == false && Validator.isEmpty(description) == false 
				&& isCheckedNeededQualifications(orderDto.getQualifications(), employeesIds) 
				&& Validator.isEmpty(price) == false && Validator.checkBigDecimal(price) ) {
			project = new Project();
			project.setTitle(title);
			project.setDescription(description);
			project.setOrder(orderDto.getOrder());
			Connection connection = null;
			try {
				connection = projectDao.startTransaction();
				boolean neededEmployeeeAreFree = true;
				//boolean neededEmployeeeAreFree = employeeDao.isEmployeesFreeFroPeriod(connection, order.getOrder().getDateStart(), order.getOrder().getDateFinish());
				if ( neededEmployeeeAreFree ) {
					project = projectDao.add(connection, project);
					projectDao.addEmployees(connection, project, employeesIds);
					Order order = orderDto.getOrder();
					order.setPrice(new BigDecimal(price).setScale(2, BigDecimal.ROUND_CEILING));
					orderDao.setPrice(connection, order);
					commitTransaction(connection);
				} else {
					rollbackTransaction(connection);
				}
			} catch (DaoException e) {
				rollbackTransaction(connection);
				e.printStackTrace();
			}
		} else {
			
			throw new ServiceException("Title or description is empty");
		}
		return null;
	}
	
	private boolean isCheckedNeededQualifications(Map<Qualification, Integer> qualificationsInOrder, Long[] employeesIds) {
		System.out.println("check checkboxes");
		Map<Long, Integer> neededQualifications = new HashMap<Long, Integer>(qualificationsInOrder.size()); 
		
		for ( Entry<Qualification, Integer> qualification : qualificationsInOrder.entrySet() ) {
		    Map.Entry<Qualification, Integer> entry = (Map.Entry<Qualification, Integer>) qualification;
		    System.out.println(entry.getKey().getId() + "=");
		    System.out.println(entry.getValue());
		    neededQualifications.put(entry.getKey().getId(), entry.getValue());
		}
		
		System.out.println("ccccc");
		boolean isCheched = false;
		try {
			Map<Long, Integer> qualificationCountByEmployees = employeeDao.getQualificationsCountByEmployees(employeesIds);
			
			for ( Entry<Long, Integer> qualification : qualificationCountByEmployees.entrySet() ) {
				Map.Entry<Long, Integer> entry = (Map.Entry<Long, Integer>) qualification;
			    System.out.println(entry.getKey() + "=");
			    System.out.print(entry.getValue());
			}
			
			
			isCheched = neededQualifications.equals(qualificationCountByEmployees);
		} catch (DaoException e) {
			e.printStackTrace();
			System.out.println("sql error");
		}
		
		return isCheched;
	}
	
	private Long[] comvertFromStringToLongArray(String[] arrayOfStringValues) {
		System.out.println(arrayOfStringValues);
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
	

}
