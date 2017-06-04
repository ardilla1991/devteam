package by.htp.devteam.dao.Impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import by.htp.devteam.bean.Employee;
import by.htp.devteam.bean.Qualification;
import by.htp.devteam.controller.Loader;
import by.htp.devteam.dao.EmployeeDao;

import by.htp.devteam.dao.SqlStatementConstantValue;

public class EmployeeDaoImpl implements EmployeeDao{
	
	@Override
	public Employee fetchByCredentials(String login, String password) {
		
		Employee employee = null;
		
		try {
			Connection dbConnector = Loader.LoaderDb();
			
			PreparedStatement ps = dbConnector.prepareStatement(SqlStatementConstantValue.EMPLOYEE_SELECT);
			ps.setString(1, login);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();

			if ( rs.next() ) {
				Long id = rs.getLong(1);
				String name = rs.getString(2);
				String log = rs.getString(3);
				String pass = rs.getString(4);
				Date startWork = rs.getDate(5);
				Long qualification_id = rs.getLong(6);
				int role = rs.getInt(7);
				String qualificationTitle = rs.getString(8);
				Qualification qualification = new Qualification();
				qualification.setId(qualification_id);
				qualification.setTitle(qualificationTitle);
				
				employee = new Employee();
				employee.setId(id);
				employee.setName(name);
				employee.setLogin(log);
				employee.setPassword(pass);
				employee.setStartWork(startWork);
				employee.setQualification(qualification);
				employee.setRole(role);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return employee;
	}
}
