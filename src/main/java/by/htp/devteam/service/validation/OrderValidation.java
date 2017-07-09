package by.htp.devteam.service.validation;

import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import by.htp.devteam.bean.Qualification;

public final class OrderValidation extends BeanValidation{
	
	public OrderValidation() {
		super();
	}
	
	public void validate(String title, String description, String specificationFileName, String dateStart, String dateFinish,
			String[] workIds, Map<String, String> qualificationsIdsAndCount) {

		title = title.trim();
		if ( Validator.isEmpty(title) || title.length() > 250 ) {
			valid &= false;
			notValidField.add("title");
		}
		
		description = description.trim();
		if ( Validator.isEmpty(description) || description.length() > 2000 ) {
			valid &= false;
			notValidField.add("description");
		}
		
		if ( !Validator.isCorrectFileExtension(specificationFileName) || specificationFileName.length() > 50) {
			valid &= false;
			notValidField.add("specification");
		}
		
		boolean validDateStart = false;
		if ( !Validator.isDate(dateStart) ) {
			valid &= false;
			notValidField.add("dateStart");
		} else {
			Calendar cal = Calendar.getInstance();
			Calendar dateStartCal = getCalendarTypeFromString(dateStart);
			if ( !dateStartCal.after(cal) ) {
				valid &= false;
				notValidField.add("dateStart");
			} else {
				validDateStart = true;
			}
		}
		
		if ( !Validator.isDate(dateFinish) ) {
			valid &= false;
			notValidField.add("dateFinish");
		} else if ( validDateStart ) {
			Calendar dateStartCal = getCalendarTypeFromString(dateStart);
			Calendar dateFinishCal = getCalendarTypeFromString(dateFinish);
			if ( !dateFinishCal.after(dateStartCal) ) {
				valid &= false;
				notValidField.add("dateFinish");
			}
		}
		
		if ( workIds != null ) {
			int workIdsLength = workIds.length;
			for ( int i = 0; i < workIdsLength; i++ ) {
				if ( !Validator.isLong(workIds[i]) ) {
					valid &= false;
					notValidField.add("work");
					break;
				}
			}
		} else {
			valid &= false;
			notValidField.add("work");
		}

		Iterator<Entry<String, String>> it = qualificationsIdsAndCount.entrySet().iterator();
		if ( it.hasNext() ) {
			while ( it.hasNext() ) {
				Map.Entry pair = (Map.Entry) it.next();
				Qualification qualification = new Qualification();
				qualification.setId(Long.valueOf((String) pair.getKey()));
				if ( !Validator.isLong( (String) pair.getKey() ) || !Validator.isInt( (String) pair.getValue() ) ) {
					valid &= false;
					notValidField.add("qualification");
					break;
				}
			}	
		} else {
			valid &= false;
			notValidField.add("qualification");
		}
	}
	
	public boolean validatePage(String pageNumber) {
		return Validator.isInt(pageNumber) && Integer.valueOf(pageNumber) > 0;
	}
	
	private Calendar getCalendarTypeFromString(String date) {
		String[] dateSplit = date.split("-");
		Calendar dateCal = Calendar.getInstance();
		dateCal.set(Calendar.YEAR, Integer.valueOf(dateSplit[0]));
		dateCal.set(Calendar.MONTH, Integer.valueOf(dateSplit[1]));
		dateCal.set(Calendar.DAY_OF_MONTH, Integer.valueOf(dateSplit[2]));
		
		return dateCal;
	}
	
}
