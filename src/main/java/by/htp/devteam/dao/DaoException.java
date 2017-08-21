package by.htp.devteam.dao;

/**
 * Exception for DAO layer
 * Use when we catch SQLException and other exception that destroy normal system's work
 * @author julia
 *
 */
public class DaoException extends Exception{
	
	private static final long serialVersionUID = 8093725306740424011L;

	public DaoException() {
		super();
	}
	
	public DaoException(String s) {
		super(s);
	}
	
	public DaoException(String s, Exception e) {
		super(s, e);
	}
	
	public DaoException(Exception e) {
		super(e);
	}
}
