package by.htp.devteam.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.devteam.command.util.SecurityException;
import by.htp.devteam.controller.Page;

/**
 * Interface for all executable actions
 * @author julia
 *
 */
public interface CommandAction {
	
	/**
	 * Execute a number of actions for command. Method get request params, logging action, call service layer 
	 * and return Page object.
	 * Catch exception from service layer
	 * @param request
	 * @param response
	 * @return {@link by.htp.devteam.controller.Page} object.
	 * @throws SecurityException 
	 */
	public Page executePOST(HttpServletRequest request, HttpServletResponse response) throws SecurityException;
	
	public Page executeGET(HttpServletRequest request, HttpServletResponse response) throws SecurityException;
}
