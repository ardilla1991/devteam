package by.htp.devteam.module.controller;

import static by.htp.devteam.module.util.ConstantValue.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.htp.devteam.bean.Order;
import by.htp.devteam.bean.vo.OrderVo;
import by.htp.devteam.bean.vo.PagingVo;
import by.htp.devteam.bean.vo.UserVo;
import by.htp.devteam.controller.Page;
import by.htp.devteam.module.Controller;
import by.htp.devteam.module.util.CSRFToken;
import by.htp.devteam.module.util.SecurityException;
import by.htp.devteam.service.OrderService;
import by.htp.devteam.service.QualificationService;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.ServiceFactory;
import by.htp.devteam.service.WorkService;
import by.htp.devteam.service.util.ErrorCode;
import by.htp.devteam.service.util.UploadFile;

public class OrderController implements Controller {

	/** Logger */
	private static final Logger logger = LogManager.getLogger(OrderController.class);
	
	/**
	 * Action for add order.
	 * @param request
	 * @param response
	 * @return
	 * @throws SecurityException
	 */
	public Page addPOST(HttpServletRequest request, HttpServletResponse response) throws SecurityException {
		
		CSRFToken.validationToken(request);
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		OrderService orderService = serviceFactory.getOrderService();
		
		//String page = PAGE_ORDER_LIST_URI;
		String page = PAGE_ORDER_ADD_MESSAGE_URI;
		boolean isRedirect = true;
		String title = request.getParameter(REQUEST_PARAM_ORDER_TITLE);
		String description = request.getParameter(REQUEST_PARAM_ORDER_DESCRIPTION);
		String dateStart = request.getParameter(REQUEST_PARAM_ORDER_DATE_START);
		String dateFinish = request.getParameter(REQUEST_PARAM_ORDER_DATE_FINISH);
		String[] workIds = request.getParameterValues(REQUEST_PARAM_ORDER_WORK);
		Map<String, String> qualifications = getQualificationsFromRequest(request);
		
		Part specification = null;
		try {
			specification = request.getPart(REQUEST_PARAM_ORDER_SPECIFICATION);
			
			HttpSession session = request.getSession(false);
			UserVo userVO = (UserVo) session.getAttribute(SESSION_PARAM_USER);

			try {
				// gets absolute path of the web application
		        //String applicationPath = request.getServletContext().getRealPath("");
		        OrderVo orderVo = orderService.add(userVO.getCustomer(), title, description, specification, dateStart, dateFinish, workIds, qualifications);
			} catch (ServiceException e) {
				request.setAttribute(REQUEST_PARAM_ERROR_CODE, e.getErrorCode().getValue());
				request.setAttribute(REQUEST_PARAM_ERROR_FIELD, e.getFields());
				request.setAttribute(REQUEST_PARAM_PROJECT_TITLE, title);
				request.setAttribute(REQUEST_PARAM_ORDER_DESCRIPTION, description);
				request.setAttribute(REQUEST_PARAM_ORDER_DATE_START, dateStart);
				request.setAttribute(REQUEST_PARAM_ORDER_DATE_FINISH, dateFinish);
				request.setAttribute(REQUEST_PARAM_ORDER_WORK, workIds);
				request.setAttribute(REQUEST_PARAM_ORDER_QUALIFICATION, qualifications);
				
				page = PAGE_ORDER_SHOW_ADD_FORM_URI;
				isRedirect = false;
			}			
		} catch (IOException | ServletException | IllegalStateException e) {
			logger.info(MSG_LOGGER_ORDER_ADD_FILE_UPLOAD, e);
			request.setAttribute(REQUEST_PARAM_ERROR_CODE, ErrorCode.FILE_UPLOAD.getValue());
			page = PAGE_ORDER_SHOW_ADD_FORM_URI;
			isRedirect = false;
		}

		return new Page(page, isRedirect);
	}
	
	/*
	 * Get map of qualifications ids and count hours from request.
	 * In request we have map such Map (  qualification[id] => count, ... )
	 * @param request
	 * @return map of qualifications ids and count hours
	 */
	private Map<String, String> getQualificationsFromRequest(HttpServletRequest request) {
		Map<String, String> assocArray = new HashMap<String, String>();
		
		Set params = request.getParameterMap().entrySet();
		Iterator it = params.iterator();
		while (it.hasNext() ) {
		    @SuppressWarnings("unchecked")
			Map.Entry<String, String[]> entry = (Map.Entry<String, String[]>) it.next();
		    String name = entry.getKey();

		    if (name.startsWith(REQUEST_PARAM_ORDER_QUALIFICATION + "[")) {
		        String key = name.substring(name.indexOf('[') + 1, name.indexOf(']')); 
		        
		        if ( entry.getValue()[0].length() > 0 )
		        	assocArray.put(key, entry.getValue()[0]);
		    }
		}
		return assocArray;
	}
	
	public Page addGET(HttpServletRequest request, HttpServletResponse response) {

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		WorkService workService = serviceFactory.getWorkService();
		QualificationService qualificationService = serviceFactory.getQualificationService();
		try {
			request.setAttribute(REQUEST_PARAM_WORK_LIST, workService.fetchAll());
			request.setAttribute(REQUEST_PARAM_QUALIFICATION_LIST, qualificationService.fetchAll());
			
			CSRFToken.setToken(request);
		} catch (ServiceException e) {
			request.setAttribute(REQUEST_PARAM_ERROR_CODE, e.getErrorCode().getValue());
		}
		
		return new Page(PAGE_ORDER_ADD);
	}
	
	/**
	 * Action after added order. Only show page with message that all is ok
	 * @param request
	 * @param response
	 * @return
	 */
	public Page messageGET(HttpServletRequest request, HttpServletResponse response) {
		
		return new Page(PAGE_ORDER_ADD_MESSAGE);
	}
	
	/**
	 * Action for order list by customer.
	 * @param request
	 * @param response
	 * @return
	 */
	public Page listGET(HttpServletRequest request, HttpServletResponse response) {
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		OrderService orderService = serviceFactory.getOrderService();
		
		String page = PAGE_ORDER_LIST;
		boolean isRedirect = false;
		
		HttpSession session = request.getSession(false);
		UserVo userVO = (UserVo) session.getAttribute(SESSION_PARAM_USER);

		try {
			request.setAttribute(REQUEST_PARAM_ORDER_LIST, orderService.geOrdersByCustomer(userVO.getCustomer()));	
			request.setAttribute(REQUEST_PARAM_UPLOAD_PATH, UploadFile.uploadPath);
		} catch (ServiceException e) {
			request.setAttribute(REQUEST_PARAM_ERROR_CODE, e.getErrorCode().getValue());
		}
		
		return new Page(page, isRedirect);
	}
	
	/**
	 * Action for new orders list.
	 * @param request
	 * @param response
	 * @return
	 */
	public Page newListGET(HttpServletRequest request, HttpServletResponse response) {
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		OrderService orderService = serviceFactory.getOrderService();
		
		String page = PAGE_ORDER_NEW_LIST;
		String currPage = request.getParameter(REQUEST_PARAM_PAGE);

		try {
			PagingVo<Order> pagingVo = orderService.getNewOrders(currPage);
			
			request.setAttribute(REQUEST_PARAM_URI, PAGE_ORDER_NEW_LIST_URI);
			request.setAttribute(REQUEST_PARAM_ORDER_LIST, pagingVo.getRecords());
			request.setAttribute(REQUEST_PARAM_CURR_PAGE, pagingVo.getCurrPage());
			request.setAttribute(REQUEST_PARAM_COUNT_PAGES, pagingVo.getCountPages());
			request.setAttribute(REQUEST_PARAM_UPLOAD_PATH, UploadFile.uploadPath);

		} catch (ServiceException e) {
			request.setAttribute(REQUEST_PARAM_ERROR_CODE, e.getErrorCode().getValue());
		}
		
		return new Page(page);
	}
	
	/**
	 * Action for view order.
	 * @param request
	 * @param response
	 * @return
	 */
	public Page viewGET(HttpServletRequest request, HttpServletResponse response) {
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		OrderService orderService = serviceFactory.getOrderService();
		
		String page = PAGE_ORDER_VIEW;
		String id = request.getParameter(REQUEST_PARAM_ORDER_ID);
		
		try {
			OrderVo orderVo = orderService.getById(id);
			request.setAttribute(REQUEST_PARAM_ORDER_VO, orderVo);
			request.setAttribute(REQUEST_PARAM_UPLOAD_PATH, UploadFile.uploadPath);
		} catch (ServiceException e) {
			request.setAttribute(REQUEST_PARAM_ERROR_CODE, e.getErrorCode().getValue());
			page = PAGE_ERROR_404;
		}

		return new Page(page);
	}
}
