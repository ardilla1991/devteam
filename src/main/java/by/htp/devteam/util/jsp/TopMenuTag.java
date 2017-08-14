package by.htp.devteam.util.jsp;

import static by.htp.devteam.controller.util.ConstantValue.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Map.Entry;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import by.htp.devteam.acl.PermissionUri;
import by.htp.devteam.bean.User;
import by.htp.devteam.bean.UserRole;

/**
 * Display top menu for user. Item is showed if user has permission
 * @author julia
 *
 */
public final class TopMenuTag extends TagSupport {

	private static final long serialVersionUID = -6330452373340715942L;

	/** Items to display */
	private final static Map<String, String> items = new HashMap<String, String>(10);
	
	{
		items.put(PAGE_ORDER_LIST_URI, "menu.top.orders");
		items.put(PAGE_ORDER_NEW_LIST_URI, "menu.top.orders.new");
		items.put(PAGE_EMPLOYEE_LIST_URI, "menu.top.employee.list");
		items.put(PAGE_USER_LIST_URI, "menu.top.user.list");
		items.put(PAGE_USER_VIEW_URI, "");
		items.put(PAGE_LOGOUT, "menu.top.logout");
	}
	
	/** User */
	private User user;
	
	/** Current action. Use for set active class for selected url */
	private String currUrl;
	
	/** Container tag for all points */
	private String containerTag;
	
	/** Container class for all points */	
	private String containerClass;
	
	/** Item tag for point */
	private String itemTag;
	
	/** Item tag class for selected points */
	private String currUrlClass;
	
	/** Current language */
	private String language;
	
	/** Current country */
	private String country;
	
	public TopMenuTag() {
		super();
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public void setCurrUrl(String currUrl) {
		this.currUrl = currUrl;
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

	public void setCurrUrlClass(String currUrlClass) {
		this.currUrlClass = currUrlClass;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	/**
	 * Create menu
	 */
	@Override
	public int doStartTag() throws JspException {
		try {
			Locale locale = new Locale(language, country);
			ResourceBundle rb = ResourceBundle.getBundle("text", locale);

			UserRole userRole = user.getRole();
			String active = "";
			
			pageContext.getOut().write("<" + containerTag + " class=\"" + containerClass + "\">");
			Iterator<Entry<String, String>> it = items.entrySet().iterator();
			while ( it.hasNext() ) {
				Map.Entry<String, String> pair = (Map.Entry<String, String>) it.next();
				if ( PermissionUri.getInstance().checkPermissionForUri(userRole, pair.getKey()) ) {
					active = currUrl.equals(pair.getKey()) ? currUrlClass : "";
					pageContext.getOut().write("<" + itemTag + " class=\"" + active + "\">" 
							   + "<a href=\"" + pair.getKey() + "\">" 
							   + ( pair.getValue() == "" ? user.getLogin() : getString(rb, pair.getValue()) )
							   + "</a></" + itemTag + ">");
				}
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
