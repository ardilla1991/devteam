package by.htp.devteam.dao;

public final class SqlStatementConstantValue {

	private SqlStatementConstantValue() {
		super();
	}
	
	public static final String EMPLOYEE_SELECT = "SELECT e.*, q.title FROM employee as e JOIN qualification as q ON e.qualification_id=q.id WHERE e.login=? AND e.password=?";

	public static final String ORDER_NEW_LIST = "SELECT SQL_CALC_FOUND_ROWS o.*, c.* "
			+ "FROM `order` as o "
			+ "JOIN customer as c "
			+ "ON o.customer_id=c.id "
			+ "WHERE o.status=0 ORDER BY o.dateCreated LIMIT ?,?";
	
	public static final String PROJECT_LIST = "";
}
