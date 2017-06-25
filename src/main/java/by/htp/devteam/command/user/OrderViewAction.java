package by.htp.devteam.command.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.devteam.bean.dto.OrderDto;
import by.htp.devteam.command.CommandAction;
import by.htp.devteam.service.OrderService;
import by.htp.devteam.service.ServiceFactory;
import static by.htp.devteam.util.ConstantValue.*;

public class OrderViewAction implements CommandAction{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		OrderService orderService = serviceFactory.getOrderService();
		String id = request.getParameter(REQUEST_PARAM_ORDER_ID);
		OrderDto order = orderService.getOrderById(id);
		request.setAttribute(REQUEST_PARAM_ORDER, order);
		return PAGE_ORDER_VIEW;
	}

}
