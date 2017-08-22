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
	 * @param request HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @return Page {@link by.htp.devteam.controller.main.Page}
	 * @throws ObjectNotFoundException When this is attempt to add project for not existing order
	 */
	public Page addPOST(HttpServletRequest request, HttpServletResponse response) 
			throws ObjectNotFoundException;
	
	/**
	 * Action to show the form for add project.
	 * @param request HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @return Page {@link by.htp.devteam.controller.main.Page}
	 * @throws ObjectNotFoundException When this is attempt to add project for not existing order
	 */
	public Page addGET(HttpServletRequest request, HttpServletResponse response) throws ObjectNotFoundException;
	
	/**
	 * Action for display all projects by employee.
	 * @param request HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @return Page {@link by.htp.devteam.controller.main.Page}
	 */
	public Page listByEmployeeGET(HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * Action for updating hours spending by employee on project. 
	 * Show form.
	 * @param request HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @return Page {@link by.htp.devteam.controller.main.Page}
	 * @throws ObjectNotFoundException If object not found. When project not founded
	 */
	public Page updateHoursGET(HttpServletRequest request, HttpServletResponse response) throws ObjectNotFoundException;
	
	/**
	 * Action for updating hours spending by employee on project. 
	 * If update was success - redirect project list page.
	 * If update wasn't success - forward to form page.
	 * @param request HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @return Page {@link by.htp.devteam.controller.main.Page}
	 * @throws ObjectNotFoundException When project not founded
	 */
	public Page updateHoursPOST(HttpServletRequest request, HttpServletResponse response) throws ObjectNotFoundException;
	
	/**
	 * Action to view project. And in jsp you cat set hours for project by employee
	 * @param request HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @return Page {@link by.htp.devteam.controller.main.Page}
	 * @throws ObjectNotFoundException When project not founded
	 */
	public Page viewGET(HttpServletRequest request, HttpServletResponse response) throws ObjectNotFoundException;
	
	/**
	 * Action for find project.
	 * @param request HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @return Page {@link by.htp.devteam.controller.main.Page}
	 */
	public Page findGET(HttpServletRequest request, HttpServletResponse response);
}
