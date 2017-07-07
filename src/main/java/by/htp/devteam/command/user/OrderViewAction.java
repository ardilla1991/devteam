package by.htp.devteam.command.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.devteam.bean.dto.OrderVo;
import by.htp.devteam.command.CommandAction;
import by.htp.devteam.controller.Page;
import by.htp.devteam.service.OrderService;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.ServiceFactory;
import by.htp.devteam.service.util.UploadFile;

import static by.htp.devteam.util.ConstantValue.*;

public class OrderViewAction implements CommandAction{

	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) {
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		OrderService orderService = serviceFactory.getOrderService();
		String id = request.getParameter(REQUEST_PARAM_ORDER_ID);

		try {
			OrderVo orderVo = orderService.getOrderById(id);
			request.setAttribute(REQUEST_PARAM_ORDER_VO, orderVo);
			request.setAttribute(REQUEST_PARAM_UPLOAD_PATH, UploadFile.uploadPath);
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		return new Page(PAGE_ORDER_VIEW);
	}

}
