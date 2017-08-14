package by.htp.devteam.controller.module.impl;

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
import by.htp.devteam.controller.ObjectNotFoundException;
import by.htp.devteam.controller.main.Page;
import by.htp.devteam.controller.module.ProjectController;
import by.htp.devteam.service.EmployeeService;
import by.htp.devteam.service.OrderService;
import by.htp.devteam.service.ProjectService;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.ServiceFactory;
import by.htp.devteam.service.util.UploadFile;

public final class ProjectControllerImpl implements ProjectController {
	
	public ProjectControllerImpl() {
		super();
	}
	
	@Override
	public Page addPOST(HttpServletRequest request, HttpServletResponse response) throws ObjectNotFoundException {
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		OrderService orderService = serviceFactory.getOrderService();
		ProjectService projectService = serviceFactory.getProjectService();
		
		String orderId = request.getParameter(REQUEST_PARAM_ORDER_ID);
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
	
	@Override
	public Page addGET(HttpServletRequest request, HttpServletResponse response) throws ObjectNotFoundException {	
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
		} catch (ServiceException e) {
			request.setAttribute(REQUEST_PARAM_ERROR_CODE, e.getErrorCode().getValue());
		}

		request.setAttribute(REQUEST_PARAM_ORDER_ID, orderId);

		return new Page(PAGE_PROJECT_ADD);
	}
	
	@Override
	public Page messageGET(HttpServletRequest request, HttpServletResponse response) {
		
		return new Page(PAGE_PROJECT_ADD_MESSAGE);
	}
	
	@Override
	public Page listGET(HttpServletRequest request, HttpServletResponse response) {
		
		return list(request, response, PAGE_PROJECT_LIST_URI, null);
	}
	
	@Override
	public Page listByEmployeeGET(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false);
		UserVo userVO = (UserVo) session.getAttribute(SESSION_PARAM_USER);
		
		return list(request, response, PAGE_DEFAULT_DEVELOPER, userVO.getEmployee());
	}
	
	/*
	 * List of records. 
	 * @param request
	 * @param response
	 * @param pageUri Uri for paging
	 * @param employee could by null. If employee != null function get records for employee.
	 * @return Page {@link by.htp.devteam.controller.main.Page}
	 */
	private Page list(HttpServletRequest request, HttpServletResponse response, String pageUri, Employee employee) {
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		ProjectService projectService = serviceFactory.getProjectService();
		
		String currPage = request.getParameter(REQUEST_PARAM_PAGE);
		
		try {
			PagingVo<Project> pagingVo = projectService.fetchAll(currPage, employee);
			pagingVo.setUri(pageUri);
			request.setAttribute(REQUEST_PARAM_PAGING_VO, pagingVo);

			request.setAttribute(REQUEST_PARAM_PROJECT_LIST, pagingVo.getRecords());
		} catch (ServiceException e) {
			request.setAttribute(REQUEST_PARAM_ERROR_CODE, e.getErrorCode().getValue());
		}
		
		return new Page(PAGE_PROJECT_LIST);
	}
	
	@Override
	public Page updateHoursGET(HttpServletRequest request, HttpServletResponse response) throws ObjectNotFoundException {		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		ProjectService projectService = serviceFactory.getProjectService();
		
		String id = request.getParameter(REQUEST_PARAM_PROJECT_ID);
		try {
			ProjectVo projectVo = projectService.getById(id);
			request.setAttribute(REQUEST_PARAM_PROJECT_VO, projectVo);
		} catch (ServiceException e) {
			request.setAttribute(REQUEST_PARAM_ERROR_CODE, e.getErrorCode().getValue());
		}
		
		return new Page(PAGE_PROJECT_UPDATE_HOURS);
	}
	
	@Override
	public Page updateHoursPOST(HttpServletRequest request, HttpServletResponse response) 
			throws ObjectNotFoundException {
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		ProjectService projectService = serviceFactory.getProjectService();

		String id = request.getParameter(REQUEST_PARAM_PROJECT_ID);
		String hours = request.getParameter(REQUEST_PARAM_PROJECT_HOURS);

		HttpSession session = request.getSession(false);
		UserVo userVO = (UserVo) session.getAttribute(SESSION_PARAM_USER);
		
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
	
	@Override
	public Page viewGET(HttpServletRequest request, HttpServletResponse response) throws ObjectNotFoundException {
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		ProjectService projectService = serviceFactory.getProjectService();
		
		String page = PAGE_PROJECT_VIEW;
		String id = request.getParameter(REQUEST_PARAM_PROJECT_ID);

		try {
			ProjectVo projectVo = projectService.getById(id);
			request.setAttribute(REQUEST_PARAM_PROJECT_VO, projectVo);
			request.setAttribute(REQUEST_PARAM_UPLOAD_PATH, UploadFile.UPLOAD_PATH);
		} catch (ServiceException e) {
			request.setAttribute(REQUEST_PARAM_ERROR_CODE, e.getErrorCode().getValue());
		}
		
		return new Page(page);
	}
	
	@Override
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
