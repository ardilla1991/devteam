package by.htp.devteam.util.jsp;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import by.htp.devteam.bean.User;
import static by.htp.devteam.command.util.ConstantValue.*;

public class TopMenuTag extends TagSupport{

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
		String active = "";
		try {

			Locale locale = new Locale(language, country);
			ResourceBundle rb = ResourceBundle.getBundle("text", locale);

			pageContext.getOut().write("<" + containerTag + " class=\"" + containerClass + "\">");
			switch (user.getRole()) {
				case CUSTOMER:
					active = currAction.equals("order_list") ? currActionClass : "";
					pageContext.getOut().write("<" + itemTag + " class=\"" + active + "\"><a href='" + PAGE_ORDER_LIST_URI + "'>" + rb.getString("menu.top.orders") + "</a></" + itemTag + ">");
					break;
				case MANAGER:
					active = currAction.equals("order_new_list") ? currActionClass : "";
					pageContext.getOut().write("<" + itemTag + " class=\"" + active + "\"><a href='" + PAGE_ORDER_NEW_LIST_URI + "'>" + rb.getString("menu.top.orders.new") + "</a></" + itemTag + ">");
					break;
				case DEVELOPER:
					break;
				default:
					break;
			}
			active = currAction.equals("user_view") ? currActionClass : "";
			pageContext.getOut().write("<" + itemTag + " class=\"" + active + "\"><a href='" + PAGE_USER_VIEW_URI +"'>" + user.getLogin() + "</a></" + itemTag + ">");
			pageContext.getOut().write("<" + itemTag + "><a href='" + PAGE_LOGOUT +"'>" + rb.getString("menu.top.logout") + "</a></" + itemTag + ">");
			pageContext.getOut().write("</" +containerTag  + ">");			
		} catch (IOException e) {
			throw new JspException(e.getMessage());
		}
		return SKIP_BODY;
	}

}
