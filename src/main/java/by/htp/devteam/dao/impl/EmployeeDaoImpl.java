package by.htp.devteam.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import by.htp.devteam.bean.Employee;
import by.htp.devteam.bean.Project;
import by.htp.devteam.bean.Qualification;
import by.htp.devteam.bean.User;
import by.htp.devteam.dao.DaoException;
import by.htp.devteam.dao.EmployeeDao;
import by.htp.devteam.dao.util.ConnectionPool;

import static by.htp.devteam.dao.util.ConstantValue.*;

public final class EmployeeDaoImpl implements EmployeeDao {
	
	private final static int ID = 1;
	private final static int NAME = 2;
	private final static int START_WORK = 3;
	private final static int QUALIFICATION_ID = 6;
	private final static int QUALIFICATION_TITLE = 7;
	
	private final static String SQL_IN_CONDITION_MASK = "##";
	
	public EmployeeDaoImpl() {
		super();
	}

	@Override
	public Employee getByUser(User user) throws DaoException {
		Employee employee = null;	
		try ( Connection dbConnection = ConnectionPool.getConnection(); 
				PreparedStatement ps = dbConnection.prepareStatement(SQL_EMPLOYEE_GET_BY_USER); ) {

			ps.setLong(1, user.getId());
			employee = getEmployeeFromResultSet(ps);
		} catch (SQLException e) {
			throw new DaoException(MSG_ERROR_EMPLOYEE_GET_BY_USER, e);
		}
		return employee;
	}
	
	private Employee getEmployeeFromResultSet(PreparedStatement ps) throws SQLException {
		Employee employee = null;
		try ( ResultSet rs = ps.executeQuery() ) {
			if ( rs.next() ) {
				employee = createEmployeeFromResultSet(rs);
			}
		}
		
		return employee;
	}
	
	@Override
	public List<Employee> getFreeEmployeesForPeriod(Date dateStart, Date dateFinish, Set<Qualification> qualifications) 
			throws DaoException {
		List<Employee> employees = new ArrayList<Employee>();
		
		StringBuilder qualificationIdsStr = new StringBuilder();
		String delimiter = "";
		for ( Qualification qualificaition : qualifications ) {
			qualificationIdsStr.append(delimiter);
			delimiter = ",";
			qualificationIdsStr.append(qualificaition.getId());
		}
		
		String query = SQL_EMPLOYEE_GET_FREE_FOR_PERIOD.replace(SQL_IN_CONDITION_MASK, qualificationIdsStr);
		try ( Connection dbConnection = ConnectionPool.getConnection();
				PreparedStatement st = dbConnection.prepareStatement(query) ) {
			
			st.setDate(1, dateStart);
			st.setDate(2, dateFinish);
			st.setDate(3, dateStart);
			st.setDate(4, dateFinish);
			st.setDate(5, dateStart);
			st.setDate(6, dateFinish);
			
			employees = executeQueryAndGetEmployeeListFromResultSet(st);
		} catch (SQLException e) {
			throw new DaoException(MSG_ERROR_EMPLOYEE_GET_FREE_FOR_PERIOD, e);
		}

		return employees;
	}
	
	private List<Employee> executeQueryAndGetEmployeeListFromResultSet(PreparedStatement st) throws SQLException {
		List<Employee> employees = new ArrayList<Employee>();
		try ( ResultSet rs = st.executeQuery() ) {
			while ( rs.next() ) {
				employees.add(createEmployeeFromResultSet(rs));
			}
		}
		
		return employees;
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

	@Override
	public boolean isEmployeesNotBusyForPeriod(Connection connection, Long[] ids, Date dateStart, Date dateFinish) 
			throws DaoException {
	
		StringBuilder qualificationIdsStr = new StringBuilder();
		String delimiter = "";
		int countIds = ids.length;
		for ( int i = 0; i < countIds; i++ ) {
			qualificationIdsStr.append(delimiter);
			delimiter = ",";
			qualificationIdsStr.append("?");
		}
		
		int countFreeEmployees = 0;
		boolean isFree = false;
		String query = SQL_EMPLOYEE_GET_COUNT_FREE_FROM_LIST.replace(SQL_IN_CONDITION_MASK, qualificationIdsStr);
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

			try ( ResultSet rs = st.executeQuery() ) {
				if ( rs.next() )
					countFreeEmployees = rs.getInt(1);
				
				if ( countIds == countFreeEmployees )
					isFree = true;
			}
		} catch (SQLException e) {
			throw new DaoException(MSG_ERROR_EMPLOYEE_GET_COUNT_FREE_FROM_LIST, e);
		}

		return isFree;
	}

	@Override
	public Map<Long, Integer> getQualificationsIdsAndCountByEmployees(Long[] employeesIds) throws DaoException {
		Map<Long, Integer> qualificationsCount = new HashMap<Long, Integer>();
		
		StringBuilder employeeIdsStr = new StringBuilder();
		String delimiter = "";
		for ( int i = 0; i < employeesIds.length; i++ ) {
			employeeIdsStr.append(delimiter);
			delimiter = ",";
			employeeIdsStr.append("?");
		}
		
		String query = SQL_EMPLOYEE_GET_QUALIFICATIONS_IDS_WITH_COUNTS_BY_EMPLOYEE_IDS.replace(SQL_IN_CONDITION_MASK, employeeIdsStr);
		try ( Connection dbConnection = ConnectionPool.getConnection();
				PreparedStatement st = dbConnection.prepareStatement(query) ) {
			
			for ( int i = 0; i < employeesIds.length; i++ ) {
				st.setLong(i+1, employeesIds[i]);
			}
			qualificationsCount = getQualificationsCountFromResultSet(st);
		} catch (SQLException e) {
			throw new DaoException(MSG_ERROR_EMPLOYEE_GET_QUALIFICATIONS_IDS_WITH_COUNTS_BY_EMPLOYEE_IDS, e);
		}

		return qualificationsCount;
	}
	
	private Map<Long, Integer> getQualificationsCountFromResultSet(PreparedStatement st) throws SQLException {
		Map<Long, Integer> qualificationsCount = new HashMap<Long, Integer>();
		try ( ResultSet rs = st.executeQuery() ) {
			while ( rs.next() ) {
				qualificationsCount.put(rs.getLong(1), rs.getInt(2));
			}
		}
		
		return qualificationsCount;
	}

	/*
	 * Order by quelification title DESC
	 * @see by.htp.devteam.dao.EmployeeDao#getEmployeesAndSpendingHoursByProject(by.htp.devteam.bean.Project)
	 */
	@Override
	public Map<Employee, Integer> getEmployeesAndSpendingHoursByProject(Project project) throws DaoException {
		Map<Employee, Integer> employees = new HashMap<Employee, Integer>();
		
		try ( Connection dbConnection = ConnectionPool.getConnection();
				PreparedStatement st = dbConnection.prepareStatement(SQL_EMPLOYEE_GET_BY_PROJECT) ) {
			
			st.setLong(1, project.getId());
			employees = executeQueryAndGetEmployeeListWithHoursOnProjectFromResultSet(st);
		} catch (SQLException e) {
			throw new DaoException(MSG_ERROR_EMPLOYEE_GET_BY_PROJECT, e);
		}

		return employees;
	}
	
	private Map<Employee, Integer> executeQueryAndGetEmployeeListWithHoursOnProjectFromResultSet(PreparedStatement st) 
			throws SQLException {
		Map<Employee, Integer> employees = new HashMap<Employee, Integer>();
		try ( ResultSet rs = st.executeQuery() ) {
			while ( rs.next() ) {
				Employee employee = new Employee();
				employee.setId(rs.getLong(ID));
				employee.setName(rs.getString(NAME));
				Qualification qualification = new Qualification();
				qualification.setTitle(rs.getString(3));
				
				employee.setQualification(qualification);
				employees.put(employee, rs.getInt(4));
			}
		}
		
		return employees;
	}

}
