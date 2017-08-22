package by.htp.devteam.controller.module;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.devteam.controller.Controller;
import by.htp.devteam.controller.ObjectNotFoundException;
import by.htp.devteam.controller.main.Page;

/**
 * Controller for order module.
 * @author julia
 *
 */
public interface OrderController extends Controller {
	
	/**
	 * Action for add order. If add order was success - redirect to message page.
	 * If add order wasn't success - forward to form page.
	 * @param request HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @return Page {@link by.htp.devteam.controller.main.Page}
	 */
	public Page addPOST(HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * Action for order show form.
	 * @param request HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @return Page {@link by.htp.devteam.controller.main.Page}
	 */
	public Page addGET(HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * Action for new orders list.
	 * @param request HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @return Page {@link by.htp.devteam.controller.main.Page}
	 */
	public Page newListGET(HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * Action for view order.
	 * @param request HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @return Page {@link by.htp.devteam.controller.main.Page}
	 * @throws ObjectNotFoundException If order not found in system.
	 */
	public Page viewGET(HttpServletRequest request, HttpServletResponse response) throws ObjectNotFoundException;
}
