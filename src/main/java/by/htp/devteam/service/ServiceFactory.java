package by.htp.devteam.service;

import by.htp.devteam.service.impl.CustomerServiceImpl;
import by.htp.devteam.service.impl.EmployeeServiceImpl;
import by.htp.devteam.service.impl.OrderServiceImpl;
import by.htp.devteam.service.impl.ProjectServiceImpl;
import by.htp.devteam.service.impl.QualificationServiceImpl;
import by.htp.devteam.service.impl.UserServiceImpl;
import by.htp.devteam.service.impl.WorkServiceImpl;

public class ServiceFactory {
	
	private static final ServiceFactory serviceFactory = new ServiceFactory();
	
	private static final UserService userService = new UserServiceImpl();
	private static final CustomerService customerService = new CustomerServiceImpl();
	private static final EmployeeService employeeService = new EmployeeServiceImpl();
	private static final OrderService orderService = new OrderServiceImpl();
	private static final ProjectService projectService = new ProjectServiceImpl();
	private static final QualificationService qualificationService = new QualificationServiceImpl();
	private static final WorkService workService = new WorkServiceImpl();
	
	private ServiceFactory() { }
	
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
