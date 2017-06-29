package by.htp.devteam.dao;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import by.htp.devteam.bean.Employee;
import by.htp.devteam.bean.Qualification;
import by.htp.devteam.bean.User;

public interface EmployeeDao {

	Employee getEmployeeByUser(User user);
	List<Employee> getFreeEmployeesForPeriod(Date dateStart, Date dateFinish, Set<Qualification> qualifications);
	boolean isEmployeesFreeFroPeriod(Connection connection, Date dateStart, Date dateFinish) throws DaoException;
	Map<Long, Integer> getQualificationsCountByEmployees(Long[] employeesIds) throws DaoException;
}
