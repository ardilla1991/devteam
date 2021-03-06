package by.htp.devteam.util;

import static by.htp.devteam.controller.util.ConstantValue.*;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Url rewriter class. Class for create path from request url.
 * It create from /appName/ServletName/module/action/param1/value1 path 
 * to /appName/ServletName?module=module&amp;action=action&amp;param1=value1.
 * Singleton class.
 * @author julia
 *
 */
public final class UrlRewriter {
	
	/** Pattern for language name */
    private static final Pattern LANGUAGE_NAME_PATTERN = Pattern.compile("^[a-z]{2}$");
	
	/** Pattern for module name */
    private static final Pattern MODULE_NAME_PATTERN = Pattern.compile("^[a-z]+$");
    
    /** Pattern for action name */
    private static final Pattern ACTION_NAME_PATTERN = Pattern.compile("^[a-z_]+$");
    
    /** Pattern for parameter name */
    private static final Pattern PARAM_NAME_PATTERN = Pattern.compile("^[a-z_]+$");
    
	private static final UrlRewriter instance = new UrlRewriter();
	
	private UrlRewriter() {
		super();
	}
	
	public static UrlRewriter getInstance() {
		return instance;
	}
	
	/**
	 * Create path by uri from request
	 * @param uri from request
	 * @param withAdditionalParams Set 1 if is needed rewrite url with additional parameters
	 * @return String Result
	 */
    public String urlRewrite(final String uri, boolean withAdditionalParams) {	
    	StringBuilder forwardPath = new StringBuilder();
    	
    	String[] urlParts = getUriParts(uri);
		// if uri has module name, action name and possible has parameters too    	
    	Map<String, String> mainParams = getMainParametersFromUriParts(urlParts);
    	if ( !mainParams.isEmpty() ) {
			String mainUri = buildMainUri(mainParams);
			forwardPath.append(mainUri);
			
			if ( withAdditionalParams ) {
				String paramsUri = buildAdditionalUri(urlParts);
				forwardPath.append(paramsUri);
			}
    	}
    	
    	return forwardPath.toString();
    }
    
    /*
     * Prepare url for processing. Delete useless slashes from the begin and from the end of url.
     * Also delete path like APP_NAME/ and SERVLET_NAME/. Function only use parameters of request.
     */
    private String pripareUri(String uri) {
		char slash = URL_DELIMITER.charAt(0);
		// delete the first slash from url
		if ( uri.charAt(0) == slash )
			uri = uri.substring(1, uri.length());
		// delete the last slash from url
		if ( uri.charAt(uri.length() - 1) == slash )
			uri = uri.substring(0, uri.length() - 1);
		// delete application name from url
		if ( APP_NAME.length() > 0 )
			uri = uri.substring(APP_NAME.length() + 1, uri.length());
		if ( SERVLET_NAME.length() > 0 )
			uri = uri.substring(SERVLET_NAME.length() + 1, uri.length());
		
		return uri;
    }
    
    /*
     * Create main uri string from params module and action.
     * Create uri like ?module=module&action=action
     */
    private String buildMainUri(Map<String, String> mainParam) {
    	return URI_START + LANGUAGE + URI_EQUAL + mainParam.get(LANGUAGE)
    			+ URI_PARAM_DELIMITER + MODULE + URI_EQUAL + mainParam.get(MODULE) 
    			+ URI_PARAM_DELIMITER + ACTION + URI_EQUAL + mainParam.get(ACTION);
    }
    
    /*
     * Create additional uri string from not main params.
     * Create uri like &param1=value1&param2=value2
     */
    private String buildAdditionalUri(String[] urlParts) {
    	int urlPartsLength = urlParts.length;
		StringBuilder paramsUri = new StringBuilder();
		for ( int i = 3; i < urlPartsLength - 1; i += 2 ) {
			if ( isUrlPartMatches(getUrlPart(urlParts[i]), PARAM_NAME_PATTERN) && urlParts.length >= i + 1 ) {
				paramsUri.append(URI_PARAM_DELIMITER + getUrlPart(urlParts[i]) + URI_EQUAL + getUrlPart(urlParts[i + 1]));
			}
		}
		
		return paramsUri.toString();
    }
    
    /*
     * Check if part of url is equals to pattern
     */
    private boolean isUrlPartMatches(String urlPart, Pattern pattern) {
    	return pattern.matcher(urlPart).matches();
    }
    
    /*
     * Prepare url part to needed view
     * @param part string url part
     */
    private String getUrlPart(final String part) {
    	return part.toLowerCase();
    }
    
    /*
     * Get url parts from url string using delimiter
     */
    private String[] getUriParts(String uri) {
    	String preparedUri = pripareUri(uri);
    	String[] urlParts = preparedUri.split(URL_DELIMITER);
    	
    	return urlParts;
    }
    
    /*
     * Get main parameters from url parts
     * @param urlParts array of url parts
     * @return hashmap of module and action values
     */
    private Map<String, String> getMainParametersFromUriParts(String[] urlParts) {
    	Map<String, String> params = new HashMap<String, String>(2);
    	
    	if ( urlParts.length == 1 && isUrlPartMatches(getUrlPart(urlParts[0]), LANGUAGE_NAME_PATTERN))
    		params.put(LANGUAGE, getUrlPart(urlParts[0]));
    	else if ( urlParts.length >= 3 
    			&& isUrlPartMatches(getUrlPart(urlParts[0]), LANGUAGE_NAME_PATTERN)
    			&& isUrlPartMatches(getUrlPart(urlParts[1]), MODULE_NAME_PATTERN) 
				&& isUrlPartMatches(getUrlPart(urlParts[2]), ACTION_NAME_PATTERN) ) {
    		params.put(LANGUAGE, getUrlPart(urlParts[0]));
    		params.put(MODULE, getUrlPart(urlParts[1]));
    		params.put(ACTION, getUrlPart(urlParts[2]));
    	}
    	
    	return params;
    }
    
    /**
     * Get main parameters from url parts
     * @param uri string
     * @return Hashmap of module and action values
     */
    public Map<String, String> getMainParametersFromUri(String uri) {
    	String[] urlParts = getUriParts(uri);
    	
    	return getMainParametersFromUriParts(urlParts);
    }
	
}
