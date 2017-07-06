package by.htp.devteam.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import by.htp.devteam.bean.RoleEnum;
import by.htp.devteam.bean.User;
import by.htp.devteam.controller.ConnectionPool;
import by.htp.devteam.dao.DaoException;
import by.htp.devteam.dao.UserDao;

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
	
}
