package by.htp.devteam.dao.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import by.htp.devteam.bean.Employee;
import by.htp.devteam.bean.Qualification;
import by.htp.devteam.bean.RoleEnum;
import by.htp.devteam.controller.ConnectionPool;
import by.htp.devteam.dao.EmployeeDao;

import by.htp.devteam.dao.SqlStatementConstantValue;

public class EmployeeDaoImpl implements EmployeeDao {
	
	private final int ID = 1;
	private final int NAME = 2;
	private final int LOGIN = 3;
	private final int PASSWORD = 4;
	private final int START_WORK = 5;
	private final int QUALIFICATION_ID = 6;
	private final int ROLE = 7;
	private final int QUALIFICATION_TITLE = 8;
	
	@Override
	public Employee fetchByCredentials(String login, String password) {
		
		Employee employee = null;
		Connection dbConnection = null;
		PreparedStatement ps = null;
		
		try {
			dbConnection = ConnectionPool.getConnection();
		
			ps = dbConnection.prepareStatement(SqlStatementConstantValue.EMPLOYEE_SELECT);
			ps.setString(1, login);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();

			if ( rs.next() ) {
				Qualification qualification = new Qualification();
				qualification.setId(rs.getLong(QUALIFICATION_ID));
				qualification.setTitle(rs.getString(QUALIFICATION_TITLE));
				
				employee = new Employee();
				employee.setId(rs.getLong(ID));
				employee.setName(rs.getString(NAME));
				employee.setLogin(rs.getString(LOGIN));
				employee.setPassword(rs.getString(PASSWORD));
				employee.setStartWork(rs.getDate(START_WORK));
				employee.setQualification(qualification);
				employee.setRole(RoleEnum.valueOf(rs.getString(ROLE)));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps);
			ConnectionPool.close(dbConnection);
		}
		return employee;
	}
	
	private void close(PreparedStatement ps) {
		if ( ps != null ) {
			try {
				ps.close();
			} catch ( SQLException e ) {
				e.printStackTrace();
			}
		}
	}
}
