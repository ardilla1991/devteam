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
import by.htp.devteam.command.Command;
import by.htp.devteam.command.CommandExeption;

import static by.htp.devteam.command.util.ConstantValue.*;

/**
 * Check if selected action for EMPLOYEE or CUSTOMER user is in ACL list. If not we send him on permission denied page
 * @author julia
 *
 */
public class ACLUserFilter implements Filter{

	/** ACL for users */
	private final static Map<UserRole, List<Command>> acl = new HashMap<UserRole, List<Command>>();
	
	/** 
	 * ACL for manager. Contains a actions from Command enum
	 * @see by.htp.devteam.command.Command
	 */
	private final static List<Command> managerACL = new ArrayList<Command>();
	
	/** 
	 * ACL for developer. Contains a actions from Command enum
	 * @see by.htp.devteam.command.Command
	 */
	private final static List<Command> developerACL = new ArrayList<Command>();
	
	/** 
	 * ACL for customer. Contains a actions from Command enum
	 * @see by.htp.devteam.command.Command
	 */
	private final static List<Command> customerACL = new ArrayList<Command>();
	
	/** 
	 * ACL for admin. Contains a actions from Command enum
	 * @see by.htp.devteam.command.Command
	 */
	private final static List<Command> adminACL = new ArrayList<Command>();
	
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
				boolean issetInACL = acl.get(role).contains(Command.getAction(action) );
				if ( !issetInACL ) {
					resp.sendRedirect(PAGE_PERMISSION_DENIED_URI);
					return;
				} else if ( issetInACL && !req.getMethod().equalsIgnoreCase(Command.getAction(action).getHTTPMethod().getValue()) ) {
					logger.info(MSG_LOGGER_WRONG_HTTP_METHOD, userVO.getUser().getLogin());
					req.getRequestDispatcher(PAGE_ERROR_404).forward(req, resp);
					return;
				}
			} catch (CommandExeption e) {
				logger.info(e.getMessage());
				req.getRequestDispatcher(PAGE_ERROR_404).forward(req, resp);
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
		managerACL.add(Command.LOGIN);
		managerACL.add(Command.LOGIN_SHOW_FORM);
		managerACL.add(Command.ORDER_NEW_LIST);
		managerACL.add(Command.ORDER_VIEW);
		managerACL.add(Command.PROJECT_SHOW_ADD_FORM);
		managerACL.add(Command.PROJECT_ADD);
		managerACL.add(Command.PROJECT_ADD_MESSAGE);
		managerACL.add(Command.PROJECT_FIND);
		managerACL.add(Command.PERMISSION_DENIED);
		managerACL.add(Command.LOGOUT);
		managerACL.add(Command.PROJECT_LIST);
		managerACL.add(Command.PROJECT_VIEW);
		managerACL.add(Command.USER_VIEW);
		
		acl.put(UserRole.MANAGER, managerACL);
	}
	
	private void setDevelopersACL() {
		developerACL.add(Command.LOGIN);
		developerACL.add(Command.LOGIN_SHOW_FORM);
		developerACL.add(Command.PERMISSION_DENIED);
		developerACL.add(Command.LOGOUT);
		developerACL.add(Command.PROJECT_LIST_BY_EMPLOYEE);
		developerACL.add(Command.PROJECT_VIEW);
		developerACL.add(Command.PROJECT_FIND);
		developerACL.add(Command.PROJECT_UPDATE_HOURS);
		developerACL.add(Command.USER_VIEW);
		
		acl.put(UserRole.DEVELOPER, developerACL);
	}
	
	private void setCustomersACL() {
		customerACL.add(Command.LOGIN);
		customerACL.add(Command.LOGIN_SHOW_FORM);
		customerACL.add(Command.PERMISSION_DENIED);
		customerACL.add(Command.LOGOUT);
		customerACL.add(Command.ORDER_LIST);
		customerACL.add(Command.ORDER_SHOW_ADD_FORM);
		customerACL.add(Command.ORDER_ADD);
		customerACL.add(Command.ORDER_ADD_MESSAGE);
		customerACL.add(Command.ORDER_VIEW);
		customerACL.add(Command.USER_VIEW);
		
		acl.put(UserRole.CUSTOMER, customerACL);
	}
	
	private void setAdminACL() {
		adminACL.add(Command.LOGIN);
		adminACL.add(Command.LOGIN_SHOW_FORM);
		adminACL.add(Command.PERMISSION_DENIED);
		adminACL.add(Command.LOGOUT);
		adminACL.add(Command.USER_LIST);
		adminACL.add(Command.USER_VIEW);
		
		acl.put(UserRole.ADMIN, adminACL);
	}

}
