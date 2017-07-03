package by.htp.devteam.bean.dto;

import java.util.List;

import by.htp.devteam.bean.Order;

public class OrderListVo {
	private List<Order> orders;
	private int countRecords;
	private int countPages;
	private int currPage;
	
	public List<Order> getOrders() {
		return orders;
	}
	
	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	
	public int getCountRecords() {
		return countRecords;
	}
	
	public void setCountRecords(int countRecords) {
		this.countRecords = countRecords;
	}

	public int getCountPages() {
		return countPages;
	}

	public void setCountPages(int countPages) {
		this.countPages = countPages;
	}

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}
}
