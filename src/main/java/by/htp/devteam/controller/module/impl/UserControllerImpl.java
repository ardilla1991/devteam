package by.htp.devteam.controller.module.impl;

import static by.htp.devteam.controller.util.ConstantValue.*;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import by.htp.devteam.bean.Employee;
import by.htp.devteam.bean.User;
import by.htp.devteam.bean.UserRole;
import by.htp.devteam.bean.vo.PagingVo;
import by.htp.devteam.bean.vo.UserVo;
import by.htp.devteam.controller.ObjectNotFoundException;
import by.htp.devteam.controller.main.Page;
import by.htp.devteam.controller.module.UserController;
import by.htp.devteam.service.CustomerService;
import by.htp.devteam.service.EmployeeService;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.UserService;

@Controller("userController")
public final class UserControllerImpl implements UserController {

	@Autowired(required = true)
	private UserService userService;
	
	@Autowired(required = true)
	private EmployeeService employeeService;
	
	@Autowired(required = true)
	private CustomerService customerService;

	public UserControllerImpl() {
		super();
	}
	
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public EmployeeService getEmployeeService() {
		return employeeService;
	}

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	@Override
	public Page addPOST(HttpServletRequest request, HttpServletResponse response) 
			throws ObjectNotFoundException {

		String login = request.getParameter(REQUEST_PARAM_USER_LOGIN);
		String password = request.getParameter(REQUEST_PARAM_USER_PASSWORD);
		String role = request.getParameter(REQUEST_PARAM_USER_ROLE);
		String employeeId = request.getParameter(REQUEST_PARAM_EMPLOYEE_ID);
		try {
			Employee employee = employeeService.getById(employeeId);
			userService.add(login, password, role, employee);
		} catch (ServiceException e) {
			request.setAttribute(REQUEST_PARAM_ERROR_CODE, e.getErrorCode().getValue());
			request.setAttribute(REQUEST_PARAM_ERROR_FIELD, e.getFields());
			request.setAttribute(REQUEST_PARAM_USER_LOGIN, login);
			request.setAttribute(REQUEST_PARAM_USER_PASSWORD, password);
			request.setAttribute(REQUEST_PARAM_USER_ROLE, role);
			request.setAttribute(REQUEST_PARAM_EMPLOYEE_ID, employeeId);

			return addGET(request, response);
		}

		return new Page(PAGE_USER_ADD_MESSAGE_URI, true);
	}

	@Override
	public Page addGET(HttpServletRequest request, HttpServletResponse response) throws ObjectNotFoundException {

		String employeeId = request.getParameter(REQUEST_PARAM_EMPLOYEE_ID);
		try {
			Employee employee = employeeService.getById(employeeId);
			request.setAttribute(REQUEST_PARAM_EMPLOYEE_NAME, employee.getName());
			request.setAttribute(REQUEST_PARAM_EMPLOYEE_ID, employee.getId());
			request.setAttribute(REQUEST_PARAM_USER_ROLE_ENUM, UserRole.values());
		} catch (ServiceException e) {
			request.setAttribute(REQUEST_PARAM_ERROR_CODE, e.getErrorCode().getValue());
		}

		return new Page(PAGE_USER_ADD);
	}

	@Override
	public Page messageGET(HttpServletRequest request, HttpServletResponse response) {
		
		return new Page(PAGE_USER_ADD_MESSAGE);
	}
	
	@Override
	public Page listGET(HttpServletRequest request, HttpServletResponse response) {
		
		String currPage = request.getParameter(REQUEST_PARAM_PAGE);
		try {
			PagingVo<UserVo> pagingVo = userService.fetchAll(currPage);
			pagingVo.setUri(PAGE_USER_LIST_URI);
			request.setAttribute(REQUEST_PARAM_PAGING_VO, pagingVo);
			request.setAttribute(REQUEST_PARAM_USER_LIST, pagingVo.getRecords());
			List<Employee> employeeList = employeeService.getListWithNotSetUser();
			request.setAttribute(REQUEST_PARAM_EMPLOYEE_LIST, employeeList);
		} catch (ServiceException e) {
			request.setAttribute(REQUEST_PARAM_ERROR_CODE, e.getErrorCode().getValue());
		}
		
		return new Page(PAGE_USER_LIST);
	}
	
	@Override
	public Page viewGET(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		UserVo userVO = (UserVo) session.getAttribute(SESSION_PARAM_USER);
		
		request.setAttribute(REQUEST_PARAM_USER_VO, userVO);
		
		return new Page(PAGE_USER_VIEW);
	}
	
	@Override
	public Page loginPOST(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		String login = request.getParameter(REQUEST_PARAM_LOGIN);
		String password = request.getParameter(REQUEST_PARAM_PASSWORD);
		String page = PAGE_LOGIN;
		
		boolean isRedirect = false;
		try {
			UserVo userVO = new UserVo();
			User user = userService.authorise(login, password);
			userVO.setUser(user);
			
			if ( user.getRole() == UserRole.CUSTOMER ) {
				userVO.setCustomer(customerService.getByUser(user));
			} else {
				userVO.setEmployee(employeeService.getByUser(user));
			}
			
			session.setAttribute(SESSION_PARAM_USER, userVO);
			isRedirect = true;
			page = getUserPage(userVO.getUser().getRole());
			
		} catch (ServiceException e) { 
			request.setAttribute(REQUEST_PARAM_ERROR_CODE, e.getErrorCode().getValue());
			request.setAttribute(REQUEST_PARAM_ERROR_FIELD, e.getFields());
			request.setAttribute(REQUEST_PARAM_LOGIN, login);
			request.setAttribute(REQUEST_PARAM_PASSWORD, password);
		}
		
		return new Page(page, isRedirect);
	}
	
	@Override
	public Page loginGET(HttpServletRequest request, HttpServletResponse response) {

		String page = PAGE_LOGIN;
		boolean isRedirect = false;
		HttpSession session = request.getSession(false);
		boolean isAuthorised = session != null && session.getAttribute(SESSION_PARAM_USER) != null;
		if ( isAuthorised ) {
			Object userObject = session.getAttribute(SESSION_PARAM_USER);
			UserVo userVO = (UserVo) userObject;
			isRedirect = true;
			page = getUserPage(userVO.getUser().getRole());
		}

		return new Page(page, isRedirect);
	}
	
	/*
	 * Get default page for user.
	 * @param userRole
	 */
	private String getUserPage(UserRole userRole) {
		String page = null;
		switch (userRole) {
		case MANAGER:
			page = PAGE_DEFAULT_MANAGER;
			break;
		case CUSTOMER:
			page = PAGE_DEFAULT_CUSTOMER;
			break;
		case DEVELOPER:
			page = PAGE_DEFAULT_DEVELOPER;
			break;
		case ADMIN:
			page = PAGE_DEFAULT_ADMIN;
			break;
		default:
			break;
		}
		
		return page;
	}
	
	@Override
	public Page logoutGET(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false);
		UserVo userVO = (UserVo) session.getAttribute(SESSION_PARAM_USER);
		
		if ( userVO != null ) {
			request.getSession().removeAttribute(SESSION_PARAM_USER);
			request.getSession().invalidate();
		}
		
		return new Page(PAGE_USER_LOGIN_URI, true);
	}
}
