package by.htp.devteam.bean;

import java.io.Serializable;

/**
 * User's object to enter into system. Have properties <b>id</b>, <b>title</b>, 
 * <b>service</b>.
 * Implemant Serializable, because object is saved in session
 * @author julia
 *
 */
public class Qualification implements Serializable {
	
	private static final long serialVersionUID = 3700326780172626583L;
	
	/** Identifier for record */
	private Long id;
	
	/** Qualification's title */
	private String title;
	
	/** Property to separate qualifications for projects and only for system administration */
	private boolean service;
	
	public Qualification() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isService() {
		return service;
	}

	public void setService(boolean service) {
		this.service = service;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (service ? 1231 : 1237);
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		Qualification other = (Qualification) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (service != other.service)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

}
