package by.htp.devteam.command.admin;

import static by.htp.devteam.command.util.ConstantValue.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.htp.devteam.bean.UserRole;
import by.htp.devteam.bean.vo.UserVo;
import by.htp.devteam.command.CommandAction;
import by.htp.devteam.command.user.OrderAddMessageAction;
import by.htp.devteam.controller.Page;

public final class EmployeeAddMessageAction implements CommandAction{
	
	/** Logger */
	private static final Logger logger = LogManager.getLogger(OrderAddMessageAction.class.getName());

	public EmployeeAddMessageAction() {
		super();
	}
	
	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		UserVo userVO = (UserVo) session.getAttribute(SESSION_PARAM_USER);
		
		logger.info(MSG_LOGGER_EMPLOYEE_ADD_OK_MESSAGE, userVO.getUser().getLogin());
		
		request.setAttribute(REQUEST_PARAM_USER_SHOW_ADD_FORM_URI, PAGE_USER_SHOW_ADD_FORM_URI);
		request.setAttribute(REQUEST_PARAM_USER_ROLE_ENUM, UserRole.values());
		System.out.println("id=" + request.getAttribute(REQUEST_PARAM_EMPLOYEE_ID));
		request.setAttribute(REQUEST_PARAM_EMPLOYEE_ID, request.getParameter(REQUEST_PARAM_EMPLOYEE_ID));
		
		return new Page(PAGE_EMPLOYEE_ADD_MESSAGE);
	}
	
	
}
