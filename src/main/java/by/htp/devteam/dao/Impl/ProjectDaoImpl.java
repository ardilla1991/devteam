package by.htp.devteam.dao.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import by.htp.devteam.bean.Customer;
import by.htp.devteam.bean.Order;
import by.htp.devteam.bean.Project;
import by.htp.devteam.bean.dto.ProjectDto;
import by.htp.devteam.controller.ConnectionPool;
import by.htp.devteam.dao.ProjectDao;
import by.htp.devteam.dao.SqlStatementConstantValue;

public class ProjectDaoImpl implements ProjectDao {
	
	private final int ID = 1;
	private final int TITLE = 2;
	private final int DESCRIPTION = 3;
	private final int ORDER_ID = 4;
	private final int ORDER_TITLE = 10;
	private final int ORDER_DESCRIPTION = 11;
	private final int ORDER_SPECIFICATION = 12;

	public ProjectDto getNewProjects(int offset, int countPerPage) {
		ProjectDto projectDto = new ProjectDto();
		List<Project> projects = new ArrayList<Project>();
		int numberOfRecords = 0;
		
		Connection dbConnection = null;
		PreparedStatement ps = null;
		try {
			dbConnection = ConnectionPool.getConnection();
			
			ps = dbConnection.prepareStatement(SqlStatementConstantValue.PROJECT_LIST);
			ps.setInt(1, offset);
			ps.setInt(2, countPerPage);
			ResultSet rs = ps.executeQuery();
			
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
			rs.close();
			
			Statement st = dbConnection.createStatement();
			ResultSet rsNumebr  = st.executeQuery("SELECT FOUND_ROWS()");
			if ( rsNumebr.next() )
				numberOfRecords = rsNumebr.getInt(1);
			
			projectDto.setProjects(projects);
			projectDto.setCountRecords(numberOfRecords);
			

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps);
			ConnectionPool.close(dbConnection);
		}
		return projectDto;
	}
	
	private void close(PreparedStatement ps) {
		if ( ps != null ) {
			try {
				ps.close();
			} catch ( SQLException e ) {
				e.printStackTrace();
			}
		}
	}
}
