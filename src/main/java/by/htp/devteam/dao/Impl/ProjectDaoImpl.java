package by.htp.devteam.dao.Impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import by.htp.devteam.bean.Customer;
import by.htp.devteam.bean.Project;
import by.htp.devteam.bean.dto.ProjectDto;
import by.htp.devteam.controller.Loader;
//import by.htp.devteam.controller.Loader;
import by.htp.devteam.dao.ProjectDao;
import by.htp.devteam.dao.SqlStatementConstantValue;

public class ProjectDaoImpl implements ProjectDao{

	public ProjectDto getNewProjects(int offset, int countPerPage) {
		ProjectDto projectDto = new ProjectDto();
		List<Project> projects = new ArrayList<Project>();
		int numberOfRecords = 0;
		
		Connection dbConnection = null;
		try {
			//Connection dbConnector = Loader.LoaderDb();
			InitialContext initContext= new InitialContext();
            Context envContext  = (Context)initContext.lookup("java:comp/env");
            DataSource ds = (DataSource)envContext.lookup("jdbc/devteam");
            dbConnection = ds.getConnection();
			
			PreparedStatement ps = dbConnection.prepareStatement(SqlStatementConstantValue.PROJECT_NEW_LIST);
			ps.setInt(1, offset);
			ps.setInt(2, countPerPage);
			ResultSet rs = ps.executeQuery();
			
			while ( rs.next() ) {
				Long id = rs.getLong(1);
				String title = rs.getString(2);
				String description = rs.getString(3);
				Date dateCreated = rs.getDate(5);
				Date dateStart = rs.getDate(6);
				Date dateFinish = rs.getDate(7);
				boolean status = rs.getBoolean(8);
				Long customerId = rs.getLong(4);
				String customerName = rs.getString(10);
				String customerEmail = rs.getString(11);
				String customerPhone = rs.getString(12);
				Customer customer = new Customer();
				customer.setId(customerId);
				customer.setName(customerName);
				customer.setEmail(customerEmail);
				customer.setPhone(customerPhone);
				
				Project progect = new Project();
				progect.setId(id);
				progect.setTitle(title);
				progect.setDescription(description);
				progect.setCustomer(customer);
				progect.setDateCreated(dateCreated);
				progect.setDateStart(dateStart);
				progect.setDateFinish(dateFinish);
				progect.setStatus(status);
				
				projects.add(progect);
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
		} /*catch (ClassNotFoundException e) {
			e.printStackTrace();
		}*/
		catch (  NamingException e ) {
			e.printStackTrace();
		}
		return projectDto;
	}
}
