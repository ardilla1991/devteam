package by.htp.devteam.controller;

/**
 * Page object for view layer
 * @author julia
 *
 */
public class Page {
	
	/** Page path (jsp or uri) */
	private String page;
	
	/** Is needed redirect to display page */
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
	
	public String getPage() {
		return page;
	}
	
	public void setPage(String page) {
		this.page = page;
	}

	public boolean isRedirect() {
		return isRedirect;
	}

	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}
}
