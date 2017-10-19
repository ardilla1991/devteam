package by.htp.devteam.controller;

/**
 * Exception for Controller layer. Catch Exceptions when object not found.
 * @author julia
 *
 */
public class ObjectNotFoundException extends Exception {

	private static final long serialVersionUID = -8518688757532469757L;
	
	public ObjectNotFoundException(String s) {
		super(s);
	}
	
	public ObjectNotFoundException(String s, Exception e) {
		super(s, e);
	}
	
	public ObjectNotFoundException(Exception e) {
		super(e);
	}
}
