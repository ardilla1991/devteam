package by.htp.devteam.service;

import by.htp.devteam.bean.User;
import by.htp.devteam.bean.vo.UserListVo;

/**
 * Interface for user's Service layer.
 * Do business logic including validation and loggin exceptions
 * Select data from storage using DAO object
 * @author julia
 *
 */
public interface UserService {
	/**
	 * Check if user has a good credentials. 
	 * Fields login and passwors are validated. If they are not valid throws Exception and stop data processing.
	 * If fields are correct we check if isset login in a storage. If isset then we check its password
	 * @param login
	 * @param password
	 * @return user with good credentials
	 * @throws ServiceException  after catching DAOException
	 */
	User authorise(String login, String password) throws ServiceException;
	
	/**
	 * Get list of all users in system with their types ( customer object or employee object).
	 * Rocords are selected according to current page. 
	 * Method checks if page has a correct value. If not - throw exception. 
	 * Also set up parameters to select records ( LIMIT )
	 * @param currPage Current selected page
	 * @return {@link  by.htp.devteam.bean.vo.UserListVo}
	 * @throws ServiceException  after catching DAOException
	 */
	UserListVo fetchAll(String currPage) throws ServiceException;
}
