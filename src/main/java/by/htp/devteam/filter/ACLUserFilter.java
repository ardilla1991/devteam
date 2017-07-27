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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.htp.devteam.bean.UserRole;
import by.htp.devteam.bean.vo.UserVo;
import by.htp.devteam.command.CommandFactory;
import by.htp.devteam.command.CommandExeption;

import static by.htp.devteam.command.util.ConstantValue.*;

/**
 * Check if selected action for EMPLOYEE or CUSTOMER user is in ACL list. If not we send him on permission denied page
 * @author julia
 *
 */
public class ACLUserFilter implements Filter{

	/** ACL for users */
	private final static Map<UserRole, List<CommandFactory>> acl = new HashMap<UserRole, List<CommandFactory>>();
	
	/** 
	 * ACL for manager. Contains a actions from Command enum
	 * @see by.htp.devteam.command.CommandFactory
	 */
	private final static List<CommandFactory> managerACL = new ArrayList<CommandFactory>();
	
	/** 
	 * ACL for developer. Contains a actions from Command enum
	 * @see by.htp.devteam.command.CommandFactory
	 */
	private final static List<CommandFactory> developerACL = new ArrayList<CommandFactory>();
	
	/** 
	 * ACL for customer. Contains a actions from Command enum
	 * @see by.htp.devteam.command.CommandFactory
	 */
	private final static List<CommandFactory> customerACL = new ArrayList<CommandFactory>();
	
	/** 
	 * ACL for admin. Contains a actions from Command enum
	 * @see by.htp.devteam.command.CommandFactory
	 */
	private final static List<CommandFactory> adminACL = new ArrayList<CommandFactory>();
	
	/** logger */
	private final static Logger logger = LogManager.getLogger(ACLUserFilter.class.getName());
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
		setManagersACL();
		setDevelopersACL();
		setCustomersACL();
		setAdminACL();
	}

	/**
	 * Check if isset user in session and after that check him permissions
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		String action = req.getParameter(REQUEST_PARAM_ACTION);

		HttpSession session = req.getSession(false);
		boolean isAuthorised = session != null && session.getAttribute(SESSION_PARAM_USER) != null;
		
		UserRole role;
		if ( isAuthorised && action != null) {
			Object userObject = session.getAttribute(SESSION_PARAM_USER);
			UserVo userVO = (UserVo) userObject;
			role = userVO.getUser().getRole();
			try {
				boolean issetInACL = acl.get(role).contains(CommandFactory.getAction(action) );
				if ( !issetInACL ) {
					resp.sendRedirect(PAGE_PERMISSION_DENIED_URI);
					return;
				} else if ( issetInACL && !req.getMethod().equalsIgnoreCase(CommandFactory.getAction(action).getHTTPMethod().getValue()) ) {
					logger.info(MSG_LOGGER_WRONG_HTTP_METHOD, userVO.getUser().getLogin());
					resp.sendError(404);
					return;
				}
			} catch (CommandExeption e) {
				logger.info(e.getMessage());
				resp.sendError(404);
				return;
			}
		}
		chain.doFilter(request, response);	
	}

	@Override
	public void destroy() {
		managerACL.clear();
		developerACL.clear();
		customerACL.clear();
		adminACL.clear();
		acl.clear();
	}
	
	private void setManagersACL() {
		managerACL.add(CommandFactory.LOGIN);
		managerACL.add(CommandFactory.LOGIN_SHOW_FORM);
		managerACL.add(CommandFactory.ORDER_NEW_LIST);
		managerACL.add(CommandFactory.ORDER_VIEW);
		managerACL.add(CommandFactory.PROJECT_SHOW_ADD_FORM);
		managerACL.add(CommandFactory.PROJECT_ADD);
		managerACL.add(CommandFactory.PROJECT_ADD_MESSAGE);
		managerACL.add(CommandFactory.PROJECT_FIND);
		managerACL.add(CommandFactory.PERMISSION_DENIED);
		managerACL.add(CommandFactory.LOGOUT);
		managerACL.add(CommandFactory.PROJECT_LIST);
		managerACL.add(CommandFactory.PROJECT_VIEW);
		managerACL.add(CommandFactory.USER_VIEW);
		managerACL.add(CommandFactory.EMPLOYEE_LIST);
		managerACL.add(CommandFactory.EMPLOYEE_SHOW_ADD_FORM);
		managerACL.add(CommandFactory.EMPLOYEE_ADD);
		managerACL.add(CommandFactory.EMPLOYEE_ADD_MESSAGE);
		
		acl.put(UserRole.MANAGER, managerACL);
	}
	
	private void setDevelopersACL() {
		developerACL.add(CommandFactory.LOGIN);
		developerACL.add(CommandFactory.LOGIN_SHOW_FORM);
		developerACL.add(CommandFactory.PERMISSION_DENIED);
		developerACL.add(CommandFactory.LOGOUT);
		developerACL.add(CommandFactory.PROJECT_LIST_BY_EMPLOYEE);
		developerACL.add(CommandFactory.PROJECT_VIEW);
		developerACL.add(CommandFactory.PROJECT_FIND);
		developerACL.add(CommandFactory.PROJECT_UPDATE_HOURS);
		developerACL.add(CommandFactory.USER_VIEW);
		
		acl.put(UserRole.DEVELOPER, developerACL);
	}
	
	private void setCustomersACL() {
		customerACL.add(CommandFactory.LOGIN);
		customerACL.add(CommandFactory.LOGIN_SHOW_FORM);
		customerACL.add(CommandFactory.PERMISSION_DENIED);
		customerACL.add(CommandFactory.LOGOUT);
		customerACL.add(CommandFactory.ORDER_LIST);
		customerACL.add(CommandFactory.ORDER_SHOW_ADD_FORM);
		customerACL.add(CommandFactory.ORDER_ADD);
		customerACL.add(CommandFactory.ORDER_ADD_MESSAGE);
		customerACL.add(CommandFactory.ORDER_VIEW);
		customerACL.add(CommandFactory.USER_VIEW);
		
		acl.put(UserRole.CUSTOMER, customerACL);
	}
	
	private void setAdminACL() {
		adminACL.add(CommandFactory.LOGIN);
		adminACL.add(CommandFactory.LOGIN_SHOW_FORM);
		adminACL.add(CommandFactory.PERMISSION_DENIED);
		adminACL.add(CommandFactory.LOGOUT);
		adminACL.add(CommandFactory.USER_LIST);
		adminACL.add(CommandFactory.USER_VIEW);
		adminACL.add(CommandFactory.USER_SHOW_ADD_FORM);
		adminACL.add(CommandFactory.USER_ADD);
		adminACL.add(CommandFactory.EMPLOYEE_LIST_NOT_USER_ACTION);
		
		acl.put(UserRole.ADMIN, adminACL);
	}

}
