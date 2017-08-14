package by.htp.devteam.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.devteam.controller.main.Page;

/**
 * Interface for all module's controller. 
 * All controllers has methods names according to HTTPMethod.
 * If method is called by GET method the name must by ended with GET.
 * If method is called by POST method the name must by ended with POST.
 * Example: addGET - display page for add object (display form).
 * Example: addPOST - add object into system.
 * @author julia
 *
 */
public interface Controller {

	/**
	 * Show message after add record. Only show page with message that all is ok.
	 * @param request
	 * @param response
	 * @return
	 */
	public Page messageGET(HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * Action for getting all records. 
	 * @param request
	 * @param response
	 * @return
	 */
	public Page listGET(HttpServletRequest request, HttpServletResponse response);
}
