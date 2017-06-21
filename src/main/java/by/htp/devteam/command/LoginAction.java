package by.htp.devteam.command;

import by.htp.devteam.util.PageConstantValue;
import by.htp.devteam.util.PageConstantValue.*;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.devteam.bean.Customer;
import by.htp.devteam.bean.Order;
import by.htp.devteam.bean.Project;
import by.htp.devteam.bean.RoleEnum;
import by.htp.devteam.bean.User;
import by.htp.devteam.bean.dto.OrderListDto;
import by.htp.devteam.bean.dto.ProjectDto;
import by.htp.devteam.service.CustomerService;
import by.htp.devteam.service.EmployeeService;
import by.htp.devteam.service.OrderService;
import by.htp.devteam.service.ProjectService;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.ServiceFactory;
import by.htp.devteam.util.RequestParamConstantValue;
import by.htp.devteam.util.admin.AdminRequestParamConstantValue;

public class LoginAction implements CommandAction{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String login = request.getParameter(RequestParamConstantValue.LOGIN);
		String password = request.getParameter(RequestParamConstantValue.PASSWORD);
		String page = PageConstantValue.PAGE_DEFAULT;
		
		User customer;
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		CustomerService customerService = serviceFactory.getCustomerService();
		
		try {
			customer = customerService.authorise(login, password);

			session.setAttribute("user", customer);
			// display list of orders

			page = "Main?action=order_list";
		} catch (ServiceException e1) { 
			//e1.printStackTrace();
			//page = PAGE_ERROR;
			System.out.println(e1.getMessage());
			request.setAttribute(AdminRequestParamConstantValue.ERROR_MSG, e1.getMessage());
			page = PageConstantValue.PAGE_LOGIN;
		}
		
		return page;
	}

}
