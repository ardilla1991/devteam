package by.htp.devteam.command;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.devteam.bean.Customer;
import by.htp.devteam.service.CustomerService;
import by.htp.devteam.service.OrderService;
import by.htp.devteam.service.ServiceFactory;
import by.htp.devteam.util.RequestParamConstantValue;

public class OrderAddAction implements CommandAction{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("4444444444444444444444444444=");
		System.out.println(request.getParameterNames());
		List<String> parameterNames = new ArrayList<String>(request.getParameterMap().keySet());
		for ( String st : parameterNames ) {
			System.out.println(st);
		}
		String title = request.getParameter(RequestParamConstantValue.ORDER_TITLE);
		System.out.println(title);
		String password = request.getParameter(RequestParamConstantValue.ORDER_DESCRIPTION);
		String specification = request.getParameter(RequestParamConstantValue.ORDER_SPECIFICATION);
		String dateStart = request.getParameter(RequestParamConstantValue.ORDER_DATE_START);
		System.out.println(dateStart);
		String dateFinish = request.getParameter(RequestParamConstantValue.ORDER_DATE_FINISH);
		String[] workIds = request.getParameterValues(RequestParamConstantValue.ORDER_WORK);
		String[] qualification = request.getParameterValues(RequestParamConstantValue.ORDER_QUALIFICATION);
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		OrderService orderService = serviceFactory.getOrderService();
		
		HttpSession session = request.getSession(false);
		Customer customer = (Customer) session.getAttribute("user");
		
		orderService.add(customer, title, password, specification, dateStart, dateFinish, workIds, qualification);
		
		return "Main?action=order_list";
	}

}
