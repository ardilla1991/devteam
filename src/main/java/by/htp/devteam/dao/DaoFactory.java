package by.htp.devteam.dao;

import by.htp.devteam.dao.Impl.CustomerDaoImpl;
import by.htp.devteam.dao.Impl.EmployeeDaoImpl;
import by.htp.devteam.dao.Impl.EmployeeProjectDaoImpl;
import by.htp.devteam.dao.Impl.OrderDaoImpl;
import by.htp.devteam.dao.Impl.OrderQualificationDaoImpl;
import by.htp.devteam.dao.Impl.ProjectDaoImpl;
import by.htp.devteam.dao.Impl.OrderWorkDaoImpl;
import by.htp.devteam.dao.Impl.QualificationDaoImpl;
import by.htp.devteam.dao.Impl.WorkDaoImpl;

public class DaoFactory {

	private static DaoFactory daoFactory = new DaoFactory();
	
	private static CustomerDao customerDao = new CustomerDaoImpl();
	private static EmployeeDao employeeDao = new EmployeeDaoImpl();
	private static EmployeeProjectDao employeeProjectDao = new EmployeeProjectDaoImpl();
	private static OrderDao orderDao = new OrderDaoImpl();
	private static OrderQualificationDao orderQualificationDao = new OrderQualificationDaoImpl();
	private static OrderWorkDao orderWorkDao = new OrderWorkDaoImpl();
	private static ProjectDao projectDao = new ProjectDaoImpl();
	private static QualificationDao qualificationDao = new QualificationDaoImpl();
	private static WorkDao workDao = new WorkDaoImpl();
	
	public static DaoFactory getInstance() {
		return daoFactory;
	}

	public CustomerDao getCustomerDao() {
		return customerDao;
	}

	public EmployeeDao getEmployeeDao() {
		return employeeDao;
	}

	public ProjectDao getProjectDao() {
		return projectDao;
	}

	public OrderWorkDao getOrderWorkDao() {
		return orderWorkDao;
	}

	public QualificationDao getQualificationDao() {
		return qualificationDao;
	}

	public WorkDao getWorkDao() {
		return workDao;
	}

	public EmployeeProjectDao getEmployeeProjectDao() {
		return employeeProjectDao;
	}

	public OrderQualificationDao getOrderQualificationDao() {
		return orderQualificationDao;
	}

	public OrderDao getOrderDao() {
		return orderDao;
	}
	
}
