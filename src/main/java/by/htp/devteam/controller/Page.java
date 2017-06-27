package by.htp.devteam.controller;

public class Page {
	
	private String page;
	private boolean isInclude;
	
	public Page() {
		super();
	}
	
	public Page(String page) {
		this.page = page;
		isInclude = false;
	}
	
	public Page(String page, int include) {
		this.page = page;
		this.isInclude = include == 1 ? true : false;
	}
	
	public String getPage() {
		return page;
	}
	
	public void setPage(String page) {
		this.page = page;
	}
	
	public boolean isInclude() {
		return isInclude;
	}
	
	public void setInclude(boolean isInclude) {
		this.isInclude = isInclude;
	}
}
