package by.htp.devteam.controller;

public class Page {
	
	private String page;
	private boolean isInclude;
	private boolean isRedirect;
	
	public Page() {
		super();
	}
	
	public Page(String page) {
		this.page = page;
		this.isRedirect = false;
	}
	
	public Page(String page, boolean isRedirect) {
		this.page = page;
		this.isRedirect = isRedirect;
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

	public boolean isRedirect() {
		return isRedirect;
	}

	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}
}
