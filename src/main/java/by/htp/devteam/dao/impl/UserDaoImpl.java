package by.htp.devteam.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import by.htp.devteam.bean.Customer;
import by.htp.devteam.bean.RoleEnum;
import by.htp.devteam.bean.User;
import by.htp.devteam.controller.ConnectionPool;
import by.htp.devteam.dao.UserDao;

public class UserDaoImpl implements UserDao{

	private final int ID = 1;
	private final int LOGIN = 2;
	private final int PASSWORD = 3;
	private final int ROLE = 4;
			
	private final String FETCH_BY_CREDENTIALS = "SELECT e.* FROM user as e "
			+ "WHERE e.login=? AND e.password=?";
	
	@Override
	public User fetchByCredentials(String login, String password) {
		User user = null;
		try ( Connection dbConnection = ConnectionPool.getConnection();
				PreparedStatement ps = dbConnection.prepareStatement(FETCH_BY_CREDENTIALS) ) {

			ps.setString(1, login);
			ps.setString(2, password);
			try ( ResultSet rs = ps.executeQuery() ) {
				user = getUserFromResultSet(rs);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	private User getUserFromResultSet(ResultSet rs) throws SQLException {
		User user = null;
		if ( rs.next() ) {
			user = new User();
			user.setId(rs.getLong(ID));
			user.setLogin(rs.getString(LOGIN));
			user.setRole(RoleEnum.valueOf(rs.getString(ROLE)));
		}
		
		return user;
	}
	
}
