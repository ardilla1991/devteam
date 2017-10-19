package by.htp.devteam.dao.impl;

import java.sql.Connection;
import java.util.Date;
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
import by.htp.devteam.bean.UserRole;
import by.htp.devteam.bean.vo.PagingVo;
import by.htp.devteam.controller.ObjectNotFoundException;
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
		List<Employee> employees = new ArrayList<>();
		
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
			java.sql.Date dStart = new java.sql.Date(dateStart.getTime());
			java.sql.Date dFinish = new java.sql.Date(dateFinish.getTime());
			st.setDate(1, dStart);
			st.setDate(2, dFinish);
			st.setDate(3, dStart);
			st.setDate(4, dFinish);
			st.setDate(5, dStart);
			st.setDate(6, dFinish);
			
			employees = executeQueryAndGetEmployeeListFromResultSet(st);
		} catch (SQLException e) {
			throw new DaoException(MSG_ERROR_EMPLOYEE_GET_FREE_FOR_PERIOD, e);
		}

		return employees;
	}
	
	private List<Employee> executeQueryAndGetEmployeeListFromResultSet(PreparedStatement st) throws SQLException {
		List<Employee> employees = new ArrayList<>();
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
		try ( PreparedStatement st = connection.prepareStatement(query) ) {
			
			for ( int i = 1; i <= countIds; i++ ) {
				st.setLong(i, ids[i - 1]);
			}
			java.sql.Date dStart = new java.sql.Date(dateStart.getTime());
			java.sql.Date dFinish = new java.sql.Date(dateFinish.getTime());
			st.setDate(countIds + 1, dStart);
			st.setDate(countIds + 2, dFinish);
			st.setDate(countIds + 3, dStart);
			st.setDate(countIds + 4, dFinish);
			st.setDate(countIds + 5, dStart);
			st.setDate(countIds + 6, dFinish);

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
		Map<Long, Integer> qualificationsCount = new HashMap<>();
		
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
		Map<Long, Integer> qualificationsCount = new HashMap<>();
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
		Map<Employee, Integer> employees = new HashMap<>();
		
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
		Map<Employee, Integer> employees = new HashMap<>();
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

	@Override
	public Employee add(Employee employee) throws DaoException {
		Employee createdEmployee = employee;
		try ( Connection connection = ConnectionPool.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL_EMPLOYEE_ADD, PreparedStatement.RETURN_GENERATED_KEYS) ) {

			prepareStatementForEmployee(ps, employee);
			ps.executeUpdate();
			try ( ResultSet rs = ps.getGeneratedKeys() ) {
				if (rs.next()) {
					createdEmployee.setId(rs.getLong(ID));
				}
			}
		} catch (SQLException e) {
			throw new DaoException(MSG_ERROR_EMPLOYEE_ADD, e);
		}
		return createdEmployee;
	}
	
	private void prepareStatementForEmployee(PreparedStatement ps, Employee employee) throws SQLException {
		ps.setString(1, employee.getName());
		ps.setDate(2, new java.sql.Date(employee.getStartWork().getTime()));
		ps.setLong(3, employee.getQualification().getId());
	}

	@Override
	public Employee getById(Long id) throws DaoException, ObjectNotFoundException {
		Employee employee = null;
		try ( Connection dbConnection = ConnectionPool.getConnection();
				PreparedStatement ps = dbConnection.prepareStatement(SQL_EMPLOYEE_GET_BY_ID) ) {

			ps.setLong(ID, id);
			employee = executeQueryAndGetEmployeeFromResultSet(ps);
		} catch ( SQLException e ) {
			throw new DaoException(MSG_ERROR_EMPLOYEE_GET_BY_ID, e);
		}
		
		return employee;
	}
	
	private Employee executeQueryAndGetEmployeeFromResultSet(PreparedStatement ps) 
			throws SQLException, ObjectNotFoundException {
		Employee employee = new Employee();
		try ( ResultSet rs = ps.executeQuery() ) {
			if ( rs.next() ) {
				employee.setId(rs.getLong(1));
				employee.setName(rs.getString(2));
				employee.setStartWork(rs.getDate(3));				
			} else {
				throw new ObjectNotFoundException(MSG_EMPLOYEE_NOT_FOUND);
			}
		}
		
		return employee;
	}

	@Override
	public void setUserForEmployee(Connection connection, Employee employee, User user) throws DaoException {
		try ( PreparedStatement ps = connection.prepareStatement(SQL_EMPLOYEE_SET_USER) ) {
			ps.setLong(1, user.getId());
			ps.setLong(2, employee.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException(MSG_ERROR_EMPLOYEE_SET_USER, e);
		}		
	}

	@Override
	public boolean isExistUserForEmployee(Connection connection, Employee employee) throws DaoException {
		boolean isExist = true;
		try ( PreparedStatement st = connection.prepareStatement(SQL_EMPLOYEE_GET_USER_ID) ) {

			st.setLong(1, employee.getId());
			try ( ResultSet rs = st.executeQuery() ) {
				if ( rs.next() && rs.getInt(1) == 0 )
					isExist = false;
			}
		} catch (SQLException e) {
			throw new DaoException(MSG_ERROR_EMPLOYEE_GET_USER, e);
		}

		return isExist;
	}

	@Override
	public PagingVo<Employee> fetchAll(int offset, int countPerPage) throws DaoException {
		PagingVo<Employee> pagingVo = new PagingVo<>();
		try ( Connection dbConnection = ConnectionPool.getConnection();
				PreparedStatement ps = dbConnection.prepareStatement(SQL_EMPLOYEE_FETCH_ALL_WITH_USER) ) {

			ps.setInt(1, offset);
			ps.setInt(2, countPerPage);

			pagingVo = executeQueryAndCreateEmployeeListVoObject(dbConnection, ps);
		} catch (SQLException e) {
			throw new DaoException(MSG_ERROR_EMPLOYEE_LIST, e);
		}
		
		return pagingVo;
	}
	
	/*
	 * execute query and get total count of employees
	 */
	private PagingVo<Employee> executeQueryAndCreateEmployeeListVoObject(Connection dbConnection, PreparedStatement ps) 
			throws SQLException{
		PagingVo<Employee> pagingVo = new PagingVo<>();
		pagingVo.setRecords(getEmployeeListFromResultSet(ps, true));
		try ( Statement st = dbConnection.createStatement();
				ResultSet rsNumebr  = st.executeQuery(SQL_FOUND_ROWS) ) {
			if (rsNumebr.next()) {
				pagingVo.setCountAllRecords(rsNumebr.getInt(1));
			}
		}
		
		return pagingVo;
	}
	
	/*
	 * Create employee list with user and qualification information
	 */
	private List<Employee> getEmployeeListFromResultSet(PreparedStatement ps, boolean needUser) throws SQLException {
		List<Employee> employees = new ArrayList<>();
		try ( ResultSet rs = ps.executeQuery() ) {
			while ( rs.next() ) {
				Employee employee = new Employee();
				employee.setId(rs.getLong(ID));
				employee.setName(rs.getString(NAME));
				employee.setStartWork(rs.getDate(START_WORK));
				
				Qualification qualification = new Qualification();
				qualification.setId(rs.getLong(4));
				qualification.setTitle(rs.getString(6));
				employee.setQualification(qualification);
				
				if ( needUser && rs.getLong(5) > 0 ) {
					User user = new User();
					user.setId(rs.getLong(5));
					user.setLogin(rs.getString(7));
					user.setRole(UserRole.valueOf(rs.getString(8)));
				}
				
				employees.add(employee);
			}
		}

		return employees;
	}

	@Override
	public List<Employee> getListWithNotSetUser() throws DaoException {
		List<Employee> employeeList = new ArrayList<>();
		try ( Connection dbConnection = ConnectionPool.getConnection();
				PreparedStatement ps = dbConnection.prepareStatement(SQL_EMPLOYEE_FETCH_NO_USER) ) {
			employeeList = getEmployeeListFromResultSet(ps, false);
		} catch (SQLException e) {
			throw new DaoException(MSG_ERROR_EMPLOYEE_LIST_NOT_USER, e);
		}
		
		return employeeList;
	}

}
