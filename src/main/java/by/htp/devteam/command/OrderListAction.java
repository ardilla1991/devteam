package by.htp.devteam.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.devteam.bean.Customer;
import by.htp.devteam.bean.Order;
import by.htp.devteam.service.OrderService;
import by.htp.devteam.service.ServiceFactory;
import by.htp.devteam.util.PageConstantValue;
import by.htp.devteam.util.RequestParamConstantValue;

public class OrderListAction implements CommandAction{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		OrderService orderService = serviceFactory.getOrderService();
		
		HttpSession session = request.getSession(false);
		Object userObject = session.getAttribute("user");
    	Customer customer = (Customer) userObject;
		List<Order> orders = orderService.geOrdersByCustomer(customer);
		request.setAttribute(RequestParamConstantValue.ORDER_LIST, orders);
		return PageConstantValue.ORDER_LIST;
	}

}
