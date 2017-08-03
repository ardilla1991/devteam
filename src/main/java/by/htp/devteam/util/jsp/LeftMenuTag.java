package by.htp.devteam.util.jsp;

import static by.htp.devteam.module.util.ConstantValue.*;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import by.htp.devteam.bean.User;

/**
 * Display left bar for user
 * @author julia
 *
 */
public final class LeftMenuTag extends TagSupport{

	private static final long serialVersionUID = -5906908027211026605L;
	
	/** User */
	private User user;
	
	/** Current action. Use for set active class for selected action */
	private String currAction;
	
	/** Container tag for all points */
	private String containerTag;
	
	/** Container class for all points */	
	private String containerClass;
	
	/** Item tag for point */
	private String itemTag;
	
	/** Item tag class for selected points */
	private String currActionClass;
	
	/** Current language */
	private String language;
	
	/** Current country */
	private String country;
	
	public LeftMenuTag() {
		super();
	}
	
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

	/**
	 * Create bar
	 */
	@Override
	public int doStartTag() throws JspException {
		try {
			Locale locale = new Locale(language, country);
			ResourceBundle rb = ResourceBundle.getBundle("text", locale);
			
			String active = "";
			pageContext.getOut().write("<" + containerTag + " class=\"" + containerClass + "\">");
			switch (user.getRole()) {
			case CUSTOMER:
				active = currAction.equals("order_show_add_form") ? currActionClass : "";
				pageContext.getOut().write("<" + itemTag + " class=\"" + active + "\">" 
										   + "<a href='" + PAGE_ORDER_SHOW_ADD_FORM_URI + "'>" 
										   + getString(rb, "order.addNew") 
										   + "</a></" + itemTag + ">");
				break;
			case MANAGER:
				active = currAction.equals("project_list") ? currActionClass : "";
				pageContext.getOut().write("<" + itemTag + " class=\"" + active + "\">"
										   + "<a href='" + PAGE_PROJECT_LIST_URI + "'>" 
										   + getString(rb, "menu.left.projects") + "</a></" + itemTag + ">");
				break;
			case DEVELOPER:
				active = currAction.equals("project_list_by_employee_id") ? currActionClass : "";
				pageContext.getOut().write("<" + itemTag + " class=\"" + active + "\">"
										   + "<a href='" + PAGE_PROJECT_LIST_BY_EMPLOYEE_URI + "'>" 
										   + getString(rb, "menu.left.projects") + "</a></" + itemTag + ">");
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
	
	/*
	 * Check if exist key in bundle and return this value
	 */
	private String getString(ResourceBundle rb, String key) {
		if ( rb.containsKey(key) ) {
			return rb.getString(key);
		}
		
		return "";
	}
}
