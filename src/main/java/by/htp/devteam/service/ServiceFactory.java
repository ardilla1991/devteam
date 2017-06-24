package by.htp.devteam.service;

import by.htp.devteam.service.Impl.CustomerServiceImpl;
import by.htp.devteam.service.Impl.EmployeeServiceImpl;
import by.htp.devteam.service.Impl.OrderServiceImpl;
import by.htp.devteam.service.Impl.ProjectServiceImpl;
import by.htp.devteam.service.Impl.QualificationServiceImpl;
import by.htp.devteam.service.Impl.WorkServiceImpl;

public class ServiceFactory {
	
	private static final ServiceFactory serviceFactory = new ServiceFactory();
	
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

}
