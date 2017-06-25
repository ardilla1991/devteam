package by.htp.devteam.bean;

import java.io.Serializable;

public class User implements Serializable{

	private static final long serialVersionUID = 3501052917938154138L;
	private Long id;
	private String login;
	private String password;
	private RoleEnum role;
	
	public User() {
		super();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public RoleEnum getRole() {
		return this.role;
	}
	
	public void setRole(RoleEnum role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", login=" + login + ", password=" + password + ", role=" + role + "]";
	}
	
}
