package by.htp.devteam.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import by.htp.devteam.bean.Customer;
import by.htp.devteam.bean.Employee;
import by.htp.devteam.bean.Order;
import by.htp.devteam.bean.Project;
import by.htp.devteam.bean.vo.PagingVo;
import by.htp.devteam.controller.ObjectNotFoundException;
import by.htp.devteam.dao.DaoException;
import by.htp.devteam.dao.ProjectDao;
import by.htp.devteam.dao.util.ConnectionPool;

import static by.htp.devteam.dao.util.ConstantValue.*;

public final class ProjectDaoImpl implements ProjectDao {
	
	private static final int ID = 1;
	private static final int TITLE = 2;
	private static final int DESCRIPTION = 3;
	private static final int DATE_CREATED = 4;
	private static final int ORDER_ID = 5;
	
	private static final String SQL_LIKE_CONDITION_PERCENT = "%";
	
	public ProjectDaoImpl() {
		super();
	}
	
	@Override
	public PagingVo<Project> fetchAll(int offset, int countPerPage, Employee employee) throws DaoException {
		if ( employee == null )
			return fetchAll(offset, countPerPage);
		
		PagingVo<Project> pagingVo = new PagingVo<>();
		try ( Connection dbConnection = ConnectionPool.getConnection();
				PreparedStatement ps = dbConnection.prepareStatement(SQL_PROJECT_LIST_BY_EMPLOYEE); ) {

			ps.setLong(1, employee.getId());
			ps.setInt(2, offset);
			ps.setInt(3, countPerPage);
			pagingVo = executeQueryAndCreatePagingVoObject(dbConnection, ps);
		} catch (SQLException e) {
			throw new DaoException(MSG_ERROR_PROJECT_LIST_BY_EMPLOYEE, e);
		}
		return pagingVo;
	}
	
	/*
	 * Get all list of projects 
	 */
	private PagingVo<Project> fetchAll(int offset, int countPerPage) throws DaoException {
		PagingVo<Project> pagingVo = new PagingVo<>();
		try ( Connection dbConnection = ConnectionPool.getConnection();
				PreparedStatement ps = dbConnection.prepareStatement(SQL_PROJECT_FETCH_ALL); ) {

			ps.setInt(1, offset);
			ps.setInt(2, countPerPage);
			pagingVo = executeQueryAndCreatePagingVoObject(dbConnection, ps);
		} catch (SQLException e) {
			throw new DaoException(MSG_ERROR_PROJECT_FETCH_ALL, e);
		}
		return pagingVo;
	}
	
	private PagingVo<Project> executeQueryAndCreatePagingVoObject(Connection dbConnection, PreparedStatement ps) 
			throws SQLException {
		PagingVo<Project> pagingVo = new PagingVo<>();
		pagingVo.setRecords(executeQueryAndGetProjectListFromResultSet(ps));
		
		try ( Statement st = dbConnection.createStatement();
				ResultSet rsNumebr  = st.executeQuery(SQL_FOUND_ROWS) ) {
			if ( rsNumebr.next() ) {
				pagingVo.setCountAllRecords(rsNumebr.getInt(1));
			}
		}
		
		return pagingVo;
	}
	
	private List<Project> executeQueryAndGetProjectListFromResultSet(PreparedStatement ps) throws SQLException {
		List<Project> projects = new ArrayList<>();
		try ( ResultSet rs = ps.executeQuery() ) {
			while ( rs.next() ) {
				Order order = new Order();
				order.setId(rs.getLong(5));
				order.setTitle(rs.getString(7));
				order.setDescription(rs.getString(8));
				order.setDateCreated(rs.getDate(11));
				order.setDateStart(rs.getDate(12));
				order.setDateFinish(rs.getDate(13));
				
				Project project = new Project();
				project.setId(rs.getLong(ID));
				project.setTitle(rs.getString(TITLE));
				project.setDescription(rs.getString(DESCRIPTION));
				project.setDateCreated(getDateFromTimestamp(rs.getTimestamp(DATE_CREATED)));
				project.setDateCreated(rs.getDate(DATE_CREATED));
				project.setOrder(order);
				
				projects.add(project);
			}
		}

		return projects;
	}
	
	private Date getDateFromTimestamp(Timestamp timestamp) {
		Date date = null;
		if (timestamp != null)
		    date = new Date(timestamp.getTime());

		return date;
	}
	
	@Override
	public Project add(Connection connection, Project project) throws DaoException {
		Project createdProject = project;
		try ( PreparedStatement ps = connection.prepareStatement(SQL_PROJECT_ADD, PreparedStatement.RETURN_GENERATED_KEYS) ) {

			prepareStatementForProject(ps, project);
			ps.executeUpdate();
			try ( ResultSet rs = ps.getGeneratedKeys() ) {
				if (rs.next()) {
					createdProject.setId(rs.getLong(ID));
				}
			}
		} catch (SQLException e) {
			throw new DaoException(MSG_ERROR_PROJECT_ADD, e);
		}
		return createdProject;
	}
	
	private void prepareStatementForProject(PreparedStatement ps, Project project) throws SQLException {
		ps.setString(ID, null);
		ps.setString(TITLE, project.getTitle());
		ps.setString(DESCRIPTION, project.getDescription());
		ps.setTimestamp(DATE_CREATED, new java.sql.Timestamp(project.getDateCreated().getTime()));
		ps.setLong(ORDER_ID, project.getOrder().getId());
	}

	@Override
	public void setEmployees(Connection connection, Project project, Long[] employeeIds) throws DaoException {
		try ( PreparedStatement ps = connection.prepareStatement(SQL_PROJECT_ADD_EMPLOYEE) ) {

			prepareAndAddBatchesForEmployee(ps, project, employeeIds);
			ps.executeBatch();
		} catch (SQLException e) {
			throw new DaoException(MSG_ERROR_PROJECT_ADD_EMPLOYEE, e);
		}
	}
	
	private void prepareAndAddBatchesForEmployee(PreparedStatement ps, Project project, Long[] ids) throws SQLException {
		for ( Long id : ids ) {
			ps.setLong(1, project.getId());
			ps.setLong(2, id);
			
			ps.addBatch();
		}
	}

	@Override
	public Project getById(Long id) throws DaoException, ObjectNotFoundException {
		Project project = null;
		try ( Connection dbConnection = ConnectionPool.getConnection();
				PreparedStatement ps = dbConnection.prepareStatement(SQL_PROJECT_GET_BY_ID) ) {

			ps.setLong(ID, id);
			project = executeQueryAndGetProjectFromResultSet(ps);
		} catch ( SQLException e ) {
			throw new DaoException(MSG_ERROR_PROJECT_GET_BY_ID, e);
		}
		
		return project;
	}
	
	private Project executeQueryAndGetProjectFromResultSet(PreparedStatement ps) throws SQLException, ObjectNotFoundException {
		Project project = new Project();
		try ( ResultSet rs = ps.executeQuery() ) {
			if ( rs.next() ) {
				project.setId(rs.getLong(1));
				project.setTitle(rs.getString(2));
				project.setDescription(rs.getString(3));
				project.setDateCreated(getDateFromTimestamp(rs.getTimestamp(4)));
				
				Order order = new Order();
				order.setId(rs.getLong(5));
				order.setSpecification(rs.getString(6));
				order.setDateStart(rs.getDate(7));
				order.setDateFinish(rs.getDate(8));

				Customer customer = new Customer();
				customer.setName(rs.getString(9));
				customer.setEmail(rs.getString(10));
				customer.setPhone(rs.getString(11));
				
				order.setCustomer(customer);
				project.setOrder(order);
			} else {
				throw new ObjectNotFoundException(MSG_PROJECT_NOT_FOUND);
			}
		}
		
		return project;
	}
	
	@Override
	public void updateHours(Project project, Employee employee, int hours) throws DaoException {
		try ( Connection dbConnection = ConnectionPool.getConnection();
				PreparedStatement ps = dbConnection.prepareStatement(SQL_PROJECT_UPDATE_HOURS) ) {

			ps.setInt(1, hours);
			ps.setLong(2, project.getId());
			ps.setLong(3, employee.getId());
			ps.executeUpdate();
				
		} catch ( SQLException e ) {
			throw new DaoException(MSG_ERROR_PROJECT_UPDATE_HOURS, e);
		}
		
	}

	@Override
	public List<Project> findByTitle(String title) throws DaoException {
		List<Project> projects = new ArrayList<>();
		try ( Connection dbConnection = ConnectionPool.getConnection();
				PreparedStatement ps = dbConnection.prepareStatement(SQL_PROJECT_FIND_BY_TITLE) ) {

			ps.setString(1, SQL_LIKE_CONDITION_PERCENT + title + SQL_LIKE_CONDITION_PERCENT);
			try ( ResultSet rs = ps.executeQuery() ) {
				while ( rs.next() ) {					
					Project project = new Project();
					project.setId(rs.getLong(ID));
					project.setTitle(rs.getString(TITLE));
					project.setDescription(rs.getString(DESCRIPTION));
					project.setDateCreated(rs.getDate(DATE_CREATED));
					
					projects.add(project);
				}
			}
		} catch (SQLException e) {
			throw new DaoException(MSG_ERROR_PROJECT_FIND_BY_TITLE, e);
		}
		
		return projects;
	}
	
	@Override
	public Connection startTransaction() throws DaoException {
		Connection dbConnection = null;
		try {
			dbConnection = ConnectionPool.getConnection();
			dbConnection.setAutoCommit(false);
		} catch (SQLException e) {
			throw new DaoException(MSG_ERROR_CONNECTION, e);
		}
		return dbConnection;
	}
	
	@Override
	public void rollbackTransaction(Connection connection) throws DaoException {
		try {
			connection.rollback();
			ConnectionPool.returnConnection(connection);
		} catch (SQLException e) {
			throw new DaoException(MSG_ERROR_ROLLBACK, e);
		}
	}
	
	@Override
	public void commitTransaction(Connection connection) throws DaoException {
		try {
			connection.commit();
			ConnectionPool.returnConnection(connection);
		} catch (SQLException e) {
			throw new DaoException(MSG_ERROR_COMMIT, e);
		}
	}

}
