package by.htp.devteam.controller;

/**
 * Exception for Controller layer. Catch Exceptions when user select not valid module.
 * @author julia
 *
 */
public class ControllerException extends Exception {

	private static final long serialVersionUID = 1390775581343738448L;

	public ControllerException(String message) {
		super(message);
	}
	
	public ControllerException(String message, Exception e) {
		super(message, e);
	}
	
	public ControllerException(Exception e) {
		super(e);
	}
}
