package by.htp.devteam.controller;

/**
 * Exception for Controller layer. Catch Exceptions when object not found.
 * @author julia
 *
 */
public class ObjectNotFoundExeption extends Exception {

	private static final long serialVersionUID = -8518688757532469757L;
	private Exception _hidden;
	
	public ObjectNotFoundExeption(String s) {
		super(s);
	}
	
	public ObjectNotFoundExeption(String s, Exception e) {
		super(s);
		_hidden = e;
	}
	
	public Exception getCommandExeption() {
		return _hidden;
	}
}
