package by.htp.devteam.filter;

import static by.htp.devteam.controller.util.ConstantValue.*;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Logging actions
 * @author julia
 *
 */
public class LogFilter implements Filter {

	/** Logger */
	private static final Logger logger = LogManager.getLogger(LogFilter.class);
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        //Get the IP address of client machine.
        String ipAddress = req.getRemoteAddr();  

        //Log the IP address and current timestamp.
        logger.info(MSG_LOGGER_REQUEST, " IP "+ ipAddress 
        		+ " - resourse: " + req.getRequestURL() 
        		+ " is requested by: " + request.getRemoteHost());
        
        chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		
	}

}
