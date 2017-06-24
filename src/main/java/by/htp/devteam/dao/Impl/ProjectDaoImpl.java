package by.htp.devteam.dao.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import by.htp.devteam.bean.Order;
import by.htp.devteam.bean.Project;
import by.htp.devteam.bean.dto.ProjectDto;
import by.htp.devteam.controller.ConnectionPool;
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
	
	private final String PROJECT_LIST = "";

	public ProjectDto getNewProjects(int offset, int countPerPage) {
		ProjectDto projectDto = new ProjectDto();
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
		List<Project> projects = new ArrayList<Project>();
		while ( rs.next() ) {
			Order order = new Order();
			order.setId(rs.getLong(ORDER_ID));
			order.setTitle(rs.getString(ORDER_TITLE));
			order.setDescription(rs.getString(ORDER_DESCRIPTION));
			order.setSpecification(rs.getString(ORDER_SPECIFICATION));
			
			Project project = new Project();
			project.setId(rs.getLong(ID));
			project.setTitle(rs.getString(TITLE));
			project.setDescription(rs.getString(DESCRIPTION));
			project.setOrder(order);
			
			projects.add(project);
		}
		
		return projects;
	}
}
