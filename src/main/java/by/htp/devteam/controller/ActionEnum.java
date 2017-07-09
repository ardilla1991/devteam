package by.htp.devteam.controller;

public enum ActionEnum {
	POST("POST"), GET("GET");
	
	private String name; 
	
	private ActionEnum(String name) {
		this.name = name;
	}
	
	 public String getValue() {
		  return name;
	 }
}
