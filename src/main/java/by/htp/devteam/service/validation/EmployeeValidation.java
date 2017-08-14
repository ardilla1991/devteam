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
	 * @param title Check if field is empty or more than definite length 
	 * @param description Check if field is empty or more than definite length 
	 * @param specificationFileName Check if correct file extension and length of title
	 * @param dateStart Check if date is real date and check if date after now
	 * @param dateFinish Check if date is real date and check if date after dateStart
	 * @param workIds Check if array length not more then 0 and all array's values have correct Long numbers
	 * @param qualificationsIdsAndCount Check if qualification's ids are correct number 
	 * and count value fields are correct too
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
