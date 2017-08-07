package by.htp.devteam.controller.main;

import static by.htp.devteam.controller.util.ConstantValue.REQUEST_PARAM_ACTION;
import static by.htp.devteam.controller.util.ConstantValue.REQUEST_PARAM_MODULE;
import static by.htp.devteam.controller.util.ConstantValue.URL_METHOD_DELIMITER;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.devteam.controller.Controller;
import by.htp.devteam.controller.ControllerExeption;
import by.htp.devteam.controller.ControllerFactory;

public final class Runner {

	private Runner() {
		super();
	}
	
	public static String generateMethodName(String action) {
		StringBuilder method = new StringBuilder();
		String[] actionParts = action.split(URL_METHOD_DELIMITER);
		int actionPartLength = actionParts.length;
		method.append(actionParts[0]);
		for ( int i = 1; i < actionPartLength; i++ ) {
			method.append(actionParts[i].substring(0, 1).toUpperCase() + actionParts[i].substring(1));
		}
		
		return method.toString();
	}
	
	public static Page run(HttpServletRequest request, HttpServletResponse response) 
			throws ControllerExeption, IllegalAccessException, IllegalArgumentException, 
			InvocationTargetException, NoSuchMethodException, SecurityException {
		
		String module = request.getParameter(REQUEST_PARAM_MODULE);
		String action = request.getParameter(REQUEST_PARAM_ACTION);
		
		Controller moduleController = ControllerFactory.getController(module).chooseController();
		Page page = (Page) moduleController.getClass()
		.getMethod(generateMethodName(action) + request.getMethod(), HttpServletRequest.class, HttpServletResponse.class)
		.invoke(moduleController, request, response);
		
		return page;
	}
}
