package by.htp.devteam.service;

import by.htp.devteam.service.impl.CustomerServiceImpl;
import by.htp.devteam.service.impl.EmployeeServiceImpl;
import by.htp.devteam.service.impl.OrderServiceImpl;
import by.htp.devteam.service.impl.ProjectServiceImpl;
import by.htp.devteam.service.impl.QualificationServiceImpl;
import by.htp.devteam.service.impl.UserServiceImpl;
import by.htp.devteam.service.impl.WorkServiceImpl;

/**
 * Factory for creating Service object. Implements Singleton pattern
 * @author julia
 *
 */
public final class ServiceFactory {
	
	/** The single factory's object */
	private static final ServiceFactory serviceFactory = new ServiceFactory();
	
	/** The single customer's service object */
	private static final UserService userService = new UserServiceImpl();
	
	/** The single user's service object */
	private static final CustomerService customerService = new CustomerServiceImpl();
	
	/** The single employee's service object */
	private static final EmployeeService employeeService = new EmployeeServiceImpl();
	
	/** The single order's service object */
	private static final OrderService orderService = new OrderServiceImpl();
	
	/** The single project's service object */
	private static final ProjectService projectService = new ProjectServiceImpl();
	
	/** The single qualification's service object */
	private static final QualificationService qualificationService = new QualificationServiceImpl();
	
	/** The single work's service object */
	private static final WorkService workService = new WorkServiceImpl();
	
	private ServiceFactory() {
		super();
	}
	
	public static ServiceFactory getInstance() {
		return serviceFactory;
	}
	
	public CustomerService getCustomerService() {
		return customerService;
	}

	public WorkService getWorkService() {
		return workService;
	}

	public EmployeeService getEmployeeService() {
		return employeeService;
	}

	public QualificationService getQualificationService() {
		return qualificationService;
	}

	public ProjectService getProjectService() {
		return projectService;
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public UserService getUserService() {
		return userService;
	}

}
