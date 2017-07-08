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
import by.htp.devteam.command.CommandEnum;
import by.htp.devteam.command.CommandExeption;

import static by.htp.devteam.command.util.ConstantValue.*;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class ACLFilterGuest implements Filter{

	private final static Map<RoleEnum, List<CommandEnum>> acl = new HashMap<RoleEnum, List<CommandEnum>>();
	private final static List<CommandEnum> guestACL = new ArrayList<CommandEnum>();
	private final static Logger logger = LogManager.getLogger(ACLFilterGuest.class.getName());
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

		setGuestsACL();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        
		String action = req.getParameter(REQUEST_PARAM_ACTION);

		HttpSession session = req.getSession(false);
		boolean isAuthorised = session != null && session.getAttribute(SESSION_PARAM_USER) != null;

		if ( !isAuthorised && action != null ) {
			RoleEnum role = RoleEnum.GUEST;

			try {
				if ( !acl.get(role).contains(CommandEnum.getAction(action))) {
					resp.sendRedirect(PAGE_USER_SHOW_FORM_URI);
					return;
				}
			} catch (CommandExeption e) {
				logger.info(e.getMessage());
				req.getRequestDispatcher(PAGE_ERROR_404).forward(req, resp);
				return;
			}
		} else if ( action == null ) {
			logger.info(MSG_LOGGER_NULL_ACTION);
			//esp.sendRedirect(PAGE_EMPTY_URI);
			//req.getRequestDispatcher(PAGE_EMPTY_URI).forward(req, resp);
			//return;
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		guestACL.clear();
		acl.clear();
	}

	private void setGuestsACL() {
		guestACL.add(CommandEnum.LOGIN);
		guestACL.add(CommandEnum.SHOW_FORM);
		guestACL.add(CommandEnum.PERMISSION_DENIED);

		acl.put(RoleEnum.GUEST, guestACL);
	}

}
