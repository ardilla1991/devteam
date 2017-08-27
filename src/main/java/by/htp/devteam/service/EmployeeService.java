package by.htp.devteam.service;

import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import by.htp.devteam.bean.Employee;
import by.htp.devteam.bean.Project;
import by.htp.devteam.bean.Qualification;
import by.htp.devteam.bean.User;
import by.htp.devteam.bean.vo.PagingVo;
import by.htp.devteam.controller.ObjectNotFoundException;
import by.htp.devteam.dao.DaoException;

/**
 * Interface for employee's Service layer.
 * Do business logic including validation and dao exceptions.
 * Select data from storage using DAO object
 * @author julia
 *
 */
public interface EmployeeService {
	
	/**
	 * Get employee record for user loggined in system
	 * @param user User that loggined in system
	 * @return Employee Employee information
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
	 * @param project  Project information
	 * @return Map where employee as key and hours number spending on project as a value
	 * @throws ServiceException  after catching DAOException
	 */
	Map<Employee, Integer> getByProject(Project project) throws ServiceException;
	
	/**
	 * Get Map of qualifications' ids and qualifications' count by selected employees' list.
	 * This method used on create project action when we ckeck 
	 * if selected employees correspond to selected qualifications in order
	 * @param ids employees ids selected in created project
	 * @return Map of qualifications' ids and qualifications' count by selected employees' list
	 * @throws ServiceException  after catching DAOException
	 */
	Map<Long, Integer> getQualificationsIdsAndCountByEmployees(Long[] ids) throws ServiceException;
	
	/**
	 * Check if selected employees are not busy in the period (between date start and date finish).
	 * Used on create project page.
	 * @param connection DB Connection 
	 * @param employeesIds employees ids
	 * @param dateStart date start of project in order
	 * @param dateFinish date finish of project in order
	 * @return if employee is not busy for definite period
	 * @throws DaoException Exception is catched during transaction process
	 */
	boolean isEmployeesNotBusyForPeriod(Connection connection, Long[] employeesIds, Date dateStart, Date dateFinish) 
			throws DaoException;
	
	/**
	 * Add employee
	 * @param name Employee name
	 * @param startWork start date of employee's work
	 * @param qualification Employee qualification
	 * @return employee Employee information
	 * @throws ServiceException When DaoException are catched
	 */
	Employee add(String name, String startWork, String qualification) throws ServiceException;
	
	/**
	 * Get employee by id
	 * @param id employee's id
	 * @return employee Employee information
	 * @throws ServiceException When DaoException are catched or it is validation error
	 * @throws ObjectNotFoundException If employee is don't exist
	 */
	Employee getById(String id) throws ServiceException, ObjectNotFoundException;
	
	/**
	 * Set user for employee
	 * @param connection DB Connection 
	 * @param employee Employee information
	 * @param user User information
	 * @throws ServiceException When DaoException are catched
	 */
	void setUserForEmployee(Connection connection, Employee employee, User user) throws ServiceException;
	
	/**
	 * Check if exist user for employee. If exist we couldn't set user for employee
	 * @param connection DB Connection 
	 * @param employee Employee information
	 * @return boolean  exist user record or not 
	 * @throws DaoException  When SQLException are catched
	 */
	boolean isExistUserForEmployee(Connection connection, Employee employee) throws DaoException;
	
	/**
	 * Get list of all employees in system with their users .
	 * Records are selected according to current page. 
	 * Method checks if page has a correct value. If not - throw exception. 
	 * Also set up parameters to select records ( LIMIT )
	 * @param currPage Current selected page
	 * @return {@link  by.htp.devteam.bean.vo.PagingVo}
	 * @throws ServiceException  after catching DAOException
	 */
	PagingVo<Employee> fetchAll(String currPage) throws ServiceException;
	
	/**
	 * Get list of employees who doesn't have a user record
	 * @return list of employees with not set user record
	 * @throws ServiceException after catching DAOException
	 */
	List<Employee> getListWithNotSetUser() throws ServiceException;
}
