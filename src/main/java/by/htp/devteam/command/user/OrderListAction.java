package by.htp.devteam.command.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.devteam.bean.dto.UserVO;
import by.htp.devteam.command.CommandAction;
import by.htp.devteam.controller.Page;
import by.htp.devteam.service.OrderService;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.ServiceFactory;
import by.htp.devteam.service.util.UploadFile;

import static by.htp.devteam.util.ConstantValue.*;
import static by.htp.devteam.util.SettingConstantValue.*;

public class OrderListAction implements CommandAction{

	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) {

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		OrderService orderService = serviceFactory.getOrderService();
		
		HttpSession session = request.getSession(false);
		UserVO userVO = (UserVO) session.getAttribute(SESSION_PARAM_USER);

		try {
			request.setAttribute(REQUEST_PARAM_ORDER_LIST, orderService.geOrdersByCustomer(userVO.getCustomer()));	
			request.setAttribute(REQUEST_PARAM_UPLOAD_PATH, UploadFile.uploadPath);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
		return new Page(PAGE_ORDER_LIST);
	}

}
