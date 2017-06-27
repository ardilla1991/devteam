package by.htp.devteam.command.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.devteam.bean.dto.OrderDto;
import by.htp.devteam.command.CommandAction;
import by.htp.devteam.controller.Page;
import by.htp.devteam.service.OrderService;
import by.htp.devteam.service.ServiceFactory;
import static by.htp.devteam.util.ConstantValue.*;

public class OrderViewAction implements CommandAction{

	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) {
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		OrderService orderService = serviceFactory.getOrderService();
		String id = request.getParameter(REQUEST_PARAM_ORDER_ID);
		OrderDto order = orderService.getOrderById(id);
		request.setAttribute(REQUEST_PARAM_ORDER_DTO, order);
		String isFragment = request.getParameter(REQUEST_PARAM_JSPF);

		if ( isFragment != null )
			return new Page(PAGE_ORDER_VIEW_FRAGMENT, Integer.valueOf(isFragment));
		else
			return new Page(PAGE_ORDER_VIEW);
	}

}
