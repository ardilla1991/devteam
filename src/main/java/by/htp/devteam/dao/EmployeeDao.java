package by.htp.devteam.dao;

import by.htp.devteam.bean.Employee;
import by.htp.devteam.bean.User;

public interface EmployeeDao {

	Employee getEmployeeByUser(User user);
}
