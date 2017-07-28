package by.htp.devteam.command.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.devteam.bean.Order;
import by.htp.devteam.bean.vo.PagingVo;
import by.htp.devteam.bean.vo.UserVo;
import by.htp.devteam.command.CommandAction;
import by.htp.devteam.controller.Page;
import by.htp.devteam.service.OrderService;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.ServiceFactory;
import by.htp.devteam.service.util.UploadFile;

import static by.htp.devteam.command.util.ConstantValue.*;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Action for new orders list.
 * Logging information about who does action
 * @author julia
 *
 */
public class OrderNewListAction implements CommandAction{
	
	/** Logger */
	private static final Logger logger = LogManager.getLogger(OrderNewListAction.class.getName());
	
	public OrderNewListAction() {
		super();
	}
	
	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) {
		logging(request);
		
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
	
	private void logging(HttpServletRequest request ) {
		HttpSession session = request.getSession(false);
		UserVo userVO = (UserVo) session.getAttribute(SESSION_PARAM_USER);
		
		logger.info(MSG_LOGGER_ORDER_NEW_LIST, userVO.getUser().getLogin());
	}

}
