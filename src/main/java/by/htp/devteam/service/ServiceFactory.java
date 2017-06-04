package by.htp.devteam.service;

import by.htp.devteam.service.Impl.CustomerServiceImpl;
import by.htp.devteam.service.Impl.EmployeeServiceImpl;
import by.htp.devteam.service.Impl.ProjectServiceImpl;
import by.htp.devteam.service.Impl.ProjectWorkServiceImpl;
import by.htp.devteam.service.Impl.QualificationServiceImpl;
import by.htp.devteam.service.Impl.WorkServiceImpl;

public class ServiceFactory {
	
	private static ServiceFactory serviceFactory = new ServiceFactory();
	
	private static CustomerService customerService = new CustomerServiceImpl();
	private static EmployeeService employeeService = new EmployeeServiceImpl();
	private static ProjectService projectService = new ProjectServiceImpl();
	private static WorkService workService = new WorkServiceImpl();
	private static ProjectWorkService projectWorkService = new ProjectWorkServiceImpl();
	private static QualificationService qualificationService = new QualificationServiceImpl();
	
	public static CustomerService getCustomerService() {
		return customerService;
	}

	public static WorkService getWorkService() {
		return workService;
	}

	public static ProjectWorkService getProjectWorkService() {
		return projectWorkService;
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
	
	
}
