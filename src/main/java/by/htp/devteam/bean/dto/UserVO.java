package by.htp.devteam.bean.dto;

import by.htp.devteam.bean.Customer;
import by.htp.devteam.bean.Employee;
import by.htp.devteam.bean.User;

public class UserVO {
	private User user;
	private Customer customer;
	private Employee employee;
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public Customer getCustomer() {
		return customer;
	}
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public Employee getEmployee() {
		return employee;
	}
	
	@Override
	public String toString() {
		return "UserVO [user=" + user + ", customer=" + customer + ", employee=" + employee + "]";
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
}
