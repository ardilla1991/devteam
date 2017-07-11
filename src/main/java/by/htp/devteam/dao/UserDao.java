package by.htp.devteam.dao;

import by.htp.devteam.bean.User;
import by.htp.devteam.bean.vo.UserListVo;

/**
 * Interface for user's DAO layer
 * @author julia
 *
 */
public interface UserDao {
	
	/**
	 * Find user by login in system
	 * @param login
	 * @return User
	 * @throws DaoException
	 */
	User fetchByCredentials(String login) throws DaoException;
	
	/**
	 * Get list of all users in system with their types ( customer object or employee object)
	 * @param offset
	 * @param countPerPage
	 * @return Users list according current page 
	 * plus page settings (all pages count, current page number)
	 * @see by.htp.devteam.bean.vo.UserListVo#UserListVo()
	 * @throws DaoException
	 */
	UserListVo fetchAll(int offset, int countPerPage) throws DaoException;
}
