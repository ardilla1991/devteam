package by.htp.devteam.service;

public class ServiceException extends Exception{

	private static final long serialVersionUID = 732081448626146873L;
	private Exception _hidden;
	
	public ServiceException(String s) {
		super(s);
	}
	
	ServiceException(String s, Exception e) {
		super(s);
		_hidden = e;
	}
	
	public Exception getCommandExeption() {
		return _hidden;
	}
}
