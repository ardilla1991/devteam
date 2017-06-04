package by.htp.devteam.bean;

import java.sql.Date;

public class Employee {
	
	private Long id;
	private String name;
	private String login;
	private String password;
	private Date startWork;
	private Qualification qualification;
	private int role;
	
	public Employee() {
		
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
	
	public Date getStartWork() {
		return startWork;
	}
	
	public void setStartWork(Date startWork) {
		this.startWork = startWork;
	}
	
	public Qualification getQualification() {
		return qualification;
	}
	
	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
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

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}
}
