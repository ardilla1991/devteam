package by.htp.devteam.module.util;

/**
 * Exception for Sequrity layer. Catch Exceptions when form token is not valid
 * @author julia
 *
 */
public class SecurityException extends Exception {

	private static final long serialVersionUID = 2471513168882599652L;
	private Exception _hidden;
	
	public SecurityException(String s) {
		super(s);
	}
	
	public SecurityException(String s, Exception e) {
		super(s);
		_hidden = e;
	}
	
	public Exception getCommandExeption() {
		return _hidden;
	}
}
