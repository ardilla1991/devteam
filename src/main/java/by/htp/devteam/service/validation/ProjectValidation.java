package by.htp.devteam.service.validation;

import java.util.Map;

/**
 * Validation projects fields. Extends {@link BeanValidation}
 * @author julia
 *
 */
public final class ProjectValidation extends BeanValidation {
	
	private final static String TITLE = "title";
	private final static String DESCRIPTION = "description";
	private final static String EMPLOYEE = "employee";
	private final static String QUALIFICATION = "qualification";
	private final static String HOURS = "hours";
	private final static String PRICE = "price";
	
	public ProjectValidation() {
		super();
	}
	
	/**
	 * Validation of main fields
	 * @param title Check if field is empty or more than definite length 
	 * @param description Check if field is empty or more than definite length 
	 * @param employees Check if selected employees have a correct ids 
	 * @param price Check if field is not empty and has a correct bigdecimal value and length not more than  defined
	 */
	public void validate(String title, String description, String[] employees, String price) {
		
		if ( title != null ) {
			title = title.trim();
			if ( Validator.isEmpty(title) || title.length() > 250 ) {
				setNotValidField(TITLE);
			}
		} else {
			setNotValidField(TITLE);
		}
		
		
		if ( description != null ) {
			description = description.trim();
			if ( Validator.isEmpty(description) || description.length() > 2000 ) {
				setNotValidField(DESCRIPTION);
			}
		} else {
			setNotValidField(DESCRIPTION);
		}
		
		if ( employees != null ) {
			int employeesIdsLength = employees.length;
			for ( int i = 0; i < employeesIdsLength; i++ ) {
				if ( !Validator.isLong(employees[i]) ) {
					setNotValidField(EMPLOYEE);
					break;
				}
			}
		} else {
			setNotValidField(EMPLOYEE);
		}
		
		if ( Validator.isEmpty(price) || !Validator.checkBigDecimal(price) || price.length() > 11) {
			setNotValidField(PRICE);
		}
		
	}
	
	/**
	 * Check if the same list of selected qualifications from order 
	 * with the selected qualifications of employee from project
	 * @param qualificationCountBySelectedEmployees List of qualifications with counts by employees list ids
	 * @param neededQualifications Qualifications list from order
	 */
	public void validate(Map<Long, Integer> qualificationCountBySelectedEmployees, 
						 Map<Long, Integer> neededQualifications) {
		
		if ( !neededQualifications.equals(qualificationCountBySelectedEmployees) ) {
			setNotValidField(QUALIFICATION);
		}
	}
	
	/**
	 * Validate hours field
	 * @param hours Check if field has a correct type
	 */
	public void validate(String hours) {
		if ( !Validator.isInt(hours) ) {
			setNotValidField(HOURS);
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
	
	/**
	 * Check if searched title has a correct length
	 * @param title Check if field has a correct length
	 * @return
	 */
	public static boolean validateFindedTitle(String title) {
		if ( title == null )
			return false;
		
		title = title.trim();
		return !Validator.isEmpty(title) && title.length() > 3 && title.length() < 250;
	}
	

}
