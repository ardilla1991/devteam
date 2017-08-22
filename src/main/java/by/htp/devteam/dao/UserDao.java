package by.htp.devteam.dao;

import java.sql.Connection;

import by.htp.devteam.bean.User;
import by.htp.devteam.bean.vo.PagingVo;
import by.htp.devteam.bean.vo.UserVo;

/**
 * Interface for user's DAO layer
 * @author julia
 *
 */
public interface UserDao {
	
	/**
	 * Find user by login in system
	 * @param login user's login
	 * @return User User information
	 * @throws DaoException When SQLException are catched
	 */
	User fetchByCredentials(String login) throws DaoException;
	
	/**
	 * Get list of all users in system with their types ( customer object or employee object)
	 * @param offset Offset to get list
	 * @param countPerPage Count records per page
	 * @return Users list according current page 
	 * plus page settings (all pages count, current page number)
	 * @see by.htp.devteam.bean.vo.PagingVo#PagingVo()
	 * @throws DaoException When SQLException are catched
	 */
	PagingVo<UserVo> fetchAll(int offset, int countPerPage) throws DaoException;
	
	/**
	 * Save user in storage. 
	 * @param connection Connection created in service layer because transaction
	 * @param user user information
	 * @return User information
	 * @throws DaoException When SQLException are catched
	 */
	User add(Connection connection, User user) throws DaoException;
	
	/**
	 * Get connection from pool and start transaction
	 * @return Connection DB Connection
	 * @throws DaoException When SQLException are catched
	 */
	Connection startTransaction() throws DaoException;
	
	/**
	 * Rollback transaction and return connection in pool
	 * @param connection DB Connection
	 * @throws DaoException When SQLException are catched
	 */
	void rollbackTransaction(Connection connection) throws DaoException;
	
	/**
	 * Commit transaction and return connection in pool
	 * @param connection DB Connection
	 * @throws DaoException When SQLException are catched
	 */
	void commitTransaction(Connection connection) throws DaoException;

}
