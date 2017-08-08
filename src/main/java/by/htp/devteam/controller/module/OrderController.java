package by.htp.devteam.controller.module;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.devteam.controller.Controller;
import by.htp.devteam.controller.ObjectNotFoundExeption;
import by.htp.devteam.controller.main.Page;
import by.htp.devteam.controller.util.SecurityException;

/**
 * Controller for order module.
 * @author julia
 *
 */
public interface OrderController extends Controller {
	
	/**
	 * Action for add order. If add order was success - redirect to message page.
	 * If add order wasn't success - forward to form page.
	 * @param request
	 * @param response
	 * @return Page {@link by.htp.devteam.controller.main.Page}
	 * @throws SecurityException If csrf token is not valid.
	 */
	public Page addPOST(HttpServletRequest request, HttpServletResponse response) throws SecurityException;
	
	/**
	 * Action for order show form.
	 * @param request
	 * @param response
	 * @return Page {@link by.htp.devteam.controller.main.Page}
	 */
	public Page addGET(HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * Action for new orders list.
	 * @param request
	 * @param response
	 * @return Page {@link by.htp.devteam.controller.main.Page}
	 */
	public Page newListGET(HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * Action for view order.
	 * @param request
	 * @param response
	 * @return Page {@link by.htp.devteam.controller.main.Page}
	 * @throws ObjectNotFoundExeption If object not found in system.
	 */
	public Page viewGET(HttpServletRequest request, HttpServletResponse response) throws ObjectNotFoundExeption;
}
