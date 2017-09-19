package by.htp.devteam.util.jsp;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import by.htp.devteam.bean.vo.PagingVo;
import static by.htp.devteam.controller.util.ConstantValue.*;

/**
 * Tag for display pages
 * @author julia
 *
 */
public final class PageTag extends TagSupport {
	
	private static final long serialVersionUID = 7633809780473295006L;
	
	/** Paging {@link  by.htp.devteam.bean.vo.PagingVo} */
	private PagingVo pagingVo;
	
	/** Container tag for all points */
	private String containerTag;
	
	/** Container class for all points */	
	private String containerClass;
	
	/** Item tag for point */
	private String itemTag;
	
	/** Item tag class for selected points */
	private String currActionClass;
	
	/** Application name and language */
	private String appNameAndLang;
	
	public PageTag() {
		super();
	}
	
	public void setPagingVo(PagingVo pagingVo) {
		this.pagingVo = pagingVo;
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
	
	public void setAppNameAndLang(String appNameAndLang) {
		this.appNameAndLang = appNameAndLang;
	}
	
	/**
	 * Display pages
	 */
	@Override
	public int doStartTag() throws JspException {
		try {
			String active = null;
			pageContext.getOut().write("<" + containerTag + " class=\"" + containerClass + "\">");
			for ( int i = 1; i <= pagingVo.getCountPages(); i++ ) {
				active = (i == pagingVo.getCurrPage() ? "class=\"" + currActionClass + "\"" : "");
				pageContext.getOut().write("<" + itemTag + " " + active + "><a href=\"" + appNameAndLang + URL_DELIMITER + pagingVo.getUri() + "/page/" + i + "\">" 
											+ i + "</a></" + itemTag + ">");
			}
			pageContext.getOut().write("</" + containerTag + ">");
		} catch (IOException e) {
			throw new JspException(e.getMessage());
		}
		
		return SKIP_BODY;
	}
}
