package by.htp.devteam.service.validation;

import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Validation orders fields. Extends {@link BeanValidation}
 * @author julia
 *
 */
public final class OrderValidation extends BeanValidation{
	
	private final static String TITLE = "title";
	private final static String DESCRIPTION = "description";
	private final static String SPECIFICATION = "specification";
	private final static String QUALIFICATION = "qualification";
	private final static String WORK = "work";
	private final static String DATE_START = "dateStart";
	private final static String DATE_FINISH = "dateFinish";
	
	public OrderValidation() {
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
	public void validate(String title, String description, String specificationFileName, String dateStart, 
						 String dateFinish, String[] workIds, Map<String, String> qualificationsIdsAndCount) {

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
		
		if ( !Validator.isCorrectFileExtension(specificationFileName) || specificationFileName.length() > 50) {
			setNotValidField(SPECIFICATION);
		}
		
		boolean validDateStart = false;
		if ( !Validator.isDate(dateStart) ) {
			setNotValidField(DATE_START);
		} else {
			Calendar cal = Calendar.getInstance();
			Calendar dateStartCal = getCalendarTypeFromString(dateStart);
			if ( !dateStartCal.after(cal) ) {
				setNotValidField(DATE_START);
			} else {
				validDateStart = true;
			}
		}
		
		if ( !Validator.isDate(dateFinish) ) {
			setNotValidField(DATE_FINISH);
		} else if ( validDateStart ) {
			Calendar dateStartCal = getCalendarTypeFromString(dateStart);
			Calendar dateFinishCal = getCalendarTypeFromString(dateFinish);
			if ( !dateFinishCal.after(dateStartCal) ) {
				setNotValidField(DATE_FINISH);
			}
		}
		
		if ( workIds != null ) {
			int workIdsLength = workIds.length;
			for ( int i = 0; i < workIdsLength; i++ ) {
				if ( !Validator.isLong(workIds[i]) ) {
					setNotValidField(WORK);
					break;
				}
			}
		} else {
			setNotValidField(WORK);
		}

		Iterator<Entry<String, String>> it = qualificationsIdsAndCount.entrySet().iterator();
		if ( it.hasNext() ) {
			while ( it.hasNext() ) {
				Map.Entry<String, String> pair = (Map.Entry<String, String>) it.next();
				if ( !Validator.isLong( (String) pair.getKey() ) || !Validator.isInt( (String) pair.getValue() ) ) {
					setNotValidField(QUALIFICATION);
					break;
				}
			}	
		} else {
			setNotValidField(QUALIFICATION);
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
