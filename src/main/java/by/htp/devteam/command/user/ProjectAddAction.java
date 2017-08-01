package by.htp.devteam.command.user;

import static by.htp.devteam.command.util.ConstantValue.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.htp.devteam.bean.vo.OrderVo;
import by.htp.devteam.bean.vo.UserVo;
import by.htp.devteam.command.CommandAction;
import by.htp.devteam.command.util.CSRFToken;
import by.htp.devteam.command.util.SecurityException;
import by.htp.devteam.controller.Page;
import by.htp.devteam.service.OrderService;
import by.htp.devteam.service.ProjectService;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.ServiceFactory;

/**
 * Action for add project.
 * Logging information about who does action
 * @author julia
 *
 */
public class ProjectAddAction implements CommandAction {

	/** Logger */
	private static final Logger logger = LogManager.getLogger(ProjectAddAction.class.getName());

	public ProjectAddAction() {
		super();
	}

	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) throws SecurityException {
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		OrderService orderService = serviceFactory.getOrderService();
		ProjectService projectService = serviceFactory.getProjectService();
		
		//String page = PAGE_PROJECT_LIST_URI;
		String page = PAGE_PROJECT_ADD_MESSAGE_URI;
		boolean isRedirect = true;
		String orderId = request.getParameter(REQUEST_PARAM_ORDER_ID);
		
		logging(request, orderId);

		CSRFToken.validationToken(request);
		
		String title = request.getParameter(REQUEST_PARAM_PROJECT_TITLE);
		String description = request.getParameter(REQUEST_PARAM_PROJECT_DESCRIPTION);
		String[] employees = request.getParameterValues(REQUEST_PARAM_PROJECT_EMPLOYEE);
		String price = request.getParameter(REQUEST_PARAM_ORDER_PRICE);
		try {
			OrderVo orderVo = orderService.getById(orderId);
			projectService.add(orderVo, title, description, employees, price);
		} catch (ServiceException e) {
			request.setAttribute(REQUEST_PARAM_ERROR_CODE, e.getErrorCode().getValue());
			request.setAttribute(REQUEST_PARAM_ERROR_FIELD, e.getFields());
			request.setAttribute(REQUEST_PARAM_PROJECT_TITLE, title);
			request.setAttribute(REQUEST_PARAM_PROJECT_DESCRIPTION, description);
			request.setAttribute(REQUEST_PARAM_PROJECT_EMPLOYEE, employees);
			request.setAttribute(REQUEST_PARAM_ORDER_PRICE, price);
			
			page = PAGE_PROJECT_SHOW_ADD_FORM_URI + orderId;
			isRedirect = false;
		}

		return new Page(page, isRedirect);
	}
	
	private void logging(HttpServletRequest request, String orderId) {
		HttpSession session = request.getSession(false);
		UserVo userVO = (UserVo) session.getAttribute(SESSION_PARAM_USER);
		
		logger.info(MSG_LOGGER_PROJECT_ADD, userVO.getUser().getLogin(), orderId);
	}

}
