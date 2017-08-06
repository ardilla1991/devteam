package by.htp.devteam.controller;

/**
 * Exception for Controller layer. Catch Exceptions when user select not valid module
 * @author julia
 *
 */
public class ControllerExeption extends Exception {
	
	private static final long serialVersionUID = 2879996083995167679L;
	private Exception _hidden;
	
	public ControllerExeption(String s) {
		super(s);
	}
	
	public ControllerExeption(String s, Exception e) {
		super(s);
		_hidden = e;
	}
	
	public Exception getCommandExeption() {
		return _hidden;
	}
}
