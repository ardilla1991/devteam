package by.htp.devteam.dao;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import by.htp.devteam.bean.Employee;
import by.htp.devteam.bean.Project;
import by.htp.devteam.bean.Qualification;
import by.htp.devteam.bean.User;
import by.htp.devteam.bean.vo.PagingVo;
import by.htp.devteam.controller.ObjectNotFoundException;

/**
 * Interface for employee's DAO layer
 * @author julia
 *
 */
public interface EmployeeDao {

	/**
	 * Get employee by user
	 * @param user User information
	 * @return Employee Employee record
	 * @throws DaoException When SQLException are catched
	 */
	Employee getByUser(User user) throws DaoException;
	
	/**
	 * Get all not busy employees with needed qualifications in the period (between date start and date finish).
	 * Used on create project page.
	 * @param dateStart date start of project in order
	 * @param dateFinish date finish of project in order
	 * @param qualifications qualifications list in order
	 * @return list of employees
	 * @throws DaoException When SQLException are catched
	 */
	List<Employee> getFreeEmployeesForPeriod(Date dateStart, Date dateFinish, Set<Qualification> qualifications) 
			throws DaoException;
	
	/**
	 * Check if selected employees are not busy in the period (between date start and date finish).
	 * Used on create project page.
	 * @param connection DB Connection 
	 * @param ids employees' ids
	 * @param dateStart date start of project in order
	 * @param dateFinish date finish of project in order
	 * @return if employee is not busy for definite period
	 * @throws DaoException When SQLException are catched
	 */
	boolean isEmployeesNotBusyForPeriod(Connection connection, Long[] ids, Date dateStart, Date dateFinish) 
			throws DaoException;
	
	/**
	 * Get Map of qualifications' ids and qualifications' count by selected employees' list.
	 * This method used on create project action when we check 
	 * if selected employees correspond to selected qualifications in order
	 * @param employeesIds selected in created project
	 * @return Map of qualifications' ids and qualifications' count by selected employees' list
	 * @throws DaoException When SQLException are catched
	 */
	Map<Long, Integer> getQualificationsIdsAndCountByEmployees(Long[] employeesIds) throws DaoException;
	
	
	/**
	 * Get map of employees and their spending time to work on project. Used on project's page
	 * @param project Project information
	 * @return map of employee as key and spending hours on project as value
	 * @throws DaoException When SQLException are catched
	 */
	Map<Employee, Integer> getEmployeesAndSpendingHoursByProject(Project project) throws DaoException;
	
	/**
	 * Storage employee in DB
	 * @param employee Employee data
	 * @return employee Stored employee
	 * @throws DaoException When SQLException are catched
	 */
	Employee add(Employee employee) throws DaoException;
	
	/**
	 * Get employee by id
	 * @param id employee's id
	 * @return employee Employee record
	 * @throws DaoException When SQLException are catched
	 * @throws ObjectNotFoundException When user is not exist
	 */
	Employee getById(Long id) throws DaoException, ObjectNotFoundException;
	
	/**
	 * Set user for employee
	 * @param connection DB Connection 
	 * @param employee Employee information
	 * @param user User information
	 * @throws DaoException When SQLException are catched
	 */
	void setUserForEmployee(Connection connection, Employee employee, User user) throws DaoException;
	
	/**
	 * Check if exist user for employee. If exist we couldn't set user for employee
	 * @param connection DB Connection 
	 * @param employee Employee information
	 * @return boolean exist user record or not 
	 * @throws DaoException When SQLException are catched
	 */
	boolean isExistUserForEmployee(Connection connection, Employee employee) throws DaoException;
	
	/**
	 * Get list of all employees in system with their user record
	 * @param offset Offset to get list
	 * @param countPerPage Count records per page
	 * @return Employee list according current page 
	 * plus page settings (all pages count, current page number)
	 * @see by.htp.devteam.bean.vo.PagingVo#PagingVo()
	 * @throws DaoException When SQLException are catched
	 */
	PagingVo<Employee> fetchAll(int offset, int countPerPage) throws DaoException;
	
	/**
	 * Get list of employees who doesn't have a user record
	 * @return list of employees with not set user record
	 * @throws DaoException When SQLException are catched
	 */
	List<Employee> getListWithNotSetUser() throws DaoException;
}
