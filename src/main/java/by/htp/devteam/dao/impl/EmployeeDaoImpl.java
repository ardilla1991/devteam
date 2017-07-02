package by.htp.devteam.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import by.htp.devteam.bean.Employee;
import by.htp.devteam.bean.Project;
import by.htp.devteam.bean.Qualification;
import by.htp.devteam.bean.User;
import by.htp.devteam.controller.ConnectionPool;
import by.htp.devteam.dao.DaoException;
import by.htp.devteam.dao.EmployeeDao;

public class EmployeeDaoImpl extends CommonDao implements EmployeeDao {
	
	private final int ID = 1;
	private final int NAME = 2;
	private final int START_WORK = 3;
	private final int QUALIFICATION_ID = 6;
	private final int QUALIFICATION_TITLE = 7;
	
	private final static String FETCH_BY_CREDENTIALS = "SELECT e.*, q.id, q.title "
			+ "FROM employee as e JOIN qualification as q ON e.qualification_id=q.id "
			+ "WHERE e.login=? AND e.password=?";
	
	private final static String GET_BY_USER = "SELECT e.*, q.id, q.title FROM employee as e JOIN qualification as q ON e.qualification_id=q.id "
			+ "WHERE e.user_id=?";
	
	private final static String GET_FREE_FOR_PERIOD = "SELECT e.*, q.id, q.title "
			+ "FROM employee as e JOIN qualification as q ON e.qualification_id=q.id "
			+ "WHERE q.id IN (##) AND e.id NOT IN "
				+ "(SELECT distinct ep.employee_id FROM project_employee as ep JOIN project as p "
				+ "ON ep.project_id=p.id JOIN `order` as o ON p.order_id=o.id "
				+ "WHERE ( o.dateStart BETWEEN ? AND ? ) "
				+ "OR ( o.dateFinish BETWEEN ? AND ? ) "
				+ "OR ( o.dateStart<? AND o.dateFinish>? ) )";
	
	private final static String GET_COUNT_FREE_EMPLOYEE_FROM_LIST = "SELECT COUNT(*) "
			+ "FROM employee as e "
			+ "WHERE e.id IN (##) AND e.id NOT IN "
				+ "(SELECT distinct ep.employee_id FROM project_employee as ep JOIN project as p "
				+ "ON ep.project_id=p.id JOIN `order` as o ON p.order_id=o.id "
				+ "WHERE ( o.dateStart BETWEEN ? AND ? ) "
				+ "OR ( o.dateFinish BETWEEN ? AND ? ) "
				+ "OR ( o.dateStart<? AND o.dateFinish>? ) )";
	
	private final static String GET_QUALIFICATIONS_IDS_WITH_COUNTS_BY_EMPLOYEE_IDS = "SELECT qualification_id, COUNT(id) FROM employee WHERE id IN(##) GROUP BY qualification_id";
	
	private final static String GET_BY_PROJECT = "SELECT e.id, e.name, q.title, pe.hours FROM (SELECT * FROM project_employee WHERE project_id=?) as pe "
			+ "JOIN employee as e ON pe.employee_id=e.id "
			+ "JOIN qualification as q ON e.qualification_id=q.id";
	
	@Override
	public Employee getEmployeeByUser(User user) {
		Employee employee = null;	
		try ( Connection dbConnection = ConnectionPool.getConnection(); 
				PreparedStatement ps = dbConnection.prepareStatement(GET_BY_USER); ) {

			ps.setLong(1, user.getId());
			try ( ResultSet rs = ps.executeQuery() ) {
				employee = getEmployeeFromResultSet(rs);
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

	@Override
	public boolean isEmployeesFreeFroPeriod(Connection connection, Long[] ids, Date dateStart, Date dateFinish)
			throws DaoException {
	
		StringBuilder qualificationIdsStr = new StringBuilder();
		String delimiter = "";
		int countIds = ids.length;
		for ( int i = 0; i < countIds; i++ ) {
			qualificationIdsStr.append(delimiter);
			delimiter = ",";
			qualificationIdsStr.append("?");
		}
		
		int countFreeEmployee = 0;
		boolean isFree = false;
		String query = GET_COUNT_FREE_EMPLOYEE_FROM_LIST.replace("##", qualificationIdsStr);
		try ( Connection dbConnection = ConnectionPool.getConnection();
				PreparedStatement st = dbConnection.prepareStatement(query) ) {
			
			for ( int i = 1; i <= countIds; i++ ) {
				st.setLong(i, ids[i - 1]);
			}
			st.setDate(countIds + 1, dateStart);
			st.setDate(countIds + 2, dateFinish);
			st.setDate(countIds + 3, dateStart);
			st.setDate(countIds + 4, dateFinish);
			st.setDate(countIds + 5, dateStart);
			st.setDate(countIds + 6, dateFinish);

			ResultSet rs = st.executeQuery();
			
			if ( rs.next() )
				countFreeEmployee = rs.getInt(1);
			
			if ( countIds == countFreeEmployee )
				isFree = true;
		} catch (SQLException e) {
			
			e.printStackTrace();
		}

		return isFree;
	}

	@Override
	public Map<Long, Integer> getQualificationsCountByEmployees(Long[] employeesIds) throws DaoException {
		Map<Long, Integer> qualificationsCount = new HashMap<Long, Integer>();
		
		StringBuilder employeeIdsStr = new StringBuilder();
		String delimiter = "";
		for ( int i = 0; i < employeesIds.length; i++ ) {
			employeeIdsStr.append(delimiter);
			delimiter = ",";
			employeeIdsStr.append("?");
		}
		
		String query = GET_QUALIFICATIONS_IDS_WITH_COUNTS_BY_EMPLOYEE_IDS.replace("##", employeeIdsStr);
		try ( Connection dbConnection = ConnectionPool.getConnection();
				PreparedStatement st = dbConnection.prepareStatement(query) ) {
			
			for ( int i = 0; i < employeesIds.length; i++ ) {
				st.setLong(i+1, employeesIds[i]);
			}

			ResultSet rs = st.executeQuery();
			qualificationsCount = getQualificationsCountFromResultSet(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return qualificationsCount;
	}
	
	private Map<Long, Integer> getQualificationsCountFromResultSet(ResultSet rs) throws SQLException {
		Map<Long, Integer> qualificationsCount = new HashMap<Long, Integer>();
		while ( rs.next() ) {
			qualificationsCount.put(rs.getLong(1), rs.getInt(2));
		}
		return qualificationsCount;
	}

	@Override
	public Map<Employee, Integer> getEmployeesByProject(Project project) {
		Map<Employee, Integer> employees = new HashMap<Employee, Integer>();
		
		try ( Connection dbConnection = ConnectionPool.getConnection();
				PreparedStatement st = dbConnection.prepareStatement(GET_BY_PROJECT) ) {
			
			st.setLong(1, project.getId());
			ResultSet rs = st.executeQuery();
			employees = getEmployeeListWithHoursOnProjectFromResultSet(rs);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}

		return employees;
	}
	
	private Map<Employee, Integer> getEmployeeListWithHoursOnProjectFromResultSet(ResultSet rs) throws SQLException {
		Map<Employee, Integer> employees = new HashMap<Employee, Integer>();
		while ( rs.next() ) {
			Employee employee = new Employee();
			employee.setId(rs.getLong(ID));
			employee.setName(rs.getString(NAME));
			Qualification qualification = new Qualification();
			qualification.setTitle(rs.getString(3));
			
			employee.setQualification(qualification);
			employees.put(employee, rs.getInt(4));
		}
		
		return employees;
	}

}
