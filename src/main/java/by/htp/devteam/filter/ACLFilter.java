package by.htp.devteam.filter;

import static by.htp.devteam.controller.util.ConstantValue.*;
import static by.htp.devteam.filter.util.ConstantValue.*;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.devteam.acl.ACL;
import by.htp.devteam.bean.UserRole;
import by.htp.devteam.bean.vo.UserVo;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Check if selected action for GUEST user is in ACL list. If not we send him on login page
 * @author julia
 *
 */
public final class ACLFilter implements Filter{

	/** logger */
	private final static Logger logger = LogManager.getLogger(ACLFilter.class.getName());
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

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

		HttpSession session = req.getSession(false);
		boolean isAuthorised = session != null && session.getAttribute(SESSION_PARAM_USER) != null;
		String module = request.getParameter(REQUEST_PARAM_MODULE);
		String action = request.getParameter(REQUEST_PARAM_ACTION);
			
		UserRole role = UserRole.GUEST;
		if (isAuthorised) {
			UserVo userVO = (UserVo) session.getAttribute(SESSION_PARAM_USER);
			role = userVO.getUser().getRole();
		}
		boolean issetInACL = ACL.getInstance().issetInACL(role, module, action);
		if ( !issetInACL ) {
			String ipAddress = req.getRemoteAddr();
			logger.info(MSG_LOGGER_REQUEST, " IP "+ ipAddress 
	        		+ " - resourse: " + req.getRequestURL() 
	        		+ " is requested by: " + request.getRemoteHost() + " " + MSG_LOGGER_PERMISSION_DENIED);
			if ( isAuthorised ) {
				resp.sendError(403);
			} else {
				resp.sendRedirect(PAGE_USER_LOGIN_URI);
			}
			return;
		}

		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}

	

}
