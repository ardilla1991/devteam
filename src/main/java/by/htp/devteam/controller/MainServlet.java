package by.htp.devteam.controller;

import static by.htp.devteam.module.util.ConstantValue.*;

/**
 * Main point for application
 */
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.devteam.module.ControllerFactory;
import by.htp.devteam.module.Controller;
import by.htp.devteam.module.ControllerExeption;
import by.htp.devteam.module.util.SecurityException;

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
		String action = request.getParameter(REQUEST_PARAM_ACTION);
		Page page = null;
		
		Controller moduleController = null;
		try {
			Router route = new Router();
			route.getRoute(request.getRequestURI());
			
			//moduleController.class.getMethod("methodName" + request.getMethod()).invoke(request, response);
		} catch (ControllerExeption e) {
			//page = new Page(PAGE_ERROR_404);
			response.sendError(404);
			return null; ///////// спорный момент!!! 
		}
		
		return page;
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
