package by.htp.devteam.controller.module;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.devteam.controller.Controller;
import by.htp.devteam.controller.main.Page;

/**
 * Controller for employee module.
 * @author julia
 *
 */
public interface EmployeeController extends Controller{
	
	/**
	 * Action for add employee. If add employee was success - redirect to message page.
	 * If add employee wasn't success - forward to form page.
	 * @param request
	 * @param response
	 * @return Page {@link by.htp.devteam.controller.main.Page}
	 */
	public Page addPOST(HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * Action for employee show form.
	 * @param request
	 * @param response
	 * @return Page {@link by.htp.devteam.controller.main.Page}
	 */
	public Page addGET(HttpServletRequest request, HttpServletResponse response);

}
