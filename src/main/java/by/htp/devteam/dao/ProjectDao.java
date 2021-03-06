package by.htp.devteam.dao;

import java.sql.Connection;
import java.util.List;

import by.htp.devteam.bean.Employee;
import by.htp.devteam.bean.Project;
import by.htp.devteam.bean.vo.PagingVo;
import by.htp.devteam.controller.ObjectNotFoundException;

/**
 * Interface for project's DAO layer
 * @author julia
 *
 */
public interface ProjectDao {

	/**
	 * Get list of all projects in system
	 * @param offset Offset to get list
	 * @param countPerPage Count records per page
	 * @param employee Employee parameter if we get all projects on which employee work or worked.
	 * Could be null if we get all projects (manager role)
	 * @return Projects list according current page 
	 * plus page settings (all pages count, current page number)
	 * @see by.htp.devteam.bean.vo.PagingVo#PagingVo()
	 * @throws DaoException When SQLException are catched
	 */
	PagingVo<Project> fetchAll(int offset, int countPerPage, Employee employee) throws DaoException;
	
	/**
	 * Add project.
	 * @param connection Connection created in service layer because of transaction
	 * @param project Project information
	 * @return created project
	 * @throws DaoException When SQLException are catched
	 */
	Project add(Connection connection, Project project) throws DaoException;
	
	/**
	 * Set employees for project
	 * @param connection Connection created in service layer because transaction
	 * @param project Project information
	 * @param employeeIds employees' ids
	 * @throws DaoException When SQLException are catched
	 */
	void setEmployees(Connection connection, Project project, Long[] employeeIds) throws DaoException;
	
	/**
	 * Get project by id
	 * @param id project id
	 * @return project information
	 * @throws DaoException When SQLException are catched
	 * @throws ObjectNotFoundException When project not found 
	 */
	Project getById(Long id) throws DaoException, ObjectNotFoundException;
	
	/**
	 * Update hours for the project spending by employee
	 * @param project Project information
	 * @param employee Employee information
	 * @param hours how much time employee spend on the project
	 * @throws DaoException When SQLException are catched
	 */
	void updateHours(Project project, Employee employee, int hours) throws DaoException;
	
	/**
	 * Get list of projects are found by title 
	 * @param title Title for search
	 * @return Projects list
	 * @throws DaoException When SQLException are catched
	 */
	List<Project> findByTitle(String title) throws DaoException;
	
	/**
	 * Get connection from pool and start transaction
	 * @return Connection DB Connection
	 * @throws DaoException When SQLException are catched
	 */
	Connection startTransaction() throws DaoException;
	
	/**
	 * Rollback tarnsaction and return connection in pool
	 * @param connection DB Connection
	 * @throws DaoException When SQLException are catched
	 */
	void rollbackTransaction(Connection connection) throws DaoException;
	
	/**
	 * Commit ttansaction and return connection in pool
	 * @param connection DB Connection
	 * @throws DaoException When SQLException are catched
	 */
	void commitTransaction(Connection connection) throws DaoException;
}
