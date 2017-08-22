package by.htp.devteam.service.validation;

import java.util.regex.Pattern;

public final class EmployeeValidation extends BeanValidation {
	
	private final static String NAME = "name";
	private final static String START_WORK = "startWork";
	private final static String QUALIFICATION = "qualification";
	
	/** Regular expression for name */
	private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z- ]{5,50}$");
	
	public EmployeeValidation() {
		super();
	}
	
	/**
	 * Validation of main fields
	 * @param name Check if field is empty or more than fixed length 
	 * @param startWork Check if date is real date
	 * @param qualification Check if field is long type
	 */
	public void validate(String name, String startWork, String qualification) {

		if ( name != null ) {
			name = name.trim();
			if ( Validator.isEmpty(name) || name.length() > 50 || !NAME_PATTERN.matcher(name).matches() ) {
				setNotValidField(NAME);
			}
		} else {
			setNotValidField(NAME);
		}
		
		if ( !Validator.isDate(startWork) ) {
			setNotValidField(START_WORK);
		}

		if ( !Validator.isLong(qualification) ) {
			setNotValidField(QUALIFICATION);			
		}
	}
	
}
