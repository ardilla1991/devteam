package by.htp.devteam.controller.module;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.devteam.controller.Controller;
import by.htp.devteam.controller.ObjectNotFoundException;
import by.htp.devteam.controller.main.Page;

/**
 * Controller for project module.
 * @author julia
 *
 */
public interface ProjectController extends Controller {

	/**
	 * Action for add project. If add project was success - redirect to message page.
	 * If add project wasn't success - forward to form page.
	 * @param request
	 * @param response
	 * @return Page {@link by.htp.devteam.controller.main.Page}
	 * @throws ObjectNotFoundException 
	 */
	public Page addPOST(HttpServletRequest request, HttpServletResponse response) 
			throws ObjectNotFoundException;
	
	/**
	 * Action to show the form for add project.
	 * @param request
	 * @param response
	 * @return Page {@link by.htp.devteam.controller.main.Page}
	 * @throws ObjectNotFoundException 
	 */
	public Page addGET(HttpServletRequest request, HttpServletResponse response) throws ObjectNotFoundException;
	
	/**
	 * Action for display all projects by employee.
	 * @param request
	 * @param response
	 * @return Page {@link by.htp.devteam.controller.main.Page}
	 */
	public Page listByEmployeeGET(HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * Action for updating hours spending by employee on project. 
	 * Show form.
	 * @param request
	 * @param response
	 * @return Page {@link by.htp.devteam.controller.main.Page}
	 * @throws ObjectNotFoundException If object not found.
	 */
	public Page updateHoursGET(HttpServletRequest request, HttpServletResponse response) throws ObjectNotFoundException;
	
	/**
	 * Action for updating hours spending by employee on project. 
	 * If update was success - redirect project list page.
	 * If update wasn't success - forward to form page.
	 * @param request
	 * @param response
	 * @return Page {@link by.htp.devteam.controller.main.Page}
	 * @throws ObjectNotFoundException If object not found in system.
	 */
	public Page updateHoursPOST(HttpServletRequest request, HttpServletResponse response) throws ObjectNotFoundException;
	
	/**
	 * Action to view project. And in jsp you cat set hours for project by employee
	 * @param request
	 * @param response
	 * @return Page {@link by.htp.devteam.controller.main.Page}
	 * @throws ObjectNotFoundException If object not found in system.
	 */
	public Page viewGET(HttpServletRequest request, HttpServletResponse response) throws ObjectNotFoundException;
	
	/**
	 * Action for find project.
	 * @param request
	 * @param response
	 * @return Page {@link by.htp.devteam.controller.main.Page}
	 */
	public Page findGET(HttpServletRequest request, HttpServletResponse response);
}
