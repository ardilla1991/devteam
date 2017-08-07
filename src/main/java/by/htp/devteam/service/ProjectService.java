package by.htp.devteam.service;

import java.util.List;

import by.htp.devteam.bean.Employee;
import by.htp.devteam.bean.Project;
import by.htp.devteam.bean.vo.PagingVo;
import by.htp.devteam.bean.vo.OrderVo;
import by.htp.devteam.bean.vo.ProjectVo;
import by.htp.devteam.controller.ObjectNotFoundExeption;

/**
 * Interface for project's Service layer.
 * Do business logic including validation and loggin exceptions.
 * Select data from storage using DAO object
 * @author julia
 *
 */
public interface ProjectService {

	/**
	 * Get list of all projects. 
	 * Rocords are selected according to current page. 
	 * Method checks if page has a correct value. If not - throw exception. 
	 * Also set up parameters to select records ( LIMIT )
	 * @param currPage Current selected page
	 * @param employee Parameter to get all projects by employee. 
	 * Also this parameter can be null. In this case method retrun all list of projects
	 * @return {@link  by.htp.devteam.bean.vo.PagingVo}
	 * @throws ServiceException  after catching DAOException
	 */
	PagingVo<Project> fetchAll(String currPage, Employee employee) throws ServiceException;
	
	/**
	 * Add project in storage.
	 * Before save method validates fields. 
	 * If not correct input feilds values - throw exception and stop data proccessing. 
	 * After that if not correct method check if selected employees' qualifications are the same 
	 * as the order has  - throw exception and stop data proccessing. 
	 * After that start transaction. the first step method check if selected employee are not busy for order's period.
	 * After that methos save project in storage, set employees, set order price and commit transaction.
	 * @param orderVo {@link by.htp.devteam.bean.vo.OrderVo} oder with list of works and list of qualifications
	 * @param title
	 * @param description
	 * @param employees array of selected employees ids
	 * @param price price for order
	 * @return Project
	 * @throws ServiceException
	 */
	Project add(OrderVo orderVo, String title, String description, String[] employees, String price) 
			throws ServiceException;
	
	/**
	 * Get project with all information : project record plus employee list.
	 * Method checks if project has a correct id's value. If not - throw exception. 
	 * @param id id of project
	 * @return ProjectVo project information with employees that work (works) on this project
	 * @see by.htp.devteam.bean.vo.ProjectVo
	 * @throws ServiceException
	 * @throws ObjectNotFoundExeption
	 */
	ProjectVo getById(String id) throws ServiceException, ObjectNotFoundExeption;
	
	/**
	 * Update hours count spending by employee on the project.
	 * Method checks if project has a correct id's value. If not - throw exception. 
	 * Method checks if project has a correct hours' value. If not - throw exception. 
	 * @param id project id
	 * @param employee 
	 * @param hours how much time employee spend on the progect
	 * @throws ServiceException
	 */
	void updateHours(String id, Employee employee, String hours) throws ServiceException;
	
	/**
	 * Get list of projects are found by title 
	 * Method checks if title has a correct value. If not - throw exception. 
	 * @param title
	 * @return List of projects
	 * @throws ServiceException
	 */
	List<Project> findByTitle(String title) throws ServiceException;
}
