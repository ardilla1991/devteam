package by.htp.devteam.service.validation;

import by.htp.devteam.bean.Employee;
import by.htp.devteam.bean.UserRole;

/**
 * Validation users fields. Extends {@link BeanValidation}
 * @author julia
 *
 */
public final class UserValidation extends BeanValidation {
	
	private final static String LOGIN = "login";
	private final static String PASSWORD = "password";
	private final static String ROLE = "role";
	private final static String EMPLOYEE = "employee";
	
	public UserValidation() {
		super();
	}
	
	/**
	 * Check if fields are empty
	 * @param login Check if field is empty
	 * @param password Check if field is empty
	 */
	public void validate(String login, String password) {
		if ( login != null ) {
			login = login.trim();
			if ( Validator.isEmpty(login) ) {
				setNotValidField(LOGIN);
			}
		} else {
			setNotValidField(LOGIN);
		}
		
		if ( password != null ) {
			password = password.trim();
			if ( Validator.isEmpty(password) ) {
				setNotValidField(PASSWORD);
			}
		} else {
			setNotValidField(PASSWORD);
		}
	}
	
	/**
	 * check if fields are correct when we try to add new user for employee
	 * @param login
	 * @param password
	 * @param role
	 * @param employee For validate an employee we check if isset employee in DB and if user already exist for employee.
	 */
	public void validate(String login, String password, String role, Employee employee) {
		validate(login, password);
		
		if ( UserRole.lookup(role) == false ) {
			setNotValidField(ROLE);
		}
		
		if ( employee.getUser().getId() != null || employee == null ) {
			setNotValidField(EMPLOYEE);
		}
	}
	
	/**
	 * Check if a page number has a correct value
	 * @param pageNumber
	 * @return if page number is int value
	 */
	public static boolean validatePage(String pageNumber) {
		return Validator.isInt(pageNumber) && Integer.valueOf(pageNumber) > 0;
	}
}
