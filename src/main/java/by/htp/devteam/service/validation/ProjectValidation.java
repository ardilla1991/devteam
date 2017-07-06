package by.htp.devteam.service.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import by.htp.devteam.service.util.Validator;

public class ProjectValidation {
	private boolean valid;
	private List<String> notValidField;
	
	public ProjectValidation() {
		super();
		valid = true;
		notValidField = new ArrayList<String>();
	}

	public boolean isValid() {
		return valid;
	}
	
	public List<String> getNotValidField() {
		return notValidField;
	}
	
	public void validate(String title, String description, String[] employees, String price) {
		
		title = title.trim();
		if ( Validator.isEmpty(title) ) {
			valid &= false;
			notValidField.add("title");
		}
		
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
	

}
