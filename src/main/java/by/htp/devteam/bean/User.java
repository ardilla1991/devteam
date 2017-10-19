package by.htp.devteam.bean;

import java.io.Serializable;

/**
 * User's object to enter into system. Has properties <b>id</b>, <b>login</b>, 
 * <b>password</b>, <b>role</b>. For password use BCrypt encrypting
 * Implemant Serializable, because object is saved in session
 * @author julia
 *
 */
public class User extends Bean implements Serializable {

	private static final long serialVersionUID = 3501052917938154138L;
	
	/** User's login */
	private String login;
	
	/** Users's password */
	private String password;
	
	/** Employee's date of start working */
	private UserRole role;
	
	public User() {
		super();
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

	public UserRole getRole() {
		return this.role;
	}
	
	public void setRole(UserRole role) {
		this.role = role;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((login == null) ? 0 : login.hashCode());
		result = PRIME * result + ((password == null) ? 0 : password.hashCode());
		result = PRIME * result + ((role == null) ? 0 : role.hashCode());
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
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (role != other.role)
			return false;
		return true;
	}
	
}
