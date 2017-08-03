package by.htp.devteam.module;

import by.htp.devteam.module.util.SecurityException;

/**
 * Interface for all modules
 * @author julia
 *
 */
public interface Controller {
	/**
	 * Execute a number of actions for command. Method get request params, logging action, call service layer 
	 * and return Page object.
	 * Catch exception from service layer
	 * @param request
	 * @param response
	 * @return {@link by.htp.devteam.controller.Page} object.
	 * @throws SecurityException 
	 */
}
