package by.htp.devteam.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import by.htp.devteam.bean.Customer;
import by.htp.devteam.bean.Employee;
import by.htp.devteam.bean.Order;
import by.htp.devteam.bean.Project;
import by.htp.devteam.bean.dto.ProjectListVo;
import by.htp.devteam.dao.DaoException;
import by.htp.devteam.dao.ProjectDao;
import by.htp.devteam.dao.util.ConnectionPool;

import static by.htp.devteam.dao.util.ConstantValue.*;

public class ProjectDaoImpl implements ProjectDao {
	
	private final static int ID = 1;
	private final static int TITLE = 2;
	private final static int DESCRIPTION = 3;
	private final static int ORDER_ID = 4;
	

	private ProjectListVo fetchAll(int offset, int countPerPage) throws DaoException {
		ProjectListVo projectListVo = new ProjectListVo();
		try ( Connection dbConnection = ConnectionPool.getConnection();
				PreparedStatement ps = dbConnection.prepareStatement(SQL_PROJECT_FETCH_ALL); ) {

			ps.setInt(1, offset);
			ps.setInt(2, countPerPage);
			projectListVo = createProjectListVoObject(dbConnection, ps);
		} catch (SQLException e) {
			throw new DaoException(MSG_ERROR_PROJECT_FETCH_ALL, e);
		}
		return projectListVo;
	}
	
	@Override
	public ProjectListVo fetchAll(int offset, int countPerPage, Employee employee) throws DaoException {
		if ( employee == null )
			return fetchAll(offset, countPerPage);
		
		ProjectListVo projectListVo = new ProjectListVo();
		try ( Connection dbConnection = ConnectionPool.getConnection();
				PreparedStatement ps = dbConnection.prepareStatement(SQL_PROJECT_LIST_BY_EMPLOYEE); ) {

			ps.setLong(1, employee.getId());
			ps.setInt(2, offset);
			ps.setInt(3, countPerPage);
			projectListVo = createProjectListVoObject(dbConnection, ps);
		} catch (SQLException e) {
			throw new DaoException(MSG_ERROR_PROJECT_LIST_BY_EMPLOYEE, e);
		}
		return projectListVo;
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

	@Override
	public void addEmployees(Connection connection, Project project, Long[] employeeIds) throws DaoException {
		try ( PreparedStatement ps = connection.prepareStatement(SQL_PROJECT_ADD_EMPLOYEE) ) {

			prepareAndAddBatchesForEmployee(ps, project, employeeIds);
			ps.executeBatch();
		} catch (SQLException e) {
			throw new DaoException(MSG_ERROR_PROJECT_ADD_EMPLOYEE, e);
		}
	}

	@Override
	public Project getById(Long id) throws DaoException {
		Project project = null;
		try ( Connection dbConnection = ConnectionPool.getConnection();
				PreparedStatement ps = dbConnection.prepareStatement(SQL_PROJECT_GET_BY_ID) ) {

			ps.setLong(ID, id);
			project = getProjectFromResultSet(ps);
		} catch ( SQLException e ) {
			throw new DaoException(MSG_ERROR_PROJECT_GET_BY_ID, e);
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
	
	private ProjectListVo createProjectListVoObject(Connection dbConnection, PreparedStatement ps) throws SQLException{
		ProjectListVo projectListVo = new ProjectListVo();
		projectListVo.setProjects(getProjectListFromResultSet(ps));
		
		try ( Statement st = dbConnection.createStatement();
				ResultSet rsNumebr  = st.executeQuery(SQL_FOUND_ROWS) ) {
			if ( rsNumebr.next() ) {
				projectListVo.setCountRecords(rsNumebr.getInt(1));
			}
		}
		
		return projectListVo;
	}
	
	private List<Project> getProjectListFromResultSet(PreparedStatement ps) throws SQLException {
		List<Project> projects = new ArrayList<Project>();
		try ( ResultSet rs = ps.executeQuery() ) {
			while ( rs.next() ) {
				Order order = new Order();
				order.setId(rs.getLong(4));
				order.setTitle(rs.getString(6));
				order.setDescription(rs.getString(7));
				order.setDateCreated(rs.getDate(11));
				order.setDateStart(rs.getDate(12));
				order.setDateFinish(rs.getDate(13));
				
				Project project = new Project();
				project.setId(rs.getLong(ID));
				project.setTitle(rs.getString(TITLE));
				project.setDescription(rs.getString(DESCRIPTION));
				project.setOrder(order);
				
				projects.add(project);
			}
		}

		return projects;
	}
	
	private void prepareStatementForProject(PreparedStatement ps, Project project) throws SQLException{
		ps.setString(ID, null);
		ps.setString(TITLE, project.getTitle());
		ps.setString(DESCRIPTION, project.getDescription());
		ps.setLong(ORDER_ID, project.getOrder().getId());
	}
	
	private void prepareAndAddBatchesForEmployee(PreparedStatement ps, Project project, Long[] ids) throws SQLException {
		for ( Long id : ids ) {
			ps.setLong(1, project.getId());
			ps.setLong(2, id);
			ps.setInt(3, project.getHours());
			
			ps.addBatch();
		}
	}
	
	private Project getProjectFromResultSet(PreparedStatement ps) throws SQLException {
		Project project = new Project();
		try ( ResultSet rs = ps.executeQuery() ) {
			if ( rs.next() ) {
				project.setId(rs.getLong(1));
				project.setTitle(rs.getString(2));
				project.setDescription(rs.getString(3));
				
				Order order = new Order();
				order.setId(rs.getLong(4));
				order.setSpecification(rs.getString(5));
				order.setDateStart(rs.getDate(6));
				order.setDateFinish(rs.getDate(7));

				Customer customer = new Customer();
				customer.setName(rs.getString(8));
				customer.setEmail(rs.getString(9));
				customer.setPhone(rs.getString(10));
				
				order.setCustomer(customer);
				project.setOrder(order);
			}
		}
		
		return project;
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

	@Override
	public List<Project> findByTitle(String title) throws DaoException {
		List<Project> projects = new ArrayList<Project>();
		try ( Connection dbConnection = ConnectionPool.getConnection();
				PreparedStatement ps = dbConnection.prepareStatement(SQL_PROJECT_FIND_BY_TITLE) ) {

			ps.setString(1, "%" + title + "%");
			//System.out.println(ps);
			try ( ResultSet rs = ps.executeQuery() ) {
				while ( rs.next() ) {					
					Project project = new Project();
					project.setId(rs.getLong(ID));
					project.setTitle(rs.getString(TITLE));
					project.setDescription(rs.getString(DESCRIPTION));
					
					projects.add(project);
				}
			}
		} catch (SQLException e) {
			throw new DaoException(MSG_ERROR_PROJECT_FIND_BY_TITLE, e);
		}
		
		return projects;
	}

}
