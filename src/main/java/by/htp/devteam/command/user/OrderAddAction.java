package by.htp.devteam.command.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.devteam.bean.Customer;
import by.htp.devteam.bean.dto.UserVO;
import by.htp.devteam.command.CommandAction;
import by.htp.devteam.controller.Page;
import by.htp.devteam.service.OrderService;
import by.htp.devteam.service.ServiceFactory;
import static by.htp.devteam.util.ConstantValue.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class OrderAddAction implements CommandAction{
	
	private OrderService orderService;
	
	public OrderAddAction() {
		super();
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		orderService = serviceFactory.getOrderService();
	}

	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) {
		/*List<String> parameterNames = new ArrayList<String>(request.getParameterMap().values());
		for ( String st : parameterNames ) {
			System.out.println(st);
		}*/
		String title = request.getParameter(REQUEST_PARAM_ORDER_TITLE);
		String password = request.getParameter(REQUEST_PARAM_ORDER_DESCRIPTION);
		String specification = request.getParameter(REQUEST_PARAM_ORDER_SPECIFICATION);
		String dateStart = request.getParameter(REQUEST_PARAM_ORDER_DATE_START);
		String dateFinish = request.getParameter(REQUEST_PARAM_ORDER_DATE_FINISH);
		String[] workIds = request.getParameterValues(REQUEST_PARAM_ORDER_WORK);
		//String[] qualifications = request.getParameterValues(REQUEST_PARAM_ORDER_QUALIFICATION);
		Map<String, String> qualifications = getQualificationsFromRequest(request);
		
		HttpSession session = request.getSession(false);
		UserVO userVO = (UserVO) session.getAttribute("user");
		
		orderService.add(userVO.getCustomer(), title, password, specification, dateStart, dateFinish, workIds, qualifications);

		return new Page("Main?action=order_list");
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
