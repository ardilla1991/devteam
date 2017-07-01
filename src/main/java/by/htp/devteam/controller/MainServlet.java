package by.htp.devteam.controller;

import java.io.IOException;

import static by.htp.devteam.util.ConstantValue.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.devteam.command.CommandAction;
import by.htp.devteam.command.CommandExeption;
import by.htp.devteam.command.CommandFactory;

public class MainServlet extends HttpServlet{

	private static final long serialVersionUID = -6529178696057273184L;

	public MainServlet() {
        super();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Page page = processRequest(request, response, ActionEnum.GET);
		
		if ( page.isInclude() ) {
			this.getServletContext().getRequestDispatcher(page.getPage()).
	        include(request, response);
		} else if ( page.isRedirect() ) {
			response.sendRedirect(page.getPage());
		} else {
			RequestDispatcher disp = request.getRequestDispatcher(page.getPage());
			disp.forward(request, response);
		}	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Page page = processRequest(request, response, ActionEnum.POST);
		if ( !page.isRedirect() ) {
			RequestDispatcher disp = request.getRequestDispatcher(page.getPage());
			disp.forward(request, response);
		} else {
			response.sendRedirect(page.getPage());
		}
	}
	
	private Page processRequest(HttpServletRequest request, HttpServletResponse response, ActionEnum actionData) throws ServletException, IOException {
		String action = request.getParameter("action");
		Page page = null;
		
		CommandFactory commandFactory = CommandFactory.getInstance();
		CommandAction commandAction = null;
		try {
			commandAction = commandFactory.chooseAction(action);
			page = commandAction.execute(request, response);
		} catch (CommandExeption e) {
			page = new Page(PAGE_ERROR);
		}
		
		return page;
	}

}
