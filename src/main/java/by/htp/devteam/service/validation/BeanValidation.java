package by.htp.devteam.service.validation;

import java.util.ArrayList;
import java.util.List;

/**
 * Validation fields. This is a parent of all beans validations.
 * @author julia
 *
 */
public class BeanValidation {
	
	/** Valid or not fields*/
	boolean valid;
	
	/** List of not valid fields */
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
