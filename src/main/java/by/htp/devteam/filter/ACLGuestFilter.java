package by.htp.devteam.filter;

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
import by.htp.devteam.command.CommandFactory;
import by.htp.devteam.command.CommandExeption;

import static by.htp.devteam.command.util.ConstantValue.*;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Check if selected action for GUEST user is in ACL list. If not we send him on login page
 * @author julia
 *
 */
public final class ACLGuestFilter implements Filter{

	/** ACL for users */
	private final static Map<UserRole, List<CommandFactory>> acl = new HashMap<UserRole, List<CommandFactory>>();
	
	/** 
	 * ACL for guest. Contains a actions from Command enum
	 * @see by.htp.devteam.command.CommandFactory
	 */
	private final static List<CommandFactory> guestACL = new ArrayList<CommandFactory>();
	
	/** logger */
	private final static Logger logger = LogManager.getLogger(ACLGuestFilter.class.getName());
	
	/** pattern for url */
	private final static Pattern URL_PATTERN = Pattern.compile("^/devteam/(Main/?)?$");
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

		setGuestsACL();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        /*System.out.println(req.getRequestURI());
		System.out.println(req.getRequestURL());
		System.out.println(req.getQueryString());*/
		String action = req.getParameter(REQUEST_PARAM_ACTION);
		HttpSession session = req.getSession(false);
		boolean isAuthorised = session != null && session.getAttribute(SESSION_PARAM_USER) != null;
		if ( !isAuthorised && action != null ) {
			UserRole role = UserRole.GUEST;

			try {
				boolean issetInACL = acl.get(role).contains(CommandFactory.getAction(action) );
				if ( !issetInACL ) {
					resp.sendRedirect(PAGE_SHOW_AUTHORIZATION_FORM_URI);
					return;
				} else if ( issetInACL && !req.getMethod().equalsIgnoreCase(CommandFactory.getAction(action).getHTTPMethod().getValue()) ) {
					logger.info(MSG_LOGGER_WRONG_HTTP_METHOD);
					req.getRequestDispatcher(PAGE_DEFAULT).forward(req, resp);
					//resp.sendError(404);
					return;
				}
			} catch (CommandExeption e) {
				logger.info(e.getMessage());
				//resp.sendError(404);
				resp.sendRedirect(PAGE_SHOW_AUTHORIZATION_FORM_URI);
				return;
			}
		} else if ( action == null ) {
			Matcher matcher = URL_PATTERN .matcher(req.getRequestURI());
			if ( matcher.matches() == false ) {
				logger.info(MSG_LOGGER_NULL_ACTION);
				//req.getRequestDispatcher(PAGE_DEFAULT).forward(req, resp);
				//resp.sendRedirect("/devteam");
				//return;
			}
		}
		
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		guestACL.clear();
		acl.clear();
	}

	private void setGuestsACL() {
		guestACL.add(CommandFactory.LOGIN);
		guestACL.add(CommandFactory.LOGIN_SHOW_FORM);
		guestACL.add(CommandFactory.PERMISSION_DENIED);

		acl.put(UserRole.GUEST, guestACL);
	}

}
