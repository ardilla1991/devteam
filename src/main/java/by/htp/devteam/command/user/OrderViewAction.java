package by.htp.devteam.command.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.devteam.bean.vo.OrderVo;
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
 * Action for view order.
 * Logging information about who does action
 * @author julia
 *
 */
public class OrderViewAction implements CommandAction{

	/** Logger */
	private static final Logger logger = LogManager.getLogger(OrderViewAction.class.getName());
	
	public OrderViewAction() {
		super();
	}
	
	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) {
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		OrderService orderService = serviceFactory.getOrderService();
		
		String page = PAGE_ORDER_VIEW;
		String id = request.getParameter(REQUEST_PARAM_ORDER_ID);
		
		logging(request, id);

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
	
	private void logging(HttpServletRequest request, String orderId) {
		HttpSession session = request.getSession(false);
		UserVo userVO = (UserVo) session.getAttribute(SESSION_PARAM_USER);
		
		logger.info(MSG_LOGGER_ORDER_VIEW, userVO.getUser().getLogin(), orderId);
	}

}
