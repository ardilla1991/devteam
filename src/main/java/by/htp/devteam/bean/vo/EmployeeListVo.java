package by.htp.devteam.bean.vo;

import java.util.List;

import by.htp.devteam.bean.Employee;

/**
 * Employee's list object. Use for display list of objects with pagings settings.
 * Has properties <b>employees</b>, <b>countRecords</b>,
 * <b>countPages</b>, <b>currPage</b>
 * @author julia
 *
 */
public class EmployeeListVo {
	
	/** List of users (userVo) on one page for display*/
	private List<Employee> employees;
	
	/** Count records in full list*/
	private int countRecords;
	
	/** Count pages for dysplay in paging*/
	private int countPages;
	
	/** Current selected page*/
	private int currPage;
	
	public EmployeeListVo() {
		super();
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
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
