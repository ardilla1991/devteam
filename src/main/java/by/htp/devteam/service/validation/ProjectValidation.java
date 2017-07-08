package by.htp.devteam.service.validation;

import java.util.Map;

public class ProjectValidation extends BeanValidation{
	
	public ProjectValidation() {
		super();
	}
	
	public void validate(String title, String description, String[] employees, String price) {
		
		title = title.trim();
		if ( Validator.isEmpty(title) ) {
			valid &= false;
			notValidField.add("title");
		}
		
		description = description.trim();
		if ( Validator.isEmpty(description) ) {
			valid &= false;
			notValidField.add("description");
		}
		
		int workIdsLength = employees.length;
		for ( int i = 0; i < workIdsLength; i++ ) {
			if ( !Validator.isLong(employees[i]) ) {
				valid &= false;
				notValidField.add("employee");
				break;
			}
		}
		
		if ( Validator.isEmpty(price) || !Validator.checkBigDecimal(price) ) {
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
