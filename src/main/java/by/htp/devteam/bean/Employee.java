package by.htp.devteam.bean;

import java.io.Serializable;
import java.sql.Date;

/**
 * Employee's object. Has properties <b>id</b>, <b>name</b>, 
 * <b>startWork</b>, <b>qualification</b>, <b>user</b>
 * Implemant Serializable, because object is saved in session
 * @author julia
 *
 */
public class Employee implements Serializable {
	
	private static final long serialVersionUID = -2004920625723770723L;

	/** Identifier for record */
	private Long id;
	
	/** Employee's name */
	private String name;
	
	/** Employee's date of start working */
	private Date startWork;
	
	/** Employee's qualification */
	private Qualification qualification;
	
	/** Customer's user to enter into system*/
	private User user;
	
	public Employee() {
		super();
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((qualification == null) ? 0 : qualification.hashCode());
		result = prime * result + ((startWork == null) ? 0 : startWork.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (qualification == null) {
			if (other.qualification != null)
				return false;
		} else if (!qualification.equals(other.qualification))
			return false;
		if (startWork == null) {
			if (other.startWork != null)
				return false;
		} else if (!startWork.equals(other.startWork))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

}
