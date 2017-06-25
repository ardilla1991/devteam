package by.htp.devteam.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import by.htp.devteam.bean.Employee;
import by.htp.devteam.bean.Qualification;
import by.htp.devteam.bean.User;
import by.htp.devteam.controller.ConnectionPool;
import by.htp.devteam.dao.EmployeeDao;

public class EmployeeDaoImpl extends CommonDao implements EmployeeDao {
	
	private final int ID = 1;
	private final int NAME = 2;
	private final int START_WORK = 5;
	private final int QUALIFICATION_ID = 6;
	private final int QUALIFICATION_TITLE = 7;
	
	private final String FETCH_BY_CREDENTIALS = "SELECT e.*, q.title "
			+ "FROM employee as e JOIN qualification as q ON e.qualification_id=q.id "
			+ "WHERE e.login=? AND e.password=?";
	
	private final String GET_BY_USER = "SELECT e.* FROM employee as e "
			+ "WHERE e.user_id=?";
	
	@Override
	public Employee getEmployeeByUser(User user) {
		Employee employee = null;	
		try ( Connection dbConnection = ConnectionPool.getConnection(); 
				PreparedStatement ps = dbConnection.prepareStatement(GET_BY_USER); ) {

			ps.setLong(1, user.getId());
			try ( ResultSet rs = ps.executeQuery() ) {
				employee = getEmployeeFromResultSet(rs);
				System.out.println(employee);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employee;
	}
	
	public List<Employee> fetchAll() {
		List<Employee> employees = new ArrayList<Employee>();
		
		try ( Connection dbConnection = ConnectionPool.getConnection();
				Statement st = dbConnection.createStatement(); 
				ResultSet rs = st.executeQuery(FETCH_BY_CREDENTIALS);) {

			employees = getEmployeeListFromResultSet(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employees;
	}
	
	private List<Employee> getEmployeeListFromResultSet(ResultSet rs) throws SQLException {
		List<Employee> employees = new ArrayList<Employee>();
		while ( rs.next() ) {
			employees.add(createEmployeeFromResultSet(rs));
		}
		
		return employees;
	}
	
	private Employee getEmployeeFromResultSet(ResultSet rs) throws SQLException {
		Employee employee = null;
		if ( rs.next() ) {
			employee = createEmployeeFromResultSet(rs);
		}
		
		return employee;
	}
	
	private Employee createEmployeeFromResultSet(ResultSet rs) throws SQLException {
		Qualification qualification = new Qualification();
		qualification.setId(rs.getLong(QUALIFICATION_ID));
		qualification.setTitle(rs.getString(QUALIFICATION_TITLE));
		
		Employee employee = new Employee();
		employee.setId(rs.getLong(ID));
		employee.setName(rs.getString(NAME));
		employee.setStartWork(rs.getDate(START_WORK));
		employee.setQualification(qualification);
		
		return employee;
	}

}
