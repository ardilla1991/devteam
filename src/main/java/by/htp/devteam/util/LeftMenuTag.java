package by.htp.devteam.util;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import static by.htp.devteam.util.ConstantValue.*;
import by.htp.devteam.command.CommandEnum;

import by.htp.devteam.bean.User;

public class LeftMenuTag extends TagSupport{

	private static final long serialVersionUID = -5906908027211026605L;
	private User user;
	private String currAction;
	private String containerTag;
	private String containerClass;
	private String itemTag;
	private String currActionClass;
	private String language;
	private String country;
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public void setCurrAction(String currAction) {
		this.currAction = currAction;
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
	
	public void setLanguage(String language) {
		this.language = language;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public int doStartTag() throws JspException {
		try {
			Locale locale = new Locale(language, country);
			ResourceBundle rb = ResourceBundle.getBundle("text", locale);
			
			String active = "";
			pageContext.getOut().write("<" + containerTag + " class=\"" + containerClass + "\">");
			switch (user.getRole()) {
				case CUSTOMER:
					//active = currAction.equals("order_list") ? currActionClass : "";
					//pageContext.getOut().write("<" + itemTag + " class=\"" + active + "\"><a href='" + "Main?action=order_list" + "'>" + "Orders" + "</a></" + itemTag + ">");
					break;
				case MANAGER:
					active = currAction.equals("project_list") ? currActionClass : "";
					pageContext.getOut().write("<" + itemTag + " class=\"" + active + "\"><a href='" + "Main?action=project_list" + "'>" + rb.getString("menu.left.projects") + "</a></" + itemTag + ">");
					break;
				case DEVELOPER:
					active = currAction.equals("project_list_by_employee_id") ? currActionClass : "";
					pageContext.getOut().write("<" + itemTag + " class=\"" + active + "\"><a href='" + "Main?action=project_list_by_employee_id" + "'>" + rb.getString("menu.left.projects") + "</a></" + itemTag + ">");
					break;
				default:
					break;
			}
			pageContext.getOut().write("</" +containerTag  + ">");			
		} catch (IOException e) {
			throw new JspException(e.getMessage());
		}
		return SKIP_BODY;
	}
}