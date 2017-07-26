package by.htp.devteam.command.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.devteam.bean.Employee;
import by.htp.devteam.bean.UserRole;
import by.htp.devteam.bean.vo.UserVo;
import by.htp.devteam.command.CommandAction;
import by.htp.devteam.controller.Page;
import by.htp.devteam.service.EmployeeService;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.ServiceFactory;

import static by.htp.devteam.command.util.ConstantValue.*;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class UserShowAddFormAction implements CommandAction{

	/** Logger */
	private static final Logger logger = LogManager.getLogger(UserShowAddFormAction.class.getName());
	
	public UserShowAddFormAction() {
		super();
	}
	
	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) {
		logging(request);
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		EmployeeService employeeService = serviceFactory.getEmployeeService();
		String employeeId = request.getParameter(REQUEST_PARAM_EMPLOYEE_ID);
		try {
			Employee employee = employeeService.getById(employeeId);
			request.setAttribute(REQUEST_PARAM_EMPLOYEE_NAME, employee.getName());
			request.setAttribute(REQUEST_PARAM_EMPLOYEE_ID, employee.getId());
			request.setAttribute(REQUEST_PARAM_USER_ROLE, UserRole.values());
		} catch (ServiceException e) {
			request.setAttribute(REQUEST_PARAM_ERROR_CODE, e.getErrorCode().getValue());
		}
		
		return new Page(PAGE_USER_ADD);
	}
	
	private void logging(HttpServletRequest request ) {
		HttpSession session = request.getSession(false);
		UserVo userVO = (UserVo) session.getAttribute(SESSION_PARAM_USER);
		
		logger.info(MSG_LOGGER_EMPLOYEE_SHOW_ADD_FORM, userVO.getUser().getLogin());
	}

}
