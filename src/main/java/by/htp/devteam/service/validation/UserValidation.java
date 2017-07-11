package by.htp.devteam.service.validation;

/**
 * Validation users fields. Extends {@link BeanValidation}
 * @author julia
 *
 */
public final class UserValidation extends BeanValidation {
	
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
				valid &= false;
				notValidField.add("login");
			}
		} else {
			valid &= false;
			notValidField.add("login");
		}
		
		if ( password != null ) {
			password = password.trim();
			if ( Validator.isEmpty(password) ) {
				valid &= false;
				notValidField.add("password");
			}
		} else {
			valid &= false;
			notValidField.add("password");
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
