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

public class UserDaoImpl implements UserDao{

	private final static int ID = 1;
	private final static int LOGIN = 2;
	private final static int PASSWORD = 3;
	private final static int ROLE = 4;
			
	private final static String FETCH_BY_CREDENTIALS = "SELECT e.* FROM user as e "
			+ "WHERE e.login=? AND e.password=?";
	
	@Override
	public User fetchByCredentials(String login, String password) throws DaoException{
		User user = null;
		try ( Connection dbConnection = ConnectionPool.getConnection();
				PreparedStatement ps = dbConnection.prepareStatement(FETCH_BY_CREDENTIALS) ) {

			ps.setString(1, login);
			ps.setString(2, password);
			
			user = getUserFromResultSet(ps);
		} catch (SQLException e) {
			throw new DaoException("sql erro", e);
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
				user.setRole(RoleEnum.valueOf(rs.getString(ROLE)));
			}
		}
		
		return user;
	}
	
}
