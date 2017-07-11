package by.htp.devteam.command.user;

import static by.htp.devteam.command.util.ConstantValue.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.devteam.bean.User;
import by.htp.devteam.bean.vo.UserVo;
import by.htp.devteam.command.CommandAction;
import by.htp.devteam.controller.Page;
import by.htp.devteam.service.CustomerService;
import by.htp.devteam.service.EmployeeService;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.ServiceFactory;
import by.htp.devteam.service.UserService;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class LoginAction implements CommandAction{
	
	private static final Logger logger = LogManager.getLogger(LoginAction.class.getName());
	
	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) {
				
		HttpSession session = request.getSession();
		String login = request.getParameter(REQUEST_PARAM_LOGIN);
		String password = request.getParameter(REQUEST_PARAM_PASSWORD);
		String page = PAGE_DEFAULT;
		
		logger.info(MSG_LOGGER_USER_LOGIN, login);
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		UserService userService = serviceFactory.getUserService();
		
		boolean isRedirect = false;
		try {
			UserVo userVO = new UserVo();
			User user = userService.authorise(login, password);
			userVO.setUser(user);
			
			CustomerService customerService = serviceFactory.getCustomerService();
			userVO.setCustomer(customerService.getByUser(user));
			
			EmployeeService employeeService = serviceFactory.getEmployeeService();
			userVO.setEmployee(employeeService.getByUser(user));
			
			session.setAttribute(SESSION_PARAM_USER, userVO);
			isRedirect = true;
			switch ( userVO.getUser().getRole() ) {
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
			
		} catch (ServiceException e) { 
			request.setAttribute(REQUEST_PARAM_ERROR_CODE, e.getErrorCode().getValue());
			request.setAttribute(REQUEST_PARAM_ERROR_FIELD, e.getMassages());
			request.setAttribute(REQUEST_PARAM_LOGIN, login);
			request.setAttribute(REQUEST_PARAM_PASSWORD, password);
			
			page = PAGE_LOGIN;
		}
		
		return new Page(page, isRedirect);
	}

}
