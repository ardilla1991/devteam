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
import static by.htp.devteam.util.ConstantValue.*;

import java.util.HashMap;
import java.util.Map;

public class OrderAddAction implements CommandAction{
	
	private OrderService orderService;
	
	public OrderAddAction() {
		super();
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		orderService = serviceFactory.getOrderService();
	}

	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) {
		String page = "Main?action=order_list";
		boolean isRedirect = true;
		String title = request.getParameter(REQUEST_PARAM_ORDER_TITLE);
		String description = request.getParameter(REQUEST_PARAM_ORDER_DESCRIPTION);
		String specification = request.getParameter(REQUEST_PARAM_ORDER_SPECIFICATION);
		String dateStart = request.getParameter(REQUEST_PARAM_ORDER_DATE_START);
		String dateFinish = request.getParameter(REQUEST_PARAM_ORDER_DATE_FINISH);
		String[] workIds = request.getParameterValues(REQUEST_PARAM_ORDER_WORK);
		Map<String, String> qualifications = getQualificationsFromRequest(request);
		
		HttpSession session = request.getSession(false);
		UserVO userVO = (UserVO) session.getAttribute("user");
		
		try {
			orderService.add(userVO.getCustomer(), title, description, specification, dateStart, dateFinish, workIds, qualifications);
		} catch (ServiceException e) {
			e.printStackTrace();
			request.setAttribute(REQUEST_PARAM_ERROR_MSG, e.getMessage());
			System.out.println("title=" + title);
			request.setAttribute(REQUEST_PARAM_PROJECT_TITLE, title);
			request.setAttribute(REQUEST_PARAM_ORDER_DESCRIPTION, description);
			request.setAttribute(REQUEST_PARAM_ORDER_DATE_START, dateStart);
			request.setAttribute(REQUEST_PARAM_ORDER_DATE_FINISH, dateFinish);
			request.setAttribute(REQUEST_PARAM_ORDER_WORK, workIds);
			page = "Main?action=order_show_add_form";
			isRedirect = false;
		}

		return new Page(page, isRedirect);
	}
	
	private Map<String, String> getQualificationsFromRequest(HttpServletRequest request) {
		Map<String, String> assocArray = new HashMap<String, String>();

		for(Object obj :request.getParameterMap().entrySet()) {
		    Map.Entry<String, String[]> entry = (Map.Entry<String, String[]>) obj;
		    String name = entry.getKey();

		    if (name.startsWith("qualification[")) {
		        String key = name.substring(name.indexOf('[') + 1, name.indexOf(']')); 
		        
		        if ( entry.getValue()[0].length() > 0 )
		        	assocArray.put(key, entry.getValue()[0]);
		    }
		}
		return assocArray;
	}

}
