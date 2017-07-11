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
import by.htp.devteam.bean.Order;
import by.htp.devteam.bean.Project;
import by.htp.devteam.bean.Qualification;
import by.htp.devteam.bean.RoleEnum;
import by.htp.devteam.bean.User;
import by.htp.devteam.bean.dto.ProjectListVo;
import by.htp.devteam.bean.dto.UserListVo;
import by.htp.devteam.bean.dto.UserVo;
import by.htp.devteam.dao.DaoException;
import by.htp.devteam.dao.UserDao;
import by.htp.devteam.dao.util.ConnectionPool;

import static by.htp.devteam.dao.util.ConstantValue.*;

public class UserDaoImpl implements UserDao{

	private final static int ID = 1;
	private final static int LOGIN = 2;
	private final static int PASSWORD = 3;
	private final static int ROLE = 4;
			

	
	@Override
	public User fetchByCredentials(String login) throws DaoException{
		User user = null;
		try ( Connection dbConnection = ConnectionPool.getConnection();
				PreparedStatement ps = dbConnection.prepareStatement(SQL_USER_FETCH_BY_CREDENTIALS) ) {

			ps.setString(1, login);
			//ps.setString(2, password);
			
			user = getUserFromResultSet(ps);
		} catch (SQLException e) {
			throw new DaoException(MSG_ERROR_USER_FETCH_BY_CREDENTIALS, e);
		}
		return user;
	}
	
	private User getUserFromResultSet(PreparedStatement ps) throws SQLException {
		User user = null;
		try ( ResultSet rs = ps.executeQuery() ) {
			if ( rs.next() ) {
				user = new User();
				user.setId(rs.getLong(ID));
				user.setLogin(rs.getString(LOGIN));
				user.setPassword(rs.getString(PASSWORD));
				user.setRole(RoleEnum.valueOf(rs.getString(ROLE)));
			}
		}
		
		return user;
	}

	@Override
	public UserListVo fetchAll(int offset, int countPerPage) throws DaoException {
		UserListVo userListVo = new UserListVo();
		try ( Connection dbConnection = ConnectionPool.getConnection();
				PreparedStatement ps = dbConnection.prepareStatement(SQL_USER_FETCH_ALL_WITH_EMPLOYEE_AND_CUSTOMER); ) {

			ps.setInt(1, offset);
			ps.setInt(2, countPerPage);

			userListVo = createUserListVoObject(dbConnection, ps);
		} catch (SQLException e) {
			throw new DaoException(MSG_ERROR_USER_LIST, e);
		}
		return userListVo;
	}
	
	private UserListVo createUserListVoObject(Connection dbConnection, PreparedStatement ps) throws SQLException{
		UserListVo userListVo = new UserListVo();
		userListVo.setUsers(getUserListFromResultSet(ps));
		try ( Statement st = dbConnection.createStatement();
				ResultSet rsNumebr  = st.executeQuery(SQL_FOUND_ROWS) ) {
			if ( rsNumebr.next() ) {
				userListVo.setCountRecords(rsNumebr.getInt(1));
			}
		}
		
		return userListVo;
	}
	
	private List<UserVo> getUserListFromResultSet(PreparedStatement ps) throws SQLException {
		List<UserVo> users = new ArrayList<UserVo>();
		try ( ResultSet rs = ps.executeQuery() ) {
			while ( rs.next() ) {
				UserVo userVo = new UserVo();
				User user = new User();
				user.setId(rs.getLong(1));
				user.setLogin(rs.getString(2));
				user.setRole(RoleEnum.valueOf(rs.getString(4)));
				userVo.setUser(user);
				if ( user.getRole() == RoleEnum.CUSTOMER ) {
					Customer customer = new Customer();
					customer.setName(rs.getString(6));
					
					userVo.setCustomer(customer);
				} else {
					Employee employee = new Employee();
					employee.setName(rs.getString(6));
					
					Qualification qualification = new Qualification();
					qualification.setTitle(rs.getString(8));
					
					employee.setQualification(qualification);
					
					userVo.setEmployee(employee);
				}
				
				users.add(userVo);
			}
		}

		return users;
	}
	
}
