package by.htp.devteam.dao;

/**
 * Exception for DAO layer
 * @author julia
 *
 */
public class DaoException extends Exception{

	private static final long serialVersionUID = -4499187042513817245L;
	
	private Exception _hidden;
	
	public DaoException() {
		super();
	}
	
	public DaoException(String s) {
		super(s);
	}
	
	public DaoException(String s, Exception e) {
		super(s);
		_hidden = e;
	}
	
	public Exception getCommandExeption() {
		return _hidden;
	}
}
