package by.htp.devteam.bean;

import java.io.Serializable;

public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8145171260868930694L;
	
	private Long id;
	private String name;
	
	public User() {
		
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RoleEnum getRole() {
		return this.getRole();
	}
	
}
