package by.htp.devteam.util.jsp;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import by.htp.devteam.bean.Employee;
import by.htp.devteam.bean.User;
import by.htp.devteam.bean.UserRole;

import static by.htp.devteam.command.util.ConstantValue.*;

public class EmployeeListTag extends TagSupport {

	private static final long serialVersionUID = -7081232509824036331L;

	/** Employees */
	private List<Employee> employees;
	
	/** Table class */
	private String tableClass;
	
	/** Current language */
	private String language;
	
	/** Current country */
	private String country;
	
	/** User */
	private User user;
	
	public EmployeeListTag() {
		super();
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
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
	
	public void setUser(User user) {
		this.user = user;
	}
	
	/**
	 * Create users tables for users list
	 */
	@Override
	public int doStartTag() throws JspException {
		if ( employees == null ) {
			return SKIP_BODY;
		}
		
		Locale locale = new Locale(language, country);
		ResourceBundle rb = ResourceBundle.getBundle("text", locale);
		try {
			for ( Employee employee : employees ) {
				displayEmployeeInformation(employee, rb);
			}

		
		} catch (IOException e) {
			throw new JspException(e.getMessage());
		}
		
		return SKIP_BODY;
	}
	
	/*
	 * @todo add "add user link" if user have acl
	 */
	private void displayEmployeeInformation(Employee employee, ResourceBundle rb) throws IOException {
		pageContext.getOut().write("<table class=\"" + tableClass + "\">");
		pageContext.getOut().write("<tr><td>" + getString(rb, "user.name") + "</td><td>" + employee.getName() + "</td>");
		if ( user != null && user.getRole() == UserRole.ADMIN ) {
			pageContext.getOut().write("<td><a href=\"" + PAGE_USER_ADD_URI + "\">"  + getString(rb, "user.action.add") + "</a></td>");
		}
		pageContext.getOut().write("</tr>");
		pageContext.getOut().write("<tr><td>" + getString(rb, "employee.startWork") + "</td><td>" + employee.getStartWork() + "</td></tr>");
		pageContext.getOut().write("<tr><td>" + getString(rb, "qualification") + "</td><td>" + employee.getQualification().getTitle() + "</td></tr>");
		pageContext.getOut().write("<tr><td>" + getString(rb, "user.login") + "</td><td>" + employee.getUser().getLogin() + "</td></tr>");
		pageContext.getOut().write("<tr><td>" + getString(rb, "user.role") + "</td><td>" + employee.getUser().getRole() + "</td></tr>");
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
