package by.htp.devteam.service.validation;

import java.util.Map;

public class ProjectValidation extends BeanValidation{
	
	public ProjectValidation() {
		super();
	}
	
	public void validate(String title, String description, String[] employees, String price) {
		
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
		
		if ( employees != null ) {
			int workIdsLength = employees.length;
			for ( int i = 0; i < workIdsLength; i++ ) {
				if ( !Validator.isLong(employees[i]) ) {
					valid &= false;
					notValidField.add("employee");
					break;
				}
			}
		} else {
			valid &= false;
			notValidField.add("employee");
		}
		
		if ( Validator.isEmpty(price) || !Validator.checkBigDecimal(price) || price.length() > 11) {
			valid &= false;
			notValidField.add("price");
		}
		
	}
	
	public void validate(Map<Long, Integer> qualificationCountByEmployees, Map<Long, Integer> neededQualifications) {
		
		if ( !neededQualifications.equals(qualificationCountByEmployees) ) {
			valid &= false;
			notValidField.add("qualification");
		}
		
	}
	
	public void validate(String hours) {
		if ( !Validator.isLong(hours) ) {
			valid &= false;
			notValidField.add("hours");
		}
	}
	
	public boolean validatePage(String pageNumber) {
		return Validator.isInt(pageNumber) && Integer.valueOf(pageNumber) > 0;
	}
	

}
