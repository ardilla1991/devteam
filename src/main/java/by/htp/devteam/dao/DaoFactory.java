package by.htp.devteam.dao;

import by.htp.devteam.dao.impl.CustomerDaoImpl;
import by.htp.devteam.dao.impl.EmployeeDaoImpl;
import by.htp.devteam.dao.impl.OrderDaoImpl;
import by.htp.devteam.dao.impl.ProjectDaoImpl;
import by.htp.devteam.dao.impl.QualificationDaoImpl;
import by.htp.devteam.dao.impl.UserDaoImpl;
import by.htp.devteam.dao.impl.WorkDaoImpl;

/**
 * Factory for creating DAO object. Implements Singleton pattern
 * @author julia
 *
 */
public final class DaoFactory {

	/** The single factory's object */
	private static final DaoFactory daoFactory = new DaoFactory();
	
	/** The single customer's dao object */
	private static final CustomerDao customerDao = new CustomerDaoImpl();
	
	/** The single employee's dao object */
	private static final EmployeeDao employeeDao = new EmployeeDaoImpl();
	
	/** The single project's dao object */
	private static final ProjectDao projectDao = new ProjectDaoImpl();
	
	/** The single qualification's dao object */
	private static final QualificationDao qualificationDao = new QualificationDaoImpl();
	
	/** The single work's dao object */
	private static final WorkDao workDao = new WorkDaoImpl();
	
	/** The single order's dao object */
	private static final OrderDao orderDao = new OrderDaoImpl();
	
	/** The single user's dao object */
	private static final UserDao userDao = new UserDaoImpl();
	
	private DaoFactory() {
		super();
	}
	
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
