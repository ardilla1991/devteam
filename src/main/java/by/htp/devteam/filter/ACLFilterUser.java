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
import by.htp.devteam.bean.dto.UserVO;
import by.htp.devteam.command.CommandEnum;
import by.htp.devteam.command.CommandExeption;

public class ACLFilterUser implements Filter{

	private final static Map<RoleEnum, List<CommandEnum>> acl = new HashMap<RoleEnum, List<CommandEnum>>();
	private final static List<CommandEnum> managerACL = new ArrayList<CommandEnum>();
	private final static List<CommandEnum> developerACL = new ArrayList<CommandEnum>();
	private final static List<CommandEnum> customerACL = new ArrayList<CommandEnum>();
	
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

		String action = req.getParameter("action");
		System.out.println("user action=" + action);

		HttpSession session = req.getSession(false);
		boolean isAuthorised = session != null && session.getAttribute("user") != null;
		System.out.println("USER ! isAuth=" + isAuthorised);
		
		RoleEnum role;
		if (isAuthorised && action != null) {
			Object userObject = session.getAttribute("user");
			UserVO userVO = (UserVO) userObject;
			role = userVO.getUser().getRole();
			System.out.println("role="+role);
			try {
				if (acl.get(role).contains(CommandEnum.getAction(action))) {
					req.setAttribute("user", session.getAttribute("user"));
				} else {
					/*String redirectAction = (role == RoleEnum.CUSTOMER ? "permission_denied"
							: "admin_permission_denied");
					resp.sendRedirect("Main?action=" + redirectAction);*/
					resp.sendRedirect("Main?action=permission_denied");
					return;
				}
			} catch (CommandExeption e) {
				System.out.println("invalid command");
			}
		}
		chain.doFilter(request, response);	
	}

	@Override
	public void destroy() {
				
	}
	
	private void setManagersACL() {
		managerACL.add(CommandEnum.SHOW_FORM);
		managerACL.add(CommandEnum.ORDER_NEW_LIST);
		managerACL.add(CommandEnum.ORDER_VIEW);
		managerACL.add(CommandEnum.PROJECT_SHOW_ADD_FORM);
		managerACL.add(CommandEnum.PROJECT_ADD);
		managerACL.add(CommandEnum.PERMISSION_DENIED);
		managerACL.add(CommandEnum.LOGOUT);
		managerACL.add(CommandEnum.PROJECT_LIST);
		
		acl.put(RoleEnum.MANAGER, managerACL);
	}
	
	private void setDevelopersACL() {
		developerACL.add(CommandEnum.SHOW_FORM);
		developerACL.add(CommandEnum.PERMISSION_DENIED);
		developerACL.add(CommandEnum.LOGOUT);
		
		acl.put(RoleEnum.DEVELOPER, developerACL);
	}
	
	private void setCustomersACL() {
		customerACL.add(CommandEnum.SHOW_FORM);
		customerACL.add(CommandEnum.PERMISSION_DENIED);
		customerACL.add(CommandEnum.LOGOUT);
		customerACL.add(CommandEnum.ORDER_LIST);
		customerACL.add(CommandEnum.ORDER_SHOW_ADD_FORM);
		customerACL.add(CommandEnum.ORDER_ADD);
		customerACL.add(CommandEnum.ORDER_VIEW);
		
		acl.put(RoleEnum.CUSTOMER, customerACL);
	}

}
