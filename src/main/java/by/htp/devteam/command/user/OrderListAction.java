package by.htp.devteam.command.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.devteam.bean.Customer;
import by.htp.devteam.bean.Order;
import by.htp.devteam.bean.User;
import by.htp.devteam.bean.dto.UserVO;
import by.htp.devteam.command.CommandAction;
import by.htp.devteam.service.OrderService;
import by.htp.devteam.service.ServiceFactory;
import static by.htp.devteam.util.ConstantValue.*;

public class OrderListAction implements CommandAction{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		OrderService orderService = serviceFactory.getOrderService();
		
		HttpSession session = request.getSession(false);
		UserVO userVO = (UserVO) session.getAttribute("user");
System.out.println(userVO);
System.out.println(userVO.getCustomer());
		List<Order> orders = orderService.geOrdersByCustomer(userVO.getCustomer());
		request.setAttribute(REQUEST_PARAM_ORDER_LIST, orders);
		return PAGE_ORDER_LIST;
	}

}
