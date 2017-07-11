package by.htp.devteam.controller;

/**
 * HTTP methods
 * @author julia
 *
 */
public enum HTTPMethod {
	POST("POST"), GET("GET");
	
	private String name; 
	
	private HTTPMethod(String name) {
		this.name = name;
	}
	
	 public String getValue() {
		  return name;
	 }
}
