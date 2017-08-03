package by.htp.devteam.module.util;

import static by.htp.devteam.module.util.ConstantValue.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public final class CSRFToken {
	
	private CSRFToken() {
		super();
	}
	
	private static String generateCSFRToken() {
		return "sf";
	}
	
	public static void validationToken(HttpServletRequest request) throws SecurityException {
		HttpSession session = request.getSession(false);
        String sessionToken = (String) session.getAttribute(SESSION_PARAM_FORM_TOKEN);
        String token = request.getParameter(REQUEST_PARAM_FORM_TOKEN);
        if ( sessionToken.equals(token) )
        	throw new SecurityException("token is not valid");
	}
	
	public static void setToken (HttpServletRequest request) {
		request.setAttribute(REQUEST_PARAM_FORM_TOKEN, generateCSFRToken());
	}
}
