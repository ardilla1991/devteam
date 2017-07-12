package by.htp.devteam.service.validation;

import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public final class EmployeeValidation extends BeanValidation {
	
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
			if ( Validator.isEmpty(name) || name.length() > 50 ) {
				valid &= false;
				notValidField.add("name");
			}
		} else {
			valid &= false;
			notValidField.add("name");
		}
		
		if ( !Validator.isDate(startWork) ) {
			valid &= false;
			notValidField.add("startWork");
		}

		if ( !Validator.isLong(qualification) ) {
			valid &= false;
			notValidField.add("qualification");
		}
	}
	
	/*
	 * Create Calendar object for date from form
	 */
	private Calendar getCalendarTypeFromString(String date) {
		String[] dateSplit = date.split("-");
		Calendar dateCal = Calendar.getInstance();
		dateCal.set(Calendar.YEAR, Integer.valueOf(dateSplit[0]));
		dateCal.set(Calendar.MONTH, Integer.valueOf(dateSplit[1]));
		dateCal.set(Calendar.DAY_OF_MONTH, Integer.valueOf(dateSplit[2]));
		
		return dateCal;
	}
}
