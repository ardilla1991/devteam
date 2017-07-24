package by.htp.devteam.bean;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * Order's object. Has properties <b>id</b>, <b>title</b>, 
 * <b>description</b>, <b>specification</b>, <b>customer</b>,
 * <b>dateCreated</b>, <b>dateStart</b>, <b>dateFinish</b>, 
 * <b>dateProcessing</b>, <b>price</b>
 * @author julia
 *
 */
public class Order {

	/** Identifier for record */
	private Long id;
	
	/** Order's title */
	private String title;
	
	/** Order's description */
	private String description;
	
	/** Order's specification (filename) */
	private String specification;
	
	/** Order's customer */
	private Customer customer;
	
	/** Order's date created */
	private Date dateCreated;
	
	/** Order's date start */
	private Date dateStart;
	
	/** Order's date finish */
	private Date dateFinish;
	
	/** Order's processing date */
	private Date dateProcessing;
	
	/** Order's price */
	private BigDecimal price;
	
	public Order() {
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Date getDateFinish() {
		return dateFinish;
	}

	public void setDateFinish(Date dateFinish) {
		this.dateFinish = dateFinish;
	}
	
	public Date getDateProcessing() {
		return dateProcessing;
	}

	public void setDateProcessing(Date dateProcessing) {
		this.dateProcessing = dateProcessing;
	}
	
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + ((dateCreated == null) ? 0 : dateCreated.hashCode());
		result = prime * result + ((dateFinish == null) ? 0 : dateFinish.hashCode());
		result = prime * result + ((dateProcessing == null) ? 0 : dateProcessing.hashCode());
		result = prime * result + ((dateStart == null) ? 0 : dateStart.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((specification == null) ? 0 : specification.hashCode());
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
		Order other = (Order) obj;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		if (dateCreated == null) {
			if (other.dateCreated != null)
				return false;
		} else if (!dateCreated.equals(other.dateCreated))
			return false;
		if (dateFinish == null) {
			if (other.dateFinish != null)
				return false;
		} else if (!dateFinish.equals(other.dateFinish))
			return false;
		if (dateProcessing == null) {
			if (other.dateProcessing != null)
				return false;
		} else if (!dateProcessing.equals(other.dateProcessing))
			return false;
		if (dateStart == null) {
			if (other.dateStart != null)
				return false;
		} else if (!dateStart.equals(other.dateStart))
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
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (specification == null) {
			if (other.specification != null)
				return false;
		} else if (!specification.equals(other.specification))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	
}
