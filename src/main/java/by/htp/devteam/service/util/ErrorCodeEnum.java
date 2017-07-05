package by.htp.devteam.service.util;

public enum ErrorCodeEnum {
	VALIDATION_ERROR(1),
	APPLICATION_ERROR(2);
	
	private int code; 
	
	private ErrorCodeEnum(int code) {
		this.code = code;
	}
	
	 public int getValue() {
		  return code;
	 }
}
