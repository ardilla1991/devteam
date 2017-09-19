package by.htp.devteam.filter;

import static by.htp.devteam.controller.util.ConstantValue.*;

import java.io.IOException;
import java.util.Locale;
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

/**
 * Servlet Filter implementation class LocaleFilter
 */
public class LocaleFilter implements Filter {

    /**
     * Default constructor. 
     */
    public LocaleFilter() {

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

		HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        
		String language = getLanguageFromURI(req.getRequestURI());
		Locale.setDefault(new Locale(language));
		req.setAttribute(REQUEST_PARAM_CURRENT_LANGUAGE, language);
		req.setAttribute(REQUEST_PARAM_APP_NAME_AND_LANG, SYSTEM_PATH + language);
		
		chain.doFilter(request, response);
	}
	
	private String getLanguageFromURI(String uri) {
		Pattern pattern = Pattern.compile("^" + SYSTEM_PATH + "(ru|en)/{0,1}.+{0,}");
        Matcher matcher = pattern.matcher(uri);
        if ( matcher.find() ) {
        	return matcher.group(1);
        } else return "";
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {

	}

}
