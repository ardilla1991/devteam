package by.htp.devteam.service;

import java.util.List;

import by.htp.devteam.service.util.ErrorCode;

/**
 * Exception for Service layer. Catch Exceptions from DAO layer
 * @author julia
 *
 */
public class ServiceException extends Exception{

	private static final long serialVersionUID = 1423465744460310398L;

	/** Fields that not valid */
	private List<String> fields;
	
	/**
	 * Error code
	 * @see by.htp.devteam.service.util.ErrorCode
	 */
	private ErrorCode errorCode;
	
	public ServiceException(String message) {
		super(message);
	}
	
	public ServiceException(String message, Exception e) {
		super(message, e);
	}
	
	public ServiceException(Exception e) {
		super(e);
	}
	
	public ServiceException(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}
	
	public ServiceException(String message, ErrorCode errorCode) {
		super(message);
		this.errorCode = errorCode;
	}
	
	public ServiceException(String message, ErrorCode errorCode, Exception e) {
		super(message, e);
		this.errorCode = errorCode;
	}
	
	public ServiceException(ErrorCode errorCode, List<String> fields) {
		this.errorCode = errorCode;
		this.fields = fields;
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
