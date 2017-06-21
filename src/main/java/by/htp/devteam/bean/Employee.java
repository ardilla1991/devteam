package by.htp.devteam.bean;

import java.io.Serializable;
import java.sql.Date;

public class Employee extends User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2004920625723770723L;


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

	public RoleEnum getRole() {
		return role;
	}

	public void setRole(RoleEnum role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Employee [startWork=" + startWork + ", qualification=" + qualification + ", role=" + role + "]";
	}
}
