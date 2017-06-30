package by.htp.devteam.bean;

import java.math.BigDecimal;
import java.sql.Date;

public class Order {



	private Long id;
	private String title;
	private String description;
	private String specification;
	private Customer customer;
	private boolean status;
	private Date dateCreated;
	private Date dateStart;
	private Date dateFinish;
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

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
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
	
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", title=" + title + ", description=" + description + ", specification="
				+ specification + ", customer=" + customer + ", status=" + status + ", dateCreated=" + dateCreated
				+ ", dateStart=" + dateStart + ", dateFinish=" + dateFinish + ", price=" + price + "]";
	}
	


}
