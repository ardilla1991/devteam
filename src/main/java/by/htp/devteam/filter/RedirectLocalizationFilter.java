package by.htp.devteam.filter;

import static by.htp.devteam.controller.util.ConstantValue.SYSTEM_PATH;

import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;
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
 * Servlet Filter implementation class LocalizationFilter
 */
public class RedirectLocalizationFilter implements Filter {
	
    private static Properties supportedLanguages;

    /**
     * Default constructor. 
     */
    public RedirectLocalizationFilter() {
        super();
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

        String[] languageAndQueryStringFromURI = getLanguageAndQueryStringFromURI(req.getRequestURI());
        if ( languageAndQueryStringFromURI[0].length() > 0 ) {
        	chain.doFilter(request, response);
        	return;
        } else {
        	Locale locale = detectLocale(req);
    		String lang = getLanguage(locale);
    		
    		resp.sendRedirect(SYSTEM_PATH + locale.getLanguage() + "/" + languageAndQueryStringFromURI[1]);
        }
		//chain.doFilter(request, response);
	}
	
	
	private String[] getLanguageAndQueryStringFromURI(String uri) {
		String language = "";
		String queryString = "";
		Pattern pattern = Pattern.compile("^" + SYSTEM_PATH + "(ru|en)/{0,1}.+{0,}");
        Matcher matcher = pattern.matcher(uri);
        if (matcher.find()) {
        	language = matcher.group(1);      	
        } else {
        	queryString = uri.replaceAll("^" + SYSTEM_PATH, "");
        }

		String res[] = new String[2]; 
        res[0] = language;
        res[1] = queryString;
        
		return res;
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
        supportedLanguages = new Properties();
        // Just for demonstration of the concept
        // you would probably load it from i.e. XML
        supportedLanguages.put("DEFAULT", Locale.US);
        // example mapping of "de" to "de_DE"
        //supportedLanguages.put("de-DEFAULT", Locale.GERMANY);
        supportedLanguages.put("ru-DEFAULT", new Locale("ru"));
        supportedLanguages.put("en-DEFAULT", new Locale("en"));
        //supportedLanguages.put("de_AT", new Locale("de", "AT"));
        //supportedLanguages.put("de_CH", new Locale("de", "CH"));
        supportedLanguages.put("ru_RU", new Locale("ru"));
        //supportedLanguages.put("ja_JP", Locale.JAPAN);
	}
	
    private Locale detectLocale(HttpServletRequest request) {
    	Locale requestLocale = null;;
        Enumeration<Locale> locales = request.getLocales();
        while (locales.hasMoreElements()) {
            Locale locale = (Locale) locales.nextElement();
            if (supportedLanguages.contains(locale)) {
                requestLocale = locale;
                break;
            }
        }
        
        if (requestLocale == null)
        	requestLocale = (Locale) supportedLanguages.get("DEFAULT");
        
        return requestLocale;
    }

    private String getLanguage(Locale requestLocale) {
        // get English name of the language
        // For native call requestLocale.getDisplayName(requestLocale)
        return requestLocale.getDisplayLanguage();
    }

}
