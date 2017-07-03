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
import by.htp.devteam.controller.ConnectionPool;
import by.htp.devteam.dao.DaoException;
import by.htp.devteam.dao.ProjectDao;

public class ProjectDaoImpl extends CommonDao implements ProjectDao {
	
	private final static int ID = 1;
	private final static int TITLE = 2;
	private final static int DESCRIPTION = 3;
	private final static int ORDER_ID = 4;
	
	private final static String PROJECT_LIST = "SELECT p.*, o.* FROM project as p JOIN `order` as o ON p.order_id=o.id LIMIT ?,?";
	
	private final static String PROJECT_LIST_BY_EMPLOYEE = "SELECT p.*, o.* FROM project as p JOIN `order` as o ON p.order_id=o.id "
			+ "JOIN  (SELECT project_id FROM project_employee WHERE employee_id=?) as pew ON p.id=pew.project_id "
			+ "ORDER BY o.dateStart DESC "
			+ "LIMIT ?,?";
	
	private final static String ADD = "INSERT INTO `project` (id, title, description, order_id) VALUES (?, ?, ?, ?)";
	
	private final static String ADD_EMPLOYEE = "INSERT INTO project_employee (project_id, employee_id, hours) VALUES(?, ?, ?)";
	
	private final static String GET_BY_ID = "SELECT p.*, o.specification, o.dateStart, o.dateFinish, c.name, c.email, c.phone "
			+ "FROM project as p JOIN `order` as o ON p.order_id=o.id JOIN customer as c ON o.customer_id=c.id WHERE p.id=?";

	private final static String UPDATE_HOURS = "UPDATE project_employee SET hours=hours+? WHERE project_id=? AND employee_id=?";
	
	private ProjectListVo fetchAll(int offset, int countPerPage) {
		ProjectListVo projectListVo = new ProjectListVo();
		try ( Connection dbConnection = ConnectionPool.getConnection();
				PreparedStatement ps = dbConnection.prepareStatement(PROJECT_LIST); ) {

			ps.setInt(1, offset);
			ps.setInt(2, countPerPage);
			projectListVo = createProjectListVoObject(dbConnection, ps);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return projectListVo;
	}
	
	@Override
	public ProjectListVo fetchAll(int offset, int countPerPage, Employee employee) {
		if ( employee == null )
			return fetchAll(offset, countPerPage);
		
		ProjectListVo projectListVo = new ProjectListVo();
		try ( Connection dbConnection = ConnectionPool.getConnection();
				PreparedStatement ps = dbConnection.prepareStatement(PROJECT_LIST_BY_EMPLOYEE); ) {

			ps.setLong(1, employee.getId());
			ps.setInt(2, offset);
			ps.setInt(3, countPerPage);
			projectListVo = createProjectListVoObject(dbConnection, ps);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return projectListVo;
	}
	
	@Override
	public Project add(Connection connection, Project project) throws DaoException {
		Project createdProject = project;
		try ( PreparedStatement ps = connection.prepareStatement(ADD, PreparedStatement.RETURN_GENERATED_KEYS) ) {

			prepareStatementForProject(ps, project);
			ps.executeUpdate();
			try ( ResultSet rs = ps.getGeneratedKeys() ) {
				if (rs.next()) {
					createdProject.setId(rs.getLong(ID));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("sql error");
		}
		return createdProject;
	}

	@Override
	public void addEmployees(Connection connection, Project project, Long[] employeeIds) throws DaoException {
		try ( PreparedStatement ps = connection.prepareStatement(ADD_EMPLOYEE) ) {

			prepareAndAddBatchesForEmployee(ps, project, employeeIds);
			ps.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("sql error! ");
		}
	}

	@Override
	public Project getById(Long id) {
		Project project = null;
		try ( Connection dbConnection = ConnectionPool.getConnection();
				PreparedStatement ps = dbConnection.prepareStatement(GET_BY_ID) ) {

			ps.setLong(ID, id);
			project = getProjectFromResultSet(ps);
		} catch ( SQLException e ) {
			e.printStackTrace();
		}
		
		return project;
	}
	
	@Override
	public void updateHours(Project project, Employee employee, int hours) throws DaoException {
		try ( Connection dbConnection = ConnectionPool.getConnection();
				PreparedStatement ps = dbConnection.prepareStatement(UPDATE_HOURS) ) {

			ps.setInt(1, hours);
			ps.setLong(2, project.getId());
			ps.setLong(3, employee.getId());
			ps.executeUpdate();
				
		} catch ( SQLException e ) {
			e.printStackTrace();
		}
		
	}
	
	private ProjectListVo createProjectListVoObject(Connection dbConnection, PreparedStatement ps) throws SQLException{
		ProjectListVo projectListVo = new ProjectListVo();
		projectListVo.setProjects(getProjectListFromResultSet(ps));
		
		try ( Statement st = dbConnection.createStatement();
				ResultSet rsNumebr  = st.executeQuery("SELECT FOUND_ROWS()");) {
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
			e.printStackTrace();
			throw new DaoException("couldn't get connection");
		}
		return dbConnection;
	}
	
	@Override
	public void rollbackTransaction(Connection connection) throws DaoException {
		try {
			connection.rollback();
			ConnectionPool.returnConnection(connection);
		} catch (SQLException e) {
			throw new DaoException("couldn't rollback");
		}
	}
	
	@Override
	public void commitTransaction(Connection connection) throws DaoException {
		try {
			connection.commit();
			ConnectionPool.returnConnection(connection);
		} catch (SQLException e) {
			throw new DaoException("couldn't commit");
		}
		
	}

}
