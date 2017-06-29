package by.htp.devteam.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import by.htp.devteam.bean.Order;
import by.htp.devteam.bean.Project;
import by.htp.devteam.bean.dto.ProjectListDto;
import by.htp.devteam.controller.ConnectionPool;
import by.htp.devteam.dao.DaoException;
import by.htp.devteam.dao.ProjectDao;

public class ProjectDaoImpl extends CommonDao implements ProjectDao {
	
	private final int ID = 1;
	private final int TITLE = 2;
	private final int DESCRIPTION = 3;
	private final int ORDER_ID = 4;
	private final int ORDER_TITLE = 10;
	private final int ORDER_DESCRIPTION = 11;
	private final int ORDER_SPECIFICATION = 12;
	
	
	private final int EMPLOYEE_ID = 1;
	private final int PROJECT_ID = 2;
	private final int HOURS = 3;
	
	private final String PROJECT_LIST = "SELECT p.*, o.* FROM project as p JOIN `order` as o ON p.order_id=o.id LIMIT ?,?";
	
	private final String ADD = "INSERT INTO `project` (id, title, description, order_id) VALUES (?, ?, ?, ?)";
	
	private final String ADD_EMPLOYEE = "INSERT INTO project_employee (project_id, employee_id, hours) VALUES(?, ?, ?)";

	public ProjectListDto fetchAll(int offset, int countPerPage) {
		ProjectListDto projectDto = new ProjectListDto();
		try ( Connection dbConnection = ConnectionPool.getConnection();
				PreparedStatement ps = dbConnection.prepareStatement(PROJECT_LIST); ) {

			ps.setInt(1, offset);
			ps.setInt(2, countPerPage);
			try ( ResultSet rs = ps.executeQuery() ) {
				projectDto.setProjects(getProjectListFromResultSet(rs));
			}
			try ( Statement st = dbConnection.createStatement();
					ResultSet rsNumebr  = st.executeQuery("SELECT FOUND_ROWS()");) {
				if ( rsNumebr.next() ) {
					projectDto.setCountRecords(rsNumebr.getInt(1));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return projectDto;
	}
	
	private List<Project> getProjectListFromResultSet(ResultSet rs) throws SQLException {
		List<Project> projects = new ArrayList<Project>();;
		
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
		
		return projects;
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
		} catch (SQLException e) {
			throw new DaoException("couldn't rollback");
		}
	}
	
	@Override
	public void commitTransaction(Connection connection) throws DaoException {
		try {
			connection.commit();
		} catch (SQLException e) {
			throw new DaoException("couldn't commit");
		}
		
	}
	
	@Override
	public Project add(Connection connection, Project project) throws DaoException {
		Project createdProject = project;
		try ( PreparedStatement ps = connection.prepareStatement(ADD, PreparedStatement.RETURN_GENERATED_KEYS) ) {

			prepareStatementForProject(ps, project);
			ps.executeUpdate();
			try ( ResultSet rs = ps.getGeneratedKeys() ) {
				if (rs.next()) {
					//project.setId(rs.getLong(ID));
					createdProject.setId(rs.getLong(ID));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("sql error");
		}
		return createdProject;
	}
	
	private void prepareStatementForProject(PreparedStatement ps, Project project) throws SQLException{
		ps.setString(ID, null);
		ps.setString(TITLE, project.getTitle());
		ps.setString(DESCRIPTION, project.getDescription());
		ps.setLong(ORDER_ID, project.getOrder().getId());
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
	
	private void prepareAndAddBatchesForEmployee(PreparedStatement ps, Project project, Long[] ids) throws SQLException {
		for ( Long id : ids ) {
			ps.setLong(1, project.getId());
			ps.setLong(2, id);
			ps.setInt(3, project.getHours());
			
			ps.addBatch();
		}
	}



}
