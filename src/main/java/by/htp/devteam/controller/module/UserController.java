package by.htp.devteam.controller.module;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.devteam.controller.Controller;
import by.htp.devteam.controller.ObjectNotFoundException;
import by.htp.devteam.controller.main.Page;

/**
 * Controller for user module.
 * @author julia
 *
 */
public interface UserController extends Controller {
	
	/**
	 * Action for add user for employee. If add user was success - redirect to message page.
	 * If add user wasn't success - forward to form page.
	 * @param request HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @return Page {@link by.htp.devteam.controller.main.Page}
	 * @throws ObjectNotFoundException When this is attempt to add user for not existing employee
	 */
	public Page addPOST(HttpServletRequest request, HttpServletResponse response) 
			throws ObjectNotFoundException;
	
	/**
	 * Show form for add user.
	 * @param request HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @return Page {@link by.htp.devteam.controller.main.Page}
	 * @throws ObjectNotFoundException When this is attempt to add user for not existing employee
	 */
	public Page addGET(HttpServletRequest request, HttpServletResponse response) throws ObjectNotFoundException;
	
	/**
	 * Action to display user information.
	 * @param request HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @return Page {@link by.htp.devteam.controller.main.Page}
	 */
	public Page viewGET(HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * Action for guest's login. Also get information who is user - employee or customer. 
	 * Return page according to role.
	 * @param request HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @return Page {@link by.htp.devteam.controller.main.Page}
	 */
	public Page loginPOST(HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * Show form for login.
	 * @param request HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @return Page {@link by.htp.devteam.controller.main.Page}
	 */
	public Page loginGET(HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * Action for user's logout.
	 * @param request HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @return Page {@link by.htp.devteam.controller.main.Page}
	 */
	public Page logoutGET(HttpServletRequest request, HttpServletResponse response);
}
