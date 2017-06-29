package by.htp.devteam.dao;

import java.sql.Connection;

import by.htp.devteam.bean.Project;
import by.htp.devteam.bean.dto.ProjectListDto;

public interface ProjectDao {

	ProjectListDto fetchAll(int offset, int countPerPage);
	Connection startTransaction() throws DaoException;
	void rollbackTransaction(Connection connection) throws DaoException;
	void commitTransaction(Connection connection) throws DaoException;
	Project add(Connection connection, Project project) throws DaoException;
	void addEmployees(Connection connection, Project project, Long[] employeeIds) throws DaoException;
}
