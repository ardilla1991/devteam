package by.htp.devteam.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import by.htp.devteam.bean.Employee;
import by.htp.devteam.bean.Qualification;
import by.htp.devteam.bean.User;
import by.htp.devteam.controller.ConnectionPool;
import by.htp.devteam.dao.EmployeeDao;

public class EmployeeDaoImpl extends CommonDao implements EmployeeDao {
	
	private final int ID = 1;
	private final int NAME = 2;
	private final int START_WORK = 3;
	private final int QUALIFICATION_ID = 6;
	private final int QUALIFICATION_TITLE = 7;
	
	private final String FETCH_BY_CREDENTIALS = "SELECT e.*, q.id, q.title "
			+ "FROM employee as e JOIN qualification as q ON e.qualification_id=q.id "
			+ "WHERE e.login=? AND e.password=?";
	
	private final String GET_BY_USER = "SELECT e.*, q.id, q.title FROM employee as e JOIN qualification as q ON e.qualification_id=q.id "
			+ "WHERE e.user_id=?";
	
	private final String GET_FREE_FOR_PERIOD = "SELECT e.*, q.id, q.title "
			+ "FROM employee as e JOIN qualification as q ON e.qualification_id=q.id "
			+ "WHERE q.id IN (##) AND e.id NOT IN "
				+ "(SELECT distinct ep.employee_id FROM employee_project as ep JOIN project as p "
				+ "ON ep.project_id=p.id JOIN `order` as o ON p.order_id=o.id "
				+ "WHERE ( o.dateStart BETWEEN ? AND ? ) "
				+ "OR ( o.dateFinish BETWEEN ? AND ? ) "
				+ "OR ( o.dateStart<? AND o.dateFinish>? ) )";
	
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
	
	public List<Employee> getFreeEmployeesForPeriod(Date dateStart, Date dateFinish, Set<Qualification> qualifications) {
		List<Employee> employees = new ArrayList<Employee>();
		
		StringBuilder qualificationIdsStr = new StringBuilder();
		String delimiter = "";
		for ( Qualification qualificaition : qualifications ) {
			qualificationIdsStr.append(delimiter);
			delimiter = ",";
			qualificationIdsStr.append(qualificaition.getId());
		}
		
		String query = GET_FREE_FOR_PERIOD.replace("##", qualificationIdsStr);
		try ( Connection dbConnection = ConnectionPool.getConnection();
				PreparedStatement st = dbConnection.prepareStatement(query) ) {
			
			st.setDate(1, dateStart);
			st.setDate(2, dateFinish);
			st.setDate(3, dateStart);
			st.setDate(4, dateFinish);
			st.setDate(5, dateStart);
			st.setDate(6, dateFinish);
			ResultSet rs = st.executeQuery();
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
		
		
		/*ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();

		// The column count starts from 1
		for (int i = 1; i <= columnCount; i++ ) {
		  String name = rsmd.getColumnName(i);
		  System.out.println(name);
		}*/
		
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
