package by.htp.devteam.util.jsp;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import by.htp.devteam.bean.Customer;
import by.htp.devteam.bean.Employee;
import by.htp.devteam.bean.User;
import by.htp.devteam.bean.UserRole;
import by.htp.devteam.bean.vo.UserVo;

/**
 * Tag for display users information
 * @author julia
 *
 */
public class UserListTag extends TagSupport {

	private static final long serialVersionUID = -6991559020909290864L;

	/** User */
	private List<UserVo> users;
	
	/** Table class */
	private String tableClass;
	
	/** Current language */
	private String language;
	
	/** Current country */
	private String country;
	
	public UserListTag() {
		super();
	}
	
	public void setUsers(List<UserVo> users) {
		this.users = users;
	}

	public void setTableClass(String tableClass) {
		this.tableClass = tableClass;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	/**
	 * Create users tables for users list
	 */
	@Override
	public int doStartTag() throws JspException {
		if ( users == null ) {
			return SKIP_BODY;
		}
		
		Locale locale = new Locale(language, country);
		ResourceBundle rb = ResourceBundle.getBundle("text", locale);
		try {
			for ( UserVo userVo : users ) {
				displayUserInformation(userVo, rb);
			}

		
		} catch (IOException e) {
			throw new JspException(e.getMessage());
		}
		return SKIP_BODY;
	}
	
	private void displayUserInformation(UserVo userVo, ResourceBundle rb) throws IOException {
		User user = userVo.getUser();
		pageContext.getOut().write("<table class=\"" + tableClass + "\">");
		pageContext.getOut().write("<tr><td>" + getString(rb, "user.login") + "</td><td>" + user.getLogin() + "</td></tr>");
		pageContext.getOut().write("<tr><td>" + getString(rb, "user.role") + "</td><td>" + user.getRole() + "</td></tr>");
		if ( user.getRole() == UserRole.CUSTOMER ) {
			Customer customer = userVo.getCustomer();
			pageContext.getOut().write("<tr><td>" + getString(rb, "user.name") + "</td><td>" + customer.getName() + "</td></tr>");
			pageContext.getOut().write("<tr><td>" + getString(rb, "customer.email") + "</td><td>" + customer.getEmail() + "</td></tr>");
			pageContext.getOut().write("<tr><td>" + getString(rb, "customer.phone") + "</td><td>" + customer.getPhone() + "</td></tr>");
		} else {
			Employee employee = userVo.getEmployee();
			pageContext.getOut().write("<tr><td>" + getString(rb, "user.name") + "</td><td>" + employee.getName() + "</td></tr>");
			pageContext.getOut().write("<tr><td>" + getString(rb, "employee.startWork") + "</td><td>" + employee.getStartWork() + "</td></tr>");
			pageContext.getOut().write("<tr><td>" + getString(rb, "qualification") + "</td><td>" + employee.getQualification().getTitle() + "</td></tr>");
		}
		pageContext.getOut().write("</table>");
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
