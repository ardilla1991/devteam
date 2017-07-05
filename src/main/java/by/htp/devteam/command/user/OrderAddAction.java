package by.htp.devteam.command.user;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import by.htp.devteam.bean.dto.OrderVo;
import by.htp.devteam.bean.dto.UserVO;
import by.htp.devteam.command.CommandAction;
import by.htp.devteam.controller.Page;
import by.htp.devteam.service.OrderService;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.ServiceFactory;
import by.htp.devteam.util.UploadFile;

import static by.htp.devteam.util.ConstantValue.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
		String page = PAGE_ORDER_LIST_URI;
		boolean isRedirect = true;
		String title = request.getParameter(REQUEST_PARAM_ORDER_TITLE);
		String description = request.getParameter(REQUEST_PARAM_ORDER_DESCRIPTION);
		String dateStart = request.getParameter(REQUEST_PARAM_ORDER_DATE_START);
		String dateFinish = request.getParameter(REQUEST_PARAM_ORDER_DATE_FINISH);
		String[] workIds = request.getParameterValues(REQUEST_PARAM_ORDER_WORK);
		Map<String, String> qualifications = getQualificationsFromRequest(request);
		
		UploadFile uploadFile = UploadFile.getInstance();
		Part specification = null;
		try {
			specification = request.getPart(REQUEST_PARAM_ORDER_SPECIFICATION);
			System.out.println("SPECIFICATION ====================");
			System.out.println(specification);
			System.out.println("fileName="+uploadFile.getFileName(specification));
		} catch (IOException | ServletException | IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		HttpSession session = request.getSession(false);
		UserVO userVO = (UserVO) session.getAttribute(SESSION_PARAM_USER);
		
		OrderVo orderVo = null;
		try {
			orderVo = orderService.add(userVO.getCustomer(), title, description, specification, dateStart, dateFinish, workIds, qualifications);
		} catch (ServiceException e) {
			e.printStackTrace();
			
			request.setAttribute(REQUEST_PARAM_ERROR_CODE, e.getErrorCode().getValue());
			request.setAttribute(REQUEST_PARAM_ERROR_FIELD, e.getMassages());
			request.setAttribute(REQUEST_PARAM_PROJECT_TITLE, title);
			request.setAttribute(REQUEST_PARAM_ORDER_DESCRIPTION, description);
			request.setAttribute(REQUEST_PARAM_ORDER_DATE_START, dateStart);
			request.setAttribute(REQUEST_PARAM_ORDER_DATE_FINISH, dateFinish);
			request.setAttribute(REQUEST_PARAM_ORDER_WORK, workIds);
			request.setAttribute(REQUEST_PARAM_ORDER_QUALIFICATION, qualifications);
			
			page = PAGE_ORDER_SHOW_ADD_FORM_URI;
			isRedirect = false;
		}

		return new Page(page, isRedirect);
	}
	
	private Map<String, String> getQualificationsFromRequest(HttpServletRequest request) {
		Map<String, String> assocArray = new HashMap<String, String>();

		for(Object obj :request.getParameterMap().entrySet()) {
		    Map.Entry<String, String[]> entry = (Map.Entry<String, String[]>) obj;
		    String name = entry.getKey();

		    if (name.startsWith(REQUEST_PARAM_ORDER_QUALIFICATION + "[")) {
		        String key = name.substring(name.indexOf('[') + 1, name.indexOf(']')); 
		        
		        if ( entry.getValue()[0].length() > 0 )
		        	assocArray.put(key, entry.getValue()[0]);
		    }
		}
		return assocArray;
	}
	


}
