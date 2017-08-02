package by.htp.devteam.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Set encoding for response and request. Encoding name set in web.xml
 * @author julia
 *
 */
public class EncodingFilter implements Filter {

	/** Encoding from web.xml */
	private String encoding;
	
	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		encoding = fConfig.getInitParameter("encoding");
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String codeRequest = request.getCharacterEncoding();
		if ( encoding != null && !encoding.equalsIgnoreCase(codeRequest) ) {
			request.setCharacterEncoding(encoding);
			response.setCharacterEncoding(encoding);
		}
		
		chain.doFilter(request, response);
	}
	
	@Override
	public void destroy() {
		encoding = null;
	}

}
