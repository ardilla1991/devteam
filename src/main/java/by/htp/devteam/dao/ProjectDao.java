package by.htp.devteam.dao;

import java.sql.Connection;
import java.util.List;

import by.htp.devteam.bean.Employee;
import by.htp.devteam.bean.Project;
import by.htp.devteam.bean.vo.PagingVo;
import by.htp.devteam.controller.ObjectNotFoundExeption;

/**
 * Interface for project's DAO layer
 * @author julia
 *
 */
public interface ProjectDao {

	/**
	 * Get list of all projects in system
	 * @param offset
	 * @param countPerPage
	 * @param employee Employee parameter if we get all projects on which employee work or worked.
	 * Could be null if we get all projects (manager role)
	 * @return Projects list according current page 
	 * plus page settings (all pages count, current page number)
	 * @see by.htp.devteam.bean.vo.PagingVo#PagingVo()
	 * @throws DaoException
	 */
	PagingVo<Project> fetchAll(int offset, int countPerPage, Employee employee) throws DaoException;
	
	/**
	 * Add project.
	 * @param connection Connection created in service layer because transaction
	 * @param project Project information
	 * @return created project
	 * @throws DaoException
	 */
	Project add(Connection connection, Project project) throws DaoException;
	
	/**
	 * Set employees for project
	 * @param connection Connection created in service layer because transaction
	 * @param project
	 * @param employeeIds employees' ids
	 * @throws DaoException
	 */
	void setEmployees(Connection connection, Project project, Long[] employeeIds) throws DaoException;
	
	/**
	 * Get project by id
	 * @param id project id
	 * @return project information
	 * @throws DaoException
	 * @throws ObjectNotFoundExeption
	 */
	Project getById(Long id) throws DaoException, ObjectNotFoundExeption;
	
	/**
	 * Update hours for the project spending by employee
	 * @param project
	 * @param employee
	 * @param hours how much time employee spend on the project
	 * @throws DaoException
	 */
	void updateHours(Project project, Employee employee, int hours) throws DaoException;
	
	/**
	 * Get list of projects are found by title 
	 * @param title
	 * @return Projects' list
	 * @throws DaoException
	 */
	List<Project> findByTitle(String title) throws DaoException;
	
	/**
	 * Get connection from pool and start transaction
	 * @return Connection
	 * @throws DaoException
	 */
	Connection startTransaction() throws DaoException;
	
	/**
	 * Rollback tarnsaction and return connection in pool
	 * @param connection
	 * @throws DaoException
	 */
	void rollbackTransaction(Connection connection) throws DaoException;
	
	/**
	 * Commit ttansaction and return connection in pool
	 * @param connection
	 * @throws DaoException
	 */
	void commitTransaction(Connection connection) throws DaoException;
}
