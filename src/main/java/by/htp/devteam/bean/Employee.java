package by.htp.devteam.bean;

import java.io.Serializable;
import java.sql.Date;

public class Employee implements Serializable{
	
	private static final long serialVersionUID = -2004920625723770723L;

	private Long id;
	private String name;
	private Date startWork;
	private Qualification qualification;
	private User user;
	
	public Employee() {
		super();
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

	@Override
	public String toString() {
		return "Employee [startWork=" + startWork + ", qualification=" + qualification + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
