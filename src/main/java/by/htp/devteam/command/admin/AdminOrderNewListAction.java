package by.htp.devteam.command.admin;

import static by.htp.devteam.util.admin.AdminPageConstantValue.PAGE_DEFAULT;
import static by.htp.devteam.util.admin.AdminPageConstantValue.PAGE_ERROR;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.devteam.bean.Order;
import by.htp.devteam.bean.Project;
import by.htp.devteam.bean.dto.OrderListDto;
import by.htp.devteam.bean.dto.ProjectDto;
import by.htp.devteam.command.CommandAction;
import by.htp.devteam.service.OrderService;
import by.htp.devteam.service.ProjectService;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.ServiceFactory;
import static by.htp.devteam.util.admin.AdminPageConstantValue.*;
import by.htp.devteam.util.admin.AdminRequestParamConstantValue;

public class AdminOrderNewListAction implements CommandAction{
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = PAGE_DEFAULT;
		System.out.println("kkkkkkkkkkk");
		String currPage = request.getParameter(AdminRequestParamConstantValue.PAGE);

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		OrderService orderService = serviceFactory.getOrderService();
		
		List<Order> orders = null;
		try {
			OrderListDto orderDto = orderService.getNewOrders(currPage);
			orders = orderDto.getOrders();
			
			System.out.println("orders=");
			System.out.println(orders);
			request.setAttribute(AdminRequestParamConstantValue.ORDER_LIST, orders);
			request.setAttribute(AdminRequestParamConstantValue.CURR_PAGE, orderDto.getCurrPage());
			request.setAttribute(AdminRequestParamConstantValue.COUNT_PAGES, orderDto.getCountPages());
	
			page = ORDER_NEW_LIST;
		} catch (ServiceException e) {
			page = PAGE_ERROR;
			request.setAttribute(AdminRequestParamConstantValue.ERROR_MSG, e.getMessage());
		}
		
		return page;
	}

}
