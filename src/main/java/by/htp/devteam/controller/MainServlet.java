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

import by.htp.devteam.command.CommandAction;
import by.htp.devteam.command.CommandExeption;
import by.htp.devteam.command.CommandFactory;

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
	
		displayPage(request, response, page);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		Page page = processRequest(request, response);

		displayPage(request, response, page);
	}
	
	private Page processRequest(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String action = request.getParameter(REQUEST_PARAM_ACTION);
		Page page = null;
		
		CommandFactory commandFactory = CommandFactory.getInstance();
		CommandAction commandAction = null;
		try {
			commandAction = commandFactory.chooseAction(action);
			page = commandAction.execute(request, response);
		} catch (CommandExeption e) {
			page = new Page(PAGE_ERROR_404);
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
	private void displayPage(HttpServletRequest request, HttpServletResponse response, Page page) 
			throws ServletException, IOException {
		
		if ( page.isRedirect() )
			response.sendRedirect(page.getPage());
		else {
			RequestDispatcher disp = request.getRequestDispatcher(page.getPage());
			disp.forward(request, response);
		}
	}

}
