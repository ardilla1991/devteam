package by.htp.devteam.service;

import by.htp.devteam.service.Impl.CustomerServiceImpl;
import by.htp.devteam.service.Impl.EmployeeProjectServiceImpl;
import by.htp.devteam.service.Impl.EmployeeServiceImpl;
import by.htp.devteam.service.Impl.OrderQualificationServiceImpl;
import by.htp.devteam.service.Impl.OrderServiceImpl;
import by.htp.devteam.service.Impl.OrderWorkServiceImpl;
import by.htp.devteam.service.Impl.ProjectServiceImpl;
import by.htp.devteam.service.Impl.ProjectWorkServiceImpl;
import by.htp.devteam.service.Impl.QualificationServiceImpl;
import by.htp.devteam.service.Impl.WorkServiceImpl;

public class ServiceFactory {
	
	private static ServiceFactory serviceFactory = new ServiceFactory();
	
	private static CustomerService customerService = new CustomerServiceImpl();
	private static EmployeeProjectService employeeProjectService = new EmployeeProjectServiceImpl();
	private static EmployeeService employeeService = new EmployeeServiceImpl();
	private static OrderQualificationService orderQualification = new OrderQualificationServiceImpl();
	private static OrderService orderService = new OrderServiceImpl();
	private static OrderWorkService orderwWorkService = new OrderWorkServiceImpl();
	private static ProjectService projectService = new ProjectServiceImpl();
	private static QualificationService qualificationService = new QualificationServiceImpl();
	private static WorkService workService = new WorkServiceImpl();
	
	public static CustomerService getCustomerService() {
		return customerService;
	}

	public static WorkService getWorkService() {
		return workService;
	}

	public static OrderWorkService getOrderWorkService() {
		return orderwWorkService;
	}

	public static ServiceFactory getInstance() {
		return serviceFactory;
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
	
	public static EmployeeProjectService getEmployeeProjectService() {
		return employeeProjectService;
	}

	public static OrderQualificationService getOrderQualification() {
		return orderQualification;
	}

	public static OrderService getOrderService() {
		return orderService;
	}

	public static OrderWorkService getOrderwWorkService() {
		return orderwWorkService;
	}
	
	
}
