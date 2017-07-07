package by.htp.devteam.service.validation;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import by.htp.devteam.bean.Qualification;
import by.htp.devteam.service.util.Validator;

public final class OrderValidation extends BeanValidation{
	
	public OrderValidation() {
		super();
	}
	
	public void validate(String title, String description, String specificationFileName, String dateStart, String dateFinish,
			String[] workIds, Map<String, String> qualificationsIdsAndCount) {

		title = title.trim();
		if ( Validator.isEmpty(title) ) {
			valid &= false;
			notValidField.add("title");
		}
		
		if ( Validator.isEmpty(description) ) {
			valid &= false;
			notValidField.add("description");
		}
		
		if ( !Validator.isCorrectFileExtension(specificationFileName) ) {
			valid &= false;
			notValidField.add("specification");
		}
		
		if ( !Validator.isDate(dateStart) ) {
			valid &= false;
			notValidField.add("dateStart");
		}
		
		if ( !Validator.isDate(dateFinish) ) {
			valid &= false;
			notValidField.add("dateFinish");
		}
		
		int workIdsLength = workIds.length;
		for ( int i = 0; i < workIdsLength; i++ ) {
			if ( !Validator.isLong(workIds[i]) ) {
				valid &= false;
				notValidField.add("work");
				break;
			}
		}

		Iterator<Entry<String, String>> it = qualificationsIdsAndCount.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			Qualification qualification = new Qualification();
			qualification.setId(Long.valueOf((String) pair.getKey()));
			if ( !Validator.isLong( (String) pair.getKey() ) || !Validator.isInt( (String) pair.getValue() ) ) {
				valid &= false;
				notValidField.add("qualification");
				break;
			}
			
		}	
	}
	
}
