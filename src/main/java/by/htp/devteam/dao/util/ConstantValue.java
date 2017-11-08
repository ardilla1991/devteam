package by.htp.devteam.dao.util;

/**
 * Constant's valus for SQL queries and MSG for logger
 * @author julia
 *
 */
public final class ConstantValue {

	private ConstantValue() {
		super();
	}
	
	public static final String SQL_FOUND_ROWS = "SELECT FOUND_ROWS()";
	
	public static final String SQL_CUSTOMER_GET_BY_USER = "FROM Customer WHERE user.id=:user";
	
	public static final String SQL_EMPLOYEE_GET_BY_USER = "FROM Employee as e JOIN FETCH e.qualification "
			+ "WHERE e.user.id=:user";
	
	public static final String SQL_EMPLOYEE_GET_FREE_FOR_PERIOD = "SELECT e "
			+ "FROM Employee as e JOIN FETCH e.qualification as q "
			+ "WHERE q.service=0 AND q.id IN (:in) AND e.id NOT IN "
				+ "(SELECT distinct pee.id FROM Project as p "
				+ "INNER JOIN p.employees as pe INNER JOIN pe.employee as pee "
				+ "INNER JOIN p.order as o "
				+ "WHERE ( o.dateStart BETWEEN :start AND :finish ) "
				+ "OR ( o.dateFinish BETWEEN :start AND :finish ) "
				+ "OR ( o.dateStart<:start AND o.dateFinish>:finish ) )";
	
	/**
	 * must be 2 queries!
	 */
	public static final String SQL_EMPLOYEE_GET_COUNT_FREE_FROM_LIST = "SELECT count(e) "
			+ "FROM Employee as e JOIN e.qualification as q "
			+ "WHERE q.service=0 AND e.id IN (:in) AND e.id NOT IN "
				+ "(SELECT distinct pee.id FROM Project as p "
				+ "INNER JOIN p.employees as pe INNER JOIN pe.employee as pee "
				+ "INNER JOIN p.order as o "
				+ "WHERE ( o.dateStart BETWEEN :start AND :finish ) "
				+ "OR ( o.dateFinish BETWEEN :start AND :finish ) "
				+ "OR ( o.dateStart<:start AND o.dateFinish>:finish ) )";
	
	public static final String SQL_EMPLOYEE_GET_QUALIFICATIONS_IDS_WITH_COUNTS_BY_EMPLOYEE_IDS = "SELECT qualification.id, COUNT(id) "
			+ "FROM Employee WHERE id IN(:ids) GROUP BY qualification.id";
	
	public static final String SQL_EMPLOYEE_GET_BY_PROJECT = "SELECT e.id, e.name, q.title, pe.hours "
			+ "FROM (SELECT * FROM project_employee WHERE project_id=?) as pe "
			+ "JOIN employee as e ON pe.employee_id=e.id "
			+ "JOIN qualification as q ON e.qualification_id=q.id ORDER BY q.title DESC";
	
	public static final String SQL_ORDER_NEW_RECORDS_LIST = "FROM Order as o "
			+ "WHERE o.price IS NULL ORDER BY o.dateCreated DESC, o.dateStart DESC";
	
	public static final String SQL_ORDER_COUNT = "SELECT count(*) "
			+ "FROM Order "
			+ "WHERE price IS NULL";
	
	public static final String SQL_ORDER_GET_BY_ID = "FROM Order WHERE id=:id";
	
	public static final String SQL_ORDER_GET_LIST_BY_CUSTOMER_ID = "FROM Order WHERE customer_id=:customer_id ORDER BY dateCreated DESC";
	
	public static final String SQL_ORDER_ADD = "INSERT INTO `order` (id, title, description, specification, customer_id, dateCreated, dateStart, dateFinish, price) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	public static final String SQL_ORDER_ADD_WORK = "INSERT INTO order_work (order_id, work_id) VALUES(?, ?)";
	
	public static final String SQL_ORDER_GET_WORKS_BY_ORDER_ID = "SELECT ow.order_id, w.* "
			+ "FROM order_work as ow JOIN work as w ON w.id = ow.work_id "
			+ "WHERE ow.order_id=?";
	
	public static final String SQL_ORDER_ADD_QUALIFICATION = "INSERT INTO order_qualification (order_id, qualification_id, count) "
			+ "VALUES(?, ?, ?)";
	
	public static final String SQL_ORDER_GET_QUALIFICATIONS_BY_ORDER_ID = "SELECT q.id, q.title, oq.count "
			+ "FROM qualification as q JOIN order_qualification as oq ON q.id = oq.qualification_id "
			+ "WHERE oq.order_id=?";
	
	public static final String SQL_ORDER_SET_PRICE = "UPDATE `order` SET price=?,dateProcessing=? WHERE id=?";
	
	public static final String SQL_PROJECT_FETCH_ALL = "FROM Project";
	
	public static final String SQL_PROJECT_COUNT = "SELECT count(*) FROM Project ";
	
	public static final String SQL_PROJECT_LIST_BY_EMPLOYEE = "SELECT p FROM Project as p "
			+ "INNER JOIN p.employees as pe INNER JOIN pe.employee as pee WHERE pee.id=:employee_id";

	/*public static final String SQL_PROJECT_LIST_BY_EMPLOYEE = "FROM Project as p  "
			+ "JOIN  (SELECT project_id FROM ProjectEmployee WHERE employee=:employee_id) as pew ON p.id=pew.project_id "
			+ "ORDER BY o.dateStart DESC ";*/
	
	public static final String SQL_PROJECT_COUNT_BY_EMPLOYEE = "select count (*) FROM Project as p "
			+ "INNER JOIN p.employees as pe INNER JOIN pe.employee as pee WHERE pee.id=:employee_id";
	
	public static final String SQL_PROJECT_ADD = "INSERT INTO `project` (id, title, description, dateCreated, order_id) VALUES (?, ?, ?, ?, ?)";
	
	public static final String SQL_PROJECT_ADD_EMPLOYEE = "INSERT INTO project_employee (project_id, employee_id) VALUES(?, ?)";
	
	public static final String SQL_PROJECT_GET_BY_ID = "FROM Project WHERE id=:id";

	public static final String SQL_PROJECT_UPDATE_HOURS = "UPDATE project_employee SET hours=hours+? WHERE project_id=? AND employee_id=?";
	
	public static final String SQL_PROJECT_FIND_BY_TITLE = "SELECT p.* FROM project as p WHERE title LIKE ? ";
	
	public static final String SQL_QUALIFICATION_FETCH_ALL = "FROM Qualification WHERE service=0 ORDER BY title DESC";
	
	public static final String SQL_USER_FETCH_BY_CREDENTIALS = "FROM User WHERE login=:login";
	
	public static final String SQL_USER_FETCH_ALL_WITH_EMPLOYEE_AND_CUSTOMER = "SELECT u.*, ec.* FROM user as u "
			+ " JOIN ( (SELECT e.id, e.name, e.start_work, '' as email, '' as phone, e.user_id, q.title FROM employee as e JOIN qualification as q ON e.qualification_id=q.id) "
			+ "			UNION (SELECT c.id, c.name, '' as start_work, c.email, c.phone, c.user_id, '' as qualification FROM customer as c )   )  as ec ON ec.user_id=u.id ORDER BY u.id DESC LIMIT ?,?";
	
	/*public static final String SQL_USER_FETCH_ALL_WITH_EMPLOYEE_AND_CUSTOMER = "FROM User as u "
			+ "JOIN FETCH ( (SELECT e FROM Employee as e JOIN e.qualification) "
			+ "				UNION (SELECT c, '' as qualification FROM customer as c )   "
			+ ")  as ec ON ec.user.id=u.id ORDER BY u.id DESC ";*/
	
	public static final String SQL_USER_FETCH_ALL_WITH_EMPLOYEE = "FROM Employee as e "
			+ "JOIN FETCH e.user as u "
			+ "ORDER BY u.id DESC ";
	
	public static final String SQL_USER_FETCH_ALL_WITH_CUSTOMER = "FROM Customer as c "
			+ "JOIN FETCH c.user as u "
			+ "ORDER BY u.id DESC ";	
	
	public static final String SQL_USER_ADD = "INSERT INTO `user` (login, password, role) VALUES (?, ?, ?)";
	
	
	public static final String SQL_EMPLOYEE_ADD = "INSERT INTO `employee` (name, start_work, qualification_id) VALUES (?, ?, ?)";
	
	public static final String SQL_EMPLOYEE_GET_BY_ID = "SELECT e.* FROM `employee` as e LEFT JOIN user as u ON e.user_id=u.id WHERE e.id=?";
	
	public static final String SQL_EMPLOYEE_GET_USER_ID = "SELECT user_id FROM employee WHERE id=?";
	
	public static final String SQL_EMPLOYEE_SET_USER = "UPDATE `employee` SET user_id=? WHERE id=?";
	
	public static final String SQL_EMPLOYEE_FETCH_ALL_WITH_USER = "FROM Employee as e "
			+ "JOIN FETCH e.qualification as q "
			+ "LEFT JOIN FETCH e.user as u ORDER BY e.name";
	
	public static final String SQL_EMPLOYEE_FETCH_COUNT_ALL_WITH_USER = "SELECT COUNT(e) FROM Employee as e "
			+ "JOIN e.qualification as q "
			+ "LEFT JOIN e.user as u ORDER BY e.name";
	
	public static final String SQL_EMPLOYEE_FETCH_NO_USER = "SELECT e FROM Employee as e INNER JOIN FETCH e.qualification WHERE e.user IS NULL";
	
	public static final String SQL_WORK_FETCH_ALL = "FROM Work ORDER BY title DESC";
	
	
	public static final String MSG_ERROR_GET_CONNECTION_TO_DB = "sql: error: cannot get connection to DB";
	public static final String MSG_ERROR_GET_CONNECTION_FROM_POOL = "sql: error: cannot get connection from pool";
	public static final String MSG_ERROR_CLOSE_CONNECTION = "sql: error: cannot close connection";
	
	public static final String MSG_ERROR_ROLLBACK = "sql error: can't rollback";
	public static final String MSG_ERROR_COMMIT = "sql error: can't commit";
	public static final String MSG_ERROR_CONNECTION = "sql error: can't get connection";
	
	public static final String MSG_ERROR_CUSTOMER_GET_BY_USER = "sql error: can't get customer by user";
	public static final String MSG_CUSTOMER_NOT_FOUND = "Customer id={} not found ";
	public static final String MSG_EMPLOYEE_NOT_FOUND = "Employee not found ";
	public static final String MSG_PROJECT_NOT_FOUND = "Project not found ";
	public static final String MSG_ORDER_NOT_FOUND = "Order not found ";

	public static final String MSG_ERROR_EMPLOYEE_GET_BY_USER = "sql error: can't get employee by user";
	public static final String MSG_ERROR_EMPLOYEE_GET_FREE_FOR_PERIOD = "sql error: can't get free employee for period";
	public static final String MSG_ERROR_EMPLOYEE_GET_COUNT_FREE_FROM_LIST = "sql error: can't get count of free employees from list ids";
	public static final String MSG_ERROR_EMPLOYEE_GET_QUALIFICATIONS_IDS_WITH_COUNTS_BY_EMPLOYEE_IDS = "sql error: can't get qualifications with counts by employees ids";
	public static final String MSG_ERROR_EMPLOYEE_GET_BY_PROJECT = "sql error: can't get employees by project";
	public static final String MSG_ERROR_EMPLOYEE_GET_USER = "sql error: can't get user for employee";
	public static final String MSG_ERROR_EMPLOYEE_SET_USER = "sql error: can't set user for employee";

	public static final String MSG_ERROR_ORDER_NEW_RECORDS_LIST = "sql error: can't get list of new orders";
	public static final String MSG_ERROR_ORDER_GET_LIST_BY_CUSTOMER_ID = "sql error: can't get list of new orders";
	public static final String MSG_ERROR_ORDER_ADD = "sql error: can't add order";
	public static final String MSG_ERROR_ORDER_GET_BY_ID = "sql error: can't get order by id";
	public static final String MSG_ERROR_ORDER_SET_PRICE = "sql error: can't set price for order";

	public static final String MSG_ERROR_PROJECT_FETCH_ALL = "sql error: can't get list of projects";
	public static final String MSG_ERROR_PROJECT_LIST_BY_EMPLOYEE = "sql error: can't get list of projects by employee";
	public static final String MSG_ERROR_PROJECT_ADD = "sql error: can't add project";
	public static final String MSG_ERROR_PROJECT_ADD_EMPLOYEE = "sql error: can't add employee to the project";
	public static final String MSG_ERROR_PROJECT_GET_BY_ID = "sql error: can't get progect by id";
	public static final String MSG_ERROR_PROJECT_UPDATE_HOURS = "sql error: can't update hours for project";
	public static final String MSG_ERROR_PROJECT_FIND_BY_TITLE = "sql error: can't get list of projects by title";

	public static final String MSG_ERROR_QUALIFICATION_FETCH_ALL = "sql error: can't get list of qualifications";

	public static final String MSG_ERROR_USER_FETCH_BY_CREDENTIALS  = "sql error: can't get user by credentials";
	public static final String MSG_ERROR_USER_LIST = "sql error: can't get list of users";
	
	public static final String MSG_ERROR_WORK_FETCH_ALL = "sql error: can't get list of works";
	
	public static final String MSG_ERROR_EMPLOYEE_ADD = "sql error: can't add employee";
	public static final String MSG_ERROR_EMPLOYEE_GET_BY_ID = "sql error: can't get employee by id";
	public static final String MSG_ERROR_EMPLOYEE_LIST = "sql error: can't get list of employees";
	public static final String MSG_ERROR_EMPLOYEE_LIST_NOT_USER = "sql error: can't get list of employees without user records";

}
