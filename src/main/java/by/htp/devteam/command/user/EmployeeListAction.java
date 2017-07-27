package by.htp.devteam.command.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.devteam.bean.vo.EmployeeListVo;
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
 * Action for getting all employees. 
 * Logging information about who does action
 * @author julia
 *
 */
public class EmployeeListAction implements CommandAction {

	/** Logger */
	private static final Logger logger = LogManager.getLogger(EmployeeListAction.class.getName());
	
	public EmployeeListAction() {
		super();
	}
	
	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) {
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		EmployeeService employeeService = serviceFactory.getEmployeeService();
		
		HttpSession session = request.getSession(false);
		UserVo userVO = (UserVo) session.getAttribute(SESSION_PARAM_USER);
		
		logger.info(MSG_LOGGER_USER_LIST, userVO.getUser().getLogin());
		
		String currPage = request.getParameter(REQUEST_PARAM_PAGE);
		try {
			EmployeeListVo employeeList = employeeService.fetchAll(currPage);
			
			request.setAttribute(REQUEST_PARAM_URI, PAGE_EMPLOYEE_LIST_URI);
			request.setAttribute(REQUEST_PARAM_EMPLOYEE_LIST, employeeList);
		} catch (ServiceException e) {
			request.setAttribute(REQUEST_PARAM_ERROR_CODE, e.getErrorCode().getValue());
		}
		
		return new Page(PAGE_EMPLOYEE_LIST);
	}

	

}
