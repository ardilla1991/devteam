package by.htp.devteam.service.validation;

import java.util.ArrayList;
import java.util.List;

import by.htp.devteam.service.util.Validator;

public class BeanValidation {
	boolean valid;
	List<String> notValidField;
	
	public BeanValidation() {
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
	
	public boolean validateId(String id) {
		return Validator.isLong(id);
	}
	
}
