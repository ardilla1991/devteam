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

import by.htp.devteam.bean.RoleEnum;
import by.htp.devteam.bean.dto.UserVO;
import by.htp.devteam.command.CommandEnum;
import by.htp.devteam.command.CommandExeption;

import static by.htp.devteam.command.util.ConstantValue.*;

public class ACLFilterUser implements Filter{

	private final static Map<RoleEnum, List<CommandEnum>> acl = new HashMap<RoleEnum, List<CommandEnum>>();
	private final static List<CommandEnum> managerACL = new ArrayList<CommandEnum>();
	private final static List<CommandEnum> developerACL = new ArrayList<CommandEnum>();
	private final static List<CommandEnum> customerACL = new ArrayList<CommandEnum>();
	private final static Logger logger = LogManager.getLogger(ACLFilterUser.class.getName());
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
		setManagersACL();
		setDevelopersACL();
		setCustomersACL();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		String action = req.getParameter(REQUEST_PARAM_ACTION);

		HttpSession session = req.getSession(false);
		boolean isAuthorised = session != null && session.getAttribute(SESSION_PARAM_USER) != null;
		
		
		
		RoleEnum role;
		if ( isAuthorised && action != null) {
			Object userObject = session.getAttribute(SESSION_PARAM_USER);
			UserVO userVO = (UserVO) userObject;
			role = userVO.getUser().getRole();
			try {
				boolean issetInACL = acl.get(role).contains(CommandEnum.getAction(action) );
				if ( !issetInACL ) {
					resp.sendRedirect(PAGE_PERMISSION_DENIED_URI);
					return;
				} else if ( issetInACL && !req.getMethod().equalsIgnoreCase(CommandEnum.getAction(action).getHTTPMethod().getValue()) ) {
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
		acl.clear();
	}
	
	private void setManagersACL() {
		managerACL.add(CommandEnum.LOGIN);
		managerACL.add(CommandEnum.LOGIN_SHOW_FORM);
		managerACL.add(CommandEnum.ORDER_NEW_LIST);
		managerACL.add(CommandEnum.ORDER_VIEW);
		managerACL.add(CommandEnum.PROJECT_SHOW_ADD_FORM);
		managerACL.add(CommandEnum.PROJECT_ADD);
		managerACL.add(CommandEnum.PROJECT_ADD_MESSAGE);
		managerACL.add(CommandEnum.PROJECT_FIND);
		managerACL.add(CommandEnum.PERMISSION_DENIED);
		managerACL.add(CommandEnum.LOGOUT);
		managerACL.add(CommandEnum.PROJECT_LIST);
		managerACL.add(CommandEnum.PROJECT_VIEW);
		managerACL.add(CommandEnum.USER_VIEW);
		
		acl.put(RoleEnum.MANAGER, managerACL);
	}
	
	private void setDevelopersACL() {
		developerACL.add(CommandEnum.LOGIN);
		developerACL.add(CommandEnum.LOGIN_SHOW_FORM);
		developerACL.add(CommandEnum.PERMISSION_DENIED);
		developerACL.add(CommandEnum.LOGOUT);
		developerACL.add(CommandEnum.PROJECT_LIST_BY_EMPLOYEE);
		developerACL.add(CommandEnum.PROJECT_VIEW);
		developerACL.add(CommandEnum.PROJECT_FIND);
		developerACL.add(CommandEnum.PROJECT_UPDATE_HOURS);
		developerACL.add(CommandEnum.USER_VIEW);
		
		acl.put(RoleEnum.DEVELOPER, developerACL);
	}
	
	private void setCustomersACL() {
		customerACL.add(CommandEnum.LOGIN);
		customerACL.add(CommandEnum.LOGIN_SHOW_FORM);
		customerACL.add(CommandEnum.PERMISSION_DENIED);
		customerACL.add(CommandEnum.LOGOUT);
		customerACL.add(CommandEnum.ORDER_LIST);
		customerACL.add(CommandEnum.ORDER_SHOW_ADD_FORM);
		customerACL.add(CommandEnum.ORDER_ADD);
		customerACL.add(CommandEnum.ORDER_ADD_MESSAGE);
		customerACL.add(CommandEnum.ORDER_VIEW);
		customerACL.add(CommandEnum.USER_VIEW);
		
		acl.put(RoleEnum.CUSTOMER, customerACL);
	}

}
