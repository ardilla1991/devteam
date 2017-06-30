package by.htp.devteam.command.user;

import static by.htp.devteam.util.ConstantValue.*;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.devteam.bean.Employee;
import by.htp.devteam.bean.dto.OrderDto;
import by.htp.devteam.command.CommandAction;
import by.htp.devteam.controller.Page;
import by.htp.devteam.service.EmployeeService;
import by.htp.devteam.service.OrderService;
import by.htp.devteam.service.ServiceFactory;

public class ProjectShowAddFormAction implements CommandAction{

	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) {		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		OrderService orderService = serviceFactory.getOrderService();
		String orderId = request.getParameter(REQUEST_PARAM_ORDER_ID);
		OrderDto order = orderService.getOrderById(orderId);
		request.setAttribute(REQUEST_PARAM_ORDER_DTO, order);
		
		EmployeeService employeeService = serviceFactory.getEmployeeService();
		List<Employee> employees = employeeService.getFreeEmployeesForPeriod(order.getOrder().getDateStart(), 
				order.getOrder().getDateFinish(), order.getQualifications().keySet());
		request.setAttribute(REQUEST_PARAM_EMPLOYEE_LIST, employees);
	
		request.setAttribute(REQUEST_PARAM_ORDER_ID, orderId);
		
		String xhr = request.getParameter(REQUEST_PARAM_XHR);
		String page = ( xhr != null ? PAGE_PROJECT_EDIT_AJAX : PAGE_PROJECT_EDIT );
		return new Page(page);
	}

}
