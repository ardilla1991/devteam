package by.htp.devteam.service;

import by.htp.devteam.bean.Employee;
import by.htp.devteam.bean.User;
import by.htp.devteam.bean.vo.PagingVo;
import by.htp.devteam.bean.vo.UserVo;

/**
 * Interface for user's Service layer.
 * Do business logic including validation and dao exceptions
 * Select data from storage using DAO object
 * @author julia
 *
 */
public interface UserService {
	/**
	 * Check if user has a good credentials. 
	 * Fields login and passwors are validated. If they are not valid throws Exception and stop data processing.
	 * If fields are correct we check if isset login in a storage. If isset then we check its password
	 * @param login User's login
	 * @param password User's password
	 * @return user with good credentials
	 * @throws ServiceException  after catching DAOException
	 */
	User authorise(String login, String password) throws ServiceException;
	
	/**
	 * Get list of all users in system with their types ( customer object or employee object).
	 * Records are selected according to current page. 
	 * Method checks if page has a correct value. If not - throw exception. 
	 * Also set up parameters to select records ( LIMIT )
	 * @param currPage Current selected page
	 * @return {@link  by.htp.devteam.bean.vo.PagingVo}
	 * @throws ServiceException  after catching DAOException
	 */
	PagingVo<UserVo> fetchAll(String currPage) throws ServiceException;
	
	/**
	 * storage user and set use for employee. User will be added if there are not user for employee and employee isset in storage.
	 * @param login User's login
	 * @param password User's password
	 * @param role User's role
	 * @param employee Employee information
	 * @return User object
	 * @throws ServiceException After validation exception or catching DAOException
	 */
	User add(String login, String password, String role, Employee employee) throws ServiceException;

}
