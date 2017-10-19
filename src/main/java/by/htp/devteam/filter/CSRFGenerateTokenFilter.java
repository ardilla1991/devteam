package by.htp.devteam.filter;

import static by.htp.devteam.filter.util.ConstantValue.*;

import java.io.IOException;
import java.security.SecureRandom;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.RandomStringUtils;

/**
 * Servlet Filter implementation class SCRFFilter
 * @author julia
 */
public class CSRFGenerateTokenFilter implements Filter {

    /**
     * Default constructor. 
     */
    public CSRFGenerateTokenFilter() {

    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {

	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// Assume its HTTP
        HttpServletRequest req = (HttpServletRequest) request;
        
        if ( req.getSession(false) != null ) {
	        // Generate the token and store it in the users session
	        String token = RandomStringUtils.random(20, 0, 0, true, true, null, new SecureRandom());
	        
	        req.getSession(false).setAttribute(SESSION_PARAM_FORM_TOKEN, token);
	
	        // Add the token to the current request so it can be used
	        // by the page rendered in this request
	        req.setAttribute(REQUEST_PARAM_FORM_TOKEN, token);
        }
        chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {

	}

}
