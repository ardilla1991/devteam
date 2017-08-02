package by.htp.devteam.command.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.devteam.bean.Employee;
import by.htp.devteam.bean.UserRole;
import by.htp.devteam.command.CommandAction;
import by.htp.devteam.command.util.CSRFToken;
import by.htp.devteam.command.util.SecurityException;
import by.htp.devteam.controller.Page;
import by.htp.devteam.service.EmployeeService;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.ServiceFactory;
import by.htp.devteam.service.UserService;

import static by.htp.devteam.command.util.ConstantValue.*;

/**
 * Add user for employee.
 * Logging information about who does action
 * @author julia
 *
 */
public final class UserAddAction implements CommandAction {
	
	public UserAddAction() {
		super();
	}
	
	/**
	 * Add user for employee.
	 */
	@Override
	public Page executePOST(HttpServletRequest request, HttpServletResponse response) throws SecurityException {
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		UserService userService = serviceFactory.getUserService();
		EmployeeService employeeService = serviceFactory.getEmployeeService();
		
		String page = PAGE_USER_ADD_MESSAGE_URI;
		boolean isRedirect = true;
		String login = request.getParameter(REQUEST_PARAM_USER_LOGIN);
		String password = request.getParameter(REQUEST_PARAM_USER_PASSWORD);
		String role = request.getParameter(REQUEST_PARAM_USER_ROLE);
		String employee_id = request.getParameter(REQUEST_PARAM_EMPLOYEE_ID);
		try {
			CSRFToken.validationToken(request);
			Employee employee = employeeService.getById(employee_id);
			userService.add(login, password, role, employee);
		} catch (ServiceException e) {
			request.setAttribute(REQUEST_PARAM_ERROR_CODE, e.getErrorCode().getValue());
			request.setAttribute(REQUEST_PARAM_ERROR_FIELD, e.getFields());
			request.setAttribute(REQUEST_PARAM_USER_LOGIN, login);
			request.setAttribute(REQUEST_PARAM_USER_PASSWORD, password);
			request.setAttribute(REQUEST_PARAM_USER_ROLE, role);
			request.setAttribute(REQUEST_PARAM_EMPLOYEE_ID, employee_id);
			
			page = PAGE_USER_SHOW_ADD_FORM_URI;
			isRedirect = false;
		}
		
		return new Page(page, isRedirect);
	}
	
	/**
	 * Action for user show form.
	 */
	@Override
	public Page executeGET(HttpServletRequest request, HttpServletResponse response) {

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		EmployeeService employeeService = serviceFactory.getEmployeeService();
		String employeeId = request.getParameter(REQUEST_PARAM_EMPLOYEE_ID);
		try {
			Employee employee = employeeService.getById(employeeId);
			request.setAttribute(REQUEST_PARAM_EMPLOYEE_NAME, employee.getName());
			request.setAttribute(REQUEST_PARAM_EMPLOYEE_ID, employee.getId());
			request.setAttribute(REQUEST_PARAM_USER_ROLE_ENUM, UserRole.values());
			
			CSRFToken.setToken(request);
		} catch (ServiceException e) {
			request.setAttribute(REQUEST_PARAM_ERROR_CODE, e.getErrorCode().getValue());
		}
		
		return new Page(PAGE_USER_ADD);
	}
	
}
