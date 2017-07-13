package by.htp.devteam.command.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.devteam.bean.Employee;
import by.htp.devteam.bean.User;
import by.htp.devteam.bean.vo.OrderVo;
import by.htp.devteam.bean.vo.UserVo;
import by.htp.devteam.command.CommandAction;
import by.htp.devteam.controller.Page;
import by.htp.devteam.service.EmployeeService;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.ServiceFactory;
import by.htp.devteam.service.UserService;

import static by.htp.devteam.command.util.ConstantValue.*;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public final class UserAddAction implements CommandAction {

	/** Logger */
	private static final Logger logger = LogManager.getLogger(EmployeeAddAction.class.getName());
	
	public UserAddAction() {
		super();
	}
	
	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) {
		logging(request);
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		UserService userService = serviceFactory.getUserService();
		EmployeeService employeeService = serviceFactory.getEmployeeService();
		
		String page = PAGE_USER_ADD_MESSAGE_URI;
		boolean isRedirect = true;
		String login = request.getParameter(REQUEST_PARAM_USER_LOGIN);
		String password = request.getParameter(REQUEST_PARAM_USER_PASSWORD);
		String role = request.getParameter(REQUEST_PARAM_USER_ROLE);
		String employee_id = request.getParameter(REQUEST_PARAM_EMPLOYEE_ID);
		User user = null;
		try {
			Employee employee = employeeService.getById(employee_id);
			user = userService.add(login, password, role, employee);
		} catch (ServiceException e) {
			request.setAttribute(REQUEST_PARAM_ERROR_CODE, e.getErrorCode().getValue());
			request.setAttribute(REQUEST_PARAM_ERROR_FIELD, e.getFields());
			request.setAttribute(REQUEST_PARAM_USER_LOGIN, login);
			request.setAttribute(REQUEST_PARAM_USER_PASSWORD, password);
			request.setAttribute(REQUEST_PARAM_USER_ROLE, role);
			request.setAttribute(REQUEST_PARAM_EMPLOYEE_ID, employee_id);
			
			page = PAGE_EMPLOYEE_SHOW_ADD_FORM_URI;
			isRedirect = false;
		}
		return new Page(page, isRedirect);
	}
	
	private void logging(HttpServletRequest request ) {
		HttpSession session = request.getSession(false);
		UserVo userVO = (UserVo) session.getAttribute(SESSION_PARAM_USER);
		
		logger.info(MSG_LOGGER_USER_SHOW_ADD_FORM, userVO.getUser().getLogin());
	}
	
}
