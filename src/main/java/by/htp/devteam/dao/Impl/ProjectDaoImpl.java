package by.htp.devteam.dao.Impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import by.htp.devteam.bean.Customer;
import by.htp.devteam.bean.Employee;
import by.htp.devteam.bean.Project;
import by.htp.devteam.bean.Qualification;
import by.htp.devteam.controller.Loader;
import by.htp.devteam.dao.ProjectDao;
import by.htp.devteam.dao.SqlStatementConstantValue;

public class ProjectDaoImpl implements ProjectDao{

	public List<Project> getNewProjects(int currPage) {
		List<Project> projects = new ArrayList<Project>();
		
		try {
			Connection dbConnector = Loader.LoaderDb();
			
			PreparedStatement ps = dbConnector.prepareStatement(SqlStatementConstantValue.PROJECT_NEW_LIST);
			ps.setInt(1, (currPage - 1) * 1);
			ps.setInt(2, 1);
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

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return projects;
	}
}
