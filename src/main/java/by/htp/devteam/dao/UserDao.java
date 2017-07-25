package by.htp.devteam.dao;

import java.sql.Connection;

import by.htp.devteam.bean.Employee;
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
	
	/**
	 * Save user in storage. 
	 * @param connection Connection created in service layer because transaction
	 * @param user user information
	 * @return
	 * @throws DaoException
	 */
	User add(Connection connection, User user) throws DaoException;
	
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
