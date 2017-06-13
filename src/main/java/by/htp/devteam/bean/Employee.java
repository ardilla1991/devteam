package by.htp.devteam.bean;

import java.io.Serializable;
import java.sql.Date;

public class Employee extends User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2004920625723770723L;

	private String login;
	private String password;
	private Date startWork;
	private Qualification qualification;
	private RoleEnum role;
	
	public Employee() {
		
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

	public RoleEnum getRole() {
		return role;
	}

	public void setRole(RoleEnum role) {
		this.role = role;
	}
}
