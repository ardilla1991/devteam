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

import by.htp.devteam.controller.ControllerException;

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
		Page page = processRequest(request, response);
	
		displayPage(request, response, page, HTTPMethod.GET);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		Page page = processRequest(request, response);

		displayPage(request, response, page, HTTPMethod.POST);
	}
	
	private Page processRequest(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {		
		
		request.setAttribute(REQUEST_PARAM_APP_PATH, SYSTEM_PATH);
		Page page = null;
		try {
			page = Runner.run(request, response);
		} catch (ControllerException | InvocationTargetException | IllegalAccessException 
				| IllegalArgumentException | NoSuchMethodException | java.lang.SecurityException e) {
			response.sendError(404);
		}
		
		return page;
	}
	
	/**
	 * Display page with way chosen in Page (is redirect or not)ю
	 * System show page with 303 status If page is redirected after post submit data.
	 * System show redirected page after redirect in code to other page.
	 * System forwards page by default.
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
