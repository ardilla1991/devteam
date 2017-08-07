package by.htp.devteam.controller.module;

import static by.htp.devteam.controller.util.ConstantValue.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.devteam.bean.Employee;
import by.htp.devteam.bean.vo.PagingVo;
import by.htp.devteam.controller.Controller;
import by.htp.devteam.controller.main.Page;
import by.htp.devteam.controller.util.CSRFToken;
import by.htp.devteam.controller.util.SecurityException;
import by.htp.devteam.service.EmployeeService;
import by.htp.devteam.service.QualificationService;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.ServiceFactory;

/**
 * Controller for employee module.
 * @author julia
 *
 */
public class EmployeeController implements Controller {
	
	/**
	 * Action for add employee. If add employee was success - redirect to message page.
	 * If add employee wasn't success - forward to form page.
	 * @param request
	 * @param response
	 * @return Page {@link by.htp.devteam.controller.main.Page}
	 * @throws SecurityException If csrf token is not valid.
	 */
	public Page addPOST(HttpServletRequest request, HttpServletResponse response) throws SecurityException {
		CSRFToken.getInstance().validationToken(request);
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		EmployeeService employeeService = serviceFactory.getEmployeeService();
		
		String page = PAGE_EMPLOYEE_ADD_MESSAGE_URI;
		String name = request.getParameter(REQUEST_PARAM_EMPLOYEE_NAME);
		String startWork = request.getParameter(REQUEST_PARAM_EMPLOYEE_START_WORK);
		String qualification = request.getParameter(REQUEST_PARAM_EMPLOYEE_QUALIFICATION);
		
		try {
			employeeService.add(name, startWork, qualification);
		} catch (ServiceException e) {
			request.setAttribute(REQUEST_PARAM_ERROR_CODE, e.getErrorCode().getValue());
			request.setAttribute(REQUEST_PARAM_ERROR_FIELD, e.getFields());
			request.setAttribute(REQUEST_PARAM_EMPLOYEE_NAME, name);
			request.setAttribute(REQUEST_PARAM_EMPLOYEE_START_WORK, startWork);
			request.setAttribute(REQUEST_PARAM_EMPLOYEE_QUALIFICATION, qualification);
			
			return addGET(request, response);
		}
		
		return new Page(page, true);
	}
	
	/**
	 * Action for employee show form.
	 * @param request
	 * @param response
	 * @return Page {@link by.htp.devteam.controller.main.Page}
	 */
	public Page addGET(HttpServletRequest request, HttpServletResponse response) {
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		QualificationService qualificationService = serviceFactory.getQualificationService();
		try {
			request.setAttribute(REQUEST_PARAM_QUALIFICATION_LIST, qualificationService.fetchAll());
			
			CSRFToken.getInstance().setToken(request);
		} catch (ServiceException e) {
			request.setAttribute(REQUEST_PARAM_ERROR_CODE, e.getErrorCode().getValue());
		}
		
		return new Page(PAGE_EMPLOYEE_ADD);
	}
	
	/**
	 * Action for show page about result of adding employee.
	 * @param request
	 * @param response
	 * @return
	 */
	public Page messageGET(HttpServletRequest request, HttpServletResponse response) {

		return new Page(PAGE_EMPLOYEE_ADD_MESSAGE);
	}
	
	/**
	 * Action for getting all employees. 
	 * @param request
	 * @param response
	 * @return
	 */
	public Page listGET(HttpServletRequest request, HttpServletResponse response) {
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		EmployeeService employeeService = serviceFactory.getEmployeeService();
		
		String currPage = request.getParameter(REQUEST_PARAM_PAGE);
		try {
			PagingVo<Employee> pagingVo = employeeService.fetchAll(currPage);
			
			request.setAttribute(REQUEST_PARAM_URI, PAGE_EMPLOYEE_LIST_URI);
			request.setAttribute(REQUEST_PARAM_EMPLOYEE_LIST, pagingVo.getRecords());
			request.setAttribute(REQUEST_PARAM_CURR_PAGE, pagingVo.getCurrPage());
			request.setAttribute(REQUEST_PARAM_COUNT_PAGES, pagingVo.getCountPages());
		} catch (ServiceException e) {
			request.setAttribute(REQUEST_PARAM_ERROR_CODE, e.getErrorCode().getValue());
		}
		
		return new Page(PAGE_EMPLOYEE_LIST);
	}
}
