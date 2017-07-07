package by.htp.devteam.command.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.devteam.bean.dto.OrderListVo;
import by.htp.devteam.command.CommandAction;
import by.htp.devteam.controller.Page;
import by.htp.devteam.service.OrderService;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.ServiceFactory;
import by.htp.devteam.service.util.UploadFile;

import static by.htp.devteam.util.ConstantValue.*;

public class OrderNewListAction implements CommandAction{
	
	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) {
		String page = PAGE_DEFAULT;
		String currPage = request.getParameter(REQUEST_PARAM_PAGE);

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		OrderService orderService = serviceFactory.getOrderService();

		try {
			OrderListVo orderListVo = orderService.getNewOrders(currPage);
			
			request.setAttribute(REQUEST_PARAM_ORDER_LIST, orderListVo.getOrders());
			request.setAttribute(REQUEST_PARAM_CURR_PAGE, orderListVo.getCurrPage());
			request.setAttribute(REQUEST_PARAM_COUNT_PAGES, orderListVo.getCountPages());
			request.setAttribute(REQUEST_PARAM_UPLOAD_PATH, UploadFile.uploadPath);
	
			page = PAGE_ORDER_NEW_LIST;
		} catch (ServiceException e) {
			page = PAGE_ERROR;
			request.setAttribute(REQUEST_PARAM_ERROR_MSG, e.getMessage());
		}
		
		return new Page(page);
	}

}
