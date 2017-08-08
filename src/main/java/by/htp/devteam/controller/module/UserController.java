package by.htp.devteam.controller.module;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.devteam.controller.Controller;
import by.htp.devteam.controller.ObjectNotFoundExeption;
import by.htp.devteam.controller.main.Page;
import by.htp.devteam.controller.util.SecurityException;

/**
 * Controller for user module.
 * @author julia
 *
 */
public interface UserController extends Controller {
	
	/**
	 * Action for add user for employee. If add user was success - redirect to message page.
	 * If add user wasn't success - forward to form page.
	 * @param request
	 * @param response
	 * @return Page {@link by.htp.devteam.controller.main.Page}
	 * @throws SecurityException If csrf token is not valid.
	 * @throws ObjectNotFoundExeption 
	 */
	public Page addPOST(HttpServletRequest request, HttpServletResponse response) 
			throws SecurityException, ObjectNotFoundExeption;
	
	/**
	 * Show form for add user.
	 * @param request
	 * @param response
	 * @return Page {@link by.htp.devteam.controller.main.Page}
	 */
	public Page addGET(HttpServletRequest request, HttpServletResponse response) throws ObjectNotFoundExeption;
	
	/**
	 * Action to display user information.
	 * @param request
	 * @param response
	 * @return Page {@link by.htp.devteam.controller.main.Page}
	 */
	public Page viewGET(HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * Action for guest's login. Also get information who is user - employee or customer. 
	 * Return page according to role.
	 * @param request
	 * @param response
	 * @return Page {@link by.htp.devteam.controller.main.Page}
	 */
	public Page loginPOST(HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * Show form for login.
	 * @param request
	 * @param response
	 * @return Page {@link by.htp.devteam.controller.main.Page}
	 */
	public Page loginGET(HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * Action for user's logout.
	 * @param request
	 * @param response
	 * @return Page {@link by.htp.devteam.controller.main.Page}
	 */
	public Page logoutGET(HttpServletRequest request, HttpServletResponse response);
}
