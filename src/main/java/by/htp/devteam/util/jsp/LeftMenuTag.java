package by.htp.devteam.util.jsp;

import static by.htp.devteam.controller.util.ConstantValue.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import by.htp.devteam.acl.PermissionUri;
import by.htp.devteam.bean.User;
import by.htp.devteam.bean.UserRole;

/**
 * Display left bar for user. Item is showed if user has permission
 * @author julia
 *
 */
public final class LeftMenuTag extends TagSupport{
	
	private static final long serialVersionUID = -8395756013256603658L;

	/** Items to display */
	private final static Map<String, String> items = new HashMap<String, String>(10);
	
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
	private String currLanguage;

	public LeftMenuTag() {
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
	
	public void setCurrLanguage(String currLanguage) {
		this.currLanguage = currLanguage;
	}

	/**
	 * Create bar
	 */
	@Override
	public int doStartTag() throws JspException {
		menuList();
		try {
			Locale locale = new Locale(currLanguage);
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
											   + getString(rb, pair.getValue()) 
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
	
	private void menuList() {
		items.put(SYSTEM_PATH + currLanguage + URL_DELIMITER + PAGE_ORDER_ADD_URI, "order.addNew");
		items.put(SYSTEM_PATH + currLanguage + URL_DELIMITER + PAGE_PROJECT_LIST_URI, "menu.left.projects");
		items.put(SYSTEM_PATH + currLanguage + URL_DELIMITER + PAGE_PROJECT_LIST_BY_EMPLOYEE_URI, "menu.left.projects");

	}
}
