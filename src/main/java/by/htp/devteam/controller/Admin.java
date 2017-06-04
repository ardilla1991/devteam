package by.htp.devteam.controller;

import java.io.IOException;

import static by.htp.devteam.util.PageConctantValue.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.devteam.command.CommandAction;
import by.htp.devteam.command.CommandExeption;
import by.htp.devteam.command.CommandFactory;

public class Admin extends HttpServlet{

	private static final long serialVersionUID = -6529178696057273184L;

	public Admin() {
        super();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
	
	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String page = null;
		
		CommandFactory commandFactory = CommandFactory.getInstance();
		CommandAction commandAction = null;
		try {
			commandAction = commandFactory.chooseAction(action);
			page = commandAction.execute(request, response);
		} catch (CommandExeption e) {
			//e.printStackTrace();
			page = PAGE_ERROR;
		}
		System.out.println(page);
		RequestDispatcher disp = request.getRequestDispatcher(page);
		disp.forward(request, response);
	}
}
