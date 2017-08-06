package by.htp.devteam.util.jsp;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Tag for display pages
 * @author julia
 *
 */
public final class PageTag extends TagSupport {
	
	private static final long serialVersionUID = 7633809780473295006L;
	
	/** Count pages */
	private int countPages;
	
	/** Current page number */
	private int currPage;
	
	/** Container tag for all points */
	private String containerTag;
	
	/** Container class for all points */	
	private String containerClass;
	
	/** Item tag for point */
	private String itemTag;
	
	/** Item tag class for selected points */
	private String currActionClass;
	
	/** Uri for pages */
	private String uri;
	
	public PageTag() {
		super();
	}
	
	public void setCountPages(int countPages) {
		this.countPages = countPages;
	}
	
	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}
	
	public void setContainerTag(String containerTag) {
		this.containerTag = containerTag;
	}

	public void setContainerClass(String containerClass) {
		this.containerClass = containerClass;
	}

	public void setItemTag(String itemTag) {
		this.itemTag = itemTag;
	}

	public void setCurrActionClass(String currActionClass) {
		this.currActionClass = currActionClass;
	}
	
	public void setUri(String uri) {
		this.uri = uri;
	}
	
	/**
	 * Display pages
	 */
	@Override
	public int doStartTag() throws JspException {
		try {
			String active = null;
			pageContext.getOut().write("<" + containerTag + " class=\"" + containerClass + "\">");
			for ( int i = 1; i <= countPages; i++ ) {
				active = (i == currPage ? "class=\"" + currActionClass + "\"" : "");
				pageContext.getOut().write("<" + itemTag + " " + active + "><a href=\"" + uri + "?page=" + i + "\">" 
											+ i + "</a></" + itemTag + ">");
			}
			pageContext.getOut().write("</" + containerTag + ">");
		} catch (IOException e) {
			throw new JspException(e.getMessage());
		}
		
		return SKIP_BODY;
	}
}
