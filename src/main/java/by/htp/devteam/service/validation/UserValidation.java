package by.htp.devteam.service.validation;

public class UserValidation extends BeanValidation {
	
	public UserValidation() {
		super();
	}
	
	public void validate(String login, String password) {
		login = login.trim();
		if ( Validator.isEmpty(login) ) {
			valid &= false;
			notValidField.add("login");
		}
		
		password = password.trim();
		if ( Validator.isEmpty(password) ) {
			valid &= false;
			notValidField.add("password");
		}
	}
}
