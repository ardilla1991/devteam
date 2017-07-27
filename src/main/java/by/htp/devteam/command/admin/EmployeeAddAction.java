package by.htp.devteam.command.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.devteam.bean.vo.UserVo;
import by.htp.devteam.command.CommandAction;
import by.htp.devteam.controller.Page;
import by.htp.devteam.service.EmployeeService;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.ServiceFactory;

import static by.htp.devteam.command.util.ConstantValue.*;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Action add employee.
 * Logging information about who does action
 * @author julia
 *
 */
public final class EmployeeAddAction implements CommandAction{

	/** Logger */
	private static final Logger logger = LogManager.getLogger(EmployeeAddAction.class.getName());
	
	public EmployeeAddAction() {
		super();
	}
	
	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) {
		logging(request);
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		EmployeeService employeeService = serviceFactory.getEmployeeService();
		
		String page = PAGE_EMPLOYEE_ADD_MESSAGE_URI;
		boolean isRedirect = true;
		String name = request.getParameter(REQUEST_PARAM_EMPLOYEE_NAME);
		String startWork = request.getParameter(REQUEST_PARAM_EMPLOYEE_START_WORK);
		String qualification = request.getParameter(REQUEST_PARAM_EMPLOYEE_QUALIFICATION);
		
		try {
			employeeService.add(name, startWork, qualification);
		} catch (ServiceException e) {
			request.setAttribute(REQUEST_PARAM_ERROR_CODE, e.getErrorCode().getValue());
			request.setAttribute(REQUEST_PARAM_ERROR_FIELD, e.getFields());
			request.setAttribute(REQUEST_PARAM_EMPLOYEE_NAME, name);
			request.setAttribute(REQUEST_PARAM_EMPLOYEE_START_WORK, startWork);
			request.setAttribute(REQUEST_PARAM_EMPLOYEE_QUALIFICATION, qualification);
			
			page = PAGE_EMPLOYEE_SHOW_ADD_FORM_URI;
			isRedirect = false;
		}
		return new Page(page, isRedirect);
	}
	
	private void logging(HttpServletRequest request ) {
		HttpSession session = request.getSession(false);
		UserVo userVO = (UserVo) session.getAttribute(SESSION_PARAM_USER);
		
		logger.info(MSG_LOGGER_ADD_EMPLOYEE, userVO.getUser().getLogin());
	}

}
