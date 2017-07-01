package by.htp.devteam.command.user;

import static by.htp.devteam.util.ConstantValue.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


import by.htp.devteam.bean.Customer;
import by.htp.devteam.bean.Employee;
import by.htp.devteam.bean.RoleEnum;
import by.htp.devteam.bean.User;
import by.htp.devteam.bean.dto.UserVO;
import by.htp.devteam.command.CommandAction;
import by.htp.devteam.controller.Page;
import by.htp.devteam.service.CustomerService;
import by.htp.devteam.service.EmployeeService;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.ServiceFactory;
import by.htp.devteam.service.UserService;

public class LoginAction implements CommandAction{
	
	private static final Logger logger = LogManager.getLogger(LoginAction.class.getName());

	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) {
		
		logger.info("Hello, World!");
		
		HttpSession session = request.getSession();
		String login = request.getParameter(REQUEST_PARAM_LOGIN);
		String password = request.getParameter(REQUEST_PARAM_PASSWORD);
		String page = PAGE_DEFAULT;
		
		User user;
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		UserService userService = serviceFactory.getUserService();
		UserVO userVO = new UserVO();
		boolean isNeededRedirect = false;
		try {
			user = userService.authorise(login, password);
			userVO.setUser(user);
			CustomerService customerService = serviceFactory.getCustomerService();
			Customer customer = customerService.getCustomerByUser(user);
			userVO.setCustomer(customer);
			
			EmployeeService employeeService = serviceFactory.getEmployeeService();
			Employee employee = employeeService.getEmployeeByUser(user);
			userVO.setEmployee(employee);
			
			session.setAttribute("user", userVO);
			isNeededRedirect = true;
			if ( userVO.getUser().getRole() == RoleEnum.MANAGER ) {
				page = PAGE_DEFAULT_MANAGER;
			} else if ( userVO.getUser().getRole() == RoleEnum.CUSTOMER ){
				page = PAGE_DEFAULT_CUSTOMER;
			} else if ( userVO.getUser().getRole() == RoleEnum.DEVELOPER ){ 
				page = PAGE_DEFAULT_DEVELOPER;
			}
		} catch (ServiceException e1) { 
			System.out.println("Login error!!");
			request.setAttribute(REQUEST_PARAM_ERROR_MSG, e1.getMessage());
			page = PAGE_LOGIN;
		}
		
		return new Page(page, isNeededRedirect);
	}

}
