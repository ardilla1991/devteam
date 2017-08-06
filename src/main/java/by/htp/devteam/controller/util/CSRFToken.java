package by.htp.devteam.controller.util;

import static by.htp.devteam.controller.util.ConstantValue.*;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public final class CSRFToken {
	
	private final static CSRFToken instance = new CSRFToken();
	
	private CSRFToken() {
		super();
	}
	
	public static CSRFToken getInstance() {
		return instance;
	}
	
	private String generateCSFRToken() {
		int random = new Random().nextInt(10000);
		StringBuilder token = new StringBuilder(random);
		token.reverse();
		return token.toString();
	}
	
	public void validationToken(HttpServletRequest request) throws SecurityException {
		HttpSession session = request.getSession(false);
        String sessionToken = (String) session.getAttribute(SESSION_PARAM_FORM_TOKEN);
        String token = request.getParameter(REQUEST_PARAM_FORM_TOKEN);
        if ( !sessionToken.equals(token) )
        	throw new SecurityException("token is not valid");
	}
	
	public void setToken (HttpServletRequest request) {
		String token = generateCSFRToken();
		
		HttpSession session = request.getSession(false);
		session.setAttribute(SESSION_PARAM_FORM_TOKEN, token);
		request.setAttribute(REQUEST_PARAM_FORM_TOKEN, token);
	}
}
