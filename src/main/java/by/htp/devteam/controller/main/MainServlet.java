package by.htp.devteam.controller.main;

import static by.htp.devteam.controller.util.ConstantValue.*;

/**
 * Main point for application
 */
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.devteam.controller.Controller;
import by.htp.devteam.controller.ControllerExeption;
import by.htp.devteam.controller.ControllerFactory;
import by.htp.devteam.controller.util.SecurityException;

/**
 * Start module controller and method using Reflection.
 * @author julia
 *
 */
@MultipartConfig(
	    location="/tmp", 
	    fileSizeThreshold=1024*1024,    // 1 MB
	    maxFileSize=1024*1024*5,        // 5 MB 
	    maxRequestSize=1024*1024*5*5    // 25 MB
	)
public class MainServlet extends HttpServlet{

	private static final long serialVersionUID = -6529178696057273184L;

	public MainServlet() {
        super();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		Page page = processRequest(request, response, HTTPMethod.GET);
	
		displayPage(request, response, page, HTTPMethod.GET);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		Page page = processRequest(request, response, HTTPMethod.POST);

		displayPage(request, response, page, HTTPMethod.POST);
	}
	
	private Page processRequest(HttpServletRequest request, HttpServletResponse response, HTTPMethod httpMethod) 
			throws ServletException, IOException {		
		String module = request.getParameter(REQUEST_PARAM_MODULE);
		String action = request.getParameter(REQUEST_PARAM_ACTION);
		
		request.setAttribute(REQUEST_PARAM_APP_PATH, SYSTEM_PATH);
		Page page = null;
		Controller moduleController = null;
		try {
			moduleController = ControllerFactory.getController(module).chooseController();
			page = (Page) moduleController.getClass()
			.getMethod(generateMethodName(action) + request.getMethod(), HttpServletRequest.class, HttpServletResponse.class)
			.invoke(moduleController, request, response);
		} catch (ControllerExeption e) {
			//page = new Page(PAGE_ERROR_404);
			response.sendError(404);
			return null; ///////// спорный момент!!! 
		} catch (InvocationTargetException e) {
			//if (e.getCause() instanceof SecurityException) {
				response.sendError(404);
				return null; ///////// спорный момент!!!
			//}
		} catch (IllegalAccessException | IllegalArgumentException
				| NoSuchMethodException | java.lang.SecurityException e) {
			//e.printStackTrace();
			response.sendError(404);
			return null; ///////// спорный момент!!! 
		} 
		
		return page;
	}
	
	private String generateMethodName(String action) {
		StringBuilder method = new StringBuilder();
		String[] actionParts = action.split(URL_METHOD_DELIMITER);
		int actionPartLength = actionParts.length;
		method.append(actionParts[0]);
		for ( int i = 1; i < actionPartLength; i++ ) {
			method.append(actionParts[i].substring(0, 1).toUpperCase() + actionParts[i].substring(1));
		}
		
		return method.toString();
	}
	
	/**
	 * Display page with way chosen in Page (is redirect or not)
	 * @param request
	 * @param response
	 * @param page Page object
	 * @throws ServletException
	 * @throws IOException
	 */
	private void displayPage(HttpServletRequest request, HttpServletResponse response, Page page, HTTPMethod httpMethod) 
			throws ServletException, IOException {
		if ( page == null )
			return;
		
		if ( page.isRedirect() ) {
			if (httpMethod == HTTPMethod.POST) {
				response.setStatus(HttpServletResponse.SC_SEE_OTHER);
				response.setHeader("Location", page.getPage());
			} else {
				//response.setHeader("cache-control", "private, max-age=0, no-cache, no-store");
				response.sendRedirect(page.getPage());
			}
		}
		else {
			RequestDispatcher disp = request.getRequestDispatcher(page.getPage());
			disp.forward(request, response);
		}
	}

}
