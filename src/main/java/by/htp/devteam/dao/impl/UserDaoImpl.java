package by.htp.devteam.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import by.htp.devteam.bean.Customer;
import by.htp.devteam.bean.Employee;
import by.htp.devteam.bean.Qualification;
import by.htp.devteam.bean.UserRole;
import by.htp.devteam.bean.vo.PagingVo;
import by.htp.devteam.bean.vo.UserVo;
import by.htp.devteam.bean.User;
import by.htp.devteam.dao.DaoException;
import by.htp.devteam.dao.UserDao;
import by.htp.devteam.dao.util.ConnectionPool;

import static by.htp.devteam.dao.util.ConstantValue.*;

public final class UserDaoImpl implements UserDao {

	private static final int ID = 1;
	private static final int LOGIN = 2;
	private static final int PASSWORD = 3;
	private static final int ROLE = 4;
	
	public UserDaoImpl() {
		super();
	}
			
	@Override
	public User fetchByCredentials(String login) throws DaoException {
		User user = null;
		try ( Connection dbConnection = ConnectionPool.getConnection();
				PreparedStatement ps = dbConnection.prepareStatement(SQL_USER_FETCH_BY_CREDENTIALS) ) {

			ps.setString(1, login);
			
			user = executeQueryAndGetUserFromResultSet(ps);
		} catch (SQLException e) {
			throw new DaoException(MSG_ERROR_USER_FETCH_BY_CREDENTIALS, e);
		}
		return user;
	}
	
	private User executeQueryAndGetUserFromResultSet(PreparedStatement ps) throws SQLException {
		User user = null;
		try ( ResultSet rs = ps.executeQuery() ) {
			if (rs.next()) {
				user = new User();
				user.setId(rs.getLong(ID));
				user.setLogin(rs.getString(LOGIN));
				user.setPassword(rs.getString(PASSWORD));
				user.setRole(UserRole.valueOf(rs.getString(ROLE)));
			}
		}
		
		return user;
	}

	@Override
	public PagingVo<UserVo> fetchAll(int offset, int countPerPage) throws DaoException {
		PagingVo<UserVo> pagingVo = new PagingVo<>();
		try ( Connection dbConnection = ConnectionPool.getConnection();
				PreparedStatement ps = dbConnection.prepareStatement(SQL_USER_FETCH_ALL_WITH_EMPLOYEE_AND_CUSTOMER); ) {

			ps.setInt(1, offset);
			ps.setInt(2, countPerPage);

			pagingVo = executeQueryAndCreatePagingVoObject(dbConnection, ps);
		} catch (SQLException e) {
			throw new DaoException(MSG_ERROR_USER_LIST, e);
		}
		return pagingVo;
	}
	
	/*
	 * execute query and get total count of users
	 */
	private PagingVo<UserVo> executeQueryAndCreatePagingVoObject(Connection dbConnection, PreparedStatement ps) 
			throws SQLException{
		PagingVo<UserVo> pagingVo = new PagingVo<>();
		pagingVo.setRecords(getUserListFromResultSet(ps));
		try ( Statement st = dbConnection.createStatement();
				ResultSet rsNumebr  = st.executeQuery(SQL_FOUND_ROWS) ) {
			if (rsNumebr.next()) {
				pagingVo.setCountAllRecords(rsNumebr.getInt(1));
			}
		}
		
		return pagingVo;
	}
	
	/*
	 * Create UserVo object with types of role (employee or customer)
	 */
	private List<UserVo> getUserListFromResultSet(PreparedStatement ps) throws SQLException {
		List<UserVo> users = new ArrayList<>();
		try ( ResultSet rs = ps.executeQuery() ) {
			while ( rs.next() ) {
				UserVo userVo = new UserVo();
				User user = new User();
				user.setId(rs.getLong(1));
				user.setLogin(rs.getString(2));
				user.setRole(UserRole.valueOf(rs.getString(4)));
				userVo.setUser(user);
				if ( user.getRole() == UserRole.CUSTOMER ) {
					Customer customer = new Customer();
					customer.setName(rs.getString(6));
					customer.setEmail(rs.getString(8));
					customer.setPhone(rs.getString(9));
					
					userVo.setCustomer(customer);
				} else {
					Employee employee = new Employee();
					employee.setName(rs.getString(6));
					employee.setStartWork(rs.getDate(7));
					
					Qualification qualification = new Qualification();
					qualification.setTitle(rs.getString(11));
					
					employee.setQualification(qualification);
					
					userVo.setEmployee(employee);
				}
				
				users.add(userVo);
			}
		}

		return users;
	}

	@Override
	public User add(Connection connection, User user) throws DaoException {
		User createdUser = user;
		try ( PreparedStatement ps = connection.prepareStatement(SQL_USER_ADD, PreparedStatement.RETURN_GENERATED_KEYS) ) {

			prepareStatementForUser(ps, user);
			ps.executeUpdate();
			try ( ResultSet rs = ps.getGeneratedKeys() ) {
				if (rs.next()) {
					createdUser.setId(rs.getLong(ID));
				}
			}
		} catch (SQLException e) {
			throw new DaoException(MSG_ERROR_EMPLOYEE_ADD, e);
		}
		return createdUser;
	}
	
	private void prepareStatementForUser(PreparedStatement ps, User user) throws SQLException {
		ps.setString(1, user.getLogin());
		ps.setString(2, user.getPassword());
		ps.setString(3, user.getRole().toString());
	}
	
	@Override
	public Connection startTransaction() throws DaoException {
		Connection dbConnection = null;
		try {
			dbConnection = ConnectionPool.getConnection();
			dbConnection.setAutoCommit(false);
		} catch (SQLException e) {
			throw new DaoException(MSG_ERROR_CONNECTION, e);
		}
		return dbConnection;
	}
	
	@Override
	public void rollbackTransaction(Connection connection) throws DaoException {
		try {
			connection.rollback();
			ConnectionPool.returnConnection(connection);
		} catch (SQLException e) {
			throw new DaoException(MSG_ERROR_ROLLBACK, e);
		}
	}
	
	@Override
	public void commitTransaction(Connection connection) throws DaoException {
		try {
			connection.commit();
			ConnectionPool.returnConnection(connection);
		} catch (SQLException e) {
			throw new DaoException(MSG_ERROR_COMMIT, e);
		}
	}

}
