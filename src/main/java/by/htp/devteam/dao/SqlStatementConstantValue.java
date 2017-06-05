package by.htp.devteam.dao;

public final class SqlStatementConstantValue {

	private SqlStatementConstantValue() {
		super();
	}
	
	public static final String EMPLOYEE_SELECT = "SELECT e.*, q.title FROM employee as e JOIN qualification as q ON e.qualification_id=q.id WHERE e.login=? AND e.password=?";

	public static final String PROJECT_NEW_LIST = "SELECT p.*, c.* FROM project as p JOIN customer as c "
			+ "ON p.customer_id=c.id WHERE p.status=0 ORDER BY p.dateCreated LIMIT ?,?";
}
