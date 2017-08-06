package by.htp.devteam.controller.main;

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
	
	/** Is needed include to display page */
	private boolean isInclude;	
	
	public Page() {
		super();
	}
	
	public Page(String page) {
		this.page = page;
		this.isRedirect = false;
		this.isInclude = false;
	}
	
	public Page(String page, boolean isRedirect) {
		this.page = page;
		this.isRedirect = isRedirect;
		this.isInclude = false;
	}
	
	public Page(boolean isInclude, String page) {
		this.page = page;
		this.isInclude = isInclude;
		this.isRedirect = false;
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

	public boolean isInclude() {
		return isInclude;
	}

	public void setInclude(boolean isInclude) {
		this.isInclude = isInclude;
	}
}
