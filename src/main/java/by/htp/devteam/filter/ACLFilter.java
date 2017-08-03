package by.htp.devteam.filter;

import static by.htp.devteam.module.util.ConstantValue.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.devteam.bean.UserRole;
import by.htp.devteam.bean.vo.UserVo;
import by.htp.devteam.module.ControllerFactory;
import by.htp.devteam.module.ControllerExeption;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Check if selected action for GUEST user is in ACL list. If not we send him on login page
 * @author julia
 *
 */
public final class ACLFilter implements Filter{

	/** ACL for users */
	private final static Map<UserRole, Map<ControllerFactory, List<String>>> acl = new HashMap<UserRole, Map<ControllerFactory, List<String>>>();
	
	/** 
	 * ACL for guest. Contains controllers from Module Enum and actions
	 * @see by.htp.devteam.module.ControllerFactory
	 */
	private final static Map<ControllerFactory, List<String>> guestACL = new HashMap<ControllerFactory, List<String>>();
	
	/** 
	 * ACL for manager. Contains controllers from Module Enum and actions
	 * @see by.htp.devteam.module.ControllerFactory
	 */
	private final static Map<ControllerFactory, List<String>> managerACL = new HashMap<ControllerFactory, List<String>>();
	
	/** 
	 * ACL for developer. Contains controllers from Module Enum and actions
	 * @see by.htp.devteam.module.ControllerFactory
	 */
	private final static Map<ControllerFactory, List<String>> developerACL = new HashMap<ControllerFactory, List<String>>();
	
	/** 
	 * ACL for customer. Contains controllers from Module Enum and actions
	 * @see by.htp.devteam.module.ControllerFactory
	 */
	private final static Map<ControllerFactory, List<String>> customerACL = new HashMap<ControllerFactory, List<String>>();
	
	/** 
	 * ACL for admin. Contains controllers from Module Enum and actions
	 * @see by.htp.devteam.module.ControllerFactory
	 */
	private final static Map<ControllerFactory, List<String>> adminACL = new HashMap<ControllerFactory, List<String>>();
	
	/** logger */
	private final static Logger logger = LogManager.getLogger(ACLFilter.class.getName());
	
	/** pattern for url */
	private final static Pattern URL_PATTERN = Pattern.compile("^/devteam/(Main/?)?$");
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

		setGuestsACL();
		setManagersACL();
		setDevelopersACL();
		setCustomersACL();
		setAdminACL();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        /*
		 * System.out.println(req.getRequestURI());
		 * System.out.println(req.getRequestURL());
		 * System.out.println(req.getQueryString());
		 */
        String module = req.getParameter(REQUEST_PARAM_ACTION);
		String action = req.getParameter(REQUEST_PARAM_ACTION);
		HttpSession session = req.getSession(false);
		boolean isAuthorised = session != null && session.getAttribute(SESSION_PARAM_USER) != null;
		
		if ( action != null ) {
			if ( isAuthorised ) {
				UserVo userVO = (UserVo) session.getAttribute(SESSION_PARAM_USER);
				UserRole role = userVO.getUser().getRole();

				boolean issetInACL = issetInACL(role, module, action);
				if ( !issetInACL ) {
					resp.sendError(403);
					return;
				}

			} else {
				UserRole role = UserRole.GUEST;

				boolean issetInACL = issetInACL(role, module, action);
				if ( !issetInACL ) {
					resp.sendRedirect(PAGE_SHOW_AUTHORIZATION_FORM_URI);
					return;
				}
			}
		}

		chain.doFilter(request, response);
	}
	
	private boolean issetInACL(UserRole role, String name, String action) {
		boolean issetInACL = false;
		issetInACL = acl.get(role).containsKey(ControllerFactory.lookup(name)) 
				&& acl.get(role).get(name).contains(action);
		
		return issetInACL;
	}

	@Override
	public void destroy() {
		guestACL.clear();
		managerACL.clear();
		developerACL.clear();
		customerACL.clear();
		adminACL.clear();
		acl.clear();
	}

	private void setGuestsACL() {
		List<String> userActions = new ArrayList<String>(5);
		userActions.add("login");
		userActions.add("message");
		guestACL.put(ControllerFactory.USER, userActions);

		acl.put(UserRole.GUEST, guestACL);
	}
	
	private void setManagersACL() {
		List<String> userActions = new ArrayList<String>(5);
		userActions.add("login");
		userActions.add("logout");
		userActions.add("view");
		managerACL.put(ControllerFactory.USER, userActions);
		
		List<String> employeeActions = new ArrayList<String>(5);
		employeeActions.add("list");
		employeeActions.add("add");
		employeeActions.add("message");
		managerACL.put(ControllerFactory.EMPLOYEE, employeeActions);
		
		List<String> orderActions = new ArrayList<String>(5);
		orderActions.add("new_list");
		orderActions.add("view");
		managerACL.put(ControllerFactory.ORDER, orderActions);
		
		List<String> projectActions = new ArrayList<String>(5);
		projectActions.add("list");
		projectActions.add("add");
		projectActions.add("message");
		projectActions.add("find");
		projectActions.add("view");
		managerACL.put(ControllerFactory.PROJECT, projectActions);
		
		acl.put(UserRole.MANAGER, managerACL);
	}
	
	private void setDevelopersACL() {
		List<String> userActions = new ArrayList<String>(5);
		userActions.add("login");
		userActions.add("logout");
		userActions.add("view");
		developerACL.put(ControllerFactory.USER, userActions);

		List<String> projectActions = new ArrayList<String>(5);
		projectActions.add("list_by_employee");
		projectActions.add("update_hours");
		projectActions.add("message");
		projectActions.add("find");
		projectActions.add("view");
		developerACL.put(ControllerFactory.PROJECT, projectActions);
		
		acl.put(UserRole.DEVELOPER, developerACL);
	}
	
	private void setCustomersACL() {
		List<String> userActions = new ArrayList<String>(5);
		userActions.add("login");
		userActions.add("logout");
		userActions.add("view");
		customerACL.put(ControllerFactory.USER, userActions);
		
		List<String> orderActions = new ArrayList<String>(5);
		orderActions.add("list");
		orderActions.add("add");
		orderActions.add("message");
		orderActions.add("view");
		customerACL.put(ControllerFactory.ORDER, orderActions);
		
		acl.put(UserRole.CUSTOMER, customerACL);
	}
	
	private void setAdminACL() {
		List<String> userActions = new ArrayList<String>(5);
		userActions.add("login");
		userActions.add("logout");
		userActions.add("view");
		userActions.add("list");
		userActions.add("add");
		userActions.add("message");
		adminACL.put(ControllerFactory.USER, userActions);
		
		acl.put(UserRole.ADMIN, adminACL);
	}


}
