package by.htp.devteam.service.util;

public enum ErrorCodeEnum {
	VALIDATION(1),
	APPLICATION(2),
	FILE_LIMIT_SIZE(3),
	FILE_UPLOAD(4),
	NO_SUCH_USER(6),
	INCORRECT_PASSWORD(7),
	PAGE_NUMBER_NOT_FOUND(8),
	FILE_DELETE(9),
	NOT_ISSET_FREE_EMPLOYEE(10),
	VALIDATION_ID(11);
	
	
	private int code; 
	
	private ErrorCodeEnum(int code) {
		this.code = code;
	}
	
	 public int getValue() {
		  return code;
	 }
}
