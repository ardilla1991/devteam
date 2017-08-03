package by.htp.devteam.module.controller;

import static by.htp.devteam.module.util.ConstantValue.*;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.devteam.bean.Employee;
import by.htp.devteam.bean.Project;
import by.htp.devteam.bean.vo.OrderVo;
import by.htp.devteam.bean.vo.PagingVo;
import by.htp.devteam.bean.vo.ProjectVo;
import by.htp.devteam.bean.vo.UserVo;
import by.htp.devteam.controller.Page;
import by.htp.devteam.module.Controller;
import by.htp.devteam.module.util.CSRFToken;
import by.htp.devteam.module.util.SecurityException;
import by.htp.devteam.service.EmployeeService;
import by.htp.devteam.service.OrderService;
import by.htp.devteam.service.ProjectService;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.ServiceFactory;

public class ProjectController implements Controller {
	
	/**
	 * Action for add project.
	 * @param request
	 * @param response
	 * @return
	 * @throws SecurityException
	 */
	public Page addPOST(HttpServletRequest request, HttpServletResponse response) throws SecurityException {
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		OrderService orderService = serviceFactory.getOrderService();
		ProjectService projectService = serviceFactory.getProjectService();
		
		//String page = PAGE_PROJECT_LIST_URI;
		String page = PAGE_PROJECT_ADD_MESSAGE_URI;
		boolean isRedirect = true;
		String orderId = request.getParameter(REQUEST_PARAM_ORDER_ID);

		CSRFToken.validationToken(request);
		
		String title = request.getParameter(REQUEST_PARAM_PROJECT_TITLE);
		String description = request.getParameter(REQUEST_PARAM_PROJECT_DESCRIPTION);
		String[] employees = request.getParameterValues(REQUEST_PARAM_PROJECT_EMPLOYEE);
		String price = request.getParameter(REQUEST_PARAM_ORDER_PRICE);
		try {
			OrderVo orderVo = orderService.getById(orderId);
			projectService.add(orderVo, title, description, employees, price);
		} catch (ServiceException e) {
			request.setAttribute(REQUEST_PARAM_ERROR_CODE, e.getErrorCode().getValue());
			request.setAttribute(REQUEST_PARAM_ERROR_FIELD, e.getFields());
			request.setAttribute(REQUEST_PARAM_PROJECT_TITLE, title);
			request.setAttribute(REQUEST_PARAM_PROJECT_DESCRIPTION, description);
			request.setAttribute(REQUEST_PARAM_PROJECT_EMPLOYEE, employees);
			request.setAttribute(REQUEST_PARAM_ORDER_PRICE, price);
			
			page = PAGE_PROJECT_SHOW_ADD_FORM_URI + orderId;
			isRedirect = false;
		}

		return new Page(page, isRedirect);
	}
	
	/**
	 * Action to show the form for add project.
	 */
	public Page addGET(HttpServletRequest request, HttpServletResponse response) {	
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		OrderService orderService = serviceFactory.getOrderService();
		
		String orderId = request.getParameter(REQUEST_PARAM_ORDER_ID);

		try {
			OrderVo orderVo = orderService.getById(orderId);
			request.setAttribute(REQUEST_PARAM_ORDER_VO, orderVo);
			
			EmployeeService employeeService = serviceFactory.getEmployeeService();
			List<Employee> employees = employeeService.getNotBusyEmployeesForPeriodByQualifications(orderVo.getOrder().getDateStart(), 
					orderVo.getOrder().getDateFinish(), orderVo.getQualifications().keySet());
			request.setAttribute(REQUEST_PARAM_EMPLOYEE_LIST, employees);
			
			CSRFToken.setToken(request);
		} catch (ServiceException e) {
			request.setAttribute(REQUEST_PARAM_ERROR_CODE, e.getErrorCode().getValue());
		}

		request.setAttribute(REQUEST_PARAM_ORDER_ID, orderId);

		return new Page(PAGE_PROJECT_ADD);
	}
	
	/**
	 * Action after added project. Only show page with message that all is ok
	 * @param request
	 * @param response
	 * @return
	 */
	public Page messageGET(HttpServletRequest request, HttpServletResponse response) {
		
		return new Page(PAGE_PROJECT_ADD_MESSAGE);
	}
	
	/**
	 * Action for display all projects
	 * @param request
	 * @param response
	 * @return
	 */
	public Page listGET(HttpServletRequest request, HttpServletResponse response) {
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		ProjectService projectService = serviceFactory.getProjectService();
		
		String currPage = request.getParameter(REQUEST_PARAM_PAGE);
		
		try {
			PagingVo<Project> pagingVo = projectService.fetchAll(currPage, null);
			
			request.setAttribute(REQUEST_PARAM_URI, PAGE_PROJECT_LIST_URI);
			request.setAttribute(REQUEST_PARAM_CURR_PAGE, pagingVo.getCurrPage());
			request.setAttribute(REQUEST_PARAM_COUNT_PAGES, pagingVo.getCountPages());
			request.setAttribute(REQUEST_PARAM_PROJECT_LIST, pagingVo.getRecords());
		} catch (ServiceException e) {
			request.setAttribute(REQUEST_PARAM_ERROR_CODE, e.getErrorCode().getValue());
		}
		
		return new Page(PAGE_PROJECT_LIST);
	}
	
	/**
	 * Action for display all projects by employee
	 * @param request
	 * @param response
	 * @return
	 */
	public Page listByEmployeeGET(HttpServletRequest request, HttpServletResponse response) {
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		ProjectService projectService = serviceFactory.getProjectService();
		
		String currPage = request.getParameter(REQUEST_PARAM_PAGE);
		
		HttpSession session = request.getSession(false);
		UserVo userVO = (UserVo) session.getAttribute(SESSION_PARAM_USER);
		
		try {
			PagingVo<Project> pagingVo = projectService.fetchAll(currPage, userVO.getEmployee());

			request.setAttribute(REQUEST_PARAM_URI, PAGE_DEFAULT_DEVELOPER);
			request.setAttribute(REQUEST_PARAM_PROJECT_LIST, pagingVo.getRecords());
			request.setAttribute(REQUEST_PARAM_CURR_PAGE, pagingVo.getCurrPage());
			request.setAttribute(REQUEST_PARAM_COUNT_PAGES, pagingVo.getCountPages());
		} catch (ServiceException e) {
			request.setAttribute(REQUEST_PARAM_ERROR_CODE, e.getErrorCode().getValue());
		}
		
		return new Page(PAGE_PROJECT_LIST);
	}
	
	/**
	 * Action to update hours count for project spending by employee.
	 * @param request
	 * @param response
	 * @return
	 * @throws SecurityException
	 */
	public Page executePOST(HttpServletRequest request, HttpServletResponse response) throws SecurityException {
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		ProjectService projectService = serviceFactory.getProjectService();
		
		String page = PAGE_PROJECT_LIST_BY_EMPLOYEE_URI;
		boolean isRedirect = true;

		String id = request.getParameter(REQUEST_PARAM_PROJECT_ID);
		String hours = request.getParameter(REQUEST_PARAM_PROJECT_HOURS);

		HttpSession session = request.getSession(false);
		UserVo userVO = (UserVo) session.getAttribute(SESSION_PARAM_USER);
		
		CSRFToken.validationToken(request);
		
		try {
			projectService.updateHours(id, userVO.getEmployee(), hours);
		} catch (ServiceException e) {
			request.setAttribute(REQUEST_PARAM_ERROR_CODE, e.getErrorCode().getValue());
			request.setAttribute(REQUEST_PARAM_ERROR_FIELD, e.getFields());
			request.setAttribute(REQUEST_PARAM_PROJECT_ID, id);
			request.setAttribute(REQUEST_PARAM_PROJECT_HOURS, hours);
			
			page = PAGE_PROJECT_VIEW_BY_ID_URI + id;
			isRedirect = false;			
		}
		
		return new Page(page, isRedirect);
	}
	
	/**
	 * Action to view project. And in jsp you cat set hours for project by employee
	 * @param request
	 * @param response
	 * @return
	 */
	public Page viewGET(HttpServletRequest request, HttpServletResponse response) {
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		ProjectService projectService = serviceFactory.getProjectService();
		
		String page = PAGE_PROJECT_VIEW;
		String id = request.getParameter(REQUEST_PARAM_PROJECT_ID);

		CSRFToken.setToken(request);
		try {
			ProjectVo projectVo = projectService.getById(id);
			request.setAttribute(REQUEST_PARAM_PROJECT_VO, projectVo);
		} catch (ServiceException e) {
			request.setAttribute(REQUEST_PARAM_ERROR_CODE, e.getErrorCode().getValue());
			page = PAGE_ERROR_404;
		}
		
		return new Page(page);
	}
	
	/**
	 * Action for find project
	 * @param request
	 * @param response
	 * @return
	 */
	public Page executeGET(HttpServletRequest request, HttpServletResponse response) {
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		ProjectService projectService = serviceFactory.getProjectService();
		
		String page = PAGE_PROJECT_FIND_AJAX;
		String title = request.getParameter(REQUEST_PARAM_PROJECT_TITLE);

		try {
			List<Project> project = projectService.findByTitle(title);
			
			request.setAttribute(REQUEST_PARAM_PROJECT_LIST, project);
		} catch (ServiceException e) {
			request.setAttribute(REQUEST_PARAM_ERROR_CODE, e.getErrorCode().getValue());
		}
		
		if ( request.getParameter(REQUEST_PARAM_XHR) == null)
			page = PAGE_PROJECT_FIND;
		
		return new Page(page);
	}
}
