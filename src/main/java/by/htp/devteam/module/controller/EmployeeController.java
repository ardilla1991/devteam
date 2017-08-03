package by.htp.devteam.module.controller;

import static by.htp.devteam.module.util.ConstantValue.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.devteam.bean.Employee;
import by.htp.devteam.bean.vo.PagingVo;
import by.htp.devteam.controller.Page;
import by.htp.devteam.module.Controller;
import by.htp.devteam.module.util.CSRFToken;
import by.htp.devteam.module.util.SecurityException;
import by.htp.devteam.service.EmployeeService;
import by.htp.devteam.service.QualificationService;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.ServiceFactory;

public class EmployeeController implements Controller {
	/**
	 * Action for add employee.
	 */
	public Page addPOST(HttpServletRequest request, HttpServletResponse response) throws SecurityException {
		
		CSRFToken.validationToken(request);
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		EmployeeService employeeService = serviceFactory.getEmployeeService();
		
		String page = PAGE_EMPLOYEE_ADD_MESSAGE_URI;
		boolean isRedirect = true;
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
			
			page = PAGE_EMPLOYEE_SHOW_ADD_FORM_URI;
			isRedirect = false;
		}
		
		return new Page(page, isRedirect);
	}
	
	/**
	 * Action for employee show form.
	 */
	public Page addGET(HttpServletRequest request, HttpServletResponse response) {
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		QualificationService qualificationService = serviceFactory.getQualificationService();
		try {
			request.setAttribute(REQUEST_PARAM_QUALIFICATION_LIST, qualificationService.fetchAll());
			
			CSRFToken.setToken(request);
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
