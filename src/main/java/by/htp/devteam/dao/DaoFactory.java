package by.htp.devteam.dao;

import by.htp.devteam.dao.Impl.CustomerDaoImpl;
import by.htp.devteam.dao.Impl.EmployeeDaoImpl;
import by.htp.devteam.dao.Impl.ProjectDaoImpl;
import by.htp.devteam.dao.Impl.ProjectWorkDaoImpl;
import by.htp.devteam.dao.Impl.QualificationDaoImpl;
import by.htp.devteam.dao.Impl.WorkDaoImpl;

public class DaoFactory {

	private static DaoFactory daoFactory = new DaoFactory();
	
	private static CustomerDao customerDao = new CustomerDaoImpl();
	private static EmployeeDao employeeDao = new EmployeeDaoImpl();
	private static ProjectDao projectDao = new ProjectDaoImpl();
	private static ProjectWorkDao projectWorkDao = new ProjectWorkDaoImpl();
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

	public ProjectWorkDao getProjectWorkDao() {
		return projectWorkDao;
	}

	public QualificationDao getQualificationDao() {
		return qualificationDao;
	}

	public WorkDao getWorkDao() {
		return workDao;
	}
	
	
}
