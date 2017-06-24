package by.htp.devteam.command.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.devteam.bean.Customer;
import by.htp.devteam.bean.Order;
import by.htp.devteam.bean.dto.OrderDto;
import by.htp.devteam.command.CommandAction;
import by.htp.devteam.service.OrderService;
import by.htp.devteam.service.ServiceFactory;
import by.htp.devteam.util.PageConstantValue;
import by.htp.devteam.util.RequestParamConstantValue;

public class OrderViewAction implements CommandAction{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		OrderService orderService = serviceFactory.getOrderService();
		String id = request.getParameter(RequestParamConstantValue.ORDER_ID);
		OrderDto order = orderService.getOrderById(id);
		request.setAttribute(RequestParamConstantValue.ORDER, order);
		return PageConstantValue.ORDER_VIEW;
	}

}
