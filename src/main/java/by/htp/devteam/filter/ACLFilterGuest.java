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
import by.htp.devteam.command.CommandEnum;
import by.htp.devteam.command.CommandExeption;

public class ACLFilterGuest implements Filter{

	private final static Map<RoleEnum, List<CommandEnum>> acl = new HashMap<RoleEnum, List<CommandEnum>>();
	private final static List<CommandEnum> guestACL = new ArrayList<CommandEnum>();
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

		setGuestsACL();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        
		String action = req.getParameter("action");
		System.out.println("guest action=" + action);

		HttpSession session = req.getSession(false);
		boolean isAuthorised = session != null && session.getAttribute("user") != null;
		
		if ( action != null && !isAuthorised ) {
			RoleEnum role = RoleEnum.GUEST;
			System.out.println("action="+action);
			System.out.println("isAu=" + isAuthorised);
			System.out.println("role="+role);
			try {
				if ( !acl.get(role).contains(CommandEnum.getAction(action))) {
					resp.sendRedirect("Main?action=permission_denied");
					return;
				}
			} catch (CommandExeption e) {
				System.out.println("invalid command");
				// e.printStackTrace();
			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
				
	}

	private void setGuestsACL() {
		guestACL.add(CommandEnum.LOGIN);
		guestACL.add(CommandEnum.SHOW_FORM);
		guestACL.add(CommandEnum.PERMISSION_DENIED);

		acl.put(RoleEnum.GUEST, guestACL);
	}

}
