package by.htp.devteam.util.jsp;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import by.htp.devteam.acl.PermissionUri;
import by.htp.devteam.bean.User;
import by.htp.devteam.bean.UserRole;

/**
 * Tag for display link for user if he has permissions
 * @author julia
 *
 */
public final class ActionTag extends TagSupport {

	private static final long serialVersionUID = 3473588471863725280L;

	/** Class name for link */
	private String className;
	
	/** Id value for link */
	private String id;
	
	/** Href value for link */
	private String href;
	
	/** Title for link */
	private String title;
	
	/** Button role (bootstrap) for link */
	private String buttonRole;
	
	/** User */
	private User user;

	public ActionTag() {
		super();
	}
	
	public void setClassName(String className) {
		this.className = className;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setButtonRole(String buttonRole) {
		this.buttonRole = buttonRole;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	@Override
	public int doStartTag() throws JspException {
		try {
			UserRole userRole = user.getRole();
			if ( PermissionUri.getInstance().checkPermissionForUri(userRole, href) ) {
				pageContext.getOut().write("<a class=\"" + className + "\" href=\"" + href + "\""
						+ " id=\"" + id + "\" role=\"" + buttonRole + "\">");
				pageContext.getOut().write(title + "</a>");
			}
		} catch (IOException e) {
			throw new JspException(e.getMessage());
		}
		
		return SKIP_BODY;
	}
}
