package by.htp.devteam.bean.vo;

import java.util.List;

import by.htp.devteam.bean.Order;

/**
 * Orders list object. Use for display list of objects with pagings settings.
 * Has properties <b>orders</b>, <b>countRecords</b>, 
 * <b>countPages</b>, <b>currPage</b>
 * @author julia
 *
 */
public class OrderListVo {
	
	/** List of orders on one page for display*/
	private List<Order> orders;
	
	/** Count records in full list*/
	private int countRecords;
	
	/** Count pages for dysplay in paging*/
	private int countPages;
	
	/** Current selected page*/
	private int currPage;
	
	public OrderListVo() {
		super();
	}
	
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
