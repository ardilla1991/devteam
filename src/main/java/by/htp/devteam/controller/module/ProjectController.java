package by.htp.devteam.controller.module;

import static by.htp.devteam.controller.util.ConstantValue.*;

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
import by.htp.devteam.controller.Controller;
import by.htp.devteam.controller.ObjectNotFoundExeption;
import by.htp.devteam.controller.main.Page;
import by.htp.devteam.controller.util.CSRFToken;
import by.htp.devteam.controller.util.SecurityException;
import by.htp.devteam.service.EmployeeService;
import by.htp.devteam.service.OrderService;
import by.htp.devteam.service.ProjectService;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.ServiceFactory;
import by.htp.devteam.service.util.UploadFile;

/**
 * Controller for project module.
 * @author julia
 *
 */
public class ProjectController implements Controller {
	
	/**
	 * Action for add project. If add project was success - redirect to message page.
	 * If add project wasn't success - forward to form page.
	 * @param request
	 * @param response
	 * @return Page {@link by.htp.devteam.controller.main.Page}
	 * @throws SecurityException If csrf token is not valid.
	 * @throws ObjectNotFoundExeption 
	 */
	public Page addPOST(HttpServletRequest request, HttpServletResponse response) throws SecurityException, ObjectNotFoundExeption {
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		OrderService orderService = serviceFactory.getOrderService();
		ProjectService projectService = serviceFactory.getProjectService();
		
		String orderId = request.getParameter(REQUEST_PARAM_ORDER_ID);

		CSRFToken.getInstance().validationToken(request);
		
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
			request.setAttribute(REQUEST_PARAM_ORDER_ID, orderId);

			return addGET(request, response);
		}

		return new Page(PAGE_PROJECT_ADD_MESSAGE_URI, true);
	}
	
	/**
	 * Action to show the form for add project.
	 * @param request
	 * @param response
	 * @return Page {@link by.htp.devteam.controller.main.Page}
	 * @throws ObjectNotFoundExeption 
	 */
	public Page addGET(HttpServletRequest request, HttpServletResponse response) throws ObjectNotFoundExeption {	
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
			
			CSRFToken.getInstance().setToken(request);
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
	 * @return Page {@link by.htp.devteam.controller.main.Page}
	 */
	public Page messageGET(HttpServletRequest request, HttpServletResponse response) {
		
		return new Page(PAGE_PROJECT_ADD_MESSAGE);
	}
	
	/**
	 * Action for display all projects.
	 * @param request
	 * @param response
	 * @return Page {@link by.htp.devteam.controller.main.Page}
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
	 * Action for display all projects by employee.
	 * @param request
	 * @param response
	 * @return Page {@link by.htp.devteam.controller.main.Page}
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
	 * Action for updating hours spending by employee on project. 
	 * Show form.
	 * @param request
	 * @param response
	 * @return Page {@link by.htp.devteam.controller.main.Page}
	 * @throws ObjectNotFoundExeption If object not found.
	 */
	public Page updateHoursGET(HttpServletRequest request, HttpServletResponse response) throws ObjectNotFoundExeption {		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		ProjectService projectService = serviceFactory.getProjectService();
		
		String id = request.getParameter(REQUEST_PARAM_PROJECT_ID);
		
		CSRFToken.getInstance().setToken(request);
		try {
			ProjectVo projectVo = projectService.getById(id);
			request.setAttribute(REQUEST_PARAM_PROJECT_VO, projectVo);
		} catch (ServiceException e) {
			request.setAttribute(REQUEST_PARAM_ERROR_CODE, e.getErrorCode().getValue());
		}
		
		return new Page(PAGE_PROJECT_UPDATE_HOURS);
	}
	
	/**
	 * Action for updating hours spending by employee on project. 
	 * If update was success - redirect project list page.
	 * If update wasn't success - forward to form page.
	 * @param request
	 * @param response
	 * @return Page {@link by.htp.devteam.controller.main.Page}
	 * @throws SecurityException If csrf token is not valid.
	 * @throws ObjectNotFoundExeption If object not found in system.
	 */
	public Page updateHoursPOST(HttpServletRequest request, HttpServletResponse response) throws SecurityException, ObjectNotFoundExeption {
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		ProjectService projectService = serviceFactory.getProjectService();

		String id = request.getParameter(REQUEST_PARAM_PROJECT_ID);
		String hours = request.getParameter(REQUEST_PARAM_PROJECT_HOURS);

		HttpSession session = request.getSession(false);
		UserVo userVO = (UserVo) session.getAttribute(SESSION_PARAM_USER);
		
		CSRFToken.getInstance().validationToken(request);
		
		try {
			projectService.updateHours(id, userVO.getEmployee(), hours);
		} catch (ServiceException e) {
			request.setAttribute(REQUEST_PARAM_ERROR_CODE, e.getErrorCode().getValue());
			request.setAttribute(REQUEST_PARAM_ERROR_FIELD, e.getFields());
			request.setAttribute(REQUEST_PARAM_PROJECT_ID, id);
			request.setAttribute(REQUEST_PARAM_PROJECT_HOURS, hours);
			
			return updateHoursGET(request, response);		
		}
		
		return new Page(PAGE_PROJECT_LIST_BY_EMPLOYEE_URI, true);
	}
	
	/**
	 * Action to view project. And in jsp you cat set hours for project by employee
	 * @param request
	 * @param response
	 * @return Page {@link by.htp.devteam.controller.main.Page}
	 * @throws ObjectNotFoundExeption If object not found in system.
	 */
	public Page viewGET(HttpServletRequest request, HttpServletResponse response) throws ObjectNotFoundExeption {
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		ProjectService projectService = serviceFactory.getProjectService();
		
		String page = PAGE_PROJECT_VIEW;
		String id = request.getParameter(REQUEST_PARAM_PROJECT_ID);

		try {
			ProjectVo projectVo = projectService.getById(id);
			request.setAttribute(REQUEST_PARAM_PROJECT_VO, projectVo);
			request.setAttribute(REQUEST_PARAM_UPLOAD_PATH, UploadFile.uploadPath);
		} catch (ServiceException e) {
			request.setAttribute(REQUEST_PARAM_ERROR_CODE, e.getErrorCode().getValue());
		}
		
		return new Page(page);
	}
	
	/**
	 * Action for find project.
	 * @param request
	 * @param response
	 * @return Page {@link by.htp.devteam.controller.main.Page}
	 */
	public Page findGET(HttpServletRequest request, HttpServletResponse response) {
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
