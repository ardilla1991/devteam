package by.htp.devteam.bean;

import java.util.Date;
import java.util.Set;

/**
 * Project's object. Project is created after creating order (if isset not busy employees for development perion)
 * Has properties <b>id</b>, <b>title</b>, 
 * <b>description</b>, <b>dateCreated</b>, <b>order</b>.
 * @author julia
 *
 */
public class Project extends Bean {
	
	/** Project's title */
	private String title;
	
	/** Project's description */
	private String description;
	
	/** Project's date created */
	private Date dateCreated;
	
	/** Project's order */
	private Order order;
	
	private Set<ProjectEmployee> employees;

	public Project() {
		super();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
	public Set<ProjectEmployee> getEmployees() {
		return employees;
	}

	public void setEmployees(Set<ProjectEmployee> employees) {
		this.employees = employees;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((dateCreated == null) ? 0 : dateCreated.hashCode());
		result = PRIME * result + ((description == null) ? 0 : description.hashCode());
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((order == null) ? 0 : order.hashCode());
		result = PRIME * result + ((title == null) ? 0 : title.hashCode());
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
		Project other = (Project) obj;
		if (dateCreated == null) {
			if (other.dateCreated != null)
				return false;
		} else if (!dateCreated.equals(other.dateCreated))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (order == null) {
			if (other.order != null)
				return false;
		} else if (!order.equals(other.order))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Project [title=" + title + ", description=" + description + ", dateCreated=" + dateCreated + ", order="
				+ order + ", employees=" + employees + "]";
	}
	
}
