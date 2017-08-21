package by.htp.devteam.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import by.htp.devteam.util.UrlRewriter;

import static by.htp.devteam.controller.util.ConstantValue.*;

/**
 * Servlet Filter implementation class UrlRewriterFilter
 */
public class UrlRewriteFilter implements Filter {
	
    /**
     * Default constructor. 
     */
    public UrlRewriteFilter() {
        super();
    }
    
    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {

    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
    		throws IOException, ServletException {
    	
        HttpServletRequest req = (HttpServletRequest) request;
		
		String forwardPath = UrlRewriter.getInstance().urlRewrite(req.getRequestURI(), true);
		if ( forwardPath.length() > 0 )
			req.getRequestDispatcher(SERVLET_NAME + forwardPath).forward(request, response);
		else
			chain.doFilter(request, response);
    }
    
    /**
     * @see Filter#destroy()
     */
    public void destroy() {

    }

}
