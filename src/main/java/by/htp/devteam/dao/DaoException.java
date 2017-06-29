package by.htp.devteam.dao;

public class DaoException extends Exception{

	private static final long serialVersionUID = -4499187042513817245L;
	
	private Exception _hidden;
	
	public DaoException(String s) {
		super(s);
	}
	
	DaoException(String s, Exception e) {
		super(s);
		_hidden = e;
	}
	
	public Exception getCommandExeption() {
		return _hidden;
	}
}
