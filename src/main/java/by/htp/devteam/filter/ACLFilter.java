package by.htp.devteam.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.devteam.bean.RoleEnum;
import by.htp.devteam.bean.User;

public class ACLFilter implements Filter{

	private final static Map<RoleEnum, List<String>> acl = new HashMap<RoleEnum, List<String>>();
	private final static List<String> adminACL = new ArrayList<String>();
	private final static List<String> managerACL = new ArrayList<String>();
	private final static List<String> developerACL = new ArrayList<String>();
	private final static List<String> guestACL = new ArrayList<String>();
	private final static List<String> customerACL = new ArrayList<String>();
	
	{
		
		guestACL.add("login");
		guestACL.add("show_form");
		guestACL.add("admin_login");
		guestACL.add("admin_show_form");
		
		//acl.put(RoleEnum.GUEST, guestACL);
		acl.put(RoleEnum.GUEST, guestACL);
	}
	
	{
		adminACL.add("admin_show_form");
		adminACL.add("admin_login");
		adminACL.add("admin_permission_denied");
		adminACL.add("admin_logout");
		
		acl.put(RoleEnum.ADMINISTRATOR, adminACL);	
	}
	
	{
		managerACL.add("admin_show_form");
		managerACL.add("admin_login");
		managerACL.add("admin_orders_new_list");
		managerACL.add("admin_permission_denied");
		managerACL.add("admin_logout");
		
		acl.put(RoleEnum.MANAGER, managerACL);
	}
	
	{
		developerACL.add("admin_show_form");
		developerACL.add("admin_login");
		developerACL.add("admin_permission_denied");
		developerACL.add("admin_logout");
		
		acl.put(RoleEnum.DEVELOPER, developerACL);
	}
	
	{
		customerACL.add("show_form");
		customerACL.add("login");
		customerACL.add("permission_denied");
		customerACL.add("logout");
		customerACL.add("order_list");
		customerACL.add("order_show_add_form");
		customerACL.add("order_add");
		
		acl.put(RoleEnum.CUSTOMER, customerACL);
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
		
		System.out.println("FILTER START");
		String action = req.getParameter("action");
		System.out.println(action);
		
		HttpSession session = req.getSession(false);
        boolean isAuthorised = session != null && session.getAttribute("user") != null;
		  
        System.out.println("FILTER END");
        //System.out.println("dddddd=");
        //System.out.println(guestACL.indexOf(action));
        if ( isAuthorised ) {
        	System.out.println("SESSION USER");
        	Object userObject = session.getAttribute("user");
        	User user = (User) userObject;
        	RoleEnum role = user.getRole();
        	if ( acl.get(role).contains(action) ) {
        		System.out.println("  OK ACTION  ");
        		req.setAttribute("user", session.getAttribute("user"));
        		//chain.doFilter(req, resp);
        	} else {
        		String redirectAction = ( role == RoleEnum.CUSTOMER
        							  ? "permission_denied"
        							  : "admin_permission_denied");
        		resp.sendRedirect("Main?action=" + redirectAction);
        		return;
        		//chain.doFilter(req, resp);
        	}
        } else if ( guestACL.contains(action) ) {
        	System.out.println("guest ");
        	//resp.sendRedirect("Main");
        	//chain.doFilter(req, resp);
        }  else {
        	System.out.println("  NOT IN SESSION ");
        	//chain.doFilter(req, resp);	
        }
		
        chain.doFilter(request, response);	
	}

	@Override
	public void destroy() {
				
	}

}
