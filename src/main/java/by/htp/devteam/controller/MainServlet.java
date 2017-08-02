package by.htp.devteam.controller;

/**
 * Main point for application
 */
import java.io.IOException;

import static by.htp.devteam.command.util.ConstantValue.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.devteam.command.CommandFactory;
import by.htp.devteam.command.util.SecurityException;
import by.htp.devteam.command.CommandAction;
import by.htp.devteam.command.CommandExeption;

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
		
		CommandAction commandAction = null;
		try {
			commandAction = CommandFactory.getAction(action).chooseAction();
			if ( httpMethod.equals(HTTPMethod.POST ) )
				page = commandAction.executePOST(request, response);
			else if ( httpMethod.equals(HTTPMethod.GET) )
				page = commandAction.executeGET(request, response);
			///////Yyyy.class.getMethod("methodName").invoke(someArgs)
		} catch (CommandExeption e) {
			//page = new Page(PAGE_ERROR_404);
			response.sendError(404);
			return null; ///////// спорный момент!!! 
		} catch (SecurityException e) {
			page = new Page(PAGE_OBJECT_NOT_FOUND_URI, true);
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
