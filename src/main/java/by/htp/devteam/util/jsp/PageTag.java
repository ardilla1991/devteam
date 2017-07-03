package by.htp.devteam.util.jsp;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class PageTag extends TagSupport{
	
	private static final long serialVersionUID = 7633809780473295006L;
	private int countPages;
	private int currPage;
	private String uri;
	
	public void setCountPages(int countPages) {
		this.countPages = countPages;
	}
	
	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}
	
	public void setUri(String uri) {
		this.uri = uri;
	}
	
	@Override
	public int doStartTag() throws JspException {
		try {
			String active = "";
			pageContext.getOut().write("<ul class=\"pagination\">");
			for ( int i = 1; i <= countPages; i++ ) {
				active = (i == currPage ? "class='active'" : "");
				pageContext.getOut().write("<li "+active+"><a href='"+uri+"&page="+i+"'>"+i+"</a></li>");
			}
			pageContext.getOut().write("</ul>");
		} catch (IOException e) {
			throw new JspException(e.getMessage());
		}
		return SKIP_BODY;
	}
}
