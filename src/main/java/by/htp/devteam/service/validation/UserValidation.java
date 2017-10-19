package by.htp.devteam.service.validation;

import java.util.regex.Pattern;

import by.htp.devteam.bean.Employee;
import by.htp.devteam.bean.UserRole;

/**
 * Validation users fields. Extends {@link BeanValidation}
 * @author julia
 *
 */
public final class UserValidation extends BeanValidation {
	
	private static final String LOGIN = "login";
	private static final String PASSWORD = "password";
	private static final String ROLE = "role";
	private static final String EMPLOYEE = "employee";
	
	/** Regular expression for login */
	private static final Pattern LOGIN_PATTERN = Pattern.compile("^[a-zA-Z0-9_]{5,50}$");
	
	/** Regular expression for password */
	private static final Pattern PASSWORD_PATTERN = Pattern.compile("^((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,50})$");
	
	public UserValidation() {
		super();
	}
	
	/**
	 * Check if fields are not empty and have correct form 
	 * @param login Check if field is not empty and has correct form 
	 * @param password Check if field is empty and has correct form 
	 */
	public void validate(String login, String password) {
		if ( login != null ) {
			login = login.trim();
			if ( Validator.isEmpty(login) || !LOGIN_PATTERN.matcher(login).matches() ) {
				setNotValidField(LOGIN);
			}
		} else {
			setNotValidField(LOGIN);
		}
		
		if ( password != null ) {
			password = password.trim();
			if ( Validator.isEmpty(password) || !PASSWORD_PATTERN.matcher(password).matches() ) {
				setNotValidField(PASSWORD);
			}
		} else {
			setNotValidField(PASSWORD);
		}
	}
	
	/**
	 * check if fields are correct when we try to add new user for employee
	 * @param login Login input field
	 * @param password Password input field
	 * @param role Role input field
	 * @param employee For validate an employee we check if isset employee in DB and if user already exist for employee.
	 */
	public void validate(String login, String password, String role, Employee employee) {
		validate(login, password);
		
		if ( UserRole.lookup(role) == false ) {
			setNotValidField(ROLE);
		}
		
		if ( (employee.getUser() !=null && employee.getUser().getId() != null) || employee == null ) {
			setNotValidField(EMPLOYEE);
		}
	}

}
