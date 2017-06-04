package by.htp.devteam.dao;

import by.htp.devteam.bean.Employee;

public interface EmployeeDao {

	Employee fetchByCredentials(String login, String password);
}
