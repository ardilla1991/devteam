package by.htp.devteam.dao;

import by.htp.devteam.dao.impl.CustomerDaoImpl;
import by.htp.devteam.dao.impl.EmployeeDaoImpl;
import by.htp.devteam.dao.impl.OrderDaoImpl;
import by.htp.devteam.dao.impl.ProjectDaoImpl;
import by.htp.devteam.dao.impl.QualificationDaoImpl;
import by.htp.devteam.dao.impl.UserDaoImpl;
import by.htp.devteam.dao.impl.WorkDaoImpl;

public class DaoFactory {

	private static final DaoFactory daoFactory = new DaoFactory();
	
	private static final UserDao userDao = new UserDaoImpl();
	private static final CustomerDao customerDao = new CustomerDaoImpl();
	private static final EmployeeDao employeeDao = new EmployeeDaoImpl();
	private static final OrderDao orderDao = new OrderDaoImpl();
	private static final ProjectDao projectDao = new ProjectDaoImpl();
	private static final QualificationDao qualificationDao = new QualificationDaoImpl();
	private static final WorkDao workDao = new WorkDaoImpl();
	
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

	public QualificationDao getQualificationDao() {
		return qualificationDao;
	}

	public WorkDao getWorkDao() {
		return workDao;
	}

	public OrderDao getOrderDao() {
		return orderDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}
	
}
