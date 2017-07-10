package by.htp.devteam.dao;

import java.sql.Connection;
import java.util.List;

import by.htp.devteam.bean.Employee;
import by.htp.devteam.bean.Project;
import by.htp.devteam.bean.dto.ProjectListVo;

public interface ProjectDao {

	ProjectListVo fetchAll(int offset, int countPerPage, Employee employee) throws DaoException;
	
	Connection startTransaction() throws DaoException;
	
	void rollbackTransaction(Connection connection) throws DaoException;
	
	void commitTransaction(Connection connection) throws DaoException;
	
	Project add(Connection connection, Project project) throws DaoException;
	
	void addEmployees(Connection connection, Project project, Long[] employeeIds) throws DaoException;
	
	Project getById(Long id) throws DaoException;
	
	void updateHours(Project project, Employee employee, int hours) throws DaoException;
	
	List<Project> findByTitle(String title) throws DaoException;
}
