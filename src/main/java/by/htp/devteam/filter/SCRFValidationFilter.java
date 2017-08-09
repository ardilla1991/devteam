package by.htp.devteam.filter;

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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.htp.devteam.controller.main.HTTPMethod;

/**
 * Servlet Filter implementation class SCRFValidationFilter
 */
public class SCRFValidationFilter implements Filter {
	
	/** Logger */
	private static final Logger logger = LogManager.getLogger(SCRFValidationFilter.class);

    /**
     * Default constructor. 
     */
    public SCRFValidationFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
		// Assume its HTTP
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp =(HttpServletResponse) response;

		if ( HTTPMethod.valueOf(req.getMethod()) != HTTPMethod.POST ) {
        	chain.doFilter(request, response);
        	return;
		}

        // Get the token sent with the request
        String requestToken = (String) req.getParameter(REQUEST_PARAM_FORM_TOKEN);
        // Validate that the token is in the session
        String sessionToken = req.getSession(false) != null 
        					? (String) req.getSession(false).getAttribute(SESSION_PARAM_FORM_TOKEN)
        					: null;

        if ( sessionToken != null && sessionToken.equals(requestToken) ){
            // If the token is in the session, we move on
            chain.doFilter(request, response);
        } else {
            logger.info(MSG_LOGGER_REQUEST, " IP "+ req.getRemoteAddr() 
            		+ " - resourse: " + req.getMethod() + " " + req.getRequestURL() 
            		+ " is requested by: " + request.getRemoteHost() + " . Invalid SCRF token");
            // Otherwise we throw an exception aborting the request flow
            resp.sendError(404);
            return;
        }
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
