package by.htp.devteam.service;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import by.htp.devteam.bean.Employee;
import by.htp.devteam.bean.Project;
import by.htp.devteam.bean.Qualification;
import by.htp.devteam.bean.User;
import by.htp.devteam.dao.DaoException;

/**
 * Interface for employee's Service layer.
 * Do business logic including validation and loggin exceptions.
 * Select data from storage using DAO object
 * @author julia
 *
 */
public interface EmployeeService {
	
	/**
	 * Get employee record for user loggined in system
	 * @param user User that loggined in system
	 * @return Employee
	 * @throws ServiceException  after catching DAOException
	 */
	Employee getByUser(User user) throws ServiceException;
	
	/**
	 * Get all not busy employees for period by qualifications list.
	 * Method are used in create project page
	 * @param dateStart project data start
	 * @param dateFinish project data finish
	 * @param qualifications Needed qualification for employees
	 * @return list of Employees
	 * @throws ServiceException  after catching DAOException
	 */
	List<Employee> getNotBusyEmployeesForPeriodByQualifications(Date dateStart, Date dateFinish, 
																Set<Qualification> qualifications) 
			throws ServiceException;
	
	/**
	 * Get map of employee and their spending hours on project
	 * @param project 
	 * @return Map where employee as key and hours number spending on project as a value
	 * @throws ServiceException  after catching DAOException
	 */
	Map<Employee, Integer> getByProject(Project project) throws ServiceException;
	
	/**
	 * Get Map of qualifications' ids and qualifications' count by selected employees' list.
	 * This method used on create project action when we ckeck 
	 * if selected employees correspond to selected qualifications in order
	 * @param employeesIds selected in created project
	 * @return Map of qualifications' ids and qualifications' count by selected employees' list
	 * @throws ServiceException  after catching DAOException
	 */
	Map<Long, Integer> getQualificationsIdsAndCountByEmployees(Long[] ids) throws ServiceException;
	
	/**
	 * Check if selected employees are not busy in the period (between date start and date finish).
	 * Used on create project page.
	 * @param connection
	 * @param ids employees' ids
	 * @param dateStart date start of project in order
	 * @param dateFinish date finish of project in order
	 * @return if employee is not busy for definite period
	 * @throws DaoException Exception is catched during transaction proccess
	 */
	boolean isEmployeesNotBusyForPeriod(Connection connection, Long[] employeesIds, Date dateStart, Date dateFinish) 
			throws DaoException;
	
	Employee add(String name, String startWork, String qualification) throws ServiceException;
}
