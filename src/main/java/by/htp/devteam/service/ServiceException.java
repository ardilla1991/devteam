package by.htp.devteam.service;

import java.util.List;

import by.htp.devteam.service.util.ErrorCode;

/**
 * Exception for Service layer. Catch Exceptions from DAO layer
 * @author julia
 *
 */
public class ServiceException extends Exception{

	private static final long serialVersionUID = 732081448626146873L;
	private Exception _hidden;
	
	/** Fields that not valid */
	private List<String> fields;
	
	/**
	 * Error code
	 * @see by.htp.devteam.service.util.ErrorCode
	 */
	private ErrorCode errorCode;
	
	public ServiceException(String s) {
		super(s);
	}
	
	public ServiceException(String s, Exception e) {
		super(s);
		_hidden = e;
	}
	
	public ServiceException(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}
	
	public ServiceException(String s, ErrorCode errorCode) {
		super(s);
		this.errorCode = errorCode;
	}
	
	public ServiceException(String s, ErrorCode errorCode, Exception e) {
		super(s);
		this.errorCode = errorCode;
		_hidden = e;
	}
	
	public ServiceException(ErrorCode errorCode, List<String> massages) {
		this.errorCode = errorCode;
		this.fields = massages;
	}
	
	public Exception getCommandExeption() {
		return _hidden;
	}

	public List<String> getFields() {
		return fields;
	}

	public void setFields(List<String> fields) {
		this.fields = fields;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}
}
