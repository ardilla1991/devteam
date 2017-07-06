package by.htp.devteam.dao.util;

public final class ConstantValue {

	private ConstantValue() {
		super();
	}
	
	public final static String SQL_FOUND_ROWS = "SELECT FOUND_ROWS()";
	
	/************  CUSTOMER  ****************/
	public final static String SQL_CUSTOMER_GET_BY_USER = "SELECT e.* FROM customer as e "
			+ "WHERE e.user_id=?";
	
	/************  EMPLOYEE  ****************/
	public final static String SQL_EMPLOYEE_GET_BY_USER = "SELECT e.*, q.id, q.title FROM employee as e JOIN qualification as q ON e.qualification_id=q.id "
			+ "WHERE e.user_id=?";
	
	public final static String SQL_EMPLOYEE_GET_FREE_FOR_PERIOD = "SELECT e.*, q.id, q.title "
			+ "FROM employee as e JOIN qualification as q ON e.qualification_id=q.id "
			+ "WHERE q.id IN (##) AND e.id NOT IN "
				+ "(SELECT distinct ep.employee_id FROM project_employee as ep JOIN project as p "
				+ "ON ep.project_id=p.id JOIN `order` as o ON p.order_id=o.id "
				+ "WHERE ( o.dateStart BETWEEN ? AND ? ) "
				+ "OR ( o.dateFinish BETWEEN ? AND ? ) "
				+ "OR ( o.dateStart<? AND o.dateFinish>? ) )";
	
	public final static String SQL_EMPLOYEE_GET_COUNT_FREE_FROM_LIST = "SELECT COUNT(*) "
			+ "FROM employee as e "
			+ "WHERE e.id IN (##) AND e.id NOT IN "
				+ "(SELECT distinct ep.employee_id FROM project_employee as ep JOIN project as p "
				+ "ON ep.project_id=p.id JOIN `order` as o ON p.order_id=o.id "
				+ "WHERE ( o.dateStart BETWEEN ? AND ? ) "
				+ "OR ( o.dateFinish BETWEEN ? AND ? ) "
				+ "OR ( o.dateStart<? AND o.dateFinish>? ) )";
	
	public final static String SQL_EMPLOYEE_GET_QUALIFICATIONS_IDS_WITH_COUNTS_BY_EMPLOYEE_IDS = "SELECT qualification_id, COUNT(id) FROM employee WHERE id IN(##) GROUP BY qualification_id";
	
	public final static String SQL_EMPLOYEE_GET_BY_PROJECT = "SELECT e.id, e.name, q.title, pe.hours FROM (SELECT * FROM project_employee WHERE project_id=?) as pe "
			+ "JOIN employee as e ON pe.employee_id=e.id "
			+ "JOIN qualification as q ON e.qualification_id=q.id";
	
	/************  Order  ****************/
	public final static String SQL_ORDER_NEW_RECORDS_LIST = "SELECT SQL_CALC_FOUND_ROWS o.*, c.* "
			+ "FROM `order` as o "
			+ "JOIN customer as c "
			+ "ON o.customer_id=c.id "
			+ "WHERE o.price IS NULL ORDER BY o.dateCreated DESC, o.dateStart DESC LIMIT ?,?";
	
	public final static String SQL_ORDER_GET_BY_ID = "SELECT o.*, c.* FROM `order` as o "
			+ " JOIN customer as c ON o.customer_id=c.id "
			+ " WHERE o.id=?";
	
	public final static String SQL_ORDER_GET_LIST_BY_CUSTOMER_ID = "SELECT * FROM `order` "
			+ "WHERE customer_id=?";
	
	public final static String SQL_ORDER_ADD = "INSERT INTO `order` (id, title, description, specification, customer_id, status, dateCreated, dateStart, dateFinish, price) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	public final static String SQL_ORDER_ADD_WORK = "INSERT INTO order_work (order_id, work_id) VALUES(?, ?)";
	
	public final static String SQL_ORDER_GET_WORKS_BY_ORDER_ID = "SELECT ow.order_id, w.* "
			+ "FROM order_work as ow JOIN work as w ON w.id = ow.work_id "
			+ "WHERE ow.order_id=?";
	
	public final static String SQL_ORDER_ADD_QUALIFICATION = "INSERT INTO order_qualification (order_id, qualification_id, count) "
			+ "VALUES(?, ?, ?)";
	
	public final static String SQL_ORDER_GET_QUALIFICATIONS_BY_ORDER_ID = "SELECT q.*, oq.count "
			+ "FROM qualification as q JOIN order_qualification as oq ON q.id = oq.qualification_id "
			+ "WHERE oq.order_id=?";
	
	public final static String SQL_ORDER_SET_PRICE = "UPDATE `order` SET price=? WHERE id=?";
	
	/************  Project  ****************/
	public final static String SQL_PROJECT_FETCH_ALL = "SELECT p.*, o.* FROM project as p JOIN `order` as o ON p.order_id=o.id LIMIT ?,?";
	
	public final static String SQL_PROJECT_LIST_BY_EMPLOYEE = "SELECT p.*, o.* FROM project as p JOIN `order` as o ON p.order_id=o.id "
			+ "JOIN  (SELECT project_id FROM project_employee WHERE employee_id=?) as pew ON p.id=pew.project_id "
			+ "ORDER BY o.dateStart DESC "
			+ "LIMIT ?,?";
	
	public final static String SQL_PROJECT_ADD = "INSERT INTO `project` (id, title, description, order_id) VALUES (?, ?, ?, ?)";
	
	public final static String SQL_PROJECT_ADD_EMPLOYEE = "INSERT INTO project_employee (project_id, employee_id, hours) VALUES(?, ?, ?)";
	
	public final static String SQL_PROJECT_GET_BY_ID = "SELECT p.*, o.specification, o.dateStart, o.dateFinish, c.name, c.email, c.phone "
			+ "FROM project as p JOIN `order` as o ON p.order_id=o.id JOIN customer as c ON o.customer_id=c.id WHERE p.id=?";

	public final static String SQL_PROJECT_UPDATE_HOURS = "UPDATE project_employee SET hours=hours+? WHERE project_id=? AND employee_id=?";
	
	/************  Qualification  ****************/
	public final static String SQL_QUALIFICATION_FETCH_ALL = "SELECT * FROM qualification";
	
	/************  User  ****************/
	public final static String SQL_USER_FETCH_BY_CREDENTIALS = "SELECT e.* FROM user as e "
			+ "WHERE e.login=?";
	
	/************  Work  ****************/
	public final static String SQL_WORK_FETCH_ALL = "SELECT * FROM work ORDER BY title DESC";
	
	
	/************************************************************************/
	/****************************   MESSAGES   ******************************/
	/************************************************************************/
	public final static String MSG_ERROR_ROLLBACK = "sql error: can't rollback";
	public final static String MSG_ERROR_COMMIT = "sql error: can't commit";
	public final static String MSG_ERROR_CONNECTION = "sql error: can't get connection";
	
	/************  CUSTOMER  ****************/
	public final static String MSG_ERROR_CUSTOMER_GET_BY_USER = "sql error: can't get customer by user";
	
	/************  EMPLOYEE  ****************/
	public final static String MSG_ERROR_EMPLOYEE_GET_BY_USER = "sql error: can't get employee by user";
	public final static String MSG_ERROR_EMPLOYEE_GET_FREE_FOR_PERIOD = "sql error: can't get free employee for period";
	public final static String MSG_ERROR_EMPLOYEE_GET_COUNT_FREE_FROM_LIST = "sql error: can't get count of free employees from list ids";
	public final static String MSG_ERROR_EMPLOYEE_GET_QUALIFICATIONS_IDS_WITH_COUNTS_BY_EMPLOYEE_IDS = "sql error: can't get qualifications with counts by employees ids";
	public final static String MSG_ERROR_EMPLOYEE_GET_BY_PROJECT = "sql error: can't get employees by project";
	
	/************  Order  ****************/
	public final static String MSG_ERROR_ORDER_NEW_RECORDS_LIST = "sql error: can't get list of new orders";
	public final static String MSG_ERROR_ORDER_GET_LIST_BY_CUSTOMER_ID = "sql error: can't get list of new orders";
	public final static String MSG_ERROR_ORDER_ADD = "sql error: can't add order";
	public final static String MSG_ERROR_ORDER_GET_BY_ID = "sql error: can't get order by id";
	public final static String MSG_ERROR_ORDER_SET_PRICE = "sql error: can't set price for order";
	
	/************  Project  ****************/
	public final static String MSG_ERROR_PROJECT_FETCH_ALL = "sql error: can't get list of projects";
	public final static String MSG_ERROR_PROJECT_LIST_BY_EMPLOYEE = "sql error: can't get list of projects by employee";
	public final static String MSG_ERROR_PROJECT_ADD = "sql error: can't add project";
	public final static String MSG_ERROR_PROJECT_ADD_EMPLOYEE = "sql error: can't add employee to the project";
	public final static String MSG_ERROR_PROJECT_GET_BY_ID = "sql error: can't get progect by id";
	public final static String MSG_ERROR_PROJECT_UPDATE_HOURS = "sql error: can't update hours for project";
	
	/************  Qualification  ****************/
	public final static String MSG_ERROR_QUALIFICATION_FETCH_ALL = "sql error: can't get list of qualifications";
	
	/************  User  ****************/
	public final static String MSG_ERROR_USER_FETCH_BY_CREDENTIALS  = "sql error: can't get user by credentials";
	
	/************  Work  ****************/
	public final static String MSG_ERROR_WORK_FETCH_ALL = "sql error: can't get list of works";
	
}