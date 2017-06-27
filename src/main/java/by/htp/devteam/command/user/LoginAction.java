package by.htp.devteam.command.user;

import static by.htp.devteam.util.ConstantValue.*;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.devteam.bean.Customer;
import by.htp.devteam.bean.Employee;
import by.htp.devteam.bean.Order;
import by.htp.devteam.bean.RoleEnum;
import by.htp.devteam.bean.User;
import by.htp.devteam.bean.dto.OrderListDto;
import by.htp.devteam.bean.dto.UserVO;
import by.htp.devteam.command.CommandAction;
import by.htp.devteam.controller.Page;
import by.htp.devteam.service.CustomerService;
import by.htp.devteam.service.EmployeeService;
import by.htp.devteam.service.OrderService;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.ServiceFactory;
import by.htp.devteam.service.UserService;

public class LoginAction implements CommandAction{

	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String login = request.getParameter(REQUEST_PARAM_LOGIN);
		String password = request.getParameter(REQUEST_PARAM_PASSWORD);
		String page = PAGE_DEFAULT;
		
		User user;
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		UserService userService = serviceFactory.getUserService();
		UserVO userVO = new UserVO();
		try {
			user = userService.authorise(login, password);
			userVO.setUser(user);
			System.out.println("user=");
			System.out.println(user);
			CustomerService customerService = serviceFactory.getCustomerService();
			Customer customer = customerService.getCustomerByUser(user);
			System.out.println("customer=");
			System.out.println(customer);
			userVO.setCustomer(customer);
			
			EmployeeService employeeService = serviceFactory.getEmployeeService();
			Employee employee = employeeService.getEmployeeByUser(user);
			System.out.println("employee=");
			System.out.println(employee);
			userVO.setEmployee(employee);
			
			session.setAttribute("user", userVO);
			
			if ( userVO.getUser().getRole() == RoleEnum.MANAGER ) { // manager
				page = PAGE_DEFAULT_MANAGER;
			} else if ( userVO.getUser().getRole() == RoleEnum.CUSTOMER ){  // customer
				System.out.println("customer");
				page = PAGE_DEFAULT_CUSTOMER;
			}
		} catch (ServiceException e1) { 
			System.out.println(e1.getMessage());
			request.setAttribute(REQUEST_PARAM_ERROR_MSG, e1.getMessage());
			page = PAGE_LOGIN;
		}
		
		return new Page(page);
	}

}
